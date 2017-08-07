package com.demo.mylistview;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;

/**
 * Created by passion on 2017/7/21.
 */
@Entity
public class PointBean {


    @Id
    public Long id;//De

    public int map_id;

    public String pointName;

    public float position_x;

    public float position_y;

    public float position_z;

    public int position;

    @ToMany(referencedJoinProperty = "point_id")
    @Transient
    public List<DescriptionBean> descriptionBeanList;

    public float getPosition_z() {
        return this.position_z;
    }

    public void setPosition_z(float position_z) {
        this.position_z = position_z;
    }

    public float getPosition_y() {
        return this.position_y;
    }

    public void setPosition_y(float position_y) {
        this.position_y = position_y;
    }

    public float getPosition_x() {
        return this.position_x;
    }

    public void setPosition_x(float position_x) {
        this.position_x = position_x;
    }

    public int getMap_id() {
        return this.map_id;
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPointName() {
        return this.pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Keep
    public PointBean(String pointName, Long id, int map_id,
            float position_x, float position_y, float position_z) {
        this.pointName = pointName;
        this.id = id;
        this.map_id = map_id;
        this.position_x = position_x;
        this.position_y = position_y;
        this.position_z = position_z;
    }

    @Keep
    public PointBean() {
    }

    @Generated(hash = 1124562027)
    public PointBean(Long id, int map_id, String pointName, float position_x,
            float position_y, float position_z, int position) {
        this.id = id;
        this.map_id = map_id;
        this.pointName = pointName;
        this.position_x = position_x;
        this.position_y = position_y;
        this.position_z = position_z;
        this.position = position;
    }
}
