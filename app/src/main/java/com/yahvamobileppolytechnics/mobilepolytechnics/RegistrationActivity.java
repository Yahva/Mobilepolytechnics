package com.yahvamobileppolytechnics.mobilepolytechnics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends ProgressActivity  implements
        RegistrationFragment1.OnFragmentInteractionListenerR1,
        RegistrationFragment2.OnFragmentInteractionListenerR2 {

    private static final String TAG = "EmailPassword";

    // UI переменные
    private Button bLeft;
    private Button bRight;

    private FragmentManager myFragmentManager;

    // Переменные Firebase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    // Переменные для управления фрагментами
    private FragmentTransaction mft ;
    private RegistrationFragment1 fragment1;
    private RegistrationFragment2 fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //Инициализации фрагментов
        fragment1 = new RegistrationFragment1();
        fragment2 = new RegistrationFragment2();

        // Связывание UI
        bLeft = (Button) findViewById(R.id.button_left);
        bRight = (Button) findViewById(R.id.button_right);

        // Инициализация Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            // при первом запуске программы
            mft = myFragmentManager.beginTransaction();
            // добавляем в контейнер при помощи метода add()
            mft.add(R.id.container_registration_activity, fragment1);
            mft.commit();
        }

        bLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Переход на экран авторизации
            Intent intent = new Intent(RegistrationActivity.this, EmailPasswordActivity.class);
            startActivity(intent);
            }
        });

        bRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!fragment2.isAdded()){
                    fragment1.createAccount();
                }else if(mUser!= null){
                    fragment2.createUser();
                }
            }
        });

    }

    // Создаём аккаунт часть 1
    @Override
    public void onFragmentInteractionR1(String email, String password) {
        showProgressDialog();
        // Создание аккаунта
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Успешно, зарегали аккаунт
                            Log.d(TAG, "Создание аккаунта часть 1: успех");
                            Toast.makeText(RegistrationActivity.this, "Первый этап пройден!",
                                    Toast.LENGTH_SHORT).show();
                            mUser = mAuth.getCurrentUser();
                            mDatabaseReference = mFirebaseDatabase.getReference().child("users/"+mUser.getUid());
                            //Переход на следующий фрагмент
                            mft = myFragmentManager.beginTransaction();
                            mft.replace(R.id.container_registration_activity, fragment2);
                            mft.commit();
                            bLeft.setVisibility(View.GONE);
                        } else {
                            // Провал, аккаунт зарегать не удалось
                            Log.w(TAG, "Создание аккаунта часть 1: провал", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Первый этап не пройден!",
                                    Toast.LENGTH_SHORT).show();
                            mUser = null;
                        }
                       hideProgressDialog();
                    }
                });
    }

    // Создаём аккаунт часть 2
    @Override
    public void onFragmentInteractionR2(User user) {
        showProgressDialog();
        mDatabaseReference.setValue(user).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Успешно, зарегестрировали пользователя
                    Log.d(TAG, "Создание аккаунта часть 2: успех");
                    Toast.makeText(RegistrationActivity.this, "Создать аккаунт удалось",
                            Toast.LENGTH_SHORT).show();
                    //Переход на главное меню
                    Intent intent = new Intent(RegistrationActivity.this, MenuActivity.class);
                    startActivity(intent);

                } else {
                    // Провал, зарегистрировать пользователя не удалось
                    Log.w(TAG, "Создание аккаунта часть 2: провал", task.getException());
                    Toast.makeText(RegistrationActivity.this, "Создать аккаунт не удалось",
                            Toast.LENGTH_SHORT).show();
                }
                hideProgressDialog();
            }
        });
    }

}
