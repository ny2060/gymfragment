package com.google.firebase.quickstart.database.java.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.quickstart.database.EntryChoiceActivity;
import com.google.firebase.quickstart.database.R;
import com.google.firebase.quickstart.database.java.SignInActivity;

public class CommuFragment extends Fragment {


    Button button;
    private View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_commupage, container, false);
        button=view.findViewById(R.id.go);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(), SignInActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
