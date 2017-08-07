package com.demo.mylistview;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by passion on 2017/7/21.
 */
@Entity
public class DescriptionBean {

    @Id
    public Long id;

    public int position;

    public int map_id;

    public Long point_id;

    public String description;

    @Transient
    public PointBean pointBean;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPoint_id() {
        return this.point_id;
    }

    public void setPoint_id(Long point_id) {
        this.point_id = point_id;
    }

    public int getMap_id() {
        return this.map_id;
    }

    public void setMap_id(int map_id) {
        this.map_id = map_id;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Keep
    public DescriptionBean(Long id, int position, int map_id, Long point_id,
            String description) {
        this.id = id;
        this.position = position;
        this.map_id = map_id;
        this.point_id = point_id;
        this.description = description;
    }

    @Keep
    public DescriptionBean() {
    }
}
