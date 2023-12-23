package com.jmaaix.testttttt;
import java.util.Collections;

import android.content.Context;
import android.content.DialogInterface;
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
        final NoteDao noteDao = appDatabase.noteDao();
        userDao = appDatabase.userDao();
        User user = userDao.getUserByEmail(this.Email);
        notesContainer = rootView.findViewById(R.id.notesContainer);
        Button saveButton = rootView.findViewById(R.id.saveButton);


        if (user!=null) {
            long userId = noteDao.getUserIDByEmail(this.Email);

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
                        createNoteView();
                    }
                }
            });
            createNoteView();
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



    private void clearInputFields() {
        EditText titleEditText = rootView.findViewById(R.id.titleEditText);
        EditText contentEditText = rootView.findViewById(R.id.contentEditText);

        titleEditText.getText().clear();
        contentEditText.getText().clear();
    }

    //creation et affichage d'une note View dans l'interface utilisateur
    private void createNoteView() {
        // Clear existing notes in the container
        notesContainer.removeAllViews();

        UserDatabase appDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        final NoteDao noteDao = appDatabase.noteDao();
        userDao = appDatabase.userDao();
        User user = userDao.getUserByEmail(this.Email);

        long userId = noteDao.getUserIDByEmail(this.Email);
        List<Note> userNotes = noteDao.getNotesByUserId(userId);

        for (Note note : userNotes) {
            View noteView = getLayoutInflater().inflate(R.layout.note_item, notesContainer, false);
            TextView titleTextView = noteView.findViewById(R.id.titleTextView);
            TextView contentTextView = noteView.findViewById(R.id.contentTextView);

            String title = note.getTitle();
            String content = note.getContent();

            titleTextView.setText(title);
            contentTextView.setText(content);
            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Show a confirmation dialog before deleting the note
                    showDeleteDialog(note);
                }
            });
            notesContainer.addView(noteView);
        }
    }

    private void deleteNoteAndRefresh(Note note) {
        UserDatabase appDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        NoteDao noteDao = appDatabase.noteDao();
        noteDao.deleteNote(note);

        // Remove the corresponding view from the container
        for (int i = 0; i < notesContainer.getChildCount(); i++) {
            View noteView = notesContainer.getChildAt(i);
            // Assuming you have set a tag to each note view with the corresponding note ID
            if (noteView.getTag() != null && noteView.getTag() instanceof Long) {
                long noteId = (Long) noteView.getTag();
                if (noteId == note.getId()) {
                    notesContainer.removeView(noteView);
                    break;
                }
            }
        }
    }

    //gérer la suppression d'une note
    private void showDeleteDialog(final Note note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the note from the database
                        deleteNoteAndRefresh(note);
                        createNoteView();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled the deletion
                    }
                })
                .create()
                .show();
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
