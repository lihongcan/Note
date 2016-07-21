package com.lihongcan.note.activity;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lihongcan.note.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_bottom_navigation_bar)
public class BottomNavigationBarActivity extends BaseActivity {

    /**
     * 底部导航
     */
    @ViewInject(R.id.bottom_navigation_bar_container)
    BottomNavigationBar bottomNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBottomBar();
    }

    private void initBottomBar() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor("#ECECEC");
        //数字标识
        BadgeItem item=new BadgeItem()
                    .setBackgroundColorResource(R.color.blue)
                    .setText("5")
                .setHideOnSelect(false);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "位置").setActiveColorResource(R.color.orange))
                .addItem(new BottomNavigationItem(R.drawable.ic_find_replace_white_24dp, "发现").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "爱好").setActiveColorResource(R.color.brown))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "图书").setActiveColorResource(R.color.teal))
                .setFirstSelectedPosition(0)
                .initialise();
    }
}
