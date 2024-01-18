package com.jmaaix.testttttt;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmaaix.testttttt.DAO.BudgetDao;
import com.jmaaix.testttttt.DAO.FactureDao;
import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.Facture;

import java.io.ByteArrayOutputStream;

public class CameraFragment extends Fragment {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;


    private String email;
    private ImageView imageView;
    private UserDatabase userDatabase;
    private FactureDao factureDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        String loggedInUsername = requireActivity().getIntent().getStringExtra("Email");

        if (loggedInUsername != null && !loggedInUsername.isEmpty()) {
            email = loggedInUsername;
            userDatabase = UserDatabase.getInstance(requireContext());
            factureDao = userDatabase.factureDao();

            // Initialize views
            imageView = view.findViewById(R.id.imageView);
            Button takePictureButton = view.findViewById(R.id.takePictureButton);
            Button consulterButton = view.findViewById(R.id.cons_facture);
            TextView textView = view.findViewById(R.id.idfacturetext);
            // Set a click listener for the button


            takePictureButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check and request camera permission
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkCameraPermission()) {
                            openCamera();
                        } else {
                            requestCameraPermission();
                        }
                    } else {
                        openCamera();
                    }
                }
            });

            consulterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ConsulterFragment consulterFragment = new ConsulterFragment();
                    replaceFragment(consulterFragment);
                }
            });

        }
            return view;

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, proceed to open the camera
                openCamera();
            } else {
                // Camera permission denied, handle accordingly
            }
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // Convert Bitmap to byte array
                byte[] imageByteArray = convertBitmapToByteArray(imageBitmap);

                // Store the image in the database
                storeImageInDatabase(imageByteArray);


                // Do something with the captured image (e.g., display it in an ImageView)
                imageView.setImageBitmap(imageBitmap);

            }
        }
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void storeImageInDatabase(byte[] imageByteArray) {

        long userId = factureDao.getUserIDByEmail(email);
        // Assuming Facture class has a constructor that takes an email and image byte array
        Facture facture = new Facture(userId, imageByteArray);
        factureDao.insertFacture(facture);
    }

        private void replaceFragment(Fragment fragment) {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameH, fragment);
            fragmentTransaction.addToBackStack(null); // Optional: Add the transaction to the back stack
            fragmentTransaction.commit();
        }



    }

