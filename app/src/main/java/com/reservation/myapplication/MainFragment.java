package com.reservation.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // onCreateView는 프래그먼트와 관련되는 뷰 계층을 만들어서 리턴한다

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        // param1을 container에 붙일거다.

        Button button = rootView.findViewById(R.id.menuFragment_button2);


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                HomeserviceActivity homeServiceActivity = (HomeserviceActivity) getActivity();
                homeServiceActivity.onFragmentChanged(0);
            }
        });
        return rootView;
        // 뷰 계층 리턴
    }
}