package com.example.forher.authentification;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forher.MainActivity;
import com.example.forher.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.firebase.storage.StorageReference;

public class RegisterActivity extends AppCompatActivity {
    private StorageReference Folder;

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


    Uri uriImageUser, finalUri;
    String userID;
    private FirebaseFirestore fstore;
    private FirebaseAuth fAuth;
    private EditText phone, user, age, email, Password;
    private Button btnRegister;

    private ImageView imageView;

    TextView login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //
        imageView = findViewById(R.id.img_register);
        login = findViewById(R.id.txt_login);

        cObjectActivityResultLauncher =
                registerForActivityResult(activityResultContract, uri -> {
                    if (uri != null) {
                        imageView.setImageURI(uri);
                        uriImageUser = uri;

                    }
                });
        imageView.setOnClickListener(v -> cObjectActivityResultLauncher.launch(null));


        ///
        //final TextView login=findViewById(R.id.txt_register);

        phone = findViewById(R.id.name);
        user = findViewById(R.id.pass_change);
        age = findViewById(R.id.Age);
        email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

        btnRegister = findViewById(R.id.register);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        /*if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }*/

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = email.getText().toString();
                String password = Password.getText().toString();
                String fullName = user.getText().toString();
                String Age = age.getText().toString();
                String Phone = phone.getText().toString();
                String Image = uriImageUser.getPath().toString();
                if (TextUtils.isEmpty(Email)) {

                    Toast.makeText(RegisterActivity.this, "Please Enter the Email !", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Please Enter the password !", Toast.LENGTH_SHORT).show();
                }
                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "the password must be greater than 6 character !", Toast.LENGTH_SHORT).show();
                }
                if (phone.length() < 11) {
                    Toast.makeText(RegisterActivity.this, "Please Enter the Phone !", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(RegisterActivity.this, "Please Enter the Name !", Toast.LENGTH_SHORT).show();
                }


                fAuth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), " profile Created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
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


                            Toast.makeText(RegisterActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            Map<String, Object> patientUsers = new HashMap<>();
                            patientUsers.put("Name", fullName);
                            patientUsers.put("Email", Email);
                            patientUsers.put("Phone", Phone);
                            patientUsers.put("Age", Age);
                            patientUsers.put("Password", password);
                            //patientUsers.put("Image Profile",finalUri);

                            DocumentReference documentReference = fstore.collection
                                    ("Pateints").document(userID);
                            documentReference.set(patientUsers).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),
                                            " OnSuccess Creating User profile !!", Toast.LENGTH_SHORT).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "don't save !", Toast.LENGTH_SHORT).show();

                                }
                            });

                            /*if (TextUtils.isEmpty(fullName)) {
                                patientUsers.put("Image Profile", "https://rmvn.cdn.jaysoft.asia/wp-content/uploads/2021/10/shutterstock_1697510932.jpg");
                            }
                            else {
                                patientUsers.put("Image Profile", Image);
                            }*/

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });

    }

    public String getextension(Uri uri) {

        ContentResolver cr = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uriImageUser));
    }


    public void upload() {
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
                            Toast.makeText(RegisterActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            fstore.collection("Pateints").document(userID)
                                    .update("Image Profile", uri.getPath());
                        }
                    });

                }
            });
        }
    }
}