package com.example.taxiornotinsubway;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//https://friedpotatospace.tistory.com/36
public class Fragment2 extends Fragment {
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
        View view =  inflater.inflate(R.layout.fragment2,container,false);
        //여기에 recycleView
        return view;
    }
}
