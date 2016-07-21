package com.lihongcan.note.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lihongcan.note.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongcan on 2016/7/11.
 */
public class MyRecyclerViewTestAdapter extends RecyclerView.Adapter {
    /**
     * HEADER
     */
    public static final int TYPE_HEADER=0;
    /**
     * NORMAL ITEM
     */
    public static final int TYPE_NORMAL=1;
    /**
     * data for recyclerView
     */
    private List<String> mDates =new ArrayList<>();
    /**
     * header view
     */
    private View headerView;
    private onItemClickListener listener;

    public onItemClickListener getListener() {
        return listener;
    }

    public List<String> getmDates() {
        return mDates;
    }

    public void setmDates(List<String> mDates) {
        this.mDates = mDates;
    }

    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public View getHeaderView() {
        return headerView;
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }
    public void addDates(ArrayList<String> newDates){
        if(null!=newDates&&newDates.size()>0){
            mDates.addAll(newDates);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getItemViewType(int position) {
        //如果headerView是空，则视为没有header
        if (null==headerView) return TYPE_NORMAL;
        if (position==0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (headerView!=null&&viewType==TYPE_HEADER) return new MyViewHolder(headerView);
        View item= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view_test,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //如果是header的位置，直接返回，不绑定
        if (getItemViewType(position)==TYPE_HEADER) return;
        final int pos=getRealPosition(holder);
        final String data=mDates.get(pos);
        if (holder instanceof MyViewHolder){
            ((MyViewHolder) holder).itemInfo.setText(data);
            //设置item点击事件
            if (listener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(pos,data);
                    }
                });
            }
        }

    }
    public int getRealPosition(RecyclerView.ViewHolder holder){
        int pos=holder.getAdapterPosition();
        return headerView==null?pos:pos-1;
    }
    @Override
    public int getItemCount() {
        //如果有header  則加1
        return null==headerView?mDates.size():mDates.size()+1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView itemInfo;
        public MyViewHolder(View itemView) {
            super(itemView);
            if (itemView==headerView) return;
            itemInfo= (TextView) itemView.findViewById(R.id.item_recycler_view_test_info);
        }
    }
    public interface onItemClickListener{
        void onItemClick(int pos,String data);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager=recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager= (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position)==TYPE_HEADER?gridLayoutManager.getSpanCount():1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp=holder.itemView.getLayoutParams();
        if (lp!=null&&lp instanceof StaggeredGridLayoutManager.LayoutParams){
            ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(holder.getAdapterPosition()==0);
        }
    }
}
