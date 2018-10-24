package com.cc.notes.model;

public class UserInfo {

    /**
     * id : 1
     * Nickname : jaky
     * Portrait : jaky
     * Manifesto : 傻逼你好
     */

    private String id;
    private String Nickname;
    private String Portrait;
    private String Manifesto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public String getPortrait() {
        return Portrait;
    }

    public void setPortrait(String Portrait) {
        this.Portrait = Portrait;
    }

    public String getManifesto() {
        return Manifesto;
    }

    public void setManifesto(String Manifesto) {
        this.Manifesto = Manifesto;
    }
}
