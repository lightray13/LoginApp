package com.login.app.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {

    @SerializedName("ListUsers")
    @Expose
    private List<ListUser> listUsers = null;

    @SerializedName("CurrentUid")
    @Expose
    private String currentUid;

    public List<ListUser> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<ListUser> listUsers) {
        this.listUsers = listUsers;
    }

    public String getCurrentUid() {
        return currentUid;
    }

    public void setCurrentUid(String currentUid) {
        this.currentUid = currentUid;
    }
}
