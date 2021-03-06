package com.example.server_test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.server_test.dataService.DataService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    private static final String TAG = "MemberAdapter";
    private List<Member> data;
    private Context context;
    private DataService dataService;

    MemberAdapter(List<Member> data, Context context, DataService dataService) {
        this.data = data;
        this.context = context;
        this.dataService = dataService;
    }

    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new MemberAdapter.ViewHolder(inflater.inflate(R.layout.member_info, parent, false));
    }

    @Override
    public int getItemCount() {
        return data.size() ;
    }

    @Override
    public void onBindViewHolder(final MemberAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.info_id.setText(String.valueOf(data.get(position).getId()));
        holder.info_user_id.setText(String.valueOf(data.get(position).getUser_id()));
        holder.info_user_name.setText(String.valueOf(data.get(position).getUser_name()));
        holder.info_user_pass.setText(String.valueOf(data.get(position).getUser_pass()));
        holder.info_user_loc.setText(String.valueOf(data.get(position).getUser_loc()));
        holder.info_user_phoneNum.setText(String.valueOf(data.get(position).getUser_phone()));

        // ?????? ????????????
        holder.info_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                holder.update_id.setText(String.valueOf(holder.info_id.getText()));
                holder.update_user_id.setText(String.valueOf(data.get(position).getUser_id()));
                holder.update_user_name.setText(String.valueOf(data.get(position).getUser_name()));
                holder.update_user_pass.setText(String.valueOf(data.get(position).getUser_pass()));
                holder.info_user_loc.setText(String.valueOf(data.get(position).getUser_loc()));
                holder.info_user_phoneNum.setText(String.valueOf(data.get(position).getUser_phone()));

                holder.info_layout.setVisibility(View.GONE);
                holder.update_layout.setVisibility(View.VISIBLE);
            }
        });

        // ?????? ???????????? ??????
        holder.update_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap();
                map.put("user_id", holder.update_user_id.getText().toString());
                map.put("user_name", holder.update_user_name.getText().toString());
                map.put("user_pass", holder.update_user_pass.getText().toString());
                map.put("user_loc", holder.update_user_loc.getText().toString());
                map.put("user_phone", holder.update_user_phoneNum.getText().toString());
                Log.d("test", data.get(position).getUser_id());

                dataService.member.updateMember(data.get(position).getUser_id(), map).enqueue(new Callback<Member>() {
                    @Override
                    public void onResponse(Call<Member> call, Response<Member> response) {
                        data.set(position, response.body());
                        Log.d(TAG, String.valueOf(data));
                        notifyDataSetChanged();
                        Toast.makeText(context, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                        holder.info_layout.setVisibility(View.VISIBLE);
                        holder.update_layout.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(Call<Member> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        // ?????? ????????????
        holder.info_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dataService.member.deleteMember(data.get(position).getUser_id()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        data.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "????????? ?????? ??????", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }
        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout info_layout, update_layout;
        TextView info_id, info_user_id, info_user_name, info_user_pass, update_id,info_user_loc,info_user_phoneNum;
        Button info_update, info_delete, update_btn;
        EditText update_user_id, update_user_name, update_user_pass,update_user_loc,update_user_phoneNum;

        ViewHolder(View itemView) {
            super(itemView);
            // ??? ??????
            info_layout = itemView.findViewById(R.id.info_layout);
            info_id = itemView.findViewById(R.id.info_id);
            info_user_id = itemView.findViewById(R.id.info_user_id);
            info_user_name = itemView.findViewById(R.id.info_user_name);
            info_user_pass = itemView.findViewById(R.id.info_user_pass);
            info_user_loc = itemView.findViewById(R.id.info_user_loc);
            info_user_phoneNum = itemView.findViewById(R.id.info_user_phoneNum);
            info_update = itemView.findViewById(R.id.info_update);
            info_delete = itemView.findViewById(R.id.info_delete);

            // ?????? ??????
            update_layout = itemView.findViewById(R.id.update_layout);
            update_id = itemView.findViewById(R.id.update_id);
            update_user_id = itemView.findViewById(R.id.update_user_id);
            update_user_name = itemView.findViewById(R.id.update_user_name);
            update_user_pass = itemView.findViewById(R.id.update_user_pass);
            update_user_loc = itemView.findViewById(R.id.update_user_loc);
            update_user_phoneNum = itemView.findViewById(R.id.update_user_phoneNum);
            update_btn = itemView.findViewById(R.id.update_btn);
        }
    }
}