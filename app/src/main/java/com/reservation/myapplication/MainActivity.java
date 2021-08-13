package com.reservation.myapplication;

import android.Manifest;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 구성요소들
        Button button_reserve = (Button)findViewById(R.id.button_reservation);
        Button button_check = (Button)findViewById(R.id.button_check);
        Button button_homeService = (Button) findViewById(R.id.home_service_button);
        ViewFlipper flipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        ConstraintLayout layout1 = (ConstraintLayout)findViewById(R.id.layout1);


        //권한 받기
        String[] permissions = {
                Manifest.permission.SEND_SMS,
        };

        checkPermissions(permissions, layout1);



        flipper.setFlipInterval(4000);
        flipper.startFlipping();


        button_homeService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeserviceActivity.class);
                startActivity(intent);
            }
        });


        button_reserve.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReserveActivity.class);
                startActivity(intent);
            }
        });


        button_check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한이 승인되었습니다 감사합니다", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "권한이 없으면 예약이 확정되지 않습니다", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    // 권한 받기 메소드
    public void checkPermissions(String[] permissions, ConstraintLayout layout){
        ArrayList<String> targetList = new ArrayList<String>();

        for(int i = 0 ; i < permissions.length ; i++){
            String curPermissions = permissions[i];
            int permissionCheck = ContextCompat.checkSelfPermission(this, curPermissions);
            // 권한이 이미 부여되어 있을때
            if(permissionCheck == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "반갑습니다. 생어거스틴 도곡점입니다. \n기능 정상작동중입니다", Toast.LENGTH_LONG).show();
                Snackbar.make(this, layout, "반갑습니다 생어거스틴 도곡접입니다\n기능 정상작동 중입니다.",Snackbar.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,curPermissions+"권한 없음",Toast.LENGTH_SHORT).show();
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,curPermissions)){
                    Toast.makeText(this,"권한이 없으면 정상적인 예약이 불가능합니다.", Toast.LENGTH_LONG).show();
                    targetList.add(curPermissions);
                    String[] targets = new String[targetList.size()];
                    targetList.toArray(targets);
                    ActivityCompat.requestPermissions(this, targets, 101);
                }else{
                    targetList.add(curPermissions);
                    String[] targets = new String[targetList.size()];
                    targetList.toArray(targets);
                    ActivityCompat.requestPermissions(this, targets, 101);
                }
            }
        }


    }

}







