package com.reservation.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
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

import com.google.android.material.snackbar.Snackbar;

public class ReserveActivity extends AppCompatActivity {
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


        Button enter_button = (Button) findViewById(R.id.enter_button);
        CheckBox checkBox_people = (CheckBox) findViewById(R.id.checkbox1);
        CheckBox checkBox_corona = (CheckBox) findViewById(R.id.checkbox2);
        ConstraintLayout layout_reserve = (ConstraintLayout) findViewById(R.id.layout2);
        Spinner reserve_people = (Spinner) findViewById(R.id.reserve_spinner);
        EditText customerName = (EditText) findViewById(R.id.reserve_personname);
        Button homeserviceButton = (Button) findViewById(R.id.home_service_button);

        reserve_people.setSelection(0);
        //String howManyPerson = reserve_people.getSelectedItem().toString();
        String String_customerName = customerName.toString();

        ArrayAdapter peopleAdapter = ArrayAdapter.createFromResource(this,R.array.reserve_people, android.R.layout.simple_spinner_dropdown_item);
        reserve_people.setAdapter(peopleAdapter);

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enterPopup(layout_reserve, String_customerName);


            }
        });



    }


    void enterPopup(ConstraintLayout layout, String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("예약을 최종확정하시겠습니까?");
        builder.setCancelable(false);

        builder.setPositiveButton("확인하고 예약번호 발급", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendingMessage(layout, name);
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

    void sendingMessage(ConstraintLayout layout, String name){
        String phoneNum = "01097499705";
        String smsContent = "*새로운 예약이 접수되었습니다*\n" + "이름 :" + name;

        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNum,null,smsContent,null,null);
            Snackbar.make(this,layout,"감사합니다. 예약이 확정되어 자동발송되었습니다", Snackbar.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "예약이 실패하였습니다.", Toast.LENGTH_SHORT).show();
            Log.e("문자발송","실패");
            e.printStackTrace();
        }
    }
}
