package com.cc.notes.adapter;

import java.io.Serializable;

/**
 * Created by cap on 2018/5/29.
 */
public class SerializableMap implements Serializable {

    private long map;

    public long getMap(String s) {
        return map;
    }

    public void setMap(long map) {
        this.map = map;
    }


}
