package com.example.quizapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.quizapp.R;
import com.example.quizapp.adapters.SetAdapter;
import com.example.quizapp.databinding.ActivitySetsBinding;
import com.example.quizapp.models.SetModel;

import java.util.ArrayList;

public class SetsActivity extends AppCompatActivity {

    ActivitySetsBinding binding;
    ArrayList<SetModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySetsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        list = new ArrayList<>();

        setInitialData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.setsRecView.setLayoutManager(linearLayoutManager);

        SetAdapter adapter = new SetAdapter(this, list);
        binding.setsRecView.setAdapter(adapter);

    }

    private void setInitialData(){

        list.add(new SetModel ("SET-1"));
        list.add(new SetModel ("SET-2"));
        list.add(new SetModel ("SET-3"));
        list.add(new SetModel ("SET-4"));
        list.add(new SetModel ("SET-5"));
        list.add(new SetModel ("SET-6"));
        list.add(new SetModel ("SET-7"));
        list.add(new SetModel ("SET-8"));
        list.add(new SetModel ("SET-9"));
        list.add(new SetModel ("SET-10"));

    }
}