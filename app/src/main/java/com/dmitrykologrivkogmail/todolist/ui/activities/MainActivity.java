package com.dmitrykologrivkogmail.todolist.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dmitrykologrivkogmail.todolist.R;

public class MainActivity extends AppCompatActivity {

    public static Intent getStartIntent(Context ctx) {
        return new Intent(ctx, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
