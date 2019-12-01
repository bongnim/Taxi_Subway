package com.example.taxiornotinsubway;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_taxi);
        //map viewer

        
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            if (bundle.getString("some") != null){
                Toast.makeText(getApplicationContext(),
                        "data:" +bundle.getString("some"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
