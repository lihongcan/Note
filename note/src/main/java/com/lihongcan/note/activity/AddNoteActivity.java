package com.lihongcan.note.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lihongcan.note.R;
import com.lihongcan.note.bean.Note;
import com.lihongcan.note.db.DbConfig;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;

@ContentView(R.layout.activity_add_note)
public class AddNoteActivity extends BaseActivity {
    @ViewInject(R.id.title)
    TextView tv_title;
    @ViewInject(R.id.content)
    EditText et_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(tv_title.getText())){
                        return;
                    }
                    if (TextUtils.isEmpty(et_content.getText())){
                        return;
                    }
                    String title=tv_title.getText().toString();
                    String content=et_content.getText().toString();
                    Note note=new Note();
                    note.setContent(content);
                    note.setTitle(title);
                    note.setCreateTime(System.currentTimeMillis());
                    DbManager manager=org.xutils.x.getDb(DbConfig.getDbConfig());
                    try {
                        manager.saveOrUpdate(note);
                        Toast.makeText(AddNoteActivity.this,"ok",Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (DbException e) {
                        e.printStackTrace();
                        Toast.makeText(AddNoteActivity.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
