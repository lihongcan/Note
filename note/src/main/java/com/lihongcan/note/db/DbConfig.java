package com.lihongcan.note.db;

import org.xutils.DbManager;

/**
 * Created by lihongcan on 2016/6/30.
 */
public class DbConfig {
    private static DbManager.DaoConfig config = new DbManager.DaoConfig().setDbName("note1.db")
            .setDbVersion(1)
            .setDbOpenListener(new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    // 开启WAL, 对写入加速提升巨大 会影响读取
                    //db.getDatabase().enableWriteAheadLogging();
                }
            });
    public static DbManager.DaoConfig getDbConfig(){
        return config;
    }
}
