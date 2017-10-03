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

// Класс для первого фрагмента
public class RegistrationFragment1 extends Fragment {

    private static final String TAG = "EmailPassword";

    private OnFragmentInteractionListenerR1 mListener;
    // 1.Пользовательский интерфейс R1 (UI)
    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mRePasswordField;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration1, container,false);
        // 3. Связываем UI
        mEmailField = (EditText) view.findViewById(R.id.reg_editText_email);
        mPasswordField = (EditText) view.findViewById(R.id.reg_editText_password);
        mRePasswordField = (EditText) view.findViewById(R.id.reg_editText_repassword);

        return view;
    }

    //Регистрация новых пользователей по адресу электронной почты
    public void createAccount() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        Log.d(TAG, "Создание аккаунта :" + email);
        if (!validateForm(email,password)) {
            return ;
        }
        // Посылаем данные Activity
        mListener.onFragmentInteractionR1(email,password);

    }

    // Проверка формы
    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Обязательно к заполнению");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Обязательно к заполнению");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        String repassword = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Обязательно к заполнению");
            valid = false;
        } else if(!password.equals(repassword)){
            mPasswordField.setError("Пароли не совпадают!");
            valid = false;
        }else {
            mPasswordField.setError(null);
        }
        return valid;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListenerR1) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    interface OnFragmentInteractionListenerR1 {
        void onFragmentInteractionR1(String email, String password);
    }
}
