package com.example.taxiornotinsubway;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

public class ResultActivity extends AppCompatActivity {
    TMapView tmapview;
    TMapData tMapData;
    TMapPoint tMapPointStart = new TMapPoint(37.582191, 127.001915); // 혜화역
    TMapPoint tMapPointEnd = new TMapPoint(37.500628, 127.036392); // 역삼역
    PathAsync pathAsync;
    TMapPolyLine polyLine;
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_taxi);
        //map viewer
        RelativeLayout relativeLayout = new RelativeLayout(this);

        tmapview = new TMapView(this);
        tMapData = new TMapData();
        tmapview.setSKTMapApiKey("0f31e295-9ada-43b5-9292-5133678f2a00");
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setZoomLevel(12);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setCenterPoint((tMapPointStart.getLongitude()+tMapPointEnd.getLongitude())/2,(tMapPointStart.getLatitude()+tMapPointEnd.getLatitude())/2, true);
        tmapview.setTrackingMode(true);
        relativeLayout.addView(tmapview);
        setContentView(relativeLayout);

        polyLine = new TMapPolyLine();
        pathAsync = new PathAsync();
        pathAsync.execute(polyLine);
;
        Bundle bundle = getIntent().getExtras();
        Data data = (Data)bundle.getSerializable("some");
        if(data != null){
            // Log.d("inside", String.valueOf(bundle.getDoubleArray("some")[0]));
            tMapPointStart = new TMapPoint(data.getStartX(), data.getEndX()); // 혜화역
            tMapPointEnd = new TMapPoint( data.getStartY(),data.getEndY()); // 역삼역
        }
    }
    class PathAsync extends AsyncTask<TMapPolyLine, Void, TMapPolyLine> {
        @Override
        protected TMapPolyLine doInBackground(TMapPolyLine... tMapPolyLines) {
            TMapPolyLine tMapPolyLine = tMapPolyLines[0];
            try {
                tMapPolyLine = new TMapData().findPathDataWithType(TMapData.TMapPathType.CAR_PATH, tMapPointStart, tMapPointEnd);
                tMapPolyLine.setLineColor(Color.BLUE);
                tMapPolyLine.setLineWidth(3);


            }catch(Exception e) {
                e.printStackTrace();
                Log.e("error",e.getMessage());
            }
            return tMapPolyLine;
        }

        @Override
        protected void onPostExecute(TMapPolyLine tMapPolyLine) {
            super.onPostExecute(tMapPolyLine);
            tmapview.addTMapPolyLine("Line1", tMapPolyLine);
        }
    }
}
