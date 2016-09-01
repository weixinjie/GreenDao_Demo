package com.will.greendao_demo;

import android.app.Application;
import android.util.Log;

import org.greenrobot.greendao.database.Database;


/**
 * Created by will on 16/8/31.
 */

public class BaseApp extends Application {

    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        String path = getDatabasePath("nodes-db").getAbsolutePath();
        Log.e("------path", path);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
