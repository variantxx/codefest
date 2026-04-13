package com.variantxx.codefest;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CakeViewAdapter extends ArrayAdapter<Cake> {
    public CakeViewAdapter(@NonNull Context context, ArrayList<Cake> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.activty_cakelist, parent, false);

        }
        Cake currentNumberPosition = getItem(position);

        ImageView thumbnail = currentItemView.findViewById(R.id.cake_thumbnail);
        thumbnail.setImageURI(currentNumberPosition.getThumbnail());
        TextView cakeTitle = currentItemView.findViewById(R.id.cake_title);
        cakeTitle.setText(currentNumberPosition.getName());
        TextView cakeDescription = currentItemView.findViewById(R.id.cake_description);
        cakeDescription.setText(currentNumberPosition.getDescription());

        return currentItemView;
    }
}
