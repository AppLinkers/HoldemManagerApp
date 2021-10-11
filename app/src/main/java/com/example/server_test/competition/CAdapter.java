package com.example.server_test.competition;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.server_test.DataService;
import com.example.server_test.Member;
import com.example.server_test.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CAdapter extends RecyclerView.Adapter<CAdapter.ViewHolder> {
    private static final String TAG = "CompetitionActivity";
    private List<Competition> data;
    private Context context;
    DataService dataService = new DataService();

    CAdapter(List<Competition> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public CAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return new CAdapter.ViewHolder(inflater.inflate(R.layout.competition_info, parent, false));
    }


    @Override
    public int getItemCount() {
        return data.size() ;
    }

    @Override
    public void onBindViewHolder(final CAdapter.ViewHolder holder, final int position) {
        holder.cName.setText(String.valueOf(data.get(position).getCompetition_name()));
        holder.cLocate.setText(String.valueOf(data.get(position).getCompetition_place()));
        holder.cBuyIn.setText(String.valueOf(data.get(position).getCompetition_buyIn()));
        holder.cStart.setText(String.valueOf(data.get(position).getCompetition_start()));
        holder.cEnd.setText(String.valueOf(data.get(position).getCompetition_end()));

        // 정보 수정하기
        holder.info_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                holder.cNameUpdate.setText(String.valueOf(data.get(position).getCompetition_name()));
                holder.cLocateUpdate.setText(String.valueOf(data.get(position).getCompetition_place()));
                holder.cBuyInUpdate.setText(String.valueOf(data.get(position).getCompetition_buyIn()));
                holder.cStartUpdate.setText(String.valueOf(data.get(position).getCompetition_start()));
                holder.cEndUpdate.setText(String.valueOf(data.get(position).getCompetition_end()));

                holder.info_layout.setVisibility(View.GONE);
                holder.update_layout.setVisibility(View.VISIBLE);
            }
        });

        // 정보 수정하기 버튼
        holder.update_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap();
                map.put("competition_name", holder.cNameUpdate.getText().toString());
                map.put("competition_locate", holder.cLocateUpdate.getText().toString());
                map.put("competition_buyin", holder.cBuyInUpdate.getText().toString());
                map.put("competition_start", holder.cStartUpdate.getText().toString());
                map.put("competition_end", holder.cEndUpdate.getText().toString());


//                dataService.update.updateOneCompetition.(data.get(position).getCompetition_name(), map).enqueue(new Callback<Competition>() {
//                    @Override
//                    public void onResponse(Call<Competition> call, Response<Competition> response) {
//                        data.set(position, response.body());
//                        Log.d(TAG, String.valueOf(data));
//                        notifyDataSetChanged();
//                        Toast.makeText(context, "아이템 수정 완료", Toast.LENGTH_SHORT).show();
//                        holder.info_layout.setVisibility(View.VISIBLE);
//                        holder.update_layout.setVisibility(View.GONE);
//                    }
//                    @Override
//                    public void onFailure(Call<Competition> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
            }
        });

        // 정보 삭제하기
//        holder.info_delete.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                dataService.delete.deleteOne(data.get(position).getCompetition_name()).enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        data.remove(position);
//                        notifyItemRemoved(position);
//                        Toast.makeText(context, "아이템 삭제 완료", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
//
//            }
//        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout info_layout, update_layout;
        TextView cLocate, cStart, cName, cBuyIn, cEnd;
        Button info_update, info_delete, update_btn;
        EditText cNameUpdate, cLocateUpdate, cBuyInUpdate,cStartUpdate,cEndUpdate;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 영역
            info_layout = itemView.findViewById(R.id.info_layout);
            cName = itemView.findViewById(R.id.cName);
            cLocate = itemView.findViewById(R.id.cLocate);
            cBuyIn = itemView.findViewById(R.id.cBuyIn);
            cStart = itemView.findViewById(R.id.cStart);
            cEnd = itemView.findViewById(R.id.cEnd);
            info_update = itemView.findViewById(R.id.info_update);
            info_delete = itemView.findViewById(R.id.info_delete);

            // 수정 영역
            update_layout = itemView.findViewById(R.id.update_layout);
            cNameUpdate = itemView.findViewById(R.id.cNameUpdate);
            cLocateUpdate = itemView.findViewById(R.id.cLocateUpdate);
            cBuyInUpdate = itemView.findViewById(R.id.cBuyInUpdate);
            cStartUpdate = itemView.findViewById(R.id.cStartUpdate);
            cEndUpdate = itemView.findViewById(R.id.cEndUpdate);
            update_btn = itemView.findViewById(R.id.update_btn);
        }
    }
}
