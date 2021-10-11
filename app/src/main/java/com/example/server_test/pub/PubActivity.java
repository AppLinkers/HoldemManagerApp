package com.example.server_test.pub;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.server_test.DataService;
import com.example.server_test.R;
import com.example.server_test.competition.CAdapter;
import com.example.server_test.competition.Competition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PubActivity extends AppCompatActivity {


    private static final String TAG = "PubActivity";
    DataService dataService = new DataService();
    LinearLayout addData;
    List<Pub> pubs;
    Button dataVisible;
    boolean open;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);
        final RecyclerView pub_list = findViewById(R.id.pub_list);
        final EditText info_pub_name = findViewById(R.id.info_pub_name);
        final EditText info_pub_info = findViewById(R.id.info_pub_info);
        final EditText info_pub_open = findViewById(R.id.info_pub_open);
        final EditText info_pub_end = findViewById(R.id.info_pub_end);
        final EditText info_pub_game = findViewById(R.id.info_pub_game);
        dataVisible = findViewById(R.id.dataVisible);
        open =false;
        addData = findViewById(R.id.addData);
        pub_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        // Member List 띄워주기
//        dataService.select.selectAll().enqueue(new Callback<List<Pub>>() {
//            @Override
//            public void onResponse(Call<List<Pub>> call, Response<List<Pub>> response) {
//                Log.d(TAG, "START");
//                pubs = response.body();
//                setAdapter(pub_list);
//            }
//            @Override
//            public void onFailure(Call<List<Pub>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

        // 멤버 추가 하기
        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap();
                map.put("pub_name", info_pub_name.getText().toString());
                map.put("pub_info", info_pub_info.getText().toString());
                map.put("pub_open", info_pub_open.getText().toString());
                map.put("pub_end", info_pub_end.getText().toString());
                map.put("pub_game", info_pub_game.getText().toString());
//                dataService.insert.insertOne(map).enqueue(new Callback<Pub>() {
//                    @Override
//                    public void onResponse(Call<Pub> call, Response<Pub> response) {
//                        pubs.add(response.body());
//                        setAdapter(pub_list);
//                        Toast.makeText(PubActivity.this, "펍 등록 완료", Toast.LENGTH_SHORT).show();
//                        info_pub_name.setText("");
//                        info_pub_info.setText("");
//                        info_pub_open.setText("");
//                        info_pub_end.setText("");
//                        info_pub_game.setText("");
//                    }
//
//                    @Override
//                    public void onFailure(Call<Pub> call, Throwable t) {
//                        Log.d(TAG, "fail");
//                        t.printStackTrace();
//                    }
//                });
            }
        });
    }

    void setAdapter(RecyclerView member_list){
        member_list.setAdapter(new PAdapter(pubs, this,dataService));
    }

    public void openAddData(View view) {
        if(!open){
            addData.setVisibility(View.VISIBLE);
            dataVisible.setText("데이터 창 없애기");
            open =true;
        }else{
            addData.setVisibility(View.GONE);
            dataVisible.setText("데이터 추가하기");
            open = false;
        }
    }

}
