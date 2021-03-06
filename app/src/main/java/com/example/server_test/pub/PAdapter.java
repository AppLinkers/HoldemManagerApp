package com.example.server_test.pub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.example.server_test.dataService.DataService;
import com.example.server_test.R;

import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PAdapter extends RecyclerView.Adapter<PAdapter.ViewHolder> {
    private static final String TAG = "PubActivity";
    private List<Pub> data;
    private Context context;
    private DataService dataService;


    PAdapter(List<Pub> data, Context context, DataService dataService) {
        this.data = data;
        this.context = context;
        this.dataService = dataService;
    }

    @Override
    public PAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new PAdapter.ViewHolder(inflater.inflate(R.layout.pub_info, parent, false));
    }


    @Override
    public int getItemCount() {
        return data.size() ;
    }

    @Override
    public void onBindViewHolder(final PAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.pubName.setText(String.valueOf(data.get(position).getPub_name()));
        holder.pubInfo.setText(String.valueOf(data.get(position).getPub_info()));
        holder.pubOpen.setText(String.valueOf(data.get(position).getPub_open()));
        holder.pubGame.setText(String.valueOf(data.get(position).getGame().games()));
        Glide.with(holder.itemView).load(data.get(position).getPub_img()).into(holder.pubImg);
        Log.d(TAG, data.get(position).getPub_img());




        // ?????? ????????????
        holder.info_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                holder.pubName_update.setText(String.valueOf(data.get(position).getPub_name()));
                holder.pubInfo_update.setText(String.valueOf(data.get(position).getPub_info()));
                holder.pubOpen_update.setText(String.valueOf(data.get(position).getPub_open()));
                holder.pubGame_update.setText(String.valueOf(data.get(position).getGame().games()));


                holder.info_layout.setVisibility(View.GONE);
                holder.update_layout.setVisibility(View.VISIBLE);
            }
        });

        // ?????? ???????????? ??????
        holder.update_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap();
                map.put("pub_name", holder.pubName_update.getText().toString());
                map.put("pub_info", holder.pubInfo_update.getText().toString());
                map.put("pub_open", holder.pubOpen_update.getText().toString());
                map.put("pub_game", holder.pubGame_update.getText().toString());
//                dataService.update.updateOne(data.get(position).getPub_name(), map).enqueue(new Callback<Pub>() {
//                    @Override
//                    public void onResponse(Call<Pub> call, Response<Pub> response) {
//                        data.set(position, response.body());
//                        Log.d(TAG, String.valueOf(data));
//                        notifyDataSetChanged();
//                        Toast.makeText(context, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
//                        holder.info_layout.setVisibility(View.VISIBLE);
//                        holder.update_layout.setVisibility(View.GONE);
//                    }
//                    @Override
//                    public void onFailure(Call<Pub> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
            }
        });

        // ?????? ????????????
        holder.info_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                dataService.delete.deleteOne(data.get(position).getPub_name()).enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        data.remove(position);
//                        notifyItemRemoved(position);
//                        Toast.makeText(context, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
            }
        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout info_layout, update_layout;
        TextView pubName, pubInfo, pubOpen, pubEnd, pubGame;
        Button info_update, info_delete, update_btn;
        EditText pubName_update, pubInfo_update, pubOpen_update,pubEnd_update,pubGame_update;
        ImageView pubImg;

        ViewHolder(View itemView) {
            super(itemView);
            // ??? ??????
            info_layout = itemView.findViewById(R.id.info_layout);
            pubName = itemView.findViewById(R.id.pubName);
            pubInfo = itemView.findViewById(R.id.pubInfo);
            pubOpen = itemView.findViewById(R.id.pubOpen);
//            pubEnd = itemView.findViewById(R.id.pubEnd);
            pubGame = itemView.findViewById(R.id.pubGame);
            pubImg = itemView.findViewById(R.id.pubImg);
            info_update = itemView.findViewById(R.id.info_update);
            info_delete = itemView.findViewById(R.id.info_delete);

            // ?????? ??????
            update_layout = itemView.findViewById(R.id.update_layout);
            pubName_update = itemView.findViewById(R.id.pubName_update);
            pubInfo_update = itemView.findViewById(R.id.pubInfo_update);
            pubOpen_update = itemView.findViewById(R.id.pubOpen_update);
//            pubEnd_update = itemView.findViewById(R.id.pubEnd_update);
            pubGame_update = itemView.findViewById(R.id.pubGame_update);
            update_btn = itemView.findViewById(R.id.update_btn);
        }
    }
}
