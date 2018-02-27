package com.example.good.automotellogin.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.good.automotellogin.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by GOOD on 06/04/2016.
 */
public class PostReview extends AppCompatActivity {
    public static final String UPLOAD_URL = "http://simplifiedcoding.16mb.com/ImageUpload/upload.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";
    private int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Button upload,btnclick;
    private Bitmap bitmap;
    String ba1;
        String Picturepath;
        private Uri selectedimage;
    private Uri fileUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_review);
        final Animation animalpha = AnimationUtils.loadAnimation(this,R.anim.anim_button_alpha);
        btnclick = (Button) findViewById(R.id.ClicktoUploadImage);
        upload = (Button) findViewById(R.id.upload_image_button);
        btnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animalpha);
                clickpic();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animalpha);
                uploadImage();
            }
        });

    }

    private void uploadImage() {
        Log.i(getClass().getSimpleName(), "URl "+ Picturepath);

        Bitmap bm = BitmapFactory.decodeFile(Picturepath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
        byte[] imagebytes= byteArrayOutputStream.toByteArray();
        ba1 = Base64.encodeToString(imagebytes,Base64.DEFAULT);
        Log.i("Base64...", "  "+ba1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 &&resultCode == RESULT_OK)
        {
           selectedimage = data.getData();
           // bitmap = (Bitmap) data.getExtras().get("data");
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedimage);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            String[] filepathColumn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedimage, filepathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filepathColumn[0]);
//            Picturepath = cursor.getString(columnIndex);
//            cursor.close();

//            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView= (ImageView) findViewById(R.id.uploaded_image);
            imageView.setImageBitmap(bitmap);
        }
    }

    private void clickpic() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(PostReview.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,100);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            100);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

//        if (getApplicationContext().getPackageManager().hasSystemFeature(
//                PackageManager.FEATURE_CAMERA)) {
//            // Open default camera
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//
//            // start the image capture Intent
//            startActivityForResult(intent, 100);
//
//        } else {
//            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG).show();
//
//        }
   }
}
