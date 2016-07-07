package com.example.administrator.monpss;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class LoginActivity extends Activity {

    @ViewInject(R.id.account)
    private EditText account;
    @ViewInject(R.id.password)
    private EditText password;
    private String accountStr;// 账号
    private String passwordStr;// 密码

    ProgressDialog dialog;

    @ViewInject(R.id.btn_login)
    private Button btn_login;

    @ViewInject(R.id.btn_qtlogin)
    private Button btn_qtlogin;

    private String url = "http://114.215.121.244:8080/auth";
    private String result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);// 使注解生效
        dialog = new ProgressDialog(LoginActivity.this);

        account.addTextChangedListener(new TextChange());
        password.addTextChangedListener(new TextChange());

        getJson2();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 按钮的点击事件
     *
     * @param view
     */
    @OnClick(R.id.btn_login)
    public void login(View view) {
        // Toast.makeText(getBaseContext(), "haha", 0).show();

        dialog.setMessage("正在登录...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();


        accountStr = account.getText().toString().trim();
        passwordStr = password.getText().toString().trim();
        PreferencesUtil.setSharedStringData(this,"user_name",accountStr);
        PreferencesUtil.setSharedStringData(this,"password",passwordStr);

        check();

        // 获得输入的账号和密码，发送

        // 封装好Java对象，讲数据写到服务器,在子线程中运行
        ThreadUtils.runInSubThread(new Runnable() {

            public void run() {
                try {
                    Gson gson = new Gson();
                    UserR user = new UserR("user_login", accountStr, passwordStr);
                    String str = gson.toJson(user);
                    result = HttpUtils.SendRequest(url, str);
                    Message msg = new Message();
                    dialog.dismiss();
                    if (result.contains("success")) {
                        msg.what = 1;
                    } else {
                        msg.what = 0;
                    }
                    uiHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });

    }
    public void getJson2()
    {
        new Thread(){
            @Override
            public void run() {
                try{
                    String url="http://114.215.121.244:8080/auth?command_type=mission&mission_id=0";

                    String str = HttpUtils.getJsonContent(url);
                    Message msg = new Message();
                    msg.what = 3;
                    msg.obj = str;
                    uiHandler.sendMessage(msg);
                }
                catch (Exception e)
                {}
            }
        }.start();
    }

    private Handler uiHandler = new Handler(){
        // 覆写这个方法，接收并处理消息。
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    break;
                case 0:
                    //Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    String str = "[";
                    str += msg.obj.toString();
                    str += "]";
                    Log.e("3result",str);
                    Gson gson = new Gson();
                    List<UserM_Get> user_get = gson.fromJson(str,new TypeToken< ArrayList<UserM_Get> >(){}.getType());
                    for(int i =0;i<user_get.size();i++)
                    {
                        UserM_Get u = user_get.get(i);
                        Log.e("PRT", u.getId());
                        Log.e("PRT",u.getMission_name());
                    }
                default:
            }
        }
    };

    @OnClick(R.id.btn_qtlogin)
    public void qtLogin(View view)
    {
        Intent intent = new Intent(getBaseContext(),RegisterActivity.class);
        startActivity(intent);
    }

    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {

            boolean Sign2 = account.getText().length() > 0;
            boolean Sign3 = password.getText().length() > 0;

            if (Sign2 & Sign3) {
                btn_login.setTextColor(0xFFFFFFFF);
                btn_login.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                btn_login.setTextColor(0xFFD0EFC6);
                btn_login.setEnabled(false);
            }
        }

    }

    public void check()
    {
        dialog.dismiss();
        if(accountStr.equals("jordan23") && passwordStr.equals("123456"))
        {
            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

}
