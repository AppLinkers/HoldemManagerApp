package com.example.server_test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private long id;
    private String user_id;
    private String user_name;
    private String user_pass;

    public String getUser_locate() {
        return user_locate;
    }

    public void setUser_locate(String user_locate) {
        this.user_locate = user_locate;
    }

    public String getUser_phoneNum() {
        return user_phoneNum;
    }

    public void setUser_phoneNum(String user_phoneNum) {
        this.user_phoneNum = user_phoneNum;
    }

    private String user_locate;
    private String user_phoneNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
