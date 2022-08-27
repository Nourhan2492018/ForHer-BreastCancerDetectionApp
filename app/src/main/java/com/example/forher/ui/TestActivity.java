package com.example.forher.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forher.MainActivity;
import com.example.forher.R;
import com.example.forher.authentification.EditProfileActivity;
import com.example.forher.authentification.LoginActivity;
import com.example.forher.authentification.RegisterActivity;
import com.example.forher.data.TestItem;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    RecyclerView testRecyclerView;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5,
            checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;

    Button sumbitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_recycle_layout);

        testRecyclerView = findViewById(R.id.recycler_test);
        TestAdapter testAdapter = new TestAdapter(getQuestion());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        testRecyclerView.setLayoutManager(layoutManager);
        testRecyclerView.setHasFixedSize(true);
        testRecyclerView.setAdapter(testAdapter);
        sumbitBtn = findViewById(R.id.submit_btn);
        sumbitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlertDialogButtonClicked(view);


            }
        });

        ImageView close = findViewById(R.id.back_test);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestActivity.this, MainActivity.class));

            }
        });
    }

    public void showAlertDialogButtonClicked(View view) {

        // setup the alert builder

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Your Test Result");
        builder.setMessage(" more than 50% may be , if you have mammography image can go to Check up...... ");

        // add a button
        builder.setPositiveButton("OK", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    public ArrayList<TestItem> getQuestion() {
        ArrayList<TestItem> getQustions = new ArrayList<>();
        getQustions.add(new TestItem("Have you gained a lot of weight recently?", "A. Yes", "B. Not really"));
        getQustions.add(new TestItem("Do you exercise regularly?", "A. No/I’m not that regular", "B. Totally"));
        getQustions.add(new TestItem("How often do you drink alcohol?", "A. More than once a week", "B. Occasionally/I don’t drink"));
        getQustions.add(new TestItem("Are you on birth control pills or any other hormones?", "A. Yeah! I’m on medication", "B. No"));
        getQustions.add(new TestItem("Have you felt any pain in the nipples lately?", "A. Yes, they hurt when I touch them", "B. Not exactly"));
        getQustions.add(new TestItem("Are you witnessing flaky skin or redness around your nipples?", "A. Yes", "B. No"));
        getQustions.add(new TestItem("Is there a change in either or both of your breasts?", "A. Yeah! They don’t look identical", "B. Not exactly"));
        getQustions.add(new TestItem("Have your breasts been itching lately?", "A. Yes", "B. Only when I sweat a lot, otherwise no"));
        getQustions.add(new TestItem("Are your nipples looking inverted?", "A. Yeah, they look weird", "B. Naah, they look fine"));
        getQustions.add(new TestItem("Have you felt a lump around your breast or under your arms?", "A. Yes", "A. Yes"));

        return getQustions;
    }
}