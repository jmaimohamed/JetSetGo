package com.jmaaix.testttttt;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.jmaaix.testttttt.DAO.ReclamationDao;
import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.Reclamation;
import com.jmaaix.testttttt.entities.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MyReclamationF extends Fragment {
    private UserDao userDao;
    private LinearLayout reclamationsContainer;
    private List<Reclamation> reclamationList;
    private ReclamationDao reclamationDao;
    private UserDatabase appDatabase;
    private View rootView;
    private long user_id; // This is the user_id of the current user

    // Declare other necessary variables and components here
    private String Email;
    private Reclamation selectedReclamation;
    private View phone;

    public MyReclamationF() {
        // Required empty public constructor
    }

    public MyReclamationF(String Email) {
        this.Email = Email;
    }

    public static MyReclamationF newInstance(String Email) {
        MyReclamationF fragment = new MyReclamationF();
        fragment.Email = Email;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_reclamation, container, false);
        UserDatabase appDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        final ReclamationDao reclamationDao = appDatabase.reclamationDao();
        userDao = appDatabase.userDao();
        User user = userDao.getUserByEmail(this.Email);
        reclamationsContainer = rootView.findViewById(R.id.reclamationsContainer);
        Button saveButton = rootView.findViewById(R.id.saveButton);
        Button buttonchat = rootView.findViewById(R.id.buttonchat);
        Button phone = rootView.findViewById(R.id.phone);
        buttonchat.setOnClickListener(v -> {
            animateButtonZoom(v);
            Intent intent = new Intent(getActivity(), MyNewActivity.class);
            startActivity(intent);
        });
        phone.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:21108700"));
            startActivity(callIntent);
        });

        if (user != null) {
            long userId = reclamationDao.getUserIdByEmail(this.Email);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animateButtonZoom(v);
                    EditText titleEditText = rootView.findViewById(R.id.titleEditText);
                    EditText contentEditText = rootView.findViewById(R.id.contentEditText);
                    String title = titleEditText.getText().toString();
                    String content = contentEditText.getText().toString();

                    if (!title.isEmpty() && !content.isEmpty()) {
                        // Check if the reclamation with the same title and description already exists
                        if (!isReclamationAlreadyExists(title, content)) {
                            Reclamation reclamation = new Reclamation(title, content, userId);
                            reclamationDao.addReclamation(reclamation);
                            createReclamationView();
                            clearInputFields();
                        } else {
                            // Show an alert or toast indicating that the reclamation already exists
                            // For example, you can replace the toast with an AlertDialog
                            new AlertDialog.Builder(getActivity())
                                    .setMessage("A reclamation with the same title and description already exists.")
                                    .setPositiveButton("OK", null)
                                    .show();
                        }
                    }
                }
            });
            createReclamationView();
        }
        return rootView;
    }
    private void animateButtonZoom(View view) {
        // Changement de couleur (vert à rouge)
        ObjectAnimator colorAnimator = ObjectAnimator.ofArgb(view, "backgroundColor", 0xFF00FF00, 0xFFFF0000);

        // Zoom
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1.1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1.1f);

        // Rotation 3D
        ObjectAnimator rotateX = ObjectAnimator.ofFloat(view, "rotationX", 0f, 360f);

        // Translation en Y
        ObjectAnimator translateY = ObjectAnimator.ofFloat(view, "translationY", 0f, -20f); // Vers le haut de 20 pixels

        // Durées
        colorAnimator.setDuration(400);
        scaleX.setDuration(200);
        scaleY.setDuration(200);
        rotateX.setDuration(400);
        translateY.setDuration(200);

        // Répétitions et modes de répétition
        colorAnimator.setRepeatCount(1);
        scaleX.setRepeatCount(1);
        scaleY.setRepeatCount(1);
        rotateX.setRepeatCount(1);
        translateY.setRepeatCount(1);

        colorAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);
        rotateX.setRepeatMode(ObjectAnimator.REVERSE);
        translateY.setRepeatMode(ObjectAnimator.REVERSE);

        // Création de l'ensemble d'animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(colorAnimator, scaleX, scaleY, rotateX, translateY);

        // Démarrer l'animation
        animatorSet.start();
    }

    private void animateText(TextView textView) {
        // Changement de couleur du texte (noir à rouge)
        ObjectAnimator textColorAnimator = ObjectAnimator.ofArgb(textView, "textColor", 0xFF000000, 0xFFFF0000);

        // Translation en Y
        ObjectAnimator translateY = ObjectAnimator.ofFloat(textView, "translationY", 0f, -20f); // Vers le haut de 20 pixels

        // Durées
        textColorAnimator.setDuration(400);
        translateY.setDuration(200);

        // Répétitions et modes de répétition
        textColorAnimator.setRepeatCount(1);
        translateY.setRepeatCount(1);

        textColorAnimator.setRepeatMode(ObjectAnimator.REVERSE);
        translateY.setRepeatMode(ObjectAnimator.REVERSE);

        // Création de l'ensemble d'animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(textColorAnimator, translateY);

        // Démarrer l'animation
        animatorSet.start();
    }





    private boolean isReclamationAlreadyExists(String title, String content) {
        UserDatabase appDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        ReclamationDao reclamationDao = appDatabase.reclamationDao();
        List<Reclamation> existingReclamations = reclamationDao.getReclamationsByTitleAndContent(title, content);
        return !existingReclamations.isEmpty();
    }

    private void clearInputFields() {
        EditText titleEditText = rootView.findViewById(R.id.titleEditText);
        EditText contentEditText = rootView.findViewById(R.id.contentEditText);

        titleEditText.getText().clear();
        contentEditText.getText().clear();
    }

    private void createReclamationView() {
        reclamationsContainer.removeAllViews();
        CountDownTimer countDownTimer;
        List<TextView> textViewsList;

        UserDatabase appDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        final ReclamationDao reclamationDao = appDatabase.reclamationDao();
        userDao = appDatabase.userDao();
        User user = userDao.getUserByEmail(this.Email);

        long userId = reclamationDao.getUserIdByEmail(this.Email);
        List<Reclamation> userReclamations = reclamationDao.getReclamationsByUserId(userId);

        for (Reclamation reclamation : userReclamations) {
            View reclamationView = getLayoutInflater().inflate(R.layout.reclamation_item, reclamationsContainer, false);
            TextView titleTextView = reclamationView.findViewById(R.id.titleTextView);
            TextView contentTextView = reclamationView.findViewById(R.id.contentTextView);
            TextView dateTextView = reclamationView.findViewById(R.id.dateTextView);
            animateText(titleTextView);
            animateText(contentTextView);
            animateText( dateTextView);
            ImageView deleteImageView = reclamationView.findViewById(R.id.deleteImageView);
            ImageView updateImageView = reclamationView.findViewById(R.id.updateImageView);

            String title = reclamation.getTitle();
            String content = reclamation.getContent();
            Date creationDate = reclamation.getCreationDate(); // Get the creation date

            titleTextView.setText(title);
            contentTextView.setText(content);

            // Display the creation date
            if (creationDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = sdf.format(creationDate);
                dateTextView.setText("Created on: " + formattedDate);
            }
            animateImagesWithDifferentColors(deleteImageView, updateImageView);
            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animateOnClick(deleteImageView);
                    // Afficher la boîte de dialogue de confirmation
                    showDeleteDialog(reclamation);
                }
            });

            updateImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animateOnClick(updateImageView);
                    // Afficher la boîte de dialogue de mise à jour
                    showUpdateDialog(reclamation);
                }
            });

            reclamationView.setTag(reclamation.getId());
            reclamationsContainer.addView(reclamationView);
        }
    }

    private void animateOnClick(View view) {
        // Scale down and up animation on click
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.8f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.8f);

        scaleX.setDuration(200);
        scaleY.setDuration(200);
        scaleX.setRepeatCount(1);
        scaleY.setRepeatCount(1);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY);
        animatorSet.start();

        // Add any other animations or logic you want for the click event
    }

    private void animateImagesWithDifferentColors(ImageView... imageViews) {
        Handler handler = new Handler();
        Random random = new Random();

        for (ImageView imageView : imageViews) {
            handler.postDelayed(() -> {
                // Generate random colors
                int randomColorStart = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                int randomColorEnd = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

                // Color change animation for image views
                ObjectAnimator imageColorAnimator = ObjectAnimator.ofArgb(imageView.getDrawable(), "color", randomColorStart, randomColorEnd);

                // Rotation animation
                ObjectAnimator rotate = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 360f);

                // Durations
                imageColorAnimator.setDuration(400);
                rotate.setDuration(400);

                // Repetitions and repeat modes
                imageColorAnimator.setRepeatCount(1);
                rotate.setRepeatCount(1);

                imageColorAnimator.setRepeatMode(ObjectAnimator.REVERSE);
                rotate.setRepeatMode(ObjectAnimator.REVERSE);

                // Animation set
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(imageColorAnimator, rotate);

                // Start the animation
                animatorSet.start();
            }, 3000); // Delayed by 3 seconds for the next iteration
        }
    }

    private void deleteReclamationAndRefresh(Reclamation reclamation) {
        UserDatabase appDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
        ReclamationDao reclamationDao = appDatabase.reclamationDao();
        reclamationDao.deleteReclamation(reclamation);

        for (int i = 0; i < reclamationsContainer.getChildCount(); i++) {
            View reclamationView = reclamationsContainer.getChildAt(i);
            if (reclamationView.getTag() != null && reclamationView.getTag() instanceof Long) {
                long reclamationId = (Long) reclamationView.getTag();
                if (reclamationId == reclamation.getId()) {
                    reclamationsContainer.removeView(reclamationView);
                    break;
                }
            }
        }
    }

    private void showDeleteDialog(final Reclamation reclamation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete this reclamation?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteReclamationAndRefresh(reclamation);
                        createReclamationView();
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

    private void showUpdateDialog(final Reclamation reclamation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_reclamation, null);
        builder.setView(dialogView)
                .setTitle("Update Reclamation")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText updatedTitleEditText = dialogView.findViewById(R.id.updatedTitleEditText);
                        EditText updatedContentEditText = dialogView.findViewById(R.id.updatedContentEditText);

                        String newTitle = updatedTitleEditText.getText().toString();
                        String newContent = updatedContentEditText.getText().toString();

                        if (!newTitle.isEmpty() && !newContent.isEmpty()) {
                            reclamation.setTitle(newTitle);
                            reclamation.setContent(newContent);

                            UserDatabase appDatabase = UserDatabase.getInstance(getActivity().getApplicationContext());
                            ReclamationDao reclamationDao = appDatabase.reclamationDao();
                            reclamationDao.updateReclamation(reclamation);

                            clearInputFields();
                            createReclamationView();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // User canceled the update
                    }
                })
                .create()
                .show();
    }
}