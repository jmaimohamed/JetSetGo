package com.jmaaix.testttttt;
import java.util.Collections;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.jmaaix.testttttt.DAO.NoteDao;
import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.Note;
import com.jmaaix.testttttt.entities.User;

import java.util.ArrayList;
import java.util.List;

public class MyNoteF extends Fragment implements SensorEventListener {

    private UserDao userDao;
    private LinearLayout notesContainer;
    private List<Note> noteList;
    private NoteDao noteDao;
    private UserDatabase appDatabase;
    private View rootView;
    private long user_id; // This is the user_id of the current user

    // Declare the sensor manager and the light sensor
    private SensorManager sensorManager;
    private Sensor mLight;

    private String Email;
    public MyNoteF() {
        // Required empty public constructor
        }
         public MyNoteF(String Email) {
            // Required empty public constructor
            this.Email = Email;
        }

        public static MyNoteF newInstance(String Email) {
        MyNoteF fragment = new MyNoteF();
        fragment.Email = Email;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_note, container, false);
        UserDatabase appDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        notesContainer = rootView.findViewById(R.id.notesContainer);
        Button saveButton = rootView.findViewById(R.id.saveButton);
        final NoteDao noteDao = appDatabase.noteDao();
        noteList = new ArrayList<>();
        userDao = appDatabase.userDao();
        User user = userDao.getUserByEmail(this.Email);

        if (user!=null) {
            long userId = noteDao.getUserIDByEmail(this.Email);
            Log.d("UserAccountF", "User found: " + user);
            Log.d("UserAccountF", "Email: " + userId);


            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText titleEditText = rootView.findViewById(R.id.titleEditText);
                    EditText contentEditText = rootView.findViewById(R.id.contentEditText);
                    String title = titleEditText.getText().toString();
                    String content = contentEditText.getText().toString();

                    if (!title.isEmpty() && !content.isEmpty()) {
                        Note notenew = new Note(title, content, userId);
                        // Add the new note to the database
                        noteDao.addNote(notenew);
                    }
                }
            });

            noteDao.getNote(userId);
        }
        return rootView;
    }
        @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        // Initialize the sensor manager and the light sensor
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    private void displayNotes() {
        for (Note note : noteList) {
            createNoteView(note);
        }
    }


    //enregistrer une nouvelle note dans une liste de notes
    // en utilisant les données entrées par l'utilisateur dans des champs de texte
    private void saveNote() {

    }
    private void clearInputFields() {
        EditText titleEditText = rootView.findViewById(R.id.titleEditText);
        EditText contentEditText = rootView.findViewById(R.id.contentEditText);

        titleEditText.getText().clear();
        contentEditText.getText().clear();
    }

    //creation et affichage d'une note View dans l'interface utilisateur
    private void createNoteView(final Note note) {
        View noteView = getLayoutInflater().inflate(R.layout.note_item, notesContainer, false);
        TextView titleTextView = noteView.findViewById(R.id.titleTextView);
        TextView contentTextView = noteView.findViewById(R.id.contentTextView);


        titleTextView.setText(note.getTitle());
        contentTextView.setText(note.getContent());

        noteView.setOnLongClickListener(v -> { showDeleteDialog(note);
            return true;
        });
        notesContainer.addView(noteView);
    }

    //gérer la suppression d'une note
    private void showDeleteDialog(final Note note) {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Delete this note.");
            builder.setMessage("Are you sure you want to delete this note?");

            builder.setPositiveButton("Delete", (dialog, which) -> deleteNoteAndRefresh(note));

            builder.setNegativeButton("Cancel", null);
            builder.show();
        }
    }
    private void deleteNoteAndRefresh (Note note){
        UserDatabase userDatabase= UserDatabase.getInstance(getActivity().getApplicationContext());
        NoteDao noteDao = userDatabase.noteDao();
        noteList.remove(note);
        noteDao.deleteNote(note);
        refreshNoteView();
    }
    private void refreshNoteView () {
        notesContainer.removeAllViews();
        displayNotes();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float lux = event.values[0];
        // Do something with this sensor value.
        // For example, update a text view in the fragment layout
        TextView lightTextView = rootView.findViewById(R.id.lightTextView);
        lightTextView.setText("Light intensity: " + lux + " lux");
    }

    // Register and unregister the sensor listener in the fragment lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

}
