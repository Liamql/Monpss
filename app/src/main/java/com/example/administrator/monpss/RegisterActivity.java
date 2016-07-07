package com.example.administrator.monpss;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


import java.io.IOException;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class RegisterActivity extends Activity {
    private EditText et_usernick;
    private EditText et_usertel;
    private EditText et_password;
    private Button btn_register;
    private TextView tv_xieyi;
    private ImageView iv_hide;
    private ImageView iv_show;
    private ImageView iv_back;
    ProgressDialog dialog;

    private String url = "http://114.215.121.244:8080/auth";

    private String result;
    private int getcode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dialog = new ProgressDialog(RegisterActivity.this);
        et_usernick = (EditText) findViewById(R.id.et_usernick);

        et_usertel = (EditText) findViewById(R.id.et_usertel);
        et_password = (EditText) findViewById(R.id.et_password);

        et_usernick.addTextChangedListener(new TextChange());
        et_usertel.addTextChangedListener(new TextChange());
        et_password.addTextChangedListener(new TextChange());
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_xieyi = (TextView) findViewById(R.id.tv_xieyi);
        iv_hide = (ImageView) findViewById(R.id.iv_hide);

        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_show = (ImageView) findViewById(R.id.iv_show);

        String xieyi = "<font color=" + "\"" + "#AAAAAA" + "\">" + "点击上面的"
                + "\"" + "注册" + "\"" + "按钮,即表示你同意" + "</font>" + "<u>"
                + "<font color=" + "\"" + "#576B95" + "\">" + "《软件许可及服务协议》"
                + "</font>" + "</u>";

        tv_xieyi.setText(Html.fromHtml(xieyi));

        iv_hide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_hide.setVisibility(View.GONE);
                iv_show.setVisibility(View.VISIBLE);
                et_password
                        .setTransformationMethod(HideReturnsTransformationMethod
                                .getInstance());
                // 切换后将EditText光标置于末尾
                CharSequence charSequence = et_password.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }

        });
        iv_show.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_show.setVisibility(View.GONE);
                iv_hide.setVisibility(View.VISIBLE);
                et_password
                        .setTransformationMethod(PasswordTransformationMethod
                                .getInstance());
                // 切换后将EditText光标置于末尾
                CharSequence charSequence = et_password.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }

        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                dialog.setMessage("正在注册...");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.show();

                final String usernick = et_usernick.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                final String usertel = et_usertel.getText().toString().trim();

                // 获得输入的账号和密码，发送
                /*
                 * 用户登录的时候获取账号和密码，封装成Java对象，字段包括类型和内容，内容是用户名和密码之间的拼接
                 */

                // 封装好Java对象，讲数据写到服务器,在子线程中运行
                ThreadUtils.runInSubThread(new Runnable() {

                    public void run() {
                        try {
                            Gson gson = new Gson();
                            UserR user = new UserR("user_register", usertel, password);
                            String str = gson.toJson(user);
                            result = HttpUtils.SendRequest(url, str);
                            Message msg = new Message();
                            dialog.dismiss();
                            if(result.contains("success"))
                            {
                                msg.what = 1;
                            }
                            else
                            {
                                msg.what = 0;
                            }
                            uiHandler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private Handler uiHandler = new Handler(){
        // 覆写这个方法，接收并处理消息。
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                    break;
                case 0:
                    Toast.makeText(RegisterActivity.this, "已存在这个用户", Toast.LENGTH_SHORT).show();
                    break;
                default:
            }
        }
    };


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

            boolean Sign1 = et_usernick.getText().length() > 0;
            boolean Sign2 = et_usertel.getText().length() > 0;
            boolean Sign3 = et_password.getText().length() > 0;

            if (Sign1 & Sign2 & Sign3) {
                btn_register.setTextColor(0xFFFFFFFF);
                btn_register.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                btn_register.setTextColor(0xFFD0EFC6);
                btn_register.setEnabled(false);
            }
        }
    }
}