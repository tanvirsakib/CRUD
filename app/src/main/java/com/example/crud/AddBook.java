package com.example.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddBook extends AppCompatActivity {

    TextView artistNameTextView;
    EditText bookNameEditText;
    RatingBar ratingBar;
    Button addBookButton;

    ListView bookListView;

    List<Books> books;
    DatabaseReference databaseBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        artistNameTextView = findViewById(R.id.writerNameTextView);
        bookNameEditText = findViewById(R.id.bookNameEditText);
        ratingBar = findViewById(R.id.ratingBarId);
        addBookButton = findViewById(R.id.addBookButtonId);
        bookListView = findViewById(R.id.BooklistViewId);

        books = new ArrayList<>();

        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.WRITER_ID);
        String name = intent.getStringExtra(MainActivity.WRITER_NAME);

        artistNameTextView.setText(name);

        databaseBooks = FirebaseDatabase.getInstance().getReference("books").child(id);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseBooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                books.clear();
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()){
                    Books book = bookSnapshot.getValue(Books.class);
                    books.add(book);
                }
                BookList adapter = new BookList(AddBook.this,books);
                bookListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveBook() {

        String bookName = bookNameEditText.getText().toString().trim();
        float rating = ratingBar.getProgress();


        if (!TextUtils.isEmpty(bookName)){

            String id = databaseBooks.push().getKey();
            Books book= new Books(id,bookName,rating);
            databaseBooks.child(id).setValue(book);
            bookNameEditText.setText("");
            Toast.makeText(getApplicationContext(),"Book added",Toast.LENGTH_SHORT).show();

        }else {
            bookNameEditText.setError("Enter a book name");
            bookNameEditText.requestFocus();
            return;
        }
    }
}
