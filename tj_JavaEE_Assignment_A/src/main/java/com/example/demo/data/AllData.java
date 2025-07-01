package com.example.demo.data;

public class AllData {
    static public String pluginDirName="tj_localhistory";
    static public String jsonDirName="CodeHistory";

    //项目打开时的差异阈值
    static public int initializeThreshold = 5;
    //单次修改时的差异阈值
    static public int onceModifyThreshold = 10;
    //轮转调度时的差异阈值
    static public int schedulerThreshold = 1;
    //文件创建时的差异阈值
    static public int createThreshold = 1;
}
