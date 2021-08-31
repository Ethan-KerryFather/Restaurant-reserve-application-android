package com.reservation.myapplication;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class ReserveActivity extends AppCompatActivity {
    ConstraintLayout reserveActivityLayout;
    EditText askEditText;
    EditText customerName;
    Spinner reserve_people;

    public void exit(){
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("예약 중입니다. 홈으로 이동합니까?");
        builder.setCancelable(false);

        builder.setPositiveButton("네. 예약진행을 취소합니다", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exit();
            }
        });

        builder.setNegativeButton("실수에요", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        askEditText = (EditText) findViewById(R.id.edittext_Ask);
        customerName = (EditText) findViewById(R.id.reserve_Customer);

        reserveActivityLayout = (ConstraintLayout) findViewById(R.id.layout2);
        Button enter_button = (Button) findViewById(R.id.enter_button);
        CheckBox checkBox_people = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkBox_corona = (CheckBox) findViewById(R.id.checkbox2);
        ConstraintLayout layout_reserve = (ConstraintLayout) findViewById(R.id.layout2);
        reserve_people = (Spinner) findViewById(R.id.reserve_spinner);
        Button homeserviceButton = (Button) findViewById(R.id.home_service_button);

        reserve_people.setSelection(0);
        //String howManyPerson = reserve_people.getSelectedItem().toString();


        ArrayAdapter peopleAdapter = ArrayAdapter.createFromResource(this,R.array.reserve_people, android.R.layout.simple_spinner_dropdown_item);
        reserve_people.setAdapter(peopleAdapter);

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enterPopup();


            }
        });



    }


    void enterPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("예약을 최종확정하시겠습니까?");
        builder.setCancelable(false);

        builder.setPositiveButton("확인하고 예약번호 발급", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendingMessage();
                Information person1 = new Information();
                Toast.makeText(getApplicationContext(), "귀하의 예약번호는 \n"+ person1.getReserveNum()+" 입니다",Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "안전하게 취소되었습니다", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }



    void sendingMessage(){
        SmsManager smsManager = SmsManager.getDefault();


        String phoneNum = "01097499705";
        String askedMessage = askEditText.getText().toString();
        String peopleNum = reserve_people.getSelectedItem().toString();
        String message_content = "예약이 도착했습니다"
                +"\n이름: " +customerName.getText().toString()+
                "\n인원수: "+peopleNum
                +"\n***요청사항***\n" + askedMessage;

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList = smsManager.divideMessage(message_content);
        int smsNum = arrayList.size();

        PendingIntent sentIntent = PendingIntent.getBroadcast(this,
                0, new Intent("SMS_SENT_ACTION"), 0);

        PendingIntent deliverIntent = PendingIntent.getBroadcast(this,
                0, new Intent("SMS_DELIVERED_ACTION"), 0);


        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 전송 성공
                        Toast.makeText(getApplicationContext(), "전송 완료", Toast.LENGTH_SHORT).show();
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


        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        // 도착 완료
                        Toast.makeText(getApplicationContext(), "SMS 도착 완료", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        // 도착 안됨
                        Toast.makeText(getApplicationContext(), "SMS 도착 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));

        try{
            for( int i = 0 ; i < smsNum ; i++ ){
                smsManager.sendTextMessage(phoneNum,null,arrayList.get(i), sentIntent, deliverIntent);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}










































































