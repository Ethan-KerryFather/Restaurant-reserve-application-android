package com.reservation.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {
    public static interface ImageSelectionCallback{
        public void onImageSelected(int position);
    }

    public ImageSelectionCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        // fragement의 상태가 onAttach일때 실행
        super.onAttach(context);

        if( context instanceof ImageSelectionCallback){
            callback = (ImageSelectionCallback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        Button button1 = rootView.findViewById(R.id.list_button1);
        Button button2 = rootView.findViewById(R.id.list_button2);
        Button button3 = rootView.findViewById(R.id.list_button3);
        Button button4 = rootView.findViewById(R.id.list_button4);

        Button button5 = rootView.findViewById(R.id.list_button5);
        Button button6 = rootView.findViewById(R.id.list_button6);
        Button button7 = rootView.findViewById(R.id.list_button7);




        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onImageSelected(0);
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onImageSelected(1);
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onImageSelected(2);
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onImageSelected(3);
                }
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onImageSelected(4);
                }
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onImageSelected(5);
                }
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onImageSelected(6);
                }
            }
        });

        return rootView;
    }
}
