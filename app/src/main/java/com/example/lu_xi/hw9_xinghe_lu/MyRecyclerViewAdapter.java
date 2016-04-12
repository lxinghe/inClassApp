package com.example.lu_xi.hw9_xinghe_lu;

/**
 * Created by lu_xi on 2/19/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private static List<Map<String, ?>> mDataset;
    private Context mContext;
    private static OnItemClickListener mItemClickListener;

    public MyRecyclerViewAdapter(Context myContext, List<Map<String, ?>> myDataset){
        mContext = myContext;
        mDataset = myDataset;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

         ImageView vIcon;
         TextView vTitle;
         TextView vDescription;
         CheckBox vCheckBox;
         ImageView vOverflowMenu;

        public ViewHolder(View v){
            super(v);
            vIcon = (ImageView)v.findViewById(R.id.movieIcon);
            vTitle = (TextView)v.findViewById(R.id.movieTitle);
            vDescription = (TextView)v.findViewById(R.id.movieDescription);
            vCheckBox = (CheckBox)v.findViewById(R.id.checkBox);
            vOverflowMenu = (ImageView)v.findViewById(R.id.pop_up_menu);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        Map<String, ?> movie = mDataset.get(getPosition());
                        mItemClickListener.onItemClick(v, getPosition(), (String) movie.get("name"));
                    }
                }
            });

            vOverflowMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        //Map<String, ?> movie = mDataset.get(getPosition());
                        mItemClickListener.onOverFlowMenuClick(v, getPosition());
                    }
                }
            });

            v.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    if (mItemClickListener != null) {
                        Map<String, ?> movie = mDataset.get(getPosition());
                        mItemClickListener.onItemLongClick(v, getPosition());
                    }
                    return true;
                }
            });
        }

        public void bindMovieData(Map<String,?> movie){

            vIcon.setImageResource((Integer) movie.get("image"));
            vDescription.setText((String) movie.get("description"));
            vTitle.setText((String) movie.get("name"));
        }

    }


    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String,?> movie = mDataset.get(position);
        holder.bindMovieData(movie);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position, String movieTitle);
        void onItemLongClick(View view, int position);
        void onOverFlowMenuClick(View v, int position);
    }

}
