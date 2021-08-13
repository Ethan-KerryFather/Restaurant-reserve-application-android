package com.reservation.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_menu, container, false);
        Button button = rootView.findViewById(R.id.menuFragment_button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeserviceActivity homeServiceActivity = (HomeserviceActivity) getActivity();
                homeServiceActivity.onFragmentChanged(1);
            }
        });

        return rootView;
    }
}