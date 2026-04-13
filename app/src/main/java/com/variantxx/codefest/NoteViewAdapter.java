package com.variantxx.codefest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class NoteViewAdapter extends ArrayAdapter<Note> {
    public NoteViewAdapter(@NonNull Context context, ArrayList<Note> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.activtiy_notelist, parent, false);
        }
        Note currentNumberPosition = getItem(position);
        TextView title = currentItemView.findViewById(R.id.textview1);
        title.setText(currentNumberPosition.getTitle());
        TextView content = currentItemView.findViewById(R.id.textview2);
        content.setText(currentNumberPosition.getContent());

        return currentItemView;
    }
}
