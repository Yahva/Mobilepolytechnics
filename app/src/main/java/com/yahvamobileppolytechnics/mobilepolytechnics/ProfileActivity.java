package com.yahvamobileppolytechnics.mobilepolytechnics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class ProfileActivity extends Fragment {

    // Переменная FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Загружаем интерфейс
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Инициализация FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        Button button_sign_out = (Button) view.findViewById(R.id.button_sign_out);

        button_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    // Логаут (выход)
    private void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(getContext(), EmailPasswordActivity.class);
        startActivity(intent);
    }
}
