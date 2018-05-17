package com.org.news.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.org.news.R;
import com.org.news.fragment.NewsDetailFragment;
import com.org.news.fragment.NewsHeadlineFragment;
import com.org.news.iface.HomeListener;
import com.org.news.model.Article;
import com.org.news.network.event.NewsSearchEvent;

import org.greenrobot.eventbus.EventBus;

public class HomeActivity extends AppCompatActivity implements HomeListener {

    private static final String TAG = HomeActivity.class.getCanonicalName();

    private EventBus eventBus = EventBus.getDefault();
    private Context context =null;
    private Menu menu = null;
    private ActionBar actionBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context=this;
        actionBar = ((AppCompatActivity)this).getSupportActionBar();

        loadNewsHeadLinesFragment();
    }


    private void loadNewsHeadLinesFragment(){

        setTittle(context.getString(R.string.news_head_line));
        changeFragment(NewsHeadlineFragment.newInstance());
    }


    private void setTittle(final String tittle){
        if(actionBar!=null)
           actionBar.setTitle(tittle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
         this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_search:
                menuSearch(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void menuSearch(MenuItem item){

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(this.getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                eventBus.post(new NewsSearchEvent(""));
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                eventBus.post(new NewsSearchEvent(newText));
                return true;
            }
        });
    }

    /**
     * @param pFragment
     */
    public void changeFragment(Fragment pFragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, pFragment, pFragment.getClass().getCanonicalName());
        transaction.commit();

    }


    /**
     * @param pFragment
     */
    public void changeChildFragmentFragment(Fragment pFragment) {

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.content_frame, pFragment);
        trans.addToBackStack(pFragment.getClass().getCanonicalName());
        trans.commit();
    }

    @Override
    public void showNewsDetail(Article article) {

        if(menu!=null){
            menu.getItem(0).setVisible(false);
        }
        changeChildFragmentFragment(NewsDetailFragment.newInstance(article));
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()<1)
            if(menu!=null){
                menu.getItem(0).setVisible(true);
            }

        super.onBackPressed();

    }
}
