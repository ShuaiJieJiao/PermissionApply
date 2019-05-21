package com.shuaijie.kotlinannotation;

import java.util.List;

public final class Data {

    private boolean isSeletor = false;
    private String id;
    private String name;
    private Object pid;
    private String type;
    private List<Data> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPid() {
        return pid;
    }

    public void setPid(Object pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Data> getChildren() {
        return children;
    }

    public void setChildren(List<Data> children) {
        this.children = children;
    }

    public boolean isSeletor() {
        return isSeletor;
    }

    public void setSeletor(boolean seletor) {
        isSeletor = seletor;
    }
}
