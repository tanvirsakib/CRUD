package com.example.crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String WRITER_NAME= "writer_name";
    public static final String WRITER_ID= "writer_id";

    EditText writerNameEditText;
    Spinner writerType;
    Button addWriterButton;
    ListView listView;
    List<Writers> writerList;

    DatabaseReference databaseWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseWriter = FirebaseDatabase.getInstance().getReference("Writers");

        writerNameEditText= findViewById(R.id.writerNameEditText);
        writerType= findViewById(R.id.selectWriterTypeId);
        addWriterButton= findViewById(R.id.addWriterButtonId);
        listView= findViewById(R.id.listViewId);

        writerList = new ArrayList<>();

        addWriterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
                writerNameEditText.setText("");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Writers writers = writerList.get(position);
                Intent intent = new Intent(getApplicationContext(),AddBook.class);

                intent.putExtra(WRITER_ID,writers.getWriterId());
                intent.putExtra(WRITER_NAME,writers.getWriterName());

                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Writers writer = writerList.get(position);
                showUpdateDialog(writer.getWriterId(),writer.getWriterName());
                return true;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseWriter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                writerList.clear();
                for (DataSnapshot writerSnapshot : dataSnapshot.getChildren()){
                    Writers writers = writerSnapshot.getValue(Writers.class);
                    writerList.add(writers);
                }

                WriterList adapter = new WriterList(MainActivity.this,writerList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void showUpdateDialog(final String writerID, String writerName){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_layout,null);
        dialogBuilder.setView(dialogView);

        final EditText nameEditText = dialogView.findViewById(R.id.updateNameEditText);
        final Spinner spinnerType = dialogView.findViewById(R.id.updateWriterTypeId);
        final Button updateButton = dialogView.findViewById(R.id.updateButtonId);
        final Button deleteButton = dialogView.findViewById(R.id.deleteButtonId);

        dialogBuilder.setTitle("Updating Writer "+writerName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String type = spinnerType.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)){

                    if (type.equals("Select writer type")){
                        Toast.makeText(getApplicationContext(),"Select writer type",Toast.LENGTH_SHORT).show();
                    }else {
                        upDateWriter(writerID,name,type);
                        alertDialog.dismiss();
                    }
                }else {
                    writerNameEditText.setError("Enter a name");
                    writerNameEditText.requestFocus();
                    return;
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWriter(writerID);
            }
        });
    }

    private void deleteWriter(String writerID) {

        DatabaseReference dbRefWriter = FirebaseDatabase.getInstance().getReference("Writers").child(writerID);
        DatabaseReference dbRefBook = FirebaseDatabase.getInstance().getReference("books").child(writerID);

        dbRefWriter.removeValue();
        dbRefBook.removeValue();
        Toast.makeText(getApplicationContext(),"Writer Deleted",Toast.LENGTH_SHORT).show();

    }

    private boolean upDateWriter(String id,String name, String type){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Writers").child(id);

        Writers writer = new Writers(id,name,type);
        databaseReference.setValue(writer);
        Toast.makeText(getApplicationContext(),"Writer Updated",Toast.LENGTH_SHORT).show();
        return true;
    }

    private void addArtist(){
        String name = writerNameEditText.getText().toString().trim();
        String type = writerType.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)){

            if (type.equals("Select writer type")){
                Toast.makeText(getApplicationContext(),"Select writer type",Toast.LENGTH_SHORT).show();
            }else {
                String id = databaseWriter.push().getKey();
                Writers writers = new Writers(id,name,type);
                databaseWriter.child(id).setValue(writers);
                Toast.makeText(getApplicationContext(),"Writer added",Toast.LENGTH_SHORT).show();
            }
        }else {
            writerNameEditText.setError("Enter a name");
            writerNameEditText.requestFocus();
            return;
        }
    }
}
