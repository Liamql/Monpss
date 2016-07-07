package com.example.administrator.monpss;

import android.os.Handler;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class ThreadUtils {

    /**
     * 运行在子线程(发送数据)
     *
     */
    public static void runInSubThread(Runnable r) {
        new Thread(r).start();
    }

    private static Handler handler = new Handler();

    /**
     * 运行在主线程(UI 线程 更新界面)
     *
     */
    public static void runInUiThread(Runnable r) {
        handler.post(r);// Message-->handler.sendMessage-->handleMessage()
        // 主线程-->r.run();
    }
}
