package com.yahvamobileppolytechnics.mobilepolytechnics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity {


    private  FragmentTransaction mft ;

    //Объявляем переменные фрагментов
    private ProfileActivity mFragmentProfile;
    private ChatActivity mFragmentChat;
    private ScheduleActivity mFragmentSchedule;


    // Динамическое переключение на другой фрагмент
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mft = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.navigation_profile:
                if(mFragmentProfile.isHidden()){
                    if(mFragmentChat.isVisible()){
                        mft.hide(mFragmentChat);
                    }
                    if(mFragmentSchedule.isVisible()){
                        mft.hide(mFragmentSchedule);
                    }
                    mft.show(mFragmentProfile);
                    mft.commit();
                }
                return true;
            case R.id.navigation_chat:
                if(mFragmentChat.isHidden()){
                    if(mFragmentProfile.isVisible()){
                        mft.hide(mFragmentProfile);
                    }
                    if(mFragmentSchedule.isVisible()){
                        mft.hide(mFragmentSchedule);
                    }
                    mft.show(mFragmentChat);
                    mft.commit();
                }
                return true;
            case R.id.navigation_schedule:
                if(mFragmentSchedule.isHidden()){
                    if(mFragmentProfile.isVisible()){
                        mft.hide(mFragmentProfile);
                    }
                    if(mFragmentChat.isVisible()){
                        mft.hide(mFragmentChat);
                    }
                    mft.show(mFragmentSchedule);
                    mft.commit();
                }
                return true;
        }
        return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Инициализируем фрагменты
        mFragmentProfile = new ProfileActivity();
        mFragmentChat = new ChatActivity();
        mFragmentSchedule = new ScheduleActivity();

        //Динамически добавляем фрагмент в activity  при первом запуске программы
        if (savedInstanceState == null) {
            //Получаем объект управляющий фрагментами
            mft = getSupportFragmentManager().beginTransaction();
            // добавляем в контейнер при помощи метода add()
            mft.add(R.id.container_menu_activity,  mFragmentProfile);
            mft.add(R.id.container_menu_activity, mFragmentChat);
            mft.add(R.id.container_menu_activity, mFragmentSchedule);
            mft.commit();
            // скрываем фрагменты кроме одного (Profile)
            mft = getSupportFragmentManager().beginTransaction();
            mft.hide(mFragmentChat);
            mft.hide(mFragmentSchedule);
            mft.commit();
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
