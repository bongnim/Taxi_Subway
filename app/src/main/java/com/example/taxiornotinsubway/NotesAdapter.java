package com.example.taxiornotinsubway;


import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taxiornotinsubway.database.DatabaseHelper;
import com.example.taxiornotinsubway.database.model.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private Context context;
    private List<Note> notesList;
    private DatabaseHelper db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView start;
        public TextView end;
        public TextView ivDelete;
        public ImageView ivStar;
        public ImageView ivSearch;
        public TextView timestamp;


        public MyViewHolder(View view) {
            super(view);
            start = view.findViewById(R.id.start);
            end = view.findViewById(R.id.end);
            ivStar = view.findViewById(R.id.iv_star);
            ivSearch = view.findViewById(R.id.iv_search);
            ivDelete = view.findViewById(R.id.delete);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }


    public NotesAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
        db = new DatabaseHelper(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Note note = notesList.get(position);
        holder.start.setText(note.getStart());
        holder.end.setText(note.getEnd());
        holder.timestamp.setText(formatDate(note.getTimestamp()));



//        add event listener
        holder.ivStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "선택 ID start : " + note.getId(), Toast.LENGTH_LONG).show();
                note.setType("bookmark");
                db.updateNote(note);
//                notesList.set(position,note);
                notesList.remove(position);
//                notifyItemChanged(position);
                notifyDataSetChanged();

            }
        });

        holder.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, ResultActivity.class);
                double start_x =  Double.parseDouble(note.getStart_x());
                double start_y =  Double.parseDouble(note.getStart_y());
                double end_x =  Double.parseDouble(note.getEnd_x());
                double end_y =  Double.parseDouble(note.getEnd_y());

                Toast.makeText(context, "선택 ID search : " + start_x +" / "+ start_y+" / "+ end_x+" / "+ end_y, Toast.LENGTH_LONG).show();
                double[] latlong ={start_x, start_y, end_x,end_y};
                in.putExtra("some", latlong);
                context.startActivity(in);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "선택 ID delete: " + note.getId(), Toast.LENGTH_LONG).show();
                db.deleteNote(note);
                notesList.remove(note);
                notifyDataSetChanged();
            }
        });

    }
    private void createNote(String start, String end, String start_x, String start_y, String end_x,String end_y) {
        String type = "history";
        long id = db.insertNote(type,start,end,start_x,start_y,end_x,end_y);
        Note n = db.getNote(id);

        if (n != null) {
            // adding new note to array list at 0 position
            notesList.add(0, n);
            notifyDataSetChanged();

            // refreshing the list
            // mAdapter.notifyDataSetChanged();
            //  toggleEmptyNotes();
        }
    }



    @Override
    public int getItemCount() {
        return notesList.size();
    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("yyyy.mm.dd   HH:mm");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
