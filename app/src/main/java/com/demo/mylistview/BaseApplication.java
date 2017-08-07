package com.demo.mylistview;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.anonymous.greendao.gen.DaoMaster;
import com.example.anonymous.greendao.gen.DaoSession;

/**
 * Created by passion on 2017/7/22.
 */

public class BaseApplication extends Application{
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static BaseApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        init();
    }

    public static BaseApplication getInstances() {
        return instances;
    }

    public void init() {
        //数据库的配置
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(instances, "navigation_db", null);
        db = devOpenHelper.getWritableDatabase();
        String path = db.getPath().toString();
        System.out.println("BaseApplication.init==========" + path);
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();

    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
