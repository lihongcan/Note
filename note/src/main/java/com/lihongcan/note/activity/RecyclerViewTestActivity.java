package com.lihongcan.note.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.lihongcan.note.R;
import com.lihongcan.note.adapter.MyRecyclerViewTestAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_recycler_view_test)
public class RecyclerViewTestActivity extends BaseActivity {
    @ViewInject(R.id.recycler_test)
    private RecyclerView recyclerView;

    private List<String> mDates;

    private RecyclerView.Adapter adapter;

    private RecyclerView.LayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initRecyclerView();
    }

    /**
     * recyclerView的初始化
     */
    private void initRecyclerView() {
        //布局管理器--LinearLayoutManager
        //manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        //布局管理器--GridLayoutManager
        //manager=new GridLayoutManager(this,2);
        //布局管理器
        manager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        //item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置adapter
        adapter=new MyRecyclerViewTestAdapter();
        recyclerView.setAdapter(adapter);
        //添加数据
        ((MyRecyclerViewTestAdapter)adapter).addDates((ArrayList<String>) mDates);
        //添加header
        View header= LayoutInflater.from(this).inflate(R.layout.header_recycler_view_test,recyclerView,false);
        ((MyRecyclerViewTestAdapter)adapter).setHeaderView(header);
        //点击事件
        ((MyRecyclerViewTestAdapter) adapter).setListener(listener);
    }
    private MyRecyclerViewTestAdapter.onItemClickListener listener=new MyRecyclerViewTestAdapter.onItemClickListener() {
        @Override
        public void onItemClick(int pos, String data) {
            Toast.makeText(RecyclerViewTestActivity.this,""+pos,Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 测试数据
     */
    private void initData() {
        mDates =new ArrayList<>();
        for (int i=0;i<20;i++){
            String temp="data_item_"+i;
            mDates.add(temp);
        }
    }
}
