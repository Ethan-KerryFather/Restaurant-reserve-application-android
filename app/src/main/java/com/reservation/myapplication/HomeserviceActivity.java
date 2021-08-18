package com.reservation.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class HomeserviceActivity extends AppCompatActivity
implements ListFragment.ImageSelectionCallback{

    ListFragment listFragment;
    ViewerFragment viewerFragment;

    int[] images = {R.drawable.food1, R.drawable.food2, R.drawable.food3};

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

            }
        });

        button_orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public void onImageSelected(int position) {
        viewerFragment.setImage(images[position]);
    }
}

