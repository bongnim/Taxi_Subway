package com.example.taxiornotinsubway;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_row);
        Bundle bundle = getIntent().getExtras();
        Data data = (Data)bundle.getSerializable("some");

    }
}
