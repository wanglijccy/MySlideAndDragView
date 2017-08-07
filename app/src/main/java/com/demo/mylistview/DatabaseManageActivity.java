package com.demo.mylistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class DatabaseManageActivity extends AppCompatActivity {

    private ArrayList<PointBean> mDatas;
    private PointDragListView mPointDragView;
    private DescriptionDragListView mDescriptionDragView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPointDragView = (PointDragListView) findViewById(R.id.point_drag_view);

        mDescriptionDragView = (DescriptionDragListView) findViewById(R.id.desc_drag_view);

        mPointDragView.setOnPointItemClickListener(mDescriptionDragView);

        mPointDragView.setOnPointItemDeleteListener(mDescriptionDragView);

        mDatas = new ArrayList<>();

        initPointData2Database();

        mDatas.addAll(DatabaseUtil.getInstance().queryAllPointsByMapId(this, new Long(5)));

        mPointDragView.notifyData(mDatas);
    }


    public void initPointData2Database() {
        for (int i = 0; i < 38; i++) {
            PointBean pointBean = new PointBean();
            if (i < 2) {
                pointBean.map_id = 0;
            } else if (i > 1 && i < 4) {
                pointBean.map_id = 1;
            } else if (i > 3 && i < 7) {
                pointBean.map_id = 2;
            } else if (i > 6 && i < 11) {
                pointBean.map_id = 3;
            } else if (i > 10 && i < 15) {
                pointBean.map_id = 4;
            } else if (i > 14 && i < 20) {
                pointBean.map_id = 5;
            } else if (i > 19 && i < 29) {
                pointBean.map_id = 6;
            } else if (i > 28) {
                pointBean.map_id = 7;
            }
            pointBean.position = i;
            pointBean.position_x = i;
            pointBean.position_y = i;
            pointBean.position_z = i;
            pointBean.pointName = "名字" + i;
            pointBean.id = null;
            DatabaseUtil.getInstance().addPoint(this, pointBean);

            initDescData2Database(new Long(i + 1),pointBean.map_id);
        }
    }

    public void initDescData2Database(Long pointId,int map_id) {

        int point_id = pointId.intValue();
        //TODO 1：利用ID 从数据库中查找相关ID 的语言集合
        DescriptionBean bean ;
        if (point_id == 8) {
            for (int i = 0; i < 3; i++) {
                bean = new DescriptionBean();
                bean.id = null;
                bean.position = i;
                bean.map_id = map_id;
                bean.description = "点" + point_id + "你好：" + i;
                bean.point_id = pointId;
                DatabaseUtil.getInstance().addDescription(this, bean);
            }
        } else if (point_id == 1) {
            for (int j = 0; j < 4; j++) {
                bean = new DescriptionBean();
                bean.id = null;
                bean.position = j;
                bean.map_id = map_id;
                bean.description = "点" + point_id + "你好：" + j;
                bean.point_id = pointId;
                DatabaseUtil.getInstance().addDescription(this, bean);
            }
        } else if (point_id == 2) {
            for (int j = 0; j < 5; j++) {
                bean = new DescriptionBean();
                bean.id = null;
                bean.position = j;
                bean.map_id = map_id;
                bean.description = "点" + point_id + "你好：" + j;
                bean.point_id = pointId;
                DatabaseUtil.getInstance().addDescription(this, bean);
            }
        } else if (point_id == 3) {
            for (int j = 0; j < 6; j++) {
                bean = new DescriptionBean();
                bean.id = null;
                bean.position = j;
                bean.map_id = map_id;
                bean.description = "点" + point_id + "你好：" + j;
                bean.point_id = pointId;
            }
        } else if (point_id == 4) {
            for (int j = 0; j < 7; j++) {
                bean = new DescriptionBean();
                bean.id = null;
                bean.position = j;
                bean.map_id = map_id;
                bean.description = "点" + point_id + "你好：" + j;
                bean.point_id = pointId;
                DatabaseUtil.getInstance().addDescription(this, bean);
            }
        } else if (point_id == 5) {
            for (int j = 0; j < 1; j++) {
                bean = new DescriptionBean();
                bean.id = null;
                bean.position = j;
                bean.map_id = map_id;
                bean.description = "点" + point_id + "你好：" + j;
                bean.point_id = pointId;
                DatabaseUtil.getInstance().addDescription(this, bean);
            }
        } else if (point_id == 6) {
            for (int j = 0; j < 2; j++) {
                bean = new DescriptionBean();
                bean.id = null;
                bean.position = j;
                bean.map_id = map_id;
                bean.description = "点" + point_id + "你好：" + j;
                bean.point_id = pointId;
                DatabaseUtil.getInstance().addDescription(this, bean);
            }
        }else if (point_id == 7) {
            for (int j = 0; j < 2; j++) {
                bean = new DescriptionBean();
                bean.id = null;
                bean.position = j;
                bean.map_id = map_id;
                bean.description = "点" + point_id + "你好：" + j;
                bean.point_id = pointId;
                DatabaseUtil.getInstance().addDescription(this, bean);
            }
        }else{
            for (int i = 0; i < 6; i++) {
                bean = new DescriptionBean();
                bean.id = null;
                bean.position = 0;
                bean.map_id = map_id;
                bean.description = "点" + point_id + "你好：" + 0;
                bean.point_id = pointId;
                DatabaseUtil.getInstance().addDescription(this, bean);
            }
        }
    }
}
