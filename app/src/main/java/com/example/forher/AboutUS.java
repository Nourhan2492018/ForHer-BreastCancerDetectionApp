package com.example.forher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutUS extends AppCompatActivity {

    final String faceBookUri = "https://www.facebook.com/BaheyaFoundation/", youtubeUri = "https://www.nationalbreastcancer.org/breast-self-exam",
            linedInUri = "https://www.linkedin.com/company/world-health-organization/";

    ImageView facebookImage, youtubeImage, linkedInImage, instigramImage, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        facebookImage = findViewById(R.id.img_faceboo);
        facebookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent faceookInIntent = new Intent(Intent.ACTION_VIEW);
                faceookInIntent.setData(Uri.parse(faceBookUri));
                startActivity(faceookInIntent);
            }
        });
        linkedInImage = findViewById(R.id.img_linkedin);
        linkedInImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linkedInIntent = new Intent(Intent.ACTION_VIEW);
                linkedInIntent.setData(Uri.parse(linedInUri));
                startActivity(linkedInIntent);

            }
        });
        youtubeImage = findViewById(R.id.img_youtube);
        youtubeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW);
                youtubeIntent.setData(Uri.parse(youtubeUri));
                startActivity(youtubeIntent);
            }
        });

        back = findViewById(R.id.back_about);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}