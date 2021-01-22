package org.framework.hsven.utils.view;

import com.fasterxml.jackson.annotation.JsonView;

public class DaoEntity {
    private String entity;
    private String group;
    @JsonView({IdView.class, DicView.class})
    private Integer id;
    @JsonView({TextView.class, DicView.class})
    private String txt;
    @JsonView(SecrecyView.class)
    private String secrecy;

    public static DaoEntity of() {
        return new DaoEntity();
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getSecrecy() {
        return secrecy;
    }

    public void setSecrecy(String secrecy) {
        this.secrecy = secrecy;
    }
}
