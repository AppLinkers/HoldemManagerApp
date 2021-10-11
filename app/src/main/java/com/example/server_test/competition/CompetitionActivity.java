package com.example.server_test.competition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.server_test.DataService;
import com.example.server_test.MainActivity;
import com.example.server_test.Member;
import com.example.server_test.MemberAdapter;
import com.example.server_test.R;
import com.example.server_test.competition.CAdapter;
import com.example.server_test.competition.Competition;
import com.example.server_test.pub.PubActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompetitionActivity extends AppCompatActivity {


    private static final String TAG = "CompetitionActivity";
    DataService dataService = new DataService();
    LinearLayout addData;
    List<Competition> competitions;
    Button dataVisible;
    boolean open;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        final RecyclerView competition_list = findViewById(R.id.competition_list);
        final EditText info_competition_name = findViewById(R.id.info_competition_name);
        final EditText info_competition_place = findViewById(R.id.info_competition_place);
        final EditText info_competition_buyin = findViewById(R.id.info_competition_buyin);
        final EditText info_competition_start = findViewById(R.id.info_competition_start);
        final EditText info_competition_end = findViewById(R.id.info_competition_end);
        dataVisible = findViewById(R.id.dataVisible);
        open =false;
        addData = findViewById(R.id.addData);
        competition_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        // Member List 띄워주기
//        dataService.select.selectAll().enqueue(new Callback<List<Competition>>() {
//            @Override
//            public void onResponse(Call<List<Competition>> call, Response<List<Competition>> response) {
//                Log.d(TAG, "START");
//                competitions = response.body();
//                setAdapter(competition_list);
//            }
//            @Override
//            public void onFailure(Call<List<Competition>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });

        // 멤버 추가 하기
        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap();
                map.put("competition_name", info_competition_name.getText().toString());
                map.put("competition_place", info_competition_place.getText().toString());
                map.put("competition_buyin", info_competition_buyin.getText().toString());
                map.put("competition_start", info_competition_start.getText().toString());
                map.put("competition_end", info_competition_end.getText().toString());
//                dataService.insert.insertOne(map).enqueue(new Callback<Competition>() {
//                    @Override
//                    public void onResponse(Call<Competition> call, Response<Competition> response) {
//                        competitions.add(response.body());
//                        setAdapter(competition_list);
//                        Toast.makeText(CompetitionActivity.this, "대회 등록 완료", Toast.LENGTH_SHORT).show();
//                        info_competition_name.setText("");
//                        info_competition_place.setText("");
//                        info_competition_buyin.setText("");
//                        info_competition_start.setText("");
//                        info_competition_end.setText("");
//                    }
//
//                    @Override
//                    public void onFailure(Call<Competition> call, Throwable t) {
//                        Log.d(TAG, "fail");
//                        t.printStackTrace();
//                    }
//                });
            }
        });
    }

    void setAdapter(RecyclerView member_list){
        member_list.setAdapter(new CAdapter(competitions, this));
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

    //메뉴 컨트롤
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.switchmenu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {

        switch(item.getItemId())
        {
            case R.id.menu_user:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_competition:
                Intent intent2 = new Intent(getApplicationContext(), CompetitionActivity.class);
                startActivity(intent2);

                break;
            case R.id.menu_pub:
                Intent intent3 = new Intent(getApplicationContext(), PubActivity.class);
                startActivity(intent3);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}
