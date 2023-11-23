package com.example.pictureshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class Sign_up extends AppCompatActivity {
    private ImageView vis;
    private ImageView confrim_vis;
    private EditText username;
    private EditText password;
    private EditText confrim;
    private TextView sign;
    private TextView sign_re;
    private User user;
    private boolean flag = false;
    private boolean flag_confrim = false;
    private Intent returnIntent;
    private boolean return_intent;
    private Mydialog mydialog;

    //上传账号密码的callback
    private okhttp3.Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            mydialog.dismiss();
            ToastHelp.show(getApplicationContext(),"请检查网络连接");
            //Toast.makeText(getApplicationContext(),"请检查网络连接",Toast.LENGTH_SHORT).show();
            Log.e("flag", "Failed to connect server!");
            e.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            if(response.isSuccessful()){
                mydialog.dismiss();
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
                                if(code.equals("410")){
                                    ToastHelp.show(getApplicationContext(),"用户名或账号为空");
                                }else{
                                    ToastHelp.show(getApplicationContext(),"用户名已经存在");
                                }
                            }else{
                                return_intent = !return_intent;
                                ToastHelp.show(getApplicationContext(),"注册成功");
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }else{
                mydialog.dismiss();
                ToastHelp.show(getApplicationContext(),"Failed to connect server!");
                Log.e("flag", "Failed to connect server!");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        //获取组件
        vis = findViewById(R.id.image_sign_vis);
        confrim_vis = findViewById(R.id.image_confrim);
        username = findViewById(R.id.sign_textusername);
        password = findViewById(R.id.signtext_password);
        confrim = findViewById(R.id.signtext_confrim);
        sign = findViewById(R.id.sign_in);
        sign_re = findViewById(R.id.sign_re);

        //初始化返回标志
        return_intent = false;
        //设置点击事件
        init_onclick();
    }

    //点击事件设置
    private void init_onclick(){
        //眼睛设置点击事件
        vis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.image_sign_vis){
                    if(flag){
                        vis.setImageResource(R.drawable.login_notlook_24);
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        password.setTypeface(Typeface.DEFAULT);

                    }else{
                        vis.setImageResource(R.drawable.login_look_24);
                        password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    }
                    flag = !flag;
                }
            }
        });

        confrim_vis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.image_confrim){
                    if(flag_confrim){
                        confrim_vis.setImageResource(R.drawable.login_notlook_24);
                        confrim.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        confrim.setTypeface(Typeface.DEFAULT);

                    }else{
                        confrim_vis.setImageResource(R.drawable.login_look_24);
                        confrim.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    }
                    flag_confrim = !flag_confrim;
                }
            }
        });
        //注册设置点击事件
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().length() <= 0 || password.getText().length() <= 0 || confrim.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(),"请输入账号和密码",Toast.LENGTH_SHORT).show();
                }else{
                    if(!password.getText().toString().equals(confrim.getText().toString())){
                        Toast.makeText(getApplicationContext(),"密码不一致",Toast.LENGTH_SHORT).show();
                    }else{
                        //设置账号和密码
                        user = new User();
                        user.setUsename(username.getText().toString());
                        user.setPassword(password.getText().toString());
                        mydialog = new Mydialog(Sign_up.this,R.style.loading_dialog,"注册中...");
                        mydialog.show();
                        //进行账号密码验证
                        refreshData();
                    }
                }
            }
        });

        //返回登陆界面点击事件
        sign_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnIntent = new Intent();
                if(return_intent){
                    if(username.getText().length() <= 0 || password.getText().length() <= 0){
                        Log.d("flag","not mess");
                        setResult(RESULT_CANCELED,returnIntent);
                    }else{
                        if(user == null){
                            ToastHelp.show(getApplicationContext(),"没有注册");
                            setResult(RESULT_CANCELED,returnIntent);
                        }else{
                            Log.d("flag1",user.getUsename());
                            returnIntent.putExtra("username",user.getUsename());
                            returnIntent.putExtra("password",user.getPassword());
                            setResult(RESULT_OK,returnIntent);
                        }
                    }
                }else{
                    setResult(RESULT_CANCELED,returnIntent);
                }
                finish();
            }
        });
    }

    //上传账号密码到服务器
    private void refreshData(){
        //网络请求不用主线程
        Log.d("flag","request start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(user != null){
                    //进行Request
                    Request request = new Request.Builder().url(Constants.SERVER_URL +
                            Constants.upload_name + user.getUsename() + Constants.password + user.getPassword()
                    ).get().build();
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
}