package com.example.server_test.competition;

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

import com.example.server_test.dataService.DataService;
import com.example.server_test.MainActivity;
import com.example.server_test.R;
import com.example.server_test.pub.PubActivity;
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

public class CompetitionActivity extends AppCompatActivity {


    private static final String TAG = "CompetitionActivity";
    DataService dataService = new DataService();
    LinearLayout addData;
    List<Competition> competitions;
    Button dataVisible;
    boolean open;

    //이미지
    private static final int PICK_FROM_ALBUM = 1;
    private static final int PICK_FROM_CAMERA = 2;
    private File tempFile;
    private Boolean isPermission = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        final RecyclerView competition_list = findViewById(R.id.competition_list);
        final EditText info_competition_name = findViewById(R.id.info_competition_name);
        final EditText info_competition_place = findViewById(R.id.info_competition_place);
        final EditText info_competition_buyin = findViewById(R.id.info_competition_buyin);
        final EditText info_competition_start = findViewById(R.id.info_competition_start);
//        final EditText info_competition_end = findViewById(R.id.info_competition_end);

        final ImageView info_competition_Img = findViewById(R.id.info_competition_Img);

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
        tedPermission();

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
//                map.put("competition_end", info_competition_end.getText().toString());

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

        findViewById(R.id.goToGallary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 권한 허용에 동의하지 않았을 경우 토스트를 띄웁니다.
                if(isPermission) goToAlbum();
                else Toast.makeText(view.getContext(), getResources().getString(R.string.permission_2), Toast.LENGTH_LONG).show();
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

        ImageView imageView = findViewById(R.id.info_competition_Img);

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        Log.d(TAG, "setImage : " + tempFile.getAbsolutePath());
        File saved_file = tempFile;
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
