package com.example.pictureshare.navigation.ui.dashboard;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.pictureshare.R;
import com.example.pictureshare.api.Constants;
import com.example.pictureshare.databinding.FragmentDashboardBinding;
import com.example.pictureshare.imageload.GlideImageLoader;
import com.example.pictureshare.pickerload.ImagePickerLoader;
import com.example.pictureshare.toast.ToastHelp;
import com.lwkandroid.imagepicker.ImagePicker;
import com.lwkandroid.imagepicker.data.ImageBean;
import com.lwkandroid.imagepicker.data.ImagePickType;
import com.lwkandroid.widget.ninegridview.NineGirdImageContainer;
import com.lwkandroid.widget.ninegridview.NineGridBean;
import com.lwkandroid.widget.ninegridview.NineGridView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DashboardFragment extends Fragment {
    private Context context;

    private NineGridView mNineGridView;

    private SharedPreferences sharedPreferences;

    private CheckBox checkBox;

    private Button button;

    private EditText editText;

    private FragmentDashboardBinding binding;
    private List<NineGridBean> files;

    private boolean flag = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();

        View root = inflater.inflate(R.layout.fragment_dashboard,container,false);



        //通过root获取组件
        mNineGridView = (NineGridView) root.findViewById(R.id.ninegridview);
        checkBox = root.findViewById(R.id.ck_main_is_edit_mode);
        button = root.findViewById(R.id.send_button);
        editText = root.findViewById(R.id.write_content);


        //获取sp数据库
        sharedPreferences = context.getSharedPreferences("Login_message",MODE_PRIVATE);

        //设置图片加载器，这个是必须的，不然图片无法显示
        mNineGridView.setImageLoader(new GlideImageLoader());
        //设置显示列数，默认3列
        mNineGridView.setColumnCount(3);
        //设置是否为编辑模式，默认为false
        mNineGridView.setIsEditMode(checkBox.isChecked());
        //设置单张图片显示时的尺寸，默认100dp
        mNineGridView.setSingleImageSize(100);
        //设置单张图片显示时的宽高比，默认1.0f
        mNineGridView.setSingleImageRatio(1.0f);
        //设置最大显示数量，默认9张
        mNineGridView.setMaxNum(9);
        //设置图片显示间隔大小，默认3dp
        mNineGridView.setSpcaeSize(2);
        //设置删除图片
        mNineGridView.setIcDeleteResId(R.drawable.ic_block_black_24dp);
        //设置删除图片与父视图的大小比例，默认0.25f
        mNineGridView.setRatioOfDeleteIcon(0.25f);
        //设置“+”号的图片
        mNineGridView.setIcAddMoreResId(R.drawable.ic_ngv_add_pic);
        //设置各类点击监听

        initOnclick();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //设置点击事件
    private void initOnclick(){
        //九宫格点击事件
        mNineGridView.setOnItemClickListener(new NineGridView.onItemClickListener() {
            @Override
            public void onNineGirdAddMoreClick(int cha){
                Log.d("flag","nine pick");
                new ImagePicker()
                        .cachePath(Environment.getExternalStorageDirectory().getAbsolutePath())
                        .pickType(ImagePickType.MULTI)
                        .displayer(new ImagePickerLoader())
                        .maxNum(cha)
                        .start(DashboardFragment.this, 100);
            }

            @Override
            public void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

            }

            @Override
            public void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer) {

            }
        });

        //添加按钮点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if(editText.getText().length() <= 0){
                    ToastHelp.show(context,"请输入标题");
                }else{
                    String usename = sharedPreferences.getString("username",null);
                    if(mNineGridView != null && usename != null){

                        files = mNineGridView.getDataList();
                        for(NineGridBean p:files){
                            flag = true;
                            uploadImage(p.getOriginUrl(),editText.getText().toString(),usename);
                            if(!flag){
                                ToastHelp.show(context,"上传失败");
                            }

                        }
                        ToastHelp.show(context,"上传成功");
                    }else{
                        ToastHelp.show(context,"上传失败");
                    }
                }
            }
        });
        //编辑按钮点击事件
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    mNineGridView.setIsEditMode(true);
                }else{
                    mNineGridView.setIsEditMode(false);
                }
            }
        });
    }
    //imagepick返回事件
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null)
        {
            List<ImageBean> list = data.getParcelableArrayListExtra(ImagePicker.INTENT_RESULT_DATA);
            List<NineGridBean> resultList = new ArrayList<>();
            for (ImageBean imageBean : list)
            {
                NineGridBean nineGirdData = new NineGridBean(imageBean.getImagePath());
                resultList.add(nineGirdData);
                Log.d("path",nineGirdData.getOriginUrl());
            }
            mNineGridView.addDataList(resultList);
        }
    }

    //上传图片到服务器
    private void uploadImage(String path,String title,String name) {
        try{
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            // 准备Body
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("title",title)
                    .addFormDataPart("usename",name)
                    .addFormDataPart("fileName", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))//文件
                    .build();
            Request request = new Request.Builder()
                    .url(Constants.SERVER_URL+Constants.upload).post(requestBody)
                    .addHeader("user-agent", "PDA")
                    .addHeader("x-userid", "752332")// 添加x-userid请求头
                    .addHeader("x-sessionkey", "kjhsfjkaskfashfuiwf")// 添加x-sessionkey请求头
                    .addHeader("x-tonce", Long.valueOf(System.currentTimeMillis()).toString())// 添加x-tonce请求头
                    .addHeader("x-timestamp", Long.valueOf(System.currentTimeMillis()).toString())// 添加x-timestamp请求头
                    .build();

            Log.d("flag","upload start");
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("flag","upload error "+e);
                    flag = false;
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String result = response.body().string();
                    if (response.isSuccessful()) {
                        Log.d("flag","upload sucess");
                    }else{
                        Log.d("flag","upload errors = "+result);
                        flag = false;
                    }
                }
            });
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }


}