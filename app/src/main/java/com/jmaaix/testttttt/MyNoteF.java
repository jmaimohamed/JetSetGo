package com.jmaaix.testttttt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyNoteF extends Fragment {

    private ActivityResultLauncher<Intent> voiceRecognitionLauncher;

    private LinearLayout notesContainer;
    private List<Note> noteList;

    private View rootView;

    private static final int REQUEST_CODE_VOICE_RECOGNITION = 100;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_note, container, false);

        notesContainer = rootView.findViewById(R.id.notesContainer);
        Button saveButton = rootView.findViewById(R.id.saveButton);
        Button recordVoiceNoteButton = rootView.findViewById(R.id.recordVoiceNoteButton);
        noteList = new ArrayList<>();

        saveButton.setOnClickListener(v -> saveNote());
        recordVoiceNoteButton.setOnClickListener(v -> startVoiceRecognition());
        displayNotes();

        return rootView;
    }

    private void displayNotes() {
        for (Note note : noteList) {
            createNoteView(note);
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Créez le gestionnaire pour la reconnaissance vocale en utilisant ActivityResultContracts
        voiceRecognitionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        ArrayList<String> voiceResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        if (voiceResults != null && !voiceResults.isEmpty()) {
                            String voiceNote = voiceResults.get(0);
                            saveVoiceNoteAndDisplay(voiceNote);
                        }
                    }
                }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_VOICE_RECOGNITION && resultCode == Activity.RESULT_OK) {
            ArrayList<String> voiceResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (voiceResults != null && !voiceResults.isEmpty()) {
                String voiceNote = voiceResults.get(0);
                saveVoiceNoteAndDisplay(voiceNote);
            } else {
                showToast("Aucun résultat vocal valide trouvé. Veuillez réessayer.");
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void saveVoiceNoteAndDisplay(String voiceNote) {
        if (!voiceNote.isEmpty()) {
            Note note = new Note();
            note.setContent(voiceNote);

            noteList.add(note);
            createNoteView(note);
        }
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parlez pour enregistrer votre note...");
        voiceRecognitionLauncher.launch(intent);
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
