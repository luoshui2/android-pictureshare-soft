package com.example.pictureshare.navigation.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictureshare.R;
import com.example.pictureshare.adapter.PictureAdapter;
import com.example.pictureshare.api.Constants;
import com.example.pictureshare.databinding.FragmentHomeBinding;
import com.example.pictureshare.picture.PictureEntity;
import com.example.pictureshare.toast.ToastHelp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;
    private SharedPreferences sharedPreferences;

    private PictureAdapter newsAdapter;
    private List<PictureEntity> datas = new ArrayList<>();
    private int pageNum = 0;
    private String username;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("time","create");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        Log.d("time","onCreateView");
        View root = inflater.inflate(R.layout.fragment_home,container,false);

        recyclerView = root.findViewById(R.id.recycleView);
        refreshLayout = root.findViewById(R.id.refreshLayout);
        sharedPreferences = context.getSharedPreferences("Login_message",MODE_PRIVATE);
        username = sharedPreferences.getString("username",null);


        initData();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("time","onViewCreated");

    }

    @Override
    public void onStart() {
        Log.d("time","onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("time","onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("time","onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("time","onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("time","onDestroy");
        super.onDestroy();
    }

    protected void initData() {

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        newsAdapter = new PictureAdapter(getActivity());
        recyclerView.setAdapter(newsAdapter);
        if(datas != null && datas.size() > 0){
            //界面切换返回时进行数据更新
            newsAdapter.setDatas(datas);
            newsAdapter.notifyDataSetChanged();
        }else{
            getNewsList(true);
        }

        //上拉刷新的监听事件
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //请求数据
                refreshLayout.postDelayed(new FinishRunnable(), 5000);
                //回到起始页
                pageNum = 0;
                ToastHelp.show(context,"开始刷新");
                getNewsList(true);
            }
        });
        //下拉加载的监听事件
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshLayout.postDelayed(new FinishRunnable(), 5000);
                pageNum = pageNum + 1;
                //ToastHelp.show(context,"开始加载");
                getNewsList(false);
            }
        });

    }

    private void getNewsList(final boolean isRefresh){
        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("page", Integer.toString(pageNum))
                .add("pageSize", "3")
                .add("username",username)
                .build();
        final Request request = new Request.Builder()
                .url(Constants.SERVER_URL + Constants.get_pic)//请求的url
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
                    final String res= response.body().string();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isRefresh) {
                                refreshLayout.finishRefresh();
                            } else {
                                refreshLayout.finishLoadMore();
                            }
                            try {
                                //json解析
                                JSONObject jsonObject = new JSONObject(res);
                                String code = jsonObject.getString("code");
                                String msg = jsonObject.getString("msg");
                                if(code.equals("200") && msg.equals("success")){
                                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                                    List<PictureEntity> pictures = new ArrayList<>();
                                    for(int i = 0;i < jsonArray.length() ; i++){
                                        JSONObject j = jsonArray.getJSONObject(i);
                                        PictureEntity p = new PictureEntity();

                                        p.setPid(Integer.parseInt(j.getString("id")));
                                        p.setTitle(j.getString("title"));
                                        p.setUsername(j.getString("usename"));
                                        p.setLike_num(j.getInt("like_number"));
                                        p.setCollect_num(j.getInt("collect_number"));
                                        p.setDown_num(j.getInt("download_number"));
                                        p.setUrl(Constants.SERVER_URL + j.getString("pic_url"));
                                        p.setIs_like(j.getBoolean("is_like"));
                                        p.setIs_collect(j.getBoolean("is_collect"));
                                        Log.d("url","" + p.getPid() + p.getUrl());
                                        pictures.add(p);
                                    }
                                    if(pictures != null && pictures.size() > 0){
                                        if(isRefresh){
                                            datas = pictures;
                                        }else{
                                            datas.addAll(pictures);
                                        }
                                        newsAdapter.setDatas(datas);
                                        newsAdapter.notifyDataSetChanged();
                                        if (isRefresh) {
                                            refreshLayout.finishRefresh();
                                        } else {
                                            refreshLayout.finishLoadMore();
                                        }
                                    }else{
                                        if (isRefresh) {
                                            ToastHelp.show(context,"暂时无数据");
                                        } else {
                                            ToastHelp.show(context,"没有更多数据");
                                        }
                                    }

                                }else{
                                    if (isRefresh) {
                                        ToastHelp.show(context,"暂时无数据");
                                    } else {
                                        ToastHelp.show(context,"我是有底线的...");
                                    }
                                }
                            }catch (JSONException e){
                                Log.d("Json except","" + e);
                            }
                        }
                    });
                }else{
                    ToastHelp.show(context,"响应失败");
                }
            }

        });
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