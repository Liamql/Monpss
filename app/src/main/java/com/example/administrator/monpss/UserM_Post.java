package com.example.administrator.monpss;

/**
 * Created by Administrator on 2016/6/22 0022.
 */
public class UserM_Post {
    private String user_id;
    private String mission_id;
    private String mission_name;
    private String password;
    private String father_id;
    private String father_name;
    private String mission_level;
    private String mission_detail = "null";
    private String remind_time = "null";
    private String remind_type = "0";
    private String remind_switch = "0";
    private String mission_status = "0";
    private String priority = "0";
    private String command_type;

    public UserM_Post(String cmd, String id, String pass, String mission_id, String mission_name, String father_id, String father_name, String mission_level) {
        super();
        this.user_id = id;
        this.command_type = cmd;
        this.mission_id = mission_id;
        this.password = pass;
        this.mission_name = mission_name;
        this.father_id = father_id;
        this.father_name = father_name;
        this.mission_level = mission_level;
    }

    public UserM_Post() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }

    public String getMission_name() {
        return mission_name;
    }

    public void setMission_name(String pass) {
        this.mission_name = mission_name;
    }

    public String getCmd() {
        return command_type;
    }

    public void setCmd(String cmd) {
        this.command_type = cmd;
    }

}

