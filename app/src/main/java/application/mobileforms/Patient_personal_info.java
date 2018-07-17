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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Inas on 26-Feb-18.
 */

public class Patient_personal_info extends AppCompatActivity {

    //Views
    private Button AddBtn;
    private Button Submit;
    private EditText Username;
    private EditText AddPhone;
    private EditText AddEmail;
    private EditText pass;
    private EditText Gender;
    private AutoCompleteTextView Status;
    private EditText BirthDate;
    private TextView ViewNote;

    //Firebase Initialization
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    //Notification Builder
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);


    private static final String TAG = "Message";
    Uri filepath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_information_layout);
            //Assigns View
        AddBtn = (Button) findViewById(R.id.button2);
        Username = (EditText) findViewById(R.id.NameEdit2);
        AddPhone = (EditText) findViewById(R.id.PhoneNo);
        AddEmail = (EditText) findViewById(R.id.Email_Text);
        Submit = (Button) findViewById(R.id.NextButton);
        pass = (EditText) findViewById(R.id.password_text);
        Gender=(EditText) findViewById(R.id.gender);
        Status=(AutoCompleteTextView) findViewById(R.id.status);
        BirthDate=(EditText) findViewById(R.id.BirthDate);
        ViewNote=(TextView)findViewById(R.id.ChangeName);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        AddBtn.setVisibility(View.VISIBLE);
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Name = Username.getText().toString().trim();
                Log.e(" Save New name :", Name);
                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(Patient_personal_info.this, "Please Enter Username", Toast.LENGTH_SHORT).show();

                } else {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(Name)) {
                                ViewNote.setVisibility(View.VISIBLE);
                               // Toast.makeText(Patient_personal_info.this, "Please Choose Another Unique Name", Toast.LENGTH_SHORT).show();
                            } else {

                                UpdateUserProfile();
                                ViewNote.setVisibility(View.INVISIBLE);
                                AddBtn.setVisibility(View.INVISIBLE);
                                AddPhone.setVisibility(View.VISIBLE);
                                AddEmail.setVisibility(View.VISIBLE);
                                Status.setVisibility(View.VISIBLE);
                                BirthDate.setVisibility(View.VISIBLE);
                                Gender.setVisibility(View.VISIBLE);
                                Submit.setVisibility(View.VISIBLE);


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            databaseError.getMessage();

                        }
                    });
                }
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUserInfo();
            }


        });
    }

    private void SaveUserInfo() {
        final String NewEmail = AddEmail.getText().toString().trim();
        final String NewPhone = AddPhone.getText().toString().trim();
        final String PatientGender = Gender.getText().toString().trim();
        final String PatientStatus = Status.getText().toString().trim();
        final String PatientDate = BirthDate.getText().toString().trim();
        final String Name = Username.getText().toString().trim();


        if (TextUtils.isEmpty(NewEmail) || TextUtils.isEmpty(NewPhone)) {
            Toast.makeText(this, "Please fill the Fields First", Toast.LENGTH_SHORT).show();

        } else {

            try {
                if (user.getDisplayName() != null) {
                    DatabaseReference childRef = databaseReference.child(user.getDisplayName()).child("Personal Info");
                    DatabaseReference usernames = FirebaseDatabase.getInstance().getReference().child("Users").child("Usernames");
                    usernames.push().child("name").setValue(Name);
                        Log.e("Name is " ,usernames.getKey());

                    Log.e("childRef", childRef.toString());

                    Map<String, String> userMap = new HashMap<>();
                    userMap.put("username", Name);
                    userMap.put("Email", NewEmail);
                    userMap.put("Phone", NewPhone);
                    userMap.put("status", PatientStatus);
                    userMap.put("BirthDate", PatientDate);
                    userMap.put("gender", PatientGender);
                    childRef.setValue(userMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(Patient_personal_info.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(Patient_personal_info.this, "Data Entered SuccessFully", Toast.LENGTH_SHORT).show();
                            Intent main = new Intent(Patient_personal_info.this, PatientProfile_Activity.class);
                            startActivity(main);

                        }
                    });
                    childRef.setValue(userMap);
                } else {
                    Toast.makeText(this, "Name is equal null", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void UpdateUserProfile() {  //get the signed-in user

        if (user != null) {
            try {

                final String Name = Username.getText().toString().trim();
                Log.e("Update New name :", Name);
                String NewPass = pass.getText().toString().trim();


                //String NewPass = PasswordText.getText().toString().trim();


                //UPDATE USER PROFILE
                if (TextUtils.isEmpty(Name)) {

                    Toast.makeText(Patient_personal_info.this, "Please Fill The Fields", Toast.LENGTH_SHORT).show();
                } else {

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(Name)
                            .setPhotoUri((filepath))
                            .build();
                    //Toast.makeText(Patient_personal_info.this, "Success", Toast.LENGTH_SHORT).show();


                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "User profile updated.");
                                        //Toast.makeText(Patient_personal_info.this, "User profile updated.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Patient_personal_info.this, "Update Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    //Set a user's password

                    user.updatePassword(NewPass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password updated.");
                                        //Toast.makeText(Patient_personal_info.this, "User password updated.", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Log.d(TAG, "User password not updated.");
                                       // Toast.makeText(Patient_personal_info.this, "Password Doesn`t Changed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error");
            }
        } else
            Toast.makeText(Patient_personal_info.this, "user is null", Toast.LENGTH_SHORT).show();

    }
}


