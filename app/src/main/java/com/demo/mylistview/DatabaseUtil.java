package com.demo.mylistview;

import android.content.Context;

import com.example.anonymous.greendao.gen.DescriptionBeanDao;
import com.example.anonymous.greendao.gen.PointBeanDao;

import java.util.List;

/**
 * Created by passion on 2017/7/22.
 */

public class DatabaseUtil {

    private PointBeanDao mPointDao;

    private DescriptionBeanDao mDescDao;

    private DatabaseUtil() {
    }

    private static class SingleDataHelper {
        static final DatabaseUtil INSTANCE = new DatabaseUtil();
    }

    public static DatabaseUtil getInstance() {
        return SingleDataHelper.INSTANCE;
    }


    public PointBeanDao getPointBeanDao(Context context) {
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        if (mPointDao == null) {
            mPointDao = ((BaseApplication) context.getApplicationContext()).getDaoSession().getPointBeanDao();
        }
        return mPointDao;
    }

    public DescriptionBeanDao getDescriptionBeanDao(Context context) {
        // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
        if (mDescDao == null) {
            mDescDao = ((BaseApplication) context.getApplicationContext()).getDaoSession().getDescriptionBeanDao();
        }
        return mDescDao;
    }

    public void addPoint(Context context, PointBean bean) {
        if (mPointDao == null && context != null) {
            getPointBeanDao(context);
        }
        if (mPointDao == null || bean == null) return;
        mPointDao.insert(bean);
    }

    public PointBean queryPointByKeyId(Context context, Long id) {
        if (mPointDao == null && context != null) {
            getPointBeanDao(context);
        }
        if (mPointDao == null || id == null) return null;
        PointBean bean = mPointDao.queryBuilder().where(PointBeanDao.Properties.Id.eq(id)).unique();
        return bean;
    }

    public void addDescription(Context context, DescriptionBean bean) {
        if (mDescDao == null && context != null) {
            getDescriptionBeanDao(context);
        }
        if (mDescDao == null || bean == null) return;
            mDescDao.insert(bean);
    }

    public DescriptionBean queryDescriptionByKeyId(Context context, Long id) {
        if (mDescDao == null && context != null) {
            getDescriptionBeanDao(context);
        }
        if (mDescDao == null || id == null) return null;
        DescriptionBean bean = mDescDao.queryBuilder().where(DescriptionBeanDao.Properties.Id.eq(id)).unique();
        return bean;
    }

    public void deletePointByKey(Context context, Long id) {
        if (mPointDao == null && context != null) {
            getPointBeanDao(context);
        }
        if (mPointDao == null || id == null) return;
        mPointDao.deleteByKey(id);
    }

    public void deletePointByEntify(Context context, PointBean entity) {
        if (mPointDao == null && context != null) {
            getPointBeanDao(context);
        }
        if (mPointDao == null || entity == null) return;
        mPointDao.delete(entity);
    }

    public void deleteAllPoint(Context context) {
        if (mPointDao == null && context != null) {
            getPointBeanDao(context);
        }
        if (mPointDao == null) return;
        mPointDao.deleteAll();
    }

    public void deleteDescriptionByKey(Context context, Long id) {
        if (mDescDao == null && context != null) {
            getDescriptionBeanDao(context);
        }
        if (mDescDao == null || id == null) return;
        mDescDao.deleteByKey(id);
    }

    public void deleteDescriptionByEntity(Context context, DescriptionBean entity) {
        if (mDescDao == null && context != null) {
            getDescriptionBeanDao(context);
        }
        if (entity == null || mDescDao == null) return;
        mDescDao.delete(entity);
    }

    public void deleteAllDescription(Context context) {
        if (mDescDao == null && context != null) {
            getDescriptionBeanDao(context);
        }
        if (mDescDao == null) return;
        mDescDao.deleteAll();
    }


    public List<PointBean> queryAllPointsByMapId(Context context, Long map_id) {
        if (mPointDao == null && context != null) {
            getPointBeanDao(context);
        }

        if (mPointDao == null || map_id == null) return null;
        List<PointBean> pointBeanList = mPointDao.queryBuilder().where(PointBeanDao.Properties.Map_id.eq(map_id)).orderAsc(PointBeanDao.Properties.Position).list();

        return pointBeanList;
    }

    public List<DescriptionBean> queryAllDescByPointId(Context context, Long point_id) {
        if (mDescDao == null && context != null) {
            getDescriptionBeanDao(context);
        }

        if (mDescDao == null) return null;
        List<DescriptionBean> descBeanList = mDescDao.queryBuilder().where(DescriptionBeanDao.Properties.Point_id.eq(point_id)).orderAsc(DescriptionBeanDao.Properties.Position).list();

        System.out.println("DatabaseUtil.queryDescsByPointId=========" + point_id + ":" + descBeanList.size());
        return descBeanList;
    }

    public List<DescriptionBean> queryAllDescByPointName(Context context, String point_name) {
        if (mDescDao == null && context != null) {
            getDescriptionBeanDao(context);
        }

        if (mDescDao == null) return null;
        List<DescriptionBean> descBeanList = mDescDao.queryBuilder().where(DescriptionBeanDao.Properties.Description.eq(point_name)).list();

        return descBeanList;
    }

    public void modifyPoint(Context context, PointBean pointBean) {
        if (mPointDao == null && context != null) {
            getPointBeanDao(context);
        }

        if (mPointDao == null || pointBean == null) return;
        mPointDao.update(pointBean);
    }

    public void modifyDescription(Context context, DescriptionBean descBean) {
        if (mDescDao == null && context != null) {
            getDescriptionBeanDao(context);
        }

        if (mDescDao == null || descBean == null) return;
        mDescDao.update(descBean);
    }

}
