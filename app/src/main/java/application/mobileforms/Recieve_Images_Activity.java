package application.mobileforms;

/**
 * Created by Inas on 10-Feb-18.
 */

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.text.SimpleDateFormat;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import android.content.Context;

public class Recieve_Images_Activity extends Fragment {
    // Folder path for Firebase Storage.
    String Storage_Path = "All_Image_Uploads/";

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "All_Image_Uploads_Database";
    public static String ImageUploadId;
    //SendMsg SM;

    // Creating button.
    Button ChooseButton, UploadButton, DisplayImageButton, CaptureBtn;


    // Creating EditText.
    EditText ImageName;

    // Creating ImageView.
    ImageView SelectImage;

    // Creating URI.
    Uri FilePathUri;

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference,lastImageRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    // Image Select request code for onActivityResult() .
    int Image_Request_Code = 7;

    // Image Capture request code for onActivityResult() .
    private final static int CAMERA_REQ_CODE = 0;
    private String userChoosenTask;

    //Assign TAG
    final String TAG = "ERROR";

    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.recieve_images_activity, null);
        try {
            // Assign FirebaseStorage instance to storageReference.
            storageReference = FirebaseStorage.getInstance().getReference();

            // Assign FirebaseDatabase instance with root database name.

            firebaseAuth = FirebaseAuth.getInstance();
            currentUser = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getDisplayName()).child(Database_Path);
            lastImageRef=databaseReference.child("Last Image");
            //Assign ID'S to button.
            ChooseButton = (Button) view.findViewById(R.id.ButtonChooseImage);
            UploadButton = (Button) view.findViewById(R.id.ButtonUploadImage);
            CaptureBtn = (Button) view.findViewById(R.id.Capture);

            //DisplayImageButton = (Button) view.findViewById(R.id.DisplayImagesButton);

            // Assign ID's to EditText.
            ImageName = (EditText) view.findViewById(R.id.ImageNameEditText);

            // Assign ID'S to image view.
            SelectImage = (ImageView) view.findViewById(R.id.ShowImageView);

            // Assigning Id to ProgressDialog.
            progressDialog = new ProgressDialog(getActivity());


            // Adding click listener to Choose image button.
            ChooseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Creating intent.
                    Intent intent = new Intent();

                    // Setting intent type as image to select image from phone storage.
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);

                }
            });


            // Adding click listener to Upload image button.
            UploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Calling method to upload selected image on Firebase storage.
                    UploadImageFileToFirebaseStorage();

                }
            });


            CaptureBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cameraIntent();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), FilePathUri);
                // Setting up bitmap selected image into ImageView.
                SelectImage.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                ChooseButton.setText("Image Selected");

            } catch (IOException e) {

                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQ_CODE && resultCode == RESULT_OK) {
           // Toast.makeText(getActivity(), "CAMERA REQ CODE", Toast.LENGTH_SHORT).show();
            onCaptureImageResult(data);
            //Toast.makeText(getActivity(), "On capture", Toast.LENGTH_SHORT).show();
        } else
          Toast.makeText(getActivity(), "ACTIVITY IS NTH", Toast.LENGTH_SHORT).show();
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getActivity().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    //Camera Intent
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQ_CODE);


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        //folder stuff
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyNewImages");
        imagesFolder.mkdirs();

        File image = new File(imagesFolder, "QR_" + timeStamp + ".png");
        Uri uriSavedImage = Uri.fromFile(image);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);


    }
//    interface SendMsg {
//        void sendData(String message);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        try {
//            SM = (SendMsg) getActivity();
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Error in retrieving data. Please try again");
//        }
//    }

    private void onCaptureImageResult(Intent data) {

        // CREATE BITMAP AND Compress it
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        // Get Bitmap Uri
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), thumbnail, "Title", null);
        FilePathUri = Uri.parse(path);


        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".png");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            SelectImage.setImageBitmap(thumbnail);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "ERROR IN SHOWING CAPTURED IMAGE");
        }

    }
//    Bundle bundle=new Bundle();
//    LastSessionFragment mfragment=new LastSessionFragment();

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {


            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            // Showing progressDialog.
            progressDialog.show();
            try {
                //Get User Id and Name
                String UserName = currentUser.getDisplayName();

                // Get Date and Time
                final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                // Creating second StorageReference.
                StorageReference storageReference2nd = storageReference.child(Storage_Path + UserName + timeStamp + "." + GetFileExtension(FilePathUri));

                // Adding addOnSuccessListener to second StorageReference.
                storageReference2nd.putFile(FilePathUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                try {
                                    // Getting image name from EditText and store into string variable.
                                    String TempImageName = ImageName.getText().toString().trim();

                                    // Hiding the progressDialog after done uploading.
                                    progressDialog.dismiss();

                                    // Showing toast message after done uploading.
                                    Toast.makeText(getActivity(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();

                                    @SuppressWarnings("VisibleForTests")

                                        ImageUploadInfo imageUploadInfo = new ImageUploadInfo(TempImageName, taskSnapshot.getDownloadUrl().toString());

                                            // Getting image upload ID.
                                            //ImageUploadId = databaseReference.child(TempImageName + timeStamp).getKey();
                                            //FragmentTransaction transection=getFragmentManager().beginTransaction();

                                            //final String ImageNames = TempImageName;

                                            // SM.sendData(ImageNames);
                                    try {
                                            databaseReference.child(TempImageName).setValue(imageUploadInfo);
                                            lastImageRef.setValue(imageUploadInfo);
                                        }catch (Exception e){
                                                 e.printStackTrace();
                                                 e.getMessage();
                                        }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                // If something goes wrong
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception)
                            {

                                // Hiding the progressDialog.
                                progressDialog.dismiss();
                                Log.e(TAG,exception.toString());
                                // Showing exception error message.
                                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    // On progress change upload time.
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                                // Setting progressDialog Title.
                                progressDialog.setTitle("Image is Uploading...");

                            }
                        });
            } catch (Exception e) {
                e.getMessage();
                e.printStackTrace();
            }


        } else {

            Toast.makeText(getActivity(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


}
