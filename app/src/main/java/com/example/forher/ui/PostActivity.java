package com.example.forher.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.forher.R;
import com.example.forher.data.PostItem;
import com.example.forher.data.TestItem;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    RecyclerView postRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postRecyclerView = findViewById(R.id.recycler_view_post);
        PostAdapter postAdapter = new PostAdapter(getPost());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        postRecyclerView.setLayoutManager(layoutManager);
        postRecyclerView.setHasFixedSize(true);
        postRecyclerView.setAdapter(postAdapter);


    }

    public ArrayList<PostItem> getPost() {
        ArrayList<PostItem> getPosts = new ArrayList<>();
        getPosts.add(new PostItem("Dr.Amira Mohamed", R.drawable.amira, "The radiology was diagnosed by Dr.Amira as benign tumor stage 1 ", R.drawable.beginn1, 30));
        getPosts.add(new PostItem("Dr.Norhan Mahmoud", R.drawable.nour, "The radiology was diagnosed by Dr.Norhan as benign tumor stage 2 ", R.drawable.beginn2, 70));
        getPosts.add(new PostItem("Dr.Sara Ali", R.drawable.doc1, "The radiology was diagnosed by Dr.ٍSara as benign tumor stage 3 ", R.drawable.beginn3, 110));
        getPosts.add(new PostItem("Dr.Hatem Mohamed", R.drawable.doc2, "The radiology was diagnosed by Dr.Hatem as malignant tumor stage 4 ", R.drawable.beginn4, 20));
        getPosts.add(new PostItem("Dr.Khaled El_Hossiny", R.drawable.doc3, "The radiology was diagnosed by Dr.Khaled as malignant tumor stage 1 ", R.drawable.malignn1, 250));
        getPosts.add(new PostItem("Dr.Marko Ezzat", R.drawable.doc5, "The radiology was diagnosed by Dr.Marko as malignant tumor stage 2 ", R.drawable.malignn2, 30));
        getPosts.add(new PostItem("Dr.Mahy Reda", R.drawable.doc4, "The radiology was diagnosed by Dr.Mahy as malignant tumor stage 3 ", R.drawable.malignn3, 60));
        getPosts.add(new PostItem("Dr.Ganna Esmail", R.drawable.doc2, "The radiology was diagnosed by Dr.Ganna as malignant tumor stage 4 ", R.drawable.malignnt4, 50));

        return getPosts;

    }
}