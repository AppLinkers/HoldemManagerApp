package com.example.server_test;

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

import com.example.server_test.competition.CompetitionActivity;
import com.example.server_test.dataService.DataService;
import com.example.server_test.pub.PubActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    DataService dataService = new DataService();
    LinearLayout addData;
    List<Member> members;
    Button dataVisible;
    boolean open;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView member_list = findViewById(R.id.member_list);
        final EditText info_user_id = findViewById(R.id.info_user_id);
        final EditText info_user_name = findViewById(R.id.info_user_name);
        final EditText info_user_pass = findViewById(R.id.info_user_pass);
        final EditText info_user_loc = findViewById(R.id.info_user_loc);
        final EditText info_user_phoneNum = findViewById(R.id.info_user_phoneNum);
        dataVisible = findViewById(R.id.dataVisible);
        open =false;
        addData = findViewById(R.id.addData);
        member_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));



        // Member List 띄워주기
        dataService.member.listMember().enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                Log.d(TAG, "START");
                members = response.body();
                setAdapter(member_list);
            }
            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // 멤버 추가 하기
        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap();
                map.put("user_id", info_user_id.getText().toString());
                map.put("user_name", info_user_name.getText().toString());
                map.put("user_pass", info_user_pass.getText().toString());
                map.put("user_loc", info_user_loc.getText().toString());
                map.put("user_phone", info_user_phoneNum.getText().toString());
                dataService.member.addMember(map).enqueue(new Callback<Member>() {
                    @Override
                    public void onResponse(Call<Member> call, Response<Member> response) {
                        members.add(response.body());
                        setAdapter(member_list);
                        Toast.makeText(MainActivity.this, "유저 등록 완료", Toast.LENGTH_SHORT).show();
                        info_user_id.setText("");
                        info_user_name.setText("");
                        info_user_pass.setText("");
                        info_user_loc.setText("");
                        info_user_phoneNum.setText("");
                    }

                    @Override
                    public void onFailure(Call<Member> call, Throwable t) {
                        Log.d(TAG, "fail");
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    void setAdapter(RecyclerView member_list){
        member_list.setAdapter(new MemberAdapter(members, this, dataService));
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