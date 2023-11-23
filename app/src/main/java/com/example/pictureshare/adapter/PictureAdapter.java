package com.example.pictureshare.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pictureshare.R;
import com.example.pictureshare.api.Constants;
import com.example.pictureshare.picture.PictureEntity;
import com.example.pictureshare.toast.ToastHelp;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//主界面图片适配器
public class PictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private SharedPreferences getLikeSp;

    private SharedPreferences getCollectSp;
    private SharedPreferences downLoadSp;
    private Context mContext;

    private List<PictureEntity> datas;


    public void setDatas(List<PictureEntity> datas) {
        this.datas = datas;
    }

    public PictureAdapter(Context context) {
        this.mContext = context;
        getLikeSp = mContext.getSharedPreferences("getlike", MODE_PRIVATE);
        getCollectSp = mContext.getSharedPreferences("getcollect", MODE_PRIVATE);
        downLoadSp = mContext.getSharedPreferences("download", MODE_PRIVATE);
    }

    public class PictureHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvAuthor;
        private TextView tvlike;
        private TextView tvDownload;
        private TextView tvCollect;
        private ImageView img_header;
        public ImageView Cover;
        public int mPosition;
        private ImageView img_like;
        private ImageView img_collect;
        private ImageView image_Download;
        private boolean islikeflag;
        private boolean iscollectflag;
        private  String username;
        private int id;

        private String pic_url;

        //获取item组件保存
        public PictureHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            tvAuthor = itemView.findViewById(R.id.author);
            tvlike = itemView.findViewById(R.id.like);
            tvCollect = itemView.findViewById(R.id.collect);
            tvDownload = itemView.findViewById(R.id.download);

            Cover = itemView.findViewById(R.id.img_cover);

            img_header = itemView.findViewById(R.id.img_header);
            img_like = itemView.findViewById(R.id.img_like);
            img_collect = itemView.findViewById(R.id.img_collect);
            image_Download = itemView.findViewById(R.id.img_download);
            //点赞按钮
            img_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int likecount = Integer.parseInt(tvlike.getText().toString());
                    SharedPreferences.Editor editor = getLikeSp.edit();
                    //前端中点击按钮的状态绑定
                    if(islikeflag)
                    {
                        likecount = likecount - 1;
                        UpdateLike(id,username,likecount,false);//false减少
                        img_like.setImageResource(R.drawable.like_pic_80);
                        tvlike.setText(String.valueOf(likecount));
                        editor.putInt("num", likecount);
                    }
                    else
                    {
                        likecount = likecount + 1;
                        UpdateLike(id,username,likecount,true);//true减少
                        img_like.setImageResource(R.drawable.like_pic_select_80);
                        tvlike.setText(String.valueOf(likecount));
                        editor.putInt("num", likecount);
                    }
                    islikeflag = !islikeflag;
                    editor.apply();
                }
            });

            //收藏按钮
            img_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int CollectCount = Integer.parseInt(tvCollect.getText().toString());
                    SharedPreferences.Editor editor = getCollectSp.edit();
                    //前端中点击按钮的状态绑定
                    if(iscollectflag)
                    {
                        CollectCount = CollectCount - 1;
                        Updatecollect(id,username,CollectCount,false);//false减少
                        img_collect.setImageResource(R.drawable.collect_3_80);
                        tvCollect.setText(String.valueOf(CollectCount));
                        editor.putInt("num", CollectCount);
                    }
                    else
                    {
                        CollectCount = CollectCount + 1;
                        Updatecollect(id,username,CollectCount,true);//true减少
                        img_collect.setImageResource(R.drawable.collect_select_3_80);
                        tvCollect.setText(String.valueOf(CollectCount));
                        editor.putInt("num", CollectCount);
                    }
                    iscollectflag = !iscollectflag;
                    editor.apply();
                }
            });

            //下载按钮
            image_Download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int downloadNum = Integer.parseInt(tvDownload.getText().toString());
                    downloadNum = downloadNum + 1;
                    tvDownload.setText(String.valueOf(downloadNum));
                    GetPictureUrl(id,pic_url,downloadNum);
                }
            });
        }
    }


    //点赞更新上传后端程序
    public void UpdateLike(int id, String username, int likecount, boolean add_or_sub) {
        OkHttpClient client=new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("pic_id",Integer.toString(id))
                .add("username",username)
                .add("likeNum",Integer.toString(likecount))
                .add("flag",Boolean.toString(add_or_sub))
                .build();
        final Request request = new Request.Builder()
                .url(Constants.SERVER_URL + Constants.pic_like)//请求的url
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("pic_like","上传点赞失败 " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String res= response.body().string();
                    try{
                        JSONObject j = new JSONObject(res);
                        String code = j.getString("code");
                        String infro_pic = j.getString("update_picture");
                        String infro_pic_user = j.getString("update_pic_user");
                        String infro_user_info = j.getString("update_user_info");
                        if(code.equals("200")){
                            Log.d("update","success");
                        }else{
                            Log.d("update","error = update_picture " + infro_pic + "update_pic_user"
                             + infro_pic_user + "update_user_info" + infro_user_info);
                        }
                    }catch (JSONException e){
                        Log.d("JSONException","JSONException = " + e);
                    }
                }else{
                    Log.d("pic_like","响应失败 上传点赞失败");
                }
            }
        });
    }

    //收藏更新上传后端程序
    public void Updatecollect(int id, String username, int collectcount, boolean add_or_sub) {
        OkHttpClient client=new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("pic_id",Integer.toString(id))
                .add("username",username)
                .add("collectNumber",Integer.toString(collectcount))
                .add("flag",Boolean.toString(add_or_sub))
                .build();
        final Request request = new Request.Builder()
                .url(Constants.SERVER_URL + Constants.pic_collect)//请求的url
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("pic_collect","上传收藏失败 " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String res= response.body().string();
                    try{
                        JSONObject j = new JSONObject(res);
                        String code = j.getString("code");
                        String infro_pic = j.getString("update_picture");
                        String infro_pic_user = j.getString("update_pic_user");
                        String infro_user_info = j.getString("update_user_info");
                        if(code.equals("200")){
                            Log.d("update","success");
                        }else{
                            Log.d("update","error = update_picture " + infro_pic + "update_pic_user"
                                    + infro_pic_user + "update_user_info" + infro_user_info);
                        }
                    }catch (JSONException e){
                        Log.d("JSONException","JSONException = " + e);
                    }
                }else{
                    Log.d("pic_like","响应失败 上传收藏失败");
                }
            }
        });
    }

    //从后端下载图片
    public void GetPictureUrl(int id,String url,int downNum) {
        Log.d("save","start");
        //开线程下载
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bp = returnBitmap(url);
                Log.d("save","start!");
                saveImageToPhotos(mContext, bp,downNum);
            }
        }).start();//线程启动
        //开线程上传下载数量
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("pic_id",Integer.toString(id))
                        .add("downNum",Integer.toString(downNum))
                        .build();
                final Request request = new Request.Builder()
                        .url(Constants.SERVER_URL + Constants.pic_down)//请求的url
                        .post(formBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("pic_down","上传下载失败 " + e);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if(response.isSuccessful()){
                            final String res= response.body().string();
                            try{
                                JSONObject j = new JSONObject(res);
                                String code = j.getString("code");
                                if(code.equals("200")){
                                    Log.d("update","success");
                                }else{
                                    Log.d("update","error");
                                }
                            }catch (JSONException e){
                                Log.d("JSONException","JSONException = " + e);
                            }
                        }else{
                            Log.d("pic_like","响应失败 上传下载失败");
                        }
                    }
                });
            }
        }).start();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false);
        PictureHolder viewHolder = new PictureHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PictureHolder vh = (PictureHolder) holder;
        PictureEntity PictureEntity = datas.get(position);

        vh.tvTitle.setText(PictureEntity.getTitle());
        vh.tvAuthor.setText(PictureEntity.getUsername());
        vh.tvlike.setText(String.valueOf(PictureEntity.getLike_num()));
        vh.tvCollect.setText(String.valueOf(PictureEntity.getCollect_num()));
        vh.tvDownload.setText(String.valueOf(PictureEntity.getDown_num()));
        boolean islike = PictureEntity.isIs_like();   //从后端获取数据，是否点赞的状态
        boolean iscollect = PictureEntity.isIs_collect();
        //请求之后绑定的状态
        if(islike)//设置点赞的效果
        {
            vh.img_like.setImageResource(R.drawable.like_pic_select_80);
        }
        else
        {
            vh.img_like.setImageResource(R.drawable.like_pic_80);
        }
        if(iscollect)//设置点赞的效果
        {
            vh.img_collect.setImageResource(R.drawable.collect_select_3_80);
        }
        else
        {
            vh.img_collect.setImageResource(R.drawable.collect_3_80);
        }
        vh.islikeflag = islike;
        vh.iscollectflag = iscollect;
        vh.username = GetUsenameFromSP("username");
        vh.id = PictureEntity.getPid();
        vh.pic_url = PictureEntity.getUrl();
        Log.d("adap","start");
        //加载图片
        Picasso.with(mContext)
                .load(PictureEntity.getUrl())
                .resize(250,250)
                .into(vh.Cover);
    }

    protected String GetUsenameFromSP(String key) {
        SharedPreferences sp= mContext.getSharedPreferences("Login_message", MODE_PRIVATE);
        return sp.getString(key,"");
    }
    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();   //返回item的值
        } else
            return 0;
    }

    private void saveImageToPhotos(Context context, Bitmap bitmap,int number) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastHelp.show(context,"保存失败,没有读写sd卡权限");
        }

        String fileName = System.currentTimeMillis() + ".jpg";

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, fileName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        OutputStream outputStream;
        try {
            outputStream = context.getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            ToastHelp.show(context,"保存失败");
            return;
        }


        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE));
        Log.d("save","保存成功");
        ToastHelp.show(context,"保存成功");
        SharedPreferences.Editor editor = downLoadSp.edit();
        editor.putInt("num", number);
        editor.apply();
    }

    /**
     * 通过地址下载图片
     *
     * @param url
     * @return bitmap type
     */
    public static Bitmap returnBitmap(String url) {
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
