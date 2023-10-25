package com.jmaaix.testttttt;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MyNoteF extends Fragment {
    private LinearLayout notesContainer;
    private List<Note> noteList;

    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_note, container, false);

        notesContainer = rootView.findViewById(R.id.notesContainer);
        Button saveButton = rootView.findViewById(R.id.saveButton);
        noteList = new ArrayList<>();

        saveButton.setOnClickListener(v -> saveNote());

        displayNotes();

        return rootView;
    }

    private void displayNotes() {
        for (Note note : noteList) {
            createNoteView(note);
        }
    }

    //enregistrer une nouvelle note dans une liste de notes
    // en utilisant les données entrées par l'utilisateur dans des champs de texte
    private void saveNote() {
        EditText titleEditText = rootView.findViewById(R.id.titleEditText);
        EditText contentEditText = rootView.findViewById(R.id.contentEditText);
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        if (!title.isEmpty() && !content.isEmpty()) {
            Note note = new Note();
            note.setTitle(title);
            note.setContent(content);

            noteList.add(note);
            createNoteView(note);
            clearInputFields();
        }
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
            noteList.remove(note);
            refreshNoteView();
        }
        private void refreshNoteView () {
            notesContainer.removeAllViews();
            displayNotes();
        }
    }
