package com.example.taxiornotinsubway;


import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiornotinsubway.database.DatabaseHelper;
import com.example.taxiornotinsubway.database.model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ResultActivity2 extends AppCompatActivity {
    private SubwayResultAdapter mAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_subway);
        Bundle bundle = getIntent().getExtras();
        Data data = (Data)bundle.getSerializable("some");
        Log.e("inside", String.valueOf(data.getExchangeStations()[0]));
        Log.e("inside", String.valueOf(data.getExchangeStations()[1]));

        ArrayList<String> ex = new ArrayList<>();
        ex.add(data.getStartName());
        for (String each : data.getExchangeStations()){
            ex.add(each);
        }
        ex.add(data.getEndName());
        recyclerView =  findViewById(R.id.recycler_view_result);

        mAdapter = new SubwayResultAdapter(this, ex);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }

}
