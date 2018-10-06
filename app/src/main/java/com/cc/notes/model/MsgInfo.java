package com.cc.notes.model;

import java.io.Serializable;

public class MsgInfo implements Serializable {

    private String content;

    private TYPE type;

    private  String me;

    private String receiver;

    private String isonline;

    private String sendtime;

    private byte[] picture;

    private String filetype;

    public MsgInfo(String content, TYPE type, String me, String receiver, String isonline, String sendtime, byte[] picture, String filetype){
        this.content = content;
        this.type = type;
        this.me=me;
        this.receiver=receiver;
        this.isonline=isonline;
        this.sendtime=sendtime;
        this.picture=picture;
        this.filetype=filetype;
    }

    public String getFiletype() { return filetype; }

    public void setFiletype(String filetype) { this.filetype = filetype; }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getIsonline() {
        return isonline;
    }

    public void setIsonline(String isonline) {
        this.isonline = isonline;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getMe() {
        return me;
    }

    public void setMe(String me) {
        this.me = me;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public enum TYPE{RECEIVED, SENT}

    public TYPE getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
