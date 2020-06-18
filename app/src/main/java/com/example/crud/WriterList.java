package com.example.crud;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WriterList extends ArrayAdapter<Writers> {

    private Activity context;
    private List<Writers> writerList;

    public WriterList(Activity context, List<Writers> writerList){
        super(context,R.layout.list_layout,writerList);
        this.context= context;
        this.writerList=writerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);

        TextView nameTextView = listViewItem.findViewById(R.id.nameTextView);
        TextView typeTextView = listViewItem.findViewById(R.id.typeTextView);

        Writers writer = writerList.get(position);
        nameTextView.setText(writer.getWriterName());
        typeTextView.setText(writer.getWriterType());

        return listViewItem;

    }

}
