package com.yahvamobileppolytechnics.mobilepolytechnics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

// Класс для второго фрагмента
public  class RegistrationFragment2 extends Fragment {

    private static final String TAG = "EmailPassword";

    private OnFragmentInteractionListenerR2 mListener;
    // 1.Пользовательский интерфейс R2 (UI)
    private EditText mSureNameFiled;
    private EditText mFirstNameFiled;
    private EditText mUniverFiled;
    private EditText mInstFiled;
    private EditText mKursFiled;
    private EditText mGroupFiled;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration2,container,false);

        // 2. Связываем UI
        mSureNameFiled = (EditText) view.findViewById(R.id.editTextName_surename);
        mFirstNameFiled = (EditText) view.findViewById(R.id.editText_firstname);
        mUniverFiled = (EditText) view.findViewById(R.id.editText_university);
        mInstFiled = (EditText) view.findViewById(R.id.editText_institute);
        mKursFiled = (EditText) view.findViewById(R.id.editText_kurs);
        mGroupFiled = (EditText) view.findViewById(R.id.editText_group);

        return view;
    }

    public void createUser() {
        String sureName = mSureNameFiled.getText().toString();
        String firstName =  mFirstNameFiled.getText().toString();

        Log.d(TAG, "Создание пользователя :" + firstName);
        if (!validateForm(sureName, firstName)) {
            return ;
        }
        User user = new User(sureName, firstName);
        // Посылаем данные Activity
        mListener.onFragmentInteractionR2(user);

    }

    // Проверка формы
    private boolean validateForm(String sureName, String firstName) {
        boolean valid = true;

        if (TextUtils.isEmpty(sureName)) {
            mSureNameFiled .setError("Обязательно к заполнению");
            valid = false;
        } else {
            mSureNameFiled .setError(null);
        }

        if (TextUtils.isEmpty(firstName)) {
            mFirstNameFiled.setError("Обязательно к заполнению");
            valid = false;
        } else {
            mFirstNameFiled.setError(null);
        }

        return valid;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListenerR2) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListenerR2");
        }
    }

    interface OnFragmentInteractionListenerR2 {

        void onFragmentInteractionR2(User user);
    }
}