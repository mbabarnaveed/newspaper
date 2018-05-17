package com.org.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.org.news.R;
import com.org.news.adapter.NewsHeadlineAdapter;
import com.org.news.iface.HomeListener;
import com.org.news.iface.OnItemClickListener;
import com.org.news.model.Article;
import com.org.news.network.controller.APIController;
import com.org.news.network.enums.ResponseStatus;
import com.org.news.network.event.NewsEvent;
import com.org.news.network.event.NewsSearchEvent;
import com.org.news.sharestorage.NewsPreference;
import com.org.news.utils.Loaders;
import com.org.news.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by babar on 5/17/2018.
 */

public class NewsHeadlineFragment extends BaseFragment {

    private static String TAG = NewsHeadlineFragment.class.getCanonicalName();

    private EventBus eventBus = EventBus.getDefault();

    private Context localContext = null;
    private Unbinder unbinder;
    private View rootView = null;

    @BindView(R.id.swipeContainer)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    public RecyclerView mRecyclerView = null;

    private NewsPreference newsPreference = null;
    private HomeListener homeListener =null;
    private LinearLayoutManager mLayoutManager = null;
    private NewsHeadlineAdapter mNewsHeadlineAdapter = null;


    private List<Article> articles = new ArrayList<Article>();

    public  static Fragment newInstance(){
        NewsHeadlineFragment fragment = new NewsHeadlineFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventBus.register(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            homeListener = (HomeListener)context;
        }catch (Exception ex){
            LogUtils.error(TAG, "Error while attaching listener :- " + ex.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        localContext = container.getContext();
        rootView= inflater.inflate(R.layout.fragment_headline, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(localContext,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        newsPreference = NewsPreference.getInstance(localContext);
        pullToRefreshProcess();
        newsHeadlineAdapter();
    }


    /**
     *
     */
    private void newsHeadlineAdapter(){

        articles.clear();

        mNewsHeadlineAdapter = new NewsHeadlineAdapter(localContext,articles);
        mRecyclerView.setAdapter(mNewsHeadlineAdapter);
        mNewsHeadlineAdapter.notifyDataSetChanged();
        mNewsHeadlineAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Article article = articles.get(position);
                if(homeListener!=null)
                    homeListener.showNewsDetail(article);
            }
        });
        loadNewsFromCache();
    }

    /**
     *
     */
    private void pullToRefreshProcess(){
        try{
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fetchLatestNews();
                }
            });

        }catch (Exception e){
            swipeRefreshLayout.setRefreshing(false);
            LogUtils.error(TAG,"Error during pull to refresh and error:- " + e.getMessage());
        }
    }

    /**
     * Note:- we putting here hard code country and category
     */
    private void fetchLatestNews(){

        try{
            APIController.getInstance().topHeadLines("us","business");
        }catch (Exception ex){
            LogUtils.debug(TAG,"Error while fetching top headline :- " + ex.getMessage());
            swipeRefreshLayout.setRefreshing(false);
            Loaders.cancelProgressDialog();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(articles.size()<1)
         Loaders.showProgressDialog(localContext,localContext.getString(R.string.loader_wait));

        fetchLatestNews();
    }
    ///mMultiSelectorAdapter.getFilter().filter(newText);
    @Subscribe
    public void onEvent(NewsEvent event){

        try{
            if(event.status.equalsIgnoreCase(ResponseStatus.OK.value())){

                if(event.articles!=null){
                    articles.clear();
                    articles.addAll(event.articles);
                    mNewsHeadlineAdapter.notifyDataSetChanged();
                }else{
                    loadNewsFromCache();
                }

            }else if(event.status.equalsIgnoreCase(ResponseStatus.FAIL.value())){

            }else{
                // in Case of any error
                loadNewsFromCache();
            }
        }catch (Exception ex){
            LogUtils.debug(TAG,"Error while fetching top headline :- " + ex.getMessage());
        }finally {
            swipeRefreshLayout.setRefreshing(false);
            Loaders.cancelProgressDialog();
        }

    }


    @Subscribe
    public void onEvent(NewsSearchEvent event){
        mNewsHeadlineAdapter.getFilter().filter(event.string);
    }

    private void loadNewsFromCache(){
        articles.clear();
        articles.addAll(newsPreference.getNews());
        mNewsHeadlineAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        eventBus.unregister(this);
    }
}
