package com.reservation.myapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReserveActivity extends AppCompatActivity {
    ConstraintLayout reserveActivityLayout;
    EditText askEditText;
    EditText customerName;
    Spinner reserve_people;
    TextView reserve_final;
    String date;
    String final_reservation;
    int reserve_number;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "yyyy/MM/dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.KOREA);
            date = dateFormat.format(myCalendar.getTime());
            final_reservation = date;
            reserve_final.setText(final_reservation);
        }
    };

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
        reserve_people = (Spinner) findViewById(R.id.reserve_spinner);
        reserve_final = (TextView) findViewById(R.id.reserve_finalView);

        CheckBox checkBox_people = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkBox_corona = (CheckBox) findViewById(R.id.checkbox2);
        ConstraintLayout layout_reserve = (ConstraintLayout) findViewById(R.id.layout2);
        Button homeserviceButton = (Button) findViewById(R.id.home_service_button);
        Button enter_button = (Button) findViewById(R.id.enter_button);
        Button date_button = (Button) findViewById(R.id.date_button);
        Button time_button = (Button) findViewById(R.id.time_button);
        // 인원 스피너 기본값 선택
        reserve_people.setSelection(0);
        //String howManyPerson = reserve_people.getSelectedItem().toString();


        ArrayAdapter peopleAdapter = ArrayAdapter.createFromResource(this,R.array.reserve_people, android.R.layout.simple_spinner_dropdown_item);
        reserve_people.setAdapter(peopleAdapter);

        // 예약 확정 버튼
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPopup();
            }
        });

        // 날짜 선택 버튼
        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReserveActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH) ).show();


            }
        });

        // 시간 선택 버튼
        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mtimePicker;
                mtimePicker = new TimePickerDialog(ReserveActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                        if(selectedHour > 20){
                            Toast.makeText(getApplicationContext(), "불가능한 시간입니다 다시 선택해주세요", Toast.LENGTH_LONG);
                        }
                        final_reservation += " "+ selectedHour +"시 "+selectedMinute+"분 예약입니다";
                        reserve_final.setText(final_reservation);
                    }
                }, hour, minute, true);

                mtimePicker.setTitle("예약 시간을 지정합니다");
                mtimePicker.show();

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
                Information person = new Information();
                reserve_number = person.getReserveNum();
                Toast.makeText(getApplicationContext(), "귀하의 예약번호는 \n"+ reserve_number+" 입니다",Toast.LENGTH_LONG).show();
                sendingMessage();
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
        String message_content = "!!!**예약이 도착했습니다**!!!"
                +"\n예약번호: "+ reserve_number
                +"\n이름: " +customerName.getText().toString()+
                "\n인원수: "+peopleNum
                +"\n예약일시: "+final_reservation
                +"\n\n***요청사항***\n" + askedMessage;

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
                        makeSnackbar(ReserveActivity.this, reserveActivityLayout, "예약문자가 전송되었습니다. 답장이 와야 예약이 확정됩니다. \n예약번호 "+reserve_number, Snackbar.LENGTH_LONG);
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
//                        Toast.makeText(getApplicationContext(), "SMS 도착 완료", Toast.LENGTH_SHORT).show();
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

    void makeSnackbar(Context context, View view ,String message, int durationTime){
        Snackbar.make(context, view, message, durationTime).show();

    }

}










































































