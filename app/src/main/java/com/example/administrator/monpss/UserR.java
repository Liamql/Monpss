package com.example.administrator.monpss;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class UserR {
    private String user_id;
    private String password;
    private String command_type;

    public UserR(String cmd, String id, String pass) {
        super();
        this.user_id = id;
        this.command_type = cmd;
        this.password = pass;
    }

    public UserR() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "Person [command_type=" + command_type + ", user_id=" + user_id + ", password=" + password + "]";
    }

    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }

    public String getPass() {
        return password;
    }

    public void setPass(String pass) {
        this.password = pass;
    }

    public String getCmd() {
        return command_type;
    }

    public void setCmd(String cmd) {
        this.command_type = cmd;
    }

}
