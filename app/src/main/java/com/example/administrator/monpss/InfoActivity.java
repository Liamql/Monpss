package com.example.administrator.monpss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/6/13 0013.
 */
public class InfoActivity extends Activity {

    private ImageView btn_back;
    private ImageView iv_avatar;
    private TextView tv_sex;
    private TextView tv_name;
    private TextView tv_sign;

    private RelativeLayout re_avatar;
    private RelativeLayout re_name;
    private RelativeLayout re_password;
    private RelativeLayout re_sex;
    private RelativeLayout re_ins;
    private RelativeLayout del_user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);

        btn_back = (ImageView) findViewById(R.id.iv_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_avatar = (ImageView) findViewById(R.id.iv_avatar);
        tv_sex = (TextView) this.findViewById(R.id.tv_sex);
        tv_sign = (TextView) this.findViewById(R.id.tv_sign);


    }

    class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.re_avatar:
                    //showPhotoDialog();
                    break;
                case R.id.re_name:
                    /*startActivityForResult(new Intent(InfoActivity.this,
                            UpdateNickActivity.class),UPDATE_NICK);*/
                    break;
                case R.id.re_password:
                    /*if (LocalUserInfo.getInstance(MyUserInfoActivity.this)
                            .getUserInfo("fxid").equals("0")) {
                        startActivityForResult(new Intent(MyUserInfoActivity.this,
                                UpdateFxidActivity.class),UPDATE_FXID);

                    }*/
                    break;
                case R.id.re_sex:
                    //showSexDialog();
                    break;
                case R.id.del_user:

                    break;

            }
        }

    }
}
