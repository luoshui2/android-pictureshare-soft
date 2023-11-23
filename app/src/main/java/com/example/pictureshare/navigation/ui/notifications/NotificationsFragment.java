package com.example.pictureshare.navigation.ui.notifications;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictureshare.R;
import com.example.pictureshare.adapter.CollectAdapter;
import com.example.pictureshare.api.Constants;
import com.example.pictureshare.databinding.FragmentNotificationsBinding;
import com.example.pictureshare.navigation.ui.home.HomeFragment;
import com.example.pictureshare.picture.CollectPic;
import com.example.pictureshare.picture.PictureEntity;
import com.example.pictureshare.toast.ToastHelp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationsFragment extends Fragment {

    private Context context;

    private TextView author;
    private TextView like;
    private TextView collect;

    private RecyclerView recyclerView;

    private CollectAdapter collectAdapter;

    private int likeNum = 0;

    private int collectNum = 0;
    private String username;

    private SharedPreferences sharedPreferences;

    private List<CollectPic> datas = new ArrayList<>();

    private SmartRefreshLayout refreshLayout;

    private int num = 0;


    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(collectAdapter != null){

                collectAdapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View root = inflater.inflate(R.layout.fragment_notifications,container,false);

        author = root.findViewById(R.id.name);
        like = root.findViewById(R.id.text_likenum);
        collect = root.findViewById(R.id.text_collectnum);
        recyclerView = root.findViewById(R.id.collect_recyclerview);
        refreshLayout = root.findViewById(R.id.RefreshLayout);

        sharedPreferences = context.getSharedPreferences("Login_message",MODE_PRIVATE);
        username = sharedPreferences.getString("username",null);
        author.setText(username);


        init();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void init(){

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setPadding(10,10,10,10);

        collectAdapter = new CollectAdapter(getActivity());
        recyclerView.setAdapter(collectAdapter);

        Log.d("data",""+ datas.size());
        Log.d("data",""+ num);
        if(datas != null && datas.size() > 0){
            //界面切换返回时进行数据更新
            Log.d("data",""+ datas.size());
            collectAdapter.setDatas(datas);
            collectAdapter.notifyDataSetChanged();
        }else{
            httprequest(true);
        }
        //上拉刷新的监听事件
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //请求数据
                refreshLayout.postDelayed(new NotificationsFragment.FinishRunnable(), 5000);
                //回到起始页
                num = 0;
                httprequest(true);
            }
        });
        //下拉加载的监听事件
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshLayout.postDelayed(new NotificationsFragment.FinishRunnable(), 5000);
                num++;
                httprequest(false);
            }
        });
        if(like == null){
            Log.d("like","null");
        }

    }

    private void httprequest( boolean flag){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody formBody = new FormBody.Builder()
                        .add("number",Integer.valueOf(num).toString())
                        .add("username",username)
                        .build();
                final Request request = new Request.Builder()
                        .url(Constants.SERVER_URL + Constants.pic_collect_url)//请求的url
                        .post(formBody)
                        .build();
                Call call = client.newCall(request);
                //加入队列 异步操作
                call.enqueue(new Callback() {
                    //请求错误回调方法
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ToastHelp.show(context,"前检查网络连接");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            if(flag){
                                refreshLayout.finishRefresh();
                            }else{
                                refreshLayout.finishLoadMore();
                            }
                            final String res= response.body().string();
                            try{
                                JSONObject jsonObject = new JSONObject(res);
                                String code = jsonObject.getString("code");
                                likeNum = jsonObject.getInt("like_number");
                                collectNum = jsonObject.getInt("collect_number");
                                like.setText(Integer.valueOf(likeNum).toString());
                                collect.setText(Integer.valueOf(collectNum).toString());
                                if(code.equals("200")){
                                    //收藏图片
                                    String url = jsonObject.getString("url");
                                    CollectPic c = new CollectPic();
                                    c.setUrl(Constants.SERVER_URL + url);
                                    c.setPid(num);
                                    if(flag){
                                        datas.clear();
                                        datas.add(c);
                                    }else{
                                        datas.add(c);
                                    }
                                    collectAdapter.setDatas(datas);

                                    handler.sendMessage(new Message());
                                }else{
                                    ToastHelp.show(context,"到底了...");
                                }
                            }catch (JSONException e){
                                Log.d("error","" + e);
                            }

                        }else{
                            ToastHelp.show(context,"响应失败");
                        }
                    }

                });
            }
        }).start();
    }

    class FinishRunnable implements Runnable
    {
        @Override
        public void run() {
            if (refreshLayout != null)
            {
                refreshLayout.finishRefresh();//刷新完成
                refreshLayout.finishLoadMore();//加载完成
            }
        }
    }
}