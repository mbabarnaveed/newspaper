package com.org.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.org.news.R;
import com.org.news.iface.OnItemClickListener;
import com.org.news.iface.OnLongItemClickListener;
import com.org.news.model.Article;
import com.org.news.model.Source;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */

public class NewsHeadlineAdapter extends RecyclerView.Adapter<NewsHeadlineAdapter.ViewHolder> implements Filterable {


    private static final String TAG = NewsHeadlineAdapter.class.getCanonicalName();

    private Context mContext = null;
    public  List<Article> articles = null;
    private LayoutInflater inflater = null;
    private OnItemClickListener mOnItemClickListener;
    public  List<Article> filterObject;
    public  ValueFilter valueFilter;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {


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





        public ViewHolder(View v, int viewType) {
            super(v);

            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    /**
     * @param position
     * @param article
     */
    public void add(int position, Article article) {
        articles.add(position, article);
        notifyItemInserted(position);
    }

    /**
     * @param item
     */
    public void remove(String item) {
        int position = articles.indexOf(item);
        articles.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * @param pContext
     * @param pArticles
     */
    public NewsHeadlineAdapter(final Context pContext,
                               final List<Article> pArticles) {
        mContext = pContext;
        articles = pArticles;
        filterObject = pArticles;
        inflater = LayoutInflater.from(pContext);
    }

    @Override
    public NewsHeadlineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteme_news_headline,
                parent, false);

        ViewHolder vh = new ViewHolder(v, viewType);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final Article article = articles.get(position);




        holder.newsTittleTV.setText(article.title);
        holder.authorTV.setText(article.author);

        Source source = article.source;

        if(source!=null)
        holder.newsSourceTV.setText(source.name);

        if(source!=null)
         holder.newsDescriptionTV.setText(article.description);

        Glide.with(mContext)
                .load(article.urlToImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_news_paper_place_holder)
                .error(R.drawable.ic_news_paper_place_holder)
                .centerCrop()
                .into(holder.newsImageView);

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            String value = constraint.toString();

            if (value != null && value.length() > 0) {
                List<Article> filterList = new ArrayList<Article>();
                for (int i = 0; i < filterObject.size(); i++) {

                    String compareValue = filterObject.get(i).title.trim();
                    if ((compareValue.toUpperCase())
                            .contains(value.toString().toUpperCase())) {
                        filterList.add(filterObject.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = filterObject.size();
                results.values = filterObject;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            articles = (List<Article>) results.values;
            notifyDataSetChanged();
        }

    }
    public void setItemClickListener(final OnItemClickListener pOnItemClickListener) {
        mOnItemClickListener = pOnItemClickListener;
    }


}
