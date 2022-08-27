package com.example.forher.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forher.MainActivity;
import com.example.forher.R;
import com.example.forher.authentification.EditProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


import com.google.firebase.firestore.DocumentSnapshot;

public class RaysTestActivity extends AppCompatActivity {

    OkHttpClient okHttpClient;
    Button button;
    String resultImage;
    TextView resultView;
    ImageView xrayIV;
    FirebaseFirestore fstore;
    FirebaseAuth firebaseAuth;
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
    Uri uriImageTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rays_test);
        resultView = findViewById(R.id.description);

        xrayIV = findViewById(R.id.image_added);
        button = findViewById(R.id.xray_test_btn);

        cObjectActivityResultLauncher =
                registerForActivityResult(activityResultContract, uri -> {
                    if (uri != null) {
                        xrayIV.setImageURI(uri);
                        uriImageTest = uri;

                    }
                });
        xrayIV.setOnClickListener(v -> cObjectActivityResultLauncher.launch(null));
        //
        ///////// send to model

        okHttpClient = new OkHttpClient();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String dummyText = editText.getText().toString();


                MultipartBody.Builder multipartBodyBuilder
                        = new MultipartBody.Builder().setType(MultipartBody.FORM);

                //BitmapFactory.Options options = new BitmapFactory.Options();
                BitmapFactory.Options options = new BitmapFactory.Options();
                //options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inPreferredConfig = Bitmap.Config.RGBA_F16;
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();


                //BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = BitmapFactory.decodeFile(uriImageTest.getPath(), options);
                //BitmapFactory.decodeResource(getResources(),R.drawable.doc1,options);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                byte[] byteArray = stream.toByteArray();

                multipartBodyBuilder.addFormDataPart("image", "Android_Flask_" + ".jpg",
                        RequestBody.create(MediaType.parse("image/*jpg"), byteArray));
                RequestBody postBodyImage = multipartBodyBuilder.build();

               /*//RequestBody formbody
                        = new FormBody.Builder()
                        .add("sample", dummyText)
                        .build();*/


                // while building request
                // we give our form
                // as a parameter to post()
                Request request = new
                        Request.Builder().url("http://192.168.1.12:5000/")
                        .post(postBodyImage)
                        .build();


                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(
                            Call call,
                            IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "server down", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response)
                            throws IOException {
                        resultImage = response.body().string();
                        resultView.setText(resultImage);
                       /* if (!response.body().string().isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    resultView.setText(response.body().string());
                                    //Toast.makeText(getApplicationContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                                    //resultView.setText(response.body().string());
                                }
                            });
                        }*/
                    }
                });
            }
        });


//////////////////////////


        //// get user data

        fstore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String id = firebaseAuth.getCurrentUser().getUid();
        ImageView imageUser = findViewById(R.id.profile_edit_image);
        TextView nameUser = findViewById(R.id.pass_change);
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
                    Picasso.with(RaysTestActivity.this)
                            .load(imageUrl)
                            .placeholder(R.drawable.profile)
                            .error(R.drawable.edit_person)
                            .into(imageUser);
                    //Picasso.get().load(imageUrl).into(imageUser);
                    //URL url = new URL(imageUrl);
                    //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    //imageUser.setImageBitmap(bmp);
                    Toast.makeText(RaysTestActivity.this, imageUrl, Toast.LENGTH_SHORT).show();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RaysTestActivity.this, "Error when Load Profil", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(),"Error!!!",Toast.LENGTH_LONG).show();
            }
        });


        /////////////

        Button uploadBtn = findViewById(R.id.xray_upload_btn);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!uriImageTest.getPath().isEmpty()) {

                    // fstore = FirebaseFirestore.getInstance();
                    // firebaseAuth = FirebaseAuth.getInstance();
                    Map<String, Object> Images = new HashMap<>();
                    Images.put("BreastImage", uriImageTest.getPath());
                    Images.put("Result", resultImage);
                    CollectionReference collRef = fstore.collection("Pateints");
                    collRef.document(firebaseAuth.getCurrentUser().
                            getUid()).collection("MammographyImage").add(Images).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Your Breast Image is Upload ", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), " Error Can't Upload  Your Breast Image  ", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), " Not image selected now  ", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


  /*  public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider

            if (isExternalStorageDocument(uri)) {

                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }*/

}