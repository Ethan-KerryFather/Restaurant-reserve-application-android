package com.reservation.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HomeserviceActivity extends AppCompatActivity {
    MainFragment mainFragment;
    MenuFragment menuFragment;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mainFragment = new MainFragment();
        menuFragment = new MenuFragment();
        // mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.realMainFragment);
        // mainFragment를 위와 같이 지정하고  menuFragment 를 new MenuFragment() 로 지정했을때 MenuFragment에서 다시 MainFragment로 전환시에러가 떴다. 
        // menuFragment = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.realMenuFragment);

    }

    public void onFragmentChanged(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, menuFragment).commit();
            // 2. 트랜젝션 시작   프래그먼트매니저. beginTransaction()
            // 2.5. replace( 해당 fragment를 채울 공간 , 해당 fragment )
            // 3. 트랜젝션 실행   '' . beginTransaction(). commit() !!
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();

        }
    }
}
