package application.mobileforms;
//https://androidjson.com/retrieve-stored-images-firebase-storage/
/**
 * Created by Inas on 10-Feb-18.
 */

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayImagesActivity extends AppCompatActivity {

    // Creating DatabaseReference.
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    List<ImageUploadInfo> list = new ArrayList<>();

    private TextView Noimages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_images);
        Noimages=(TextView)findViewById(R.id.NoImages) ;

        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayImagesActivity.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(DisplayImagesActivity.this);



        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in Recieve Images Activity.
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        databaseReference =FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getDisplayName()).child(Recieve_Images_Activity.Database_Path);

        // Adding Add Value Event Listener to databaseReference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    // Setting up message in Progress dialog.
                    progressDialog.setMessage("Loading Images From Firebase.");

                    // Showing progress dialog.
                    progressDialog.show();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        ImageUploadInfo imageUploadInfo = postSnapshot.getValue(ImageUploadInfo.class);

                        list.add(imageUploadInfo);
                    }

                    adapter = new RecyclerViewAdapterDisplayImages(getApplicationContext(), list);

                    recyclerView.setAdapter(adapter);

                    // Hiding the progress dialog.
                    progressDialog.dismiss();
                }
                else{
                    Noimages.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }
        });

    }
}