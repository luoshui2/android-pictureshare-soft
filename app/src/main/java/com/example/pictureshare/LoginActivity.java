package com.example.pictureshare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pictureshare.api.Constants;
import com.example.pictureshare.dialog.Mydialog;
import com.example.pictureshare.toast.ToastHelp;
import com.example.pictureshare.users.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private ImageView vis;
    private EditText username;
    private EditText password;
    private CheckBox checkBox;
    private ImageButton login_in;
    private TextView sign;
    private ImageView image_sign;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private User user;
    //弹窗
    private Mydialog mydialog;

    private int sign_requestcode = 10086;


    //账号验证的callback
    private okhttp3.Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            mydialog.dismiss();
            ToastHelp.show(getApplicationContext(),"请检查网络连接");
            Log.e("flag", "Failed to connect server!");
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            mydialog.dismiss();
            if(response.isSuccessful()){
                Log.d("flag","response success");
                //拿到返回的响应体
                final String body = response.body().string();
                Log.d("flag",body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //json解析
                            JSONObject jsonObject = new JSONObject(body);
                            String code = jsonObject.getString("code");
                            String msg = jsonObject.getString("msg");
                            if(msg.equals("error")){//用==的话会比较错误
                                Log.d("flag",msg);
                                if(code.equals("400")){
                                    ToastHelp.show(getApplicationContext(),"用户名为空");
                                }else{
                                    ToastHelp.show(getApplicationContext(),"用户名不存在");
                                }
                                editor.remove("username");
                            }else{
                                Log.d("flag",msg);
                                if(code.equals("200")){
                                    editor.putString("username",user.getUsename());
                                    ToastHelp.show(getApplicationContext(),"登陆成功");
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    editor.remove("username");
                                    ToastHelp.show(getApplicationContext(),"密码错误");
                                }
                            }
                            editor.apply();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }else{
                ToastHelp.show(getApplicationContext(),"响应失败");
                Log.d("flag", "Failed to connect server!");
            }
        }
    };


    //初始化账号密码的callback_start
    private okhttp3.Callback callback_start = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            ToastHelp.show(getApplicationContext(),"请检查网络连接");
            //Toast.makeText(getApplicationContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            Log.e("flag", "Failed to connect server!");
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            if(response.isSuccessful()){
                Log.d("flag","response success");
                //拿到返回的响应体
                final String body = response.body().string();
                Log.d("flag",body);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //json解析
                            JSONObject jsonObject = new JSONObject(body);
                            String code = jsonObject.getString("code");
                            String msg = jsonObject.getString("msg");
                            if(msg.equals("error")){//用==的话会比较错误
                                Log.d("flag",msg);
                                if(code.equals("400")){
                                    ToastHelp.show(getApplicationContext(),"用户名为空");
                                }else{
                                    ToastHelp.show(getApplicationContext(),"用户名不存在");
                                }
                            }else{
                                Log.d("flag","start callback"+msg);
                                String pass = jsonObject.getString("password");
                                username.setText(user.getUsename());
                                password.setText(pass);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }else{
                Log.e("flag", "Failed to connect server!");
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



        //获取组件
        vis = findViewById(R.id.image_vis);
        username = findViewById(R.id.text_username);
        password = findViewById(R.id.text_password);
        checkBox = findViewById(R.id.checkBox);
        login_in = findViewById(R.id.image_in);
        sign = findViewById(R.id.sign_up);
        image_sign = findViewById(R.id.image_sign);

        //获取sp数据库
        sharedPreferences = getSharedPreferences("Login_message",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //读取数据库设置初始眼睛和复选框
        boolean flag_eye = sharedPreferences.getBoolean("eye_vis",false);
        boolean flag_check = sharedPreferences.getBoolean("check",false);
        if(flag_eye){
            vis.setImageResource(R.drawable.login_look_24);
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else{
            vis.setImageResource(R.drawable.login_notlook_24);
            password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            password.setTypeface(Typeface.DEFAULT);
        }
        if(flag_check){
            String name = sharedPreferences.getString("username",null);
            if(name != null){
                Log.d("flag",name);
                user = new User();
                user.setUsename(name);
                refreshData_start();
            }
            Log.d("flag","11");
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
        init_onclick();
    }

    //初始化点击事件设置
    private void init_onclick(){
        //眼睛设置点击事件
        vis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.image_vis){
                    if(sharedPreferences != null){
                        boolean flag = sharedPreferences.getBoolean("eye_vis",false);
                        if(flag){
                            vis.setImageResource(R.drawable.login_notlook_24);
                            password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                            password.setTypeface(Typeface.DEFAULT);
                            editor.putBoolean("eye_vis",false);
                            editor.apply();
                        }else{
                            vis.setImageResource(R.drawable.login_look_24);
                            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            editor.putBoolean("eye_vis",true);
                            editor.apply();
                        }
                    }
                }
            }
        });
        //登陆按钮设置点击事件（保存复选框是否保存密码）
        login_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //不要账号密码直接登陆取消注释，并且将下面的注释掉
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                if(username.getText().length() <= 0 || password.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(),"请输入账号和密码",Toast.LENGTH_SHORT).show();
                }else{
                    //设置账号和密码
                    user = new User();
                    user.setUsename(username.getText().toString());
                    user.setPassword(password.getText().toString());
                    if(checkBox.isChecked()){
                        editor.putBoolean("check",true);
                    }else{
                        editor.putBoolean("check",false);
                    }
                    editor.apply();
                    //进行账号密码验证
                    mydialog = new Mydialog(LoginActivity.this,R.style.loading_dialog,"Loading...");
                    mydialog.show();
                    refreshData();
                }
            }
        });
        //注册设置点击按钮
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Sign_up.class);
                startActivityForResult(intent, sign_requestcode);//返回数据
            }
        });
        image_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Sign_up.class);
                startActivityForResult(intent, sign_requestcode);//返回数据
            }
        });
    }
    //从服务器获取账号密码
    private void refreshData(){
        //网络请求不用主线程
        Log.d("flag","request start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(user != null){
                    //进行Request
                    Request request = new Request.Builder().url(Constants.SERVER_URL +
                            Constants.get_name + user.getUsename() + Constants.password + user.getPassword()).get().build();
                    try{
                        OkHttpClient client = new OkHttpClient();
                        client.newCall(request).enqueue(callback);
                        Log.d("flag","request success");
                    }catch (NetworkOnMainThreadException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //初始化账号密码
    private void refreshData_start(){
        //网络请求不用主线程
        Log.d("flag","request start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(user != null){
                    //进行Request
                    Request request = new Request.Builder().url(Constants.SERVER_URL +
                            Constants.get_names + user.getUsename()).get().build();
                    try{
                        OkHttpClient client = new OkHttpClient();
                        client.newCall(request).enqueue(callback_start);
                        Log.d("flag","request success");
                    }catch (NetworkOnMainThreadException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //注册页面返回的信息处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == sign_requestcode){
            if(resultCode == RESULT_CANCELED){
                Log.d("flag","canceled");
            }
            if (resultCode == RESULT_OK){
                Log.d("flag","111"+resultCode);
                String name = data.getStringExtra("username");
                String pass = data.getStringExtra("password");
                if(name != null && pass != null){
                    Log.d("flag","Main"+name);
                    username.setText(name);
                    password.setText(pass);
                }
            }
        }

    }
}