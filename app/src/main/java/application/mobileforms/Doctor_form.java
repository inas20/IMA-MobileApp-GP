package application.mobileforms;
//https://www.youtube.com/watch?v=0QnvFYsKl4U

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class Doctor_form extends AppCompatActivity {

    private EditText SearchNameField;
    //private SearchView SearchBtn;
    private Button SearchButton;


    FirebaseDatabase mDatabase;

    DatabaseReference mDataRef, mDataRef2;


    private RecyclerView DisplayInfo;
    private UserListAdapter userListAdapter;

    private RecyclerAdapter recyclerAdapter;
    private final String TAG = "Doctor Searching";

    List<UserAddInformation> usersList;
    //new Holder


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_form);
        SearchButton = (Button) findViewById(R.id.SearchBtn);
        //setUplist();

        //Recycler View
        DisplayInfo = (RecyclerView) findViewById(R.id.DisplayUserInfo);
        DisplayInfo.setHasFixedSize(true);
        DisplayInfo.setLayoutManager(new LinearLayoutManager(this));

        //Adapter
        usersList = new ArrayList<>();
//        recyclerAdapter = new RecyclerAdapter(usersList, this);
//        DisplayInfo.setAdapter(recyclerAdapter);

        userListAdapter =new UserListAdapter(usersList);
        DisplayInfo.setAdapter(userListAdapter);

        //database
        mDatabase = FirebaseDatabase.getInstance();
        mDataRef = mDatabase.getReference("Users");
        try {
            mDataRef2 = mDatabase.getReference("Users").child("Personal info");

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {

            SearchNameField = (EditText) findViewById(R.id.SearchName);
            //SearchBtn = (SearchView) findViewById(R.id.searchView);


            SearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDataRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                Log.e("Children", dataSnapshot.getValue().toString());
                                String SearchName = SearchNameField.getText().toString().trim();

                                if (dataSnapshot.hasChild(SearchName)) {
                                    Log.e("Patient info ", dataSnapshot.child(SearchName).child("Personal Info").getValue().toString());
                                    UserAddInformation users = dataSnapshot.child(SearchName).child("Personal Info").getValue(UserAddInformation.class);
                                   // for (int i = 1; i <= 4; i++) {
                                        usersList.add( users);
                                        //recyclerAdapter.notifyDataSetChanged();
                                    //}
                                    Toast.makeText(Doctor_form.this, "Patient's info", Toast.LENGTH_SHORT).show();
                                    userListAdapter.notifyDataSetChanged();

                                } else {
                                    Toast.makeText(Doctor_form.this, "NO Such Patient with this name", Toast.LENGTH_SHORT).show();
                                }


                            } catch (Exception e) {
                                e.getMessage();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e(TAG, "Error" + databaseError.getMessage());

                        }
                    });


                }
            });
        } catch (Exception e) {
            e.getMessage();
        }


    }
//
//    private void setUplist() {
//        for (int i =1 ;i<=10;i++)
//        {
//               usersList.add();
//        }
//    }
}