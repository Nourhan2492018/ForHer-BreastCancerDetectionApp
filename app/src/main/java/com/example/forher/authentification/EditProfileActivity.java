package com.example.forher.authentification;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forher.MainActivity;
import com.example.forher.R;

import com.google.firebase.storage.StorageReference;
import com.example.forher.data.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.firebase.storage.FirebaseStorage;

public class EditProfileActivity extends AppCompatActivity {


    ImageView changeImage;
    EditText name, pass, phone;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
    TextView save, close;
    Uri uriImageUser;
    String userID;
    Patient patient;
    FirebaseStorage storage;
    StorageReference storageReference;
    ActivityResultLauncher<Object> cObjectActivityResultLauncher;
    ActivityResultContract<Object, Uri> activityResultContract =
            new ActivityResultContract<Object, Uri>() {
                // core op
                @NonNull
                @Override
                public Intent createIntent(@NonNull Context context, Object input) {
                    return CropImage.activity().setCropShape(CropImageView.CropShape.OVAL)
                            .setAspectRatio(1, 1).getIntent(getApplicationContext());
                }

                /// sort
                @Override
                public Uri parseResult(int resultCode, @Nullable Intent intent) {
                    if (CropImage.getActivityResult(intent) != null) {
                        return CropImage.getActivityResult(intent).getUri();
                    } else {
                        return null;
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        ///

        //// get user data

        fstore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String id = firebaseAuth.getCurrentUser().getUid();
        changeImage = findViewById(R.id.profile_edit_image);
        name = findViewById(R.id.full_name_changed);
        pass = findViewById(R.id.pass_change);
        phone = findViewById(R.id.bio);
///////////////

        DocumentReference docRef = fstore.collection("Pateints")
                .document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                final String name1 = documentSnapshot.getString("Name").toString();
                name.setText(name1);
                //patient.setName(name1);

                final String password1 = documentSnapshot.getString("Password").toString();
                pass.setText(password1);
                //patient.setName(password1);
                final String phone1 = documentSnapshot.getString("Phone").toString();
                phone.setText(phone1);
                //patient.setPhone(phone1);

                if (documentSnapshot.getString("Image Profile") != null) {
                    final String imageUrl = documentSnapshot.
                            getString("Image Profile").toString();
                    Picasso.with(EditProfileActivity.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.edit_person)
                            .into(changeImage);
                    //Picasso.get().load(imageUrl).into(imageUser);
                    //URL url = new URL(imageUrl);
                    //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    //imageUser.setImageBitmap(bmp);
                    Toast.makeText(EditProfileActivity.this, imageUrl, Toast.LENGTH_SHORT).show();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfileActivity.this, "Error when Load Profil", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),"Error!!!",Toast.LENGTH_LONG).show();
            }
        });


        ////////


        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ///


                String password = pass.getText().toString();
                String fullName = name.getText().toString();
                String Phone = phone.getText().toString();
                String Image = uriImageUser.getPath().toString();


                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(EditProfileActivity.this, "Please Enter the password !", Toast.LENGTH_SHORT).show();
                }
                if (password.length() < 6) {
                    Toast.makeText(EditProfileActivity.this, "the password must be greater than 6 character !", Toast.LENGTH_SHORT).show();
                }
                if (phone.length() < 11) {
                    Toast.makeText(EditProfileActivity.this, "Please Enter the Phone !", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(EditProfileActivity.this, "Please Enter the Name !", Toast.LENGTH_SHORT).show();
                }

                userID = firebaseAuth.getCurrentUser().getUid();
                Toast.makeText(getApplicationContext(), userID, Toast.LENGTH_SHORT).show();
                Uri uri1 = uriImageUser;
                StorageReference reference = storage.getReference();
                if (uri1 != null) {
                    StorageReference filepath = reference.child("images/" + UUID.randomUUID().toString());
                    filepath.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    fstore.collection("Pateints").document(userID).
                                            update("Image Profile", String.valueOf(uri));
                                }
                            });

                        }
                    });

                }

                Map<String, Object> patientUsers = new HashMap<>();
                patientUsers.put("Name", fullName);

                patientUsers.put("Phone", Phone);

                patientUsers.put("Password", password);
                //patientUsers.put("Image Profile",finalUri);

                DocumentReference documentReference = fstore.collection
                        ("Pateints").document(userID);
                documentReference.set(patientUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditProfileActivity.this,
                                " OnSuccess Updata User profile !!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditProfileActivity.this, MainActivity.class));


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, "don't Updata !", Toast.LENGTH_SHORT).show();

                    }
                });


                //
            }


        });


        cObjectActivityResultLauncher =
                registerForActivityResult(activityResultContract, uri -> {
                    if (uri != null) {
                        changeImage.setImageURI(uri);
                        uriImageUser = uri;

                    }
                });
        changeImage.setOnClickListener(v -> cObjectActivityResultLauncher.launch(null));
    }
}