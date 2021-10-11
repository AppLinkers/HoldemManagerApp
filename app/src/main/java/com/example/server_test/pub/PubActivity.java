package com.example.server_test.pub;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.server_test.DataService;
import com.example.server_test.MainActivity;
import com.example.server_test.R;
import com.example.server_test.competition.CAdapter;
import com.example.server_test.competition.Competition;
import com.example.server_test.competition.CompetitionActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    //이미지
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private File tempFile;
    private File saved_file;
    private Boolean isPermission = true;


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


        tedPermission();

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
<<<<<<< HEAD
=======
            }
        });

        findViewById(R.id.goToGallary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission) goToAlbum();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
>>>>>>> 405f706f55e6a239579ec213c5487fe208df1171
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    //이미지 가져오기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if (tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e(TAG, tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();
            Log.d(TAG, "PICK_FROM_ALBUM photoUri : " + photoUri);

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

                Log.d(TAG, "tempFile Uri : " + Uri.fromFile(tempFile));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        } else if (requestCode == PICK_FROM_CAMERA) {

            setImage();

        }
    }

    /**
     *  앨범에서 이미지 가져오기
     */
    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    /**
     *  카메라에서 이미지 가져오기
     */
    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            tempFile = createImageFile();
        } catch (IOException e) {
            Toast.makeText(this, "이미지 처리 오류! 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }
        if (tempFile != null) {

            Uri photoUri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    /**
     *  폴더 및 파일 만들기
     */
    private File createImageFile() throws IOException {

        // 이미지 파일 이름 ( blackJin_{시간}_ )
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "Upload_" + timeStamp + "_";

        // 이미지가 저장될 폴더 이름 ( blackJin )
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/UploadImg/");
        if (!storageDir.exists()) storageDir.mkdirs();

        // 파일 생성
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        Log.d(TAG, "createImageFile : " + image.getAbsolutePath());

        return image;
    }

    /**
     *  tempFile 을 bitmap 으로 변환 후 ImageView 에 설정한다.
     */
    private void setImage() {

        ImageView imageView = findViewById(R.id.info_pub_Img);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());
        saved_file = tempFile;
        imageView.setImageBitmap(originalBm);

        /**
         *  tempFile 사용 후 null 처리를 해줘야 합니다.
         *  (resultCode != RESULT_OK) 일 때 tempFile 을 삭제하기 때문에
         *  기존에 데이터가 남아 있게 되면 원치 않은 삭제가 이뤄집니다.
         */
        tempFile = null;

    }

    private void tedPermission() {

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공
                isPermission = true;

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
                isPermission = false;

            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

    }



}
