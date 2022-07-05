package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
// Widget refers to the elements of the UI (User Interface) that helps user interacts with the Android App.
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//A Toast is a feedback message. It takes a very little space for displaying while overall activity is interactive and visible to the user. It disappears after a few seconds. It disappears automatically. If user wants permanent visible message, Notification can be used.
import java.util.ArrayList;
import java.util.HashSet;
//The java.util.HashSet class implements the Set interface, backed by a hash table.Following are the important points about HashSet − This class makes no guarantees as to the iteration order of the set; in particular, it does not guarantee that the order will remain constant over time.
import static android.media.CamcorderProfile.get;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId;
    EditText titleEditText;
    //ArrayList<String> titles=new ArrayList<>();
    //ArrayAdapter titleArrayAdapter;
    public void searchNearby(View view) {
        String titleString = titleEditText.getText().toString();
        if(titleString.length()==0)
        {
            Toast.makeText(this,"Give some title related to items you need to look for in the note to search it nearby you!",Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(),MapsActivity.class); //MapsActivity class mein jayega
            intent.putExtra("title",titleString); //maps activ
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText=findViewById(R.id.editText);
        titleEditText=findViewById(R.id.titleEditText);
        Button button=findViewById(R.id.button);
        Intent intent = getIntent();
        noteId=intent.getIntExtra("noteId",-1);

        if(noteId!=-1)
        {
            editText.setText(MainActivity.notes.get(noteId));
        }
        else {
            MainActivity.notes.add("");
            noteId=MainActivity.notes.size()-1;
        }
//TextWatcher - When an object of this type is attached to an Editable, its methods will be called when the text is changed.
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MainActivity.notes.set(noteId,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
//notifyDataSetChanged() Notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself.
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);
          
//Shared preferences allow you to store small amounts of primitive data as key/value pairs in a file on the device. To get a handle to a preference file, and to read, write, and manage preference data, use the SharedPreferences class. The Android framework manages the shared preferences file itself.               
                HashSet<String> set = new HashSet<>(MainActivity.notes);

                sharedPreferences.edit().putStringSet("notes",set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
