package com.lihongcan.note.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lihongcan.note.R;
import com.lihongcan.note.bean.Note;
import com.lihongcan.note.db.DbConfig;
import com.lihongcan.note.utils.FileUtils;
import com.lihongcan.note.utils.MLog;
import com.lihongcan.note.utils.RecycleViewDivider;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG=MainActivity.class.getName();
    @ViewInject(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewInject(R.id.toolbar)
    Toolbar toolbar;
    @ViewInject(R.id.fab)
    FloatingActionButton fab;
    /**
     * 底部导航
     */
    @ViewInject(R.id.bottom_navigation_bar_container)
    BottomNavigationBar bottomNavigationBar;
    @ViewInject(R.id.note_list)
    private RecyclerView noteList;
    private List<Note> localNote = new ArrayList<>();
    private NoteListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        initView();
        logSomeInfo();
        initBottomBar();
    }

    private void logSomeInfo() {
        int meSize= (int) (Runtime.getRuntime().maxMemory()/1024/1024);
        MLog.e(TAG,"maxMemory=="+meSize+"M");
        MLog.e(TAG, FileUtils.getDiskCachePath(this));
    }

    private void initView() {
        fab.setOnClickListener(fabListener);
        noteList.setLayoutManager(new LinearLayoutManager(this));
//        noteList.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL));
        adapter = new NoteListAdapter();
        noteList.setAdapter(adapter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
//            startActivity(intent);
            showAlertDialog();
        }
    };

    private void showAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        AlertDialog d=builder.setTitle("test").setMessage("message").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        d.show();
    }

    private void initBottomBar() {
        //设置模式
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "位置").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_find_replace_white_24dp, "发现").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "爱好").setActiveColorResource(R.color.brown))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "图书").setActiveColorResource(R.color.teal))
                .setFirstSelectedPosition(0)
                .initialise();
    }
    @Override
    protected void onResume() {
        super.onResume();
        localNote.removeAll(localNote);
        x.task().run(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final DbManager dbManager = x.getDb(DbConfig.getDbConfig());

            try {
                List<Note> list = dbManager.selector(Note.class).orderBy("createTime",true).findAll();
                if (list != null && list.size() > 0) localNote.addAll(list);
            } catch (DbException e) {
                e.printStackTrace();
            }
            x.task().post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }


    class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.MyViewHolder> {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_note_list, null));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.title.setText(localNote.get(position).getTitle());
            holder.createTime.setText(format.format(new Date(localNote.get(position).getCreateTime())));
            holder.content.setText(localNote.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return localNote.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView title, createTime, content;

            public MyViewHolder(View itemView) {
                super(itemView);
                title = (TextView) itemView.findViewById(R.id.item_note_list_title);
                createTime = (TextView) itemView.findViewById(R.id.item_note_list_create_time);
                content = (TextView) itemView.findViewById(R.id.item_note_list_content);
            }
        }
    }
}
