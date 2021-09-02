package com.reservation.saintapl;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class CheckActivity extends AppCompatActivity {
    EditText reserveCode;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);


        TextView textview = (TextView) findViewById(R.id.check_textview1);
        Button cancel_Button = (Button) findViewById(R.id.check_cancel);
        ConstraintLayout layout = (ConstraintLayout)findViewById(R.id.layout3);

        reserveCode = (EditText)findViewById(R.id.reservationCode);
        view = (ConstraintLayout) findViewById(R.id.layout3);

        textview.setText(R.string.check_guide1);
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendingMessage();
            }
        });



    }

    void sendingMessage(){
        SmsManager smsManager = SmsManager.getDefault();
        String phoneNum = "01097499705";
        String message_content = "< 예약취소 >\n" + reserveCode.getText().toString();

        PendingIntent sentIntent = PendingIntent.getBroadcast(this,
                0, new Intent("SMS_SENT_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 전송 성공
                        Snackbar.make(CheckActivity.this,view, "예약취소가 완료되었습니다", Snackbar.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        // 전송 실패
                        Toast.makeText(getApplicationContext(), "전송 실패", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        // 서비스 지역 아님
                        Toast.makeText(getApplicationContext(), "서비스 지역이 아닙니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        // 무선 꺼짐
                        Toast.makeText(getApplicationContext(), "무선(Radio)가 꺼져있습니다", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        // PDU 실패
                        Toast.makeText(getApplicationContext(), "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList = smsManager.divideMessage(message_content);
//        int smsNum = arrayList.size();

        try{

                smsManager.sendTextMessage(phoneNum,null,message_content, sentIntent, null);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
