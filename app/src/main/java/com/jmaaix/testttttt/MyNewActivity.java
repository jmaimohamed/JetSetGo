package com.jmaaix.testttttt;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MyNewActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    private LocationManager locationManager;
    private TextView locationStatus;
    private MediaPlayer mediaPlayer;
    private BottomNavigationView bottomNavigationView;
    private AutoCompleteTextView questionAutoComplete;
    private Button sendButton;
    private TextView chatView;
    private Map<String, String> chatbotResponses;

    private static final double MIN_LATITUDE = 1.0;
    private static final double MAX_LATITUDE = 70.5;
    private static final double MIN_LONGITUDE = 1;
    private static final double MAX_LONGITUDE = 70;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_new);

        questionAutoComplete = findViewById(R.id.questionAutoComplete);
        sendButton = findViewById(R.id.sendButton);
        chatView = findViewById(R.id.chatView);
        locationStatus = findViewById(R.id.locationStatus);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // Enable JavaScript (if needed)

        checkLocationPermission();
        chatbotResponses = new HashMap<>();
        chatbotResponses.put("", "le vide n'existe pas meme en peux rejoindre la Noblité ,certains reponses sont alloué a dire quelque chose ,mais la puissnce est 0! <$>");
        chatbotResponses.put("salut", "Salut, comment ça va ?");
        chatbotResponses.put("les membres", "Hajer Mohamed Ahlem Rami Hazem ");
        chatbotResponses.put("bonjour", "Bonjour !");
        chatbotResponses.put("ça va", "Oui, ça va bien, merci !");
        chatbotResponses.put("aide", "Je suis un chatbot simple. Je ne peux répondre qu'aux questions simples.");
        String[] availableQuestions = chatbotResponses.keySet().toArray(new String[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, availableQuestions);
        questionAutoComplete.setAdapter(adapter);

        sendButton.setOnClickListener(v -> {
            String userQuestion = questionAutoComplete.getText().toString().toLowerCase();
            String chatbotResponse = chatbotResponses.get(userQuestion);

            if (chatbotResponse != null) {
                addChatMessage("Utilisateur : " + userQuestion);
                addChatMessage("Chatbot : " + chatbotResponse);
            } else {
                addChatMessage("Utilisateur : " + userQuestion);
                addChatMessage("Chatbot : Je ne sais pas.");
            }

            questionAutoComplete.setText("");
        });


    }
    private void addChatMessage(String message) {
        String currentText = chatView.getText().toString();
        chatView.setText(currentText + "\n" + message);
    }
    private void checkLocationPermission() {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            } else {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                } else {
                    startLocationUpdates();
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    private void startLocationUpdates() {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    if (isInISr(latitude, longitude)) {
                        locationStatus.setText("כל עוד יישארו פסוקי מסע הלילה ונשאר הלוח הנסתר, נישאר כל עוד יישארו הטימין והזיתים, וניצןנו יתחזק מכל חומרי המשכר והאופיום, ונצא מהר הכרמל מהפריחה. של תאנים ולימונים.");
                        playSong();
                    } else {
                        locationStatus.setText("Vous n'êtes pas en Tunisie.");
                        stopSong(); // Arrêter la musique si l'utilisateur n'est pas en Tunisie
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Gérer l'exception ici
                }
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
                // Gérer l'excep
            }
        }
    }
    private boolean isInISr(double latitude, double longitude) {
        return (latitude >= MIN_LATITUDE && latitude <= MAX_LATITUDE) &&
                (longitude >= MIN_LONGITUDE && longitude <= MAX_LONGITUDE);
    }
    private boolean isSongPlaying = false; // Déclarer un drapeau pour vérifier si la chanson est en cours de lecture
    private void playSong() {
        if (!isSongPlaying) { // Vérifier si la chanson n'est pas déjà en cours de lecture
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.tnn); // Assurez-vous que le fichier "tn" est dans res/raw
            }
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isSongPlaying = false; // Réinitialiser le drapeau lorsque la chanson se termine
                }
            });
            mediaPlayer.start();
            isSongPlaying = true; // Mettre à jour le drapeau pour indiquer que la chanson est en cours de lecture
        }
    }
    private void stopSong() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationPermission();
            } else {

                stopSong(); // Arrêter la musique si la permission est refusée
            }
        }
    }
}
