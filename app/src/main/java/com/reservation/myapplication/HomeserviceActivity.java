package com.reservation.myapplication;

import android.os.Bundle;

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


    }


    @Override
    public void onImageSelected(int position) {
        viewerFragment.setImage(position);
    }
}

