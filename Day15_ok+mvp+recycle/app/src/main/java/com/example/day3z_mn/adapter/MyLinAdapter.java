package com.example.day3z_mn.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.day3z_mn.R;
import com.example.day3z_mn.bean.MyBean;

import java.util.List;

public class MyLinAdapter extends RecyclerView.Adapter<MyLinAdapter.ViewHolder> implements View.OnClickListener {
    private List<MyBean.DataBean> list;
    private Context context;

    public MyLinAdapter(List<MyBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyLinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.xrecyclelin_item,null);
        ViewHolder holder = new ViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyLinAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(list.get(i).getTitle());
        viewHolder.price.setText("￥"+list.get(i).getBargainPrice()+"");

//        截取第一个| 前面的图片
        String str = list.get(i).getImages()+"";
//        遇到第一个| 的时候截下来
        String s = str.substring(0, str.indexOf("|"));
//        Log.e("图片地址",s+"");
        Glide.with(context).load(s).into(viewHolder.img);
        viewHolder.itemView.setTag(i);
        viewHolder.salenum.setText("销量："+list.get(i).getSalenum()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    声明一个接口
public interface ItemClick{
        void setOnItemClick(View view,int position);
    }

    private ItemClick mitemClick ;

    public void setOnClickListener(ItemClick listener){
        this.mitemClick = listener;
    }

    @Override
    public void onClick(View v) {
        if(mitemClick!=null){
            mitemClick.setOnItemClick(v, (Integer) v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView title;
        private TextView price;
        private TextView salenum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.lin_item_img);
            title = itemView.findViewById(R.id.lin_item_title);
            price = itemView.findViewById(R.id.lin_item_price);
            salenum = itemView.findViewById(R.id.lin_item_salenum);
        }
    }
}
