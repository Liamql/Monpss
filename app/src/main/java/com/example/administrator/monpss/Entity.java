package com.example.administrator.monpss;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class Entity {
    public String mainMission;
    public ArrayList<String> missonList;

    Entity(String mainMission,ArrayList<String> missonList)
    {
        this.mainMission = mainMission;
        this.missonList = missonList;
    }
    Entity(String mainMission)
    {
        this.mainMission = mainMission;
        this.missonList = new ArrayList<String>();
    }
}
