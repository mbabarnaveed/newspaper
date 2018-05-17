package com.org.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.org.news.R;
import com.org.news.model.Article;
import com.org.news.model.Source;
import com.org.news.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by babar on 5/17/2018.
 */

public class NewsDetailFragment extends BaseFragment {

    private static final String TAG = NewsDetailFragment.class.getCanonicalName();

    private static final String ARG_ARTICLE = "arg_article";

    private Context localContext = null;
    private Unbinder unbinder;
    private View rootView = null;

    @BindView(R.id.tittleTV)
    public TextView newsTittleTV = null;

    @BindView(R.id.authorTV)
    public TextView authorTV = null;

    @BindView(R.id.sourceTV)
    public TextView newsSourceTV = null;

    @BindView(R.id.descriptionTV)
    public TextView newsDescriptionTV = null;

    @BindView(R.id.newsImageView)
    public ImageView newsImageView = null;


    private Article article = null;

    /**
     *
     * @param article
     * @return
     */
    public  static Fragment newInstance(final Article article){
        NewsDetailFragment fragment = new NewsDetailFragment();

         Bundle bundle = new Bundle();
         bundle.putParcelable(ARG_ARTICLE,article);

         fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle =getArguments();

        if(bundle!=null){
            article = bundle.getParcelable(ARG_ARTICLE);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        localContext = container.getContext();
        rootView= inflater.inflate(R.layout.fragment_news_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateNewsData();

    }

    private void populateNewsData(){

        try{
            newsTittleTV.setText(article.title);
            authorTV.setText(article.author);

            Source source = article.source;

            if(source!=null)
                newsSourceTV.setText(source.name);

            if(source!=null)
                newsDescriptionTV.setText(article.description);

            Glide.with(localContext)
                    .load(article.urlToImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_news_paper_place_holder)
                    .error(R.drawable.ic_news_paper_place_holder)
                    .centerCrop()
                    .into(newsImageView);

        }catch (Exception ex){
            LogUtils.error(TAG,"Error while showing news data :- " + ex.getMessage() );
        }
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
    }
}
