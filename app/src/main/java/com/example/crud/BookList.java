package com.example.crud;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class BookList extends ArrayAdapter<Books> {

    private Activity context;
    private List<Books> books;


    public BookList(@NonNull Activity context, List<Books> books) {
        super(context, R.layout.booklist_layout,books);
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.booklist_layout,null,true);

        TextView nameTextView = listViewItem.findViewById(R.id.nameTextView);
        TextView ratingBar = listViewItem.findViewById(R.id.ratingTextView);

        Books book = books.get(position);
        nameTextView.setText(book.getBookName());
        ratingBar.setText("Rating: "+String.valueOf(book.getBookRating()));

        return listViewItem;

    }
}
