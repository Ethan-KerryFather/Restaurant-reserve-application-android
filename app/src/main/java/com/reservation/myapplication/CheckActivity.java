package com.reservation.myapplication;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class CheckActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);


        TextView textview = (TextView) findViewById(R.id.check_textview1);
        EditText reserve_code = (EditText) findViewById(R.id.reserve_code);
        Button cancel_Button = (Button) findViewById(R.id.check_cancel);
        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.layout3);

        String reserveCode = reserve_code.toString();


        textview.setText(R.string.check_guide1);
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendingMessage(layout, reserveCode);
            }
        });



    }

    void sendingMessage(ConstraintLayout layout, String reserve_code){
        String phoneNum = "01097499705";
        String smsContent = "*예약이 취소되었습니다*\n" + "예약번호 :" + reserve_code;

        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum,null,smsContent,null,null);
            Snackbar.make(this,layout,"예약이 취소되어 자동발송되었습니다. 예약 취소는 답장이 돌아오면 확정됩니다.", Snackbar.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "예약취소가 실패하였습니다.", Toast.LENGTH_SHORT).show();
            Log.e("문자발송","실패");
            e.printStackTrace();
        }
    }
}
