package com.example.taxiornotinsubway;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiornotinsubway.database.DatabaseHelper;
import com.example.taxiornotinsubway.database.model.Note;

import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment {

    private NotesAdapter mAdapter;
    private List<Note> notesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView noNotesView;
    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment3, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        noNotesView = view.findViewById(R.id.empty_notes_view);

        db = new DatabaseHelper(getActivity());

        notesList.addAll(db.getAllBookMark());

        mAdapter = new NotesAdapter(getActivity(), notesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();
        return view;
    }

    // result 버튼 클릭시 호출
    // 새 노트 생성 >> type 은 무조건 history...



    private void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (db.getNotesCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }
}
