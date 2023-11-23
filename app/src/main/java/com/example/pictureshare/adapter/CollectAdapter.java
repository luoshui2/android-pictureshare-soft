package com.example.pictureshare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pictureshare.R;
import com.example.pictureshare.picture.CollectPic;
import com.squareup.picasso.Picasso;

import java.util.List;

//收藏图片适配器
public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<CollectPic> datas;

    private Context context;

    public CollectAdapter(Context context){
        this.context = context;
    }

    public void setDatas(List<CollectPic> datas) {
        this.datas = datas;
    }

    public class CollectViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;

        public CollectViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_collects);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_collect,parent,false);
        CollectViewHolder collectViewHolder = new CollectViewHolder(view);
        return collectViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CollectViewHolder vh = (CollectViewHolder) holder;
        CollectPic collectPic = datas.get(position);

        //加载图片
        Picasso.with(context)
                .load(collectPic.getUrl())
                .resize(250,250)
                .into(vh.img);
    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();   //返回item的值
        } else
            return 0;
    }
}
