package com.example.administrator.monpss;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class SplashActivity extends Activity {

    private AnimationDrawable animationDrawable;
    private ImageView imgPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final View view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        super.onCreate(savedInstanceState);
        imgPic = (ImageView) findViewById(R.id.logo_splash);
        imgPic.setImageResource(R.drawable.writing);
        animationDrawable = (AnimationDrawable) imgPic.getDrawable();
        animationDrawable.start();

        /*
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(1500);
        view.startAnimation(animation);
        */

    }

    /**
     * 在子线程中做一些操作，之后跳转到登录界面
     */
    @Override
    protected void onStart() {
        super.onStart();
        ThreadUtils.runInSubThread(new Runnable() {

            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                animationDrawable.stop();
                startActivity(new Intent(getApplicationContext(),
                        LoginActivity.class));
                finish();
            }
        });
    }
}
