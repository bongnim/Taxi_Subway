package com.example.taxiornotinsubway;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

public class Fragment1 extends Fragment {
    public ODsayService odsayService;
    public Context context;
    public JSONObject jsonObject;
    public String subwayTravelTime = "0";
    public String exchangeStation;
    public String taxiTravelTime = "0";
    //Handler
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what == 0){
                Log.d("response","request fail");
            } else if(msg.what == 1){
                try {
                    Log.d("handle",(String)msg.obj);
                    JSONObject taxiInformation = new JSONObject((String)msg.obj);
                    String taxiTime_stringFormat = taxiInformation.getJSONArray("features").getJSONObject(0).getJSONObject("properties").getString("totalTime");
                    taxiTravelTime = String.valueOf(Integer.parseInt(taxiTime_stringFormat)/60);
                    Log.d("taxiTime",taxiTravelTime);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    //Handler
    private Button button;
    private Spinner spinner1;
    private static final String[] schools = new String[]{"혜화"};
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();

        return fragment;
    }
    private Spinner spinner2;
    //지하철 노선 시간 확인
    public void PathSeeking() {
        odsayService = ODsayService.init(getActivity(), "ohyAzciqIm641X57gUSvS8GNcWZscObNioWn+1HlQHE");
        odsayService.setReadTimeout(5000);
        odsayService.setConnectionTimeout(5000);
        //SID는 DB에서 맞는 지하철역 정보를 받아와 DB에서 ID를 받아올 것
        odsayService.requestSubwayPath("1000", "420", "221", "1", onResultCallbackListener);
        //stationName에 조회할 View를 넣어야함
    }

    //지하철 경유 시간 및 환승 지점 확인
    OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
        // 호출 성공 시 실행
        @Override
        public void onSuccess(ODsayData odsayData, API api) {
            try {
                // API Value 는 API 호출 메소드 명을 따라갑니다.
                if (api == API.SUBWAY_PATH) {
                    subwayTravelTime = odsayData.getJson().getJSONObject("result").getString("globalTravelTime");
                    exchangeStation = odsayData.getJson().getJSONObject("result").getJSONObject("exChangeInfoSet").getJSONArray("exChangeInfo").getJSONObject(0).getString("exName");
                    Log.d("Travel Time : %s", subwayTravelTime);
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 호출 실패 시 실행
        @Override
        public void onError(int i, String s, API api) {
            if (api == API.SUBWAY_PATH) {}
        }
    };

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment1, container,false);
        spinner1= (Spinner)view.findViewById(R.id.spinner_start);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,schools);
        //adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        PathSeeking();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_SHORT); //본인이 원하는 작업.
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                HttpThread httpThread = new HttpThread("https://apis.openapi.sk.com/tmap/routes?version=1&format=json&appKey=0f31e295-9ada-43b5-9292-5133678f2a00&startX=126.9850380932383&startY=37.566567545861645&endX=127.10331814639885&endY=37.403049076341794&totalValue=2");
                httpThread.start();
                Log.d("taxi and subway","Taxi Time : "+taxiTravelTime + " Subway Time: " + subwayTravelTime);
                if(Integer.parseInt(subwayTravelTime) < Integer.parseInt(taxiTravelTime)){
                    Intent in = new Intent(getActivity(), ResultActivity.class);
                    in.putExtra("some", subwayTravelTime);
                    startActivity(in);
                }else{
                    Intent in = new Intent(getActivity(), ResultActivity.class); //ResultActivity를 2개로 나눠서
                    in.putExtra("some", taxiTravelTime);
                    startActivity(in);
                }
            }
        });
        return view;
    }
    class HttpThread extends Thread {
        private String url;

        public HttpThread(String url) {
            this.url = url;
        }//end of HttpThread init

        @Override
        public void run() {
            try{
                URL serverUrl = new URL(this.url);
                HttpURLConnection http = (HttpURLConnection)serverUrl.openConnection();
                http.setRequestMethod("GET");
                http.setConnectTimeout(10 * 10000);
                http.setReadTimeout(10 * 10000);
                http.setDoInput(true);
                http.setDoOutput(false);
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(), "UTF-8"));
                StringBuffer sb = new StringBuffer();
                String strLine = null;
                while((strLine = in.readLine()) != null){
                    sb.append(strLine);
                }
                Message msg = new Message();
                msg.what = 1;

                msg.obj = sb.toString();
                handler.sendMessage(msg);

            } catch (Exception e ){
                Log.e("Main", "ERROR : ", e);
                handler.sendEmptyMessage(0);
            } // end of TryCatch
        } // end of run
    } // end of HttpThread

}

