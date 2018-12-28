package com.zjh.administrat.greendaodemo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TextBean {
    @Id(autoincrement = true)
    private Long id;
    private String name;

    @Generated(hash = 2047704953)
    public TextBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 809912491)
    public TextBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }



}
