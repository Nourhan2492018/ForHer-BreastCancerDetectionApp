package com.example.forher;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.forher.authentification.EditProfileActivity;
import com.example.forher.notification.MyReceiver;
import com.example.forher.ui.PostActivity;
import com.example.forher.ui.RaysTestActivity;
import com.example.forher.ui.TestActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import android.widget.TextView;

import com.squareup.picasso.Picasso;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    CardView testQuestionBtn, checkUpBtn, postsBtn, aboutUsBtn;
    final String faceBookUri = "https://www.facebook.com/BaheyaFoundation/", youtubeUri = "https://www.nationalbreastcancer.org/breast-self-exam",
            linedInUri = "https://www.linkedin.com/company/world-health-organization/";

    ImageView editProfile, facebookImage, youtubeImage, linkedInImage, recrute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();

        //// get user data

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        ImageView imageUser = findViewById(R.id.image_main_activit);
        TextView nameUser = findViewById(R.id.user_name_main_activity);
///////////////
        String name = "oooo";
        DocumentReference docRef = fstore.collection("Pateints")
                .document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                final String name1 = documentSnapshot.getString("Name").toString();
                nameUser.setText(name1);

                if (documentSnapshot.getString("Image Profile") != null) {
                    final String imageUrl = documentSnapshot.
                            getString("Image Profile").toString();
                    Picasso.with(MainActivity.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.edit_person)
                            .into(imageUser);
                    //Picasso.get().load(imageUrl).into(imageUser);
                    //URL url = new URL(imageUrl);
                    //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    //imageUser.setImageBitmap(bmp);
                    //Toast.makeText(MainActivity.this, imageUrl, Toast.LENGTH_SHORT).show();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Error when Load Profil", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),"Error!!!",Toast.LENGTH_LONG).show();
            }
        });


        ///
        testQuestionBtn = findViewById(R.id.card_crew);
        testQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TestActivity.class);
                startActivity(i);
            }
        });

        aboutUsBtn = findViewById(R.id.card_about);
        aboutUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AboutUS.class);
                startActivity(i);
            }
        });

        checkUpBtn = findViewById(R.id.card_event);
        checkUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RaysTestActivity.class);
                startActivity(i);
            }
        });

        postsBtn = findViewById(R.id.card_project);
        postsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(i);
            }
        });

        facebookImage = findViewById(R.id.bottom_facebook);
        facebookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent faceookInIntent = new Intent(Intent.ACTION_VIEW);
                faceookInIntent.setData(Uri.parse(faceBookUri));
                startActivity(faceookInIntent);
            }
        });
        linkedInImage = findViewById(R.id.bottom_linkedin);
        linkedInImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linkedInIntent = new Intent(Intent.ACTION_VIEW);
                linkedInIntent.setData(Uri.parse(linedInUri));
                startActivity(linkedInIntent);
            }
        });
        //
        editProfile = findViewById(R.id.edit_nav_btn);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileInIntent = new Intent(getApplicationContext(), EditProfileActivity.class);

                startActivity(profileInIntent);
            }
        });
        ///
        youtubeImage = findViewById(R.id.bottm_youtube);
        youtubeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW);
                youtubeIntent.setData(Uri.parse(youtubeUri));
                startActivity(youtubeIntent);
            }
        });
        recrute = findViewById(R.id.bottom_recrutment_btn);
        recrute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recrutmentInIntent = new Intent(Intent.ACTION_VIEW);
                recrutmentInIntent.setData(Uri.parse("https://www.treatedwell.com/breast-cancer-quiz/"));
                startActivity(recrutmentInIntent);
            }
        });


    }


    private void creatNotify() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 35);

        long secound = 1000 * 10;
        Intent intent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        long time = System.currentTimeMillis();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time + secound, AlarmManager.INTERVAL_DAY, pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel("notify", "my channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

}
