package com.reservation.saintapl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mainActivityLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 구성요소들
        Button button_reserve = (Button) findViewById(R.id.button_reservation);
        Button button_check = (Button) findViewById(R.id.button_check);
        Button button_homeService = (Button) findViewById(R.id.home_service_button);
        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        mainActivityLayout = (ConstraintLayout) findViewById(R.id.layout1);


        String[] permissions = {
                Permission.SEND_SMS,
                Permission.RECEIVE_SMS,
                Permission.READ_SMS
        };

        try{
        checkPermissions(permissions);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }



        flipper.setFlipInterval(4000);
        flipper.startFlipping();


        button_homeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), HomeserviceActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
//                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                startActivity(intent);
                Toast.makeText(getApplicationContext(), "곧 서비스 예정입니다", Toast.LENGTH_SHORT).show();
            }
        });


        button_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReserveActivity.class);
                startActivity(intent);
            }
        });


        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
                startActivity(intent);
            }
        });



            //권한 요청하기
            AndPermission.with(this)
                    .runtime()
                    .permission(
                            Permission.SEND_SMS,
                            Permission.RECEIVE_SMS,
                            Permission.READ_SMS
                    )
                    .onGranted(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> data) {

                        }
                    }).onDenied(new Action<List<String>>() {
                @Override
                public void onAction(List<String> data) {
                }
            }).start();



    }


    void makeToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    void makeToast(String message, int duration_time){
        Toast.makeText(this, message, duration_time).show();
    }
    void makeSnackbar(Context context, View view ,String message, int durationTime){
        Snackbar.make(context, view, message, durationTime).show();

    }


    // 권한 확인
    public void checkPermissions(String[] permissions) throws InterruptedException {
        ArrayList<String> permissionList = new ArrayList<>();

        for( int i = 0 ; i < permissions.length ; i++ ){
            String curPermissions = permissions[i];
            int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), curPermissions);

            if( permissionCheck == PackageManager.PERMISSION_GRANTED ){

                if ( i== permissions.length-1 )
                    Snackbar.make(mainActivityLayout, "어플 정상작동 확인완료", Snackbar.LENGTH_SHORT ).show();
            }else{
                if( ActivityCompat.shouldShowRequestPermissionRationale(this, curPermissions) ){
                    // 이경우 shouldShow~가 true를 반환한 경우인데 이 경우는 사용자가 명시적으로 거부를 눌렀을 때이다
                    makeToast("권한이 없으면 예약이 불가능합니다");
                    permissionList.add(curPermissions);

                    // requestPermissions 의 2번째 파라미터는 스트링 배열만 받기 때문에 임시로 만들어서 전달하려는 것
                    String[] reRequestPermission = new String[permissionList.size()];
                    permissionList.toArray(reRequestPermission);

                    ActivityCompat.requestPermissions(this,reRequestPermission ,101);
                }else{
                    // 사용자가 권한요청을 처음 보거나 다시묻지 않음을 선택한 경우, 그리고 권한을 허용한 경우
                    permissionList.add(curPermissions);
                    String[] reRequestPermission = new String[permissionList.size()];
                    permissionList.toArray(reRequestPermission);
                    ActivityCompat.requestPermissions(this, reRequestPermission, 101);

                }
            }
        }
    }

}











