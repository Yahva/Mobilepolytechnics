package com.yahvamobileppolytechnics.mobilepolytechnics;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

public class ProgressActivity extends AppCompatActivity {

    // Диалоговое окно загрузки
    public ProgressDialog mProgressDialog;

    public ProgressActivity() {
    }

    // Отображаем диалог заузки
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    // Скрываем дилог загрузки
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

}
