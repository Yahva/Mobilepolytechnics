package com.yahvamobileppolytechnics.mobilepolytechnics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class ChatActivity extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Загружаем интерфейс
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        return view;
    }
}
