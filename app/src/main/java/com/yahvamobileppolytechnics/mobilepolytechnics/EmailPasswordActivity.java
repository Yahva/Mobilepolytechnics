package com.yahvamobileppolytechnics.mobilepolytechnics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Библиотеуы от Firebase

public class EmailPasswordActivity extends ProgressActivity  {

    // Метка служебного сообщения
    private static final String TAG = "EmailPassword";

    // Переменная FirebaseAuth
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // UI переменные
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button button_sign_in;
    private Button button_reg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Соединяем UI - Иницииализируем компоненты аутентификации
        mEmailField = (EditText) findViewById(R.id.editText_email);
        mPasswordField = (EditText) findViewById(R.id.editText_password);
        button_sign_in = (Button) findViewById(R.id.button_sign_in);
        button_reg = (Button) findViewById(R.id.button_reg);

        //Задаём действие на нажатие кнопки "Войти"
        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
        });

        //Задаём действие на нажатие кнопки "Регистрация"
        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailPasswordActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        // Инициализация FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(EmailPasswordActivity.this, "Пользователь аутентифицирован!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Toast.makeText(EmailPasswordActivity.this, "Пользователь не аутентифицирован!",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    // Проверка, залогинен ли уже текущий пользователь при зауске
    @Override
    public void onStart() {
        super.onStart();
        // Проверяем залогиненный ли пользователь (не null)
        // и обновляем пользовательский интерфейс
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthListener);
        updateUI(currentUser);
    }

    // Удалаяем слушатель состояния пользователя при закрытие
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // Аутентификация
    private void signIn(String email, String password) {
        Log.d(TAG, "Логин: " + email);
        if (!validateForm()) {
            return;
        }

        // Логин с email
        showProgressDialog();
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Логин успешен, обновляем пользовательский интерфейс, отображаем информацию аккаунта пользователя
                        Log.d(TAG, "Логин: успех");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(EmailPasswordActivity.this, "Аутентификация удалась.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(user);
                    } else {
                        // Если логин неудачный, отобрази сообщение пользователю
                        Log.w(TAG, "Логин: провал", task.getException());
                        Toast.makeText(EmailPasswordActivity.this, "Аутентификация не удалась.",
                                Toast.LENGTH_SHORT).show();
                    }
                    hideProgressDialog();
                }
            });
    }

    // Проверка формы
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Обязательно к заполнению");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Обязательно к заполнению");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(EmailPasswordActivity.this, "Заполните поля!",
                    Toast.LENGTH_SHORT).show();
        }

        return valid;
    }

    // Обновление пользовательского интерфейся
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(EmailPasswordActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }


}
