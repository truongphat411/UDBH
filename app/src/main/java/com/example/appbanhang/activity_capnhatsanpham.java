package com.example.appbanhang;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.appbanhang.models.SanPham;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class activity_capnhatsanpham extends AppCompatActivity {
    EditText edttenSP,edtgiaSP,edtmotaSP,edtmathuonghieu,edtsoluongKho;
    ImageView imvhinhSP;
    Button btncapnhatSP,btnxoaSP;
    String idSP;
    DatabaseReference reference;
    ImageButton imgBack,imgcamera;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    String currentPhotoPath;
    StorageReference storageReference;
    String hinhSP;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietcapnhat);
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://udbh-35c1f.appspot.com");
        anhxa();
        btncapnhatSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    CapNhatSP();
            }
        });
        btnxoaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaSP();
            }
        });
        imgcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });
        imvhinhSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });
    }
    private void anhxa() {
        edttenSP = findViewById(R.id.edttenSP);
        edtgiaSP = findViewById(R.id.edtgiaSP);
        edtmotaSP = findViewById(R.id.edtmotaSP);
        edtmathuonghieu = findViewById(R.id.edtmathuonghieu);
        edtsoluongKho =findViewById(R.id.edtsoluongkho);
        imvhinhSP = findViewById(R.id.imvhinhSP);
        btncapnhatSP = findViewById(R.id.btncapnhatSP);
        btnxoaSP = findViewById(R.id.btnxoaSP);
        imgBack = findViewById(R.id.imgBack);
        imgcamera = findViewById(R.id.imgcamera);
        loadData();
    }
    private void loadData(){
        Intent intent = getIntent();
        edttenSP.setText(intent.getStringExtra("tenSP"));
        idSP =  intent.getStringExtra("idSP");
        edtgiaSP.setText(String.valueOf(intent.getIntExtra("giaSP",0)));
        edtmotaSP.setText(intent.getStringExtra("motaSP"));
        edtsoluongKho.setText(String.valueOf(intent.getIntExtra("soluongKho",0)));
        Uri myUri = Uri.parse(intent.getStringExtra("hinhSP"));
        Picasso.with(activity_capnhatsanpham.this).load(myUri).placeholder(R.drawable.image).into(imvhinhSP);
        edtmathuonghieu.setText(intent.getStringExtra("idTH"));
    }
    private void CapNhatSP(){
        reference = FirebaseDatabase.getInstance().getReference().child("sanpham");
        Query query = reference.orderByChild("idSP").equalTo(idSP);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    reference.child(String.valueOf(idSP)).child("tenSP").setValue(edttenSP.getText().toString().trim());
                    reference.child(String.valueOf(idSP)).child("giaSP").setValue(Integer.parseInt(edtgiaSP.getText().toString().trim()));
                    reference.child(String.valueOf(idSP)).child("motaSP").setValue(edtmotaSP.getText().toString().trim());
                    reference.child(String.valueOf(idSP)).child("soluongKho").setValue(Integer.parseInt(edtsoluongKho.getText().toString().trim()));
                    reference.child(String.valueOf(idSP)).child("idTH").setValue(Integer.parseInt(edtmathuonghieu.getText().toString().trim()));
                    Toast.makeText(activity_capnhatsanpham.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void XoaSP() {
        reference = FirebaseDatabase.getInstance().getReference().child("sanpham");
        Query query = reference.orderByChild("idSP").equalTo(idSP);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        ds.getRef().removeValue();
                    }
                    Toast.makeText(activity_capnhatsanpham.this,"Xóa sản phẩm thành công",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void askCameraPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else {
            dispatchTakePictureIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                File f = new File(currentPhotoPath);
                imvhinhSP.setImageURI(Uri.fromFile(f));
                Log.d("tag", "ABsolute Url of Image is " + Uri.fromFile(f));
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
                uploadImageToFirebase(f.getName(), contentUri);
            }
        }
            if (requestCode == GALLERY_REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    Uri contentUri = data.getData();
                    @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
                    Log.d("tag", "onActivityResult: Gallery Image Uri:  " + imageFileName);
                    imvhinhSP.setImageURI(contentUri);
                    uploadImageToFirebase(imageFileName, contentUri);
                }
            }
    }
        private String getFileExt(Uri contentUri) {
            ContentResolver c = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(c.getType(contentUri));
        }
    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = storageReference.child("pictures/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("hihi", "onSuccess: Uploaded Image URl is " + uri.toString());
                        hinhSP = uri.toString();
                    }
                });
                Toast.makeText(activity_capnhatsanpham.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity_capnhatsanpham.this, "Upload Failled.",Toast.LENGTH_SHORT).show();
            }
        });
    }
        private File createImageFile() throws IOException {
            // Create an image file name
            @SuppressLint("SimpleDateFormat")
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
       //     File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();
            return image;
        }
        @SuppressLint("QueryPermissionsNeeded")
        private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "net.smallacademy.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                }
            }
        }
    @Override
    protected void onStart() {
        super.onStart();
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.aqua));
        }
    }
}
