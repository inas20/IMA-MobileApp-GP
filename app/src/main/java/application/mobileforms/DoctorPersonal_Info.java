package application.mobileforms;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Inas on 08-Mar-18.
 */


public class DoctorPersonal_Info extends AppCompatActivity {

    //Views
    private Button AddBtn;
    //private Button AddName;

    private EditText Username;
    private EditText AddPhone;
    private EditText AddEmail;
    //private EditText pass;
    private EditText Age;

    private TextView AgeView;
    private TextView StatusView;
    private TextView GenderView;
    private TextView phoneView;
    //private TextView SameName;

    //Spinners
    private Spinner StatusSpinner;
    private Spinner GenderSpinner;


    //Firebase Initialization
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    //Notification Builder
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

    //Declarations
    private static final String TAG = "Message";
    Uri filepath = null;
    String GenderList[] = {"--", "Male", "Female"};
    String StatusList[] = {"--", "Single", "Married"};
    ArrayAdapter<String> GenderAdapter;
    ArrayAdapter<String> StatusAdapter;
    private String status;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);

        //Personal Information
        Username = (EditText) findViewById(R.id.PatientName);
        AddPhone = (EditText) findViewById(R.id.PhoneNumber);
        AddEmail = (EditText) findViewById(R.id.NewEmail2);
        // pass = (EditText) findViewById(R.id.password2);
        Age = (EditText) findViewById(R.id.AgeNumber);

        //AddName = (Button) findViewById(R.id.enter);

        StatusSpinner = (Spinner) findViewById(R.id.spinner_status);
        GenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        AgeView = (TextView) findViewById(R.id.AgeView);

        //SameName = (TextView) findViewById(R.id.EmailView);

        GenderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, GenderList);
        StatusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, StatusList);

        //Set Adapter to Spinners
        StatusSpinner.setAdapter(StatusAdapter);
        GenderSpinner.setAdapter(GenderAdapter);


        AddBtn = (Button) findViewById(R.id.Submit);
        //pass = (EditText) findViewById(R.id.password2);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveUserInfo();
            }


        });


    }

    private void SaveUserInfo() {
        try {
            StatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    status = parent.getItemAtPosition(position).toString();


                    Toast.makeText(DoctorPersonal_Info.this, status + "is selected", Toast.LENGTH_SHORT).show();
                    if (status == "--") {
                        Toast.makeText(DoctorPersonal_Info.this, "please select status", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Item is selected", status);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    gender = parent.getItemAtPosition(position).toString();
                    Toast.makeText(DoctorPersonal_Info.this, gender + "is selected", Toast.LENGTH_SHORT).show();
                    if (gender == "--") {
                        Toast.makeText(DoctorPersonal_Info.this, "please select status", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Item is selected", gender);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String NewEmail = AddEmail.getText().toString().trim();
        final String NewPhone = AddPhone.getText().toString().trim();
        final String Name = Username.getText().toString().trim();
        final String AgeNum = Age.getText().toString();


        if (TextUtils.isEmpty(NewEmail) || TextUtils.isEmpty(NewPhone)) {
            Toast.makeText(this, "Please fill the Fields First", Toast.LENGTH_SHORT).show();

        } else {

            try {
                // if (Username != null) {
                DatabaseReference childRef = databaseReference.child(Name).child("Personal Info");
                Log.e("childRef", childRef.toString());

                Map<String, String> userMap = new HashMap<>();
                userMap.put("username", Name);
                userMap.put("Email", NewEmail);
                userMap.put("Phone", NewPhone);
                userMap.put("Status", StatusSpinner.getSelectedItem().toString());
                Log.e("Status is ", StatusSpinner.getSelectedItem().toString());
                userMap.put("Gender", GenderSpinner.getSelectedItem().toString());
                Log.e("Gender is ", GenderSpinner.getSelectedItem().toString());
                userMap.put("Age", AgeNum);

                childRef.setValue(userMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(DoctorPersonal_Info.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(DoctorPersonal_Info.this, "Data Entered SuccessFully", Toast.LENGTH_SHORT).show();
                        Intent main = new Intent(DoctorPersonal_Info.this, Doctor_Profile_Activity.class);
                        startActivity(main);

                    }
                });
                childRef.setValue(userMap);
//                } else {
//                    Toast.makeText(this, "Name is equal null", Toast.LENGTH_SHORT).show();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
/*
    private void UpdateUserProfile() {  //get the signed-in user

        if (user != null) {
            try {


                String name = user.getDisplayName();
                String email = user.getEmail();
                Uri photoUrl = user.getPhotoUrl();
                String uid = user.getUid();
                //final String NewName = Username.getText().toString().toLowerCase();
                //Log.e("Update New name :", NewName);
                //String NewPass = pass.getText().toString();

                //String NewPass = PasswordText.getText().toString().trim();


                //UPDATE USER PROFILE
                //if (TextUtils.isEmpty(NewName)) {

                    Toast.makeText(DoctorPersonal_Info.this, "Please Fill The Fields", Toast.LENGTH_SHORT).show();
                } else {

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(NewName)

                            .build();
                    //Toast.makeText(DoctorPersonal_Info.this, "Success", Toast.LENGTH_SHORT).show();


                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "User profile updated.");
                                        //Toast.makeText(DoctorPersonal_Info.this, "User profile updated.", Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(DoctorPersonal_Info.this, "Update Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    //Set a user's password

                    user.updatePassword(NewPass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "User password updated.");
                                        //Toast.makeText(DoctorPersonal_Info.this, "User password updated.", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(DoctorPersonal_Info.this, "Password Doesn`t Changed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error");
            }
        } else
            Toast.makeText(DoctorPersonal_Info.this, "user is null", Toast.LENGTH_SHORT).show();

    }
    */
}



