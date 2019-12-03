package com.example.taxiornotinsubway;
import android.content.Intent;
import android.os.Bundle;
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

public class Fragment1 extends Fragment {
    private Button button;
    private Spinner spinner1;
    private Spinner spinner2;
    private static final String[] schools = new String[]{"새힘관", "명신관", "진리관","순헌관","학생회관","행정관","수련관","행파관","이과대학","중앙 도서회관","사회 교육관","미술 대학","약학 대학","백주년 기념관","음악 대학"};
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();

        return fragment;
    }
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
                Intent in = new Intent(getActivity(), ResultActivity.class);
                double[] latlong ={37.582191, 127.001915, 37.500628,127.036392};
                in.putExtra("some", latlong);
                startActivity(in);
            }
        });
        return view;
    }
}
