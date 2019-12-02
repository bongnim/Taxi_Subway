package com.example.taxiornotinsubway;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.skt.Tmap.TMapView;

public class ResultActivity extends AppCompatActivity {
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_taxi);
        //map viewer
        RelativeLayout relativeLayout = new RelativeLayout(this);

        TMapView tmapview = new TMapView(this);

        tmapview.setSKTMapApiKey("0f31e295-9ada-43b5-9292-5133678f2a00");
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setIconVisibility(true);
        tmapview.setZoomLevel(10);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setCompassMode(true);
        tmapview.setTrackingMode(true);

        relativeLayout.addView(tmapview);

        setContentView(relativeLayout);
        
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
