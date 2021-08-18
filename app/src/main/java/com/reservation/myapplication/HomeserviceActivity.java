package com.reservation.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class HomeserviceActivity extends AppCompatActivity
implements ListFragment.ImageSelectionCallback{

    ListFragment listFragment;
    ViewerFragment viewerFragment;

    int[] images = {R.drawable.potthai, R.drawable.chilinonga, R.drawable.nuapotnaman, R.drawable.food2,
    R.drawable.food2, R.drawable.food2, R.drawable.pupatbong};

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fragmentManager = getSupportFragmentManager();
        listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.listFragment);
        viewerFragment = (ViewerFragment) fragmentManager.findFragmentById(R.id.viewerFragment);

        Button button_add = findViewById(R.id.home_menuAdd);
        Button button_orderList = findViewById(R.id.home_orderList);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"추가되었습니다", Toast.LENGTH_SHORT).show();
            }
        });

        button_orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , homepop.class);
                intent.putExtra("data", "test popup");
                startActivity(intent);
            }
        });

    }


    @Override
    public void onImageSelected(int position) {
        viewerFragment.setImage(images[position]);
    }
}

