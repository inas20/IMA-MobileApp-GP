package application.mobileforms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Inas on 04-Feb-18.
 */

public class ProfileActivity extends Activity {

    private Button UpdateBtnn;
    private Button AddBtn;
    private Button ViewInfoBtn;

    private EditText Username;
    private EditText PasswordText;
    private EditText AddEmail;
    private EditText AddPhone;

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

    Uri filepath = null;
    private static final String TAG = "ProfileActivity";
    String NewName;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        PasswordText = (EditText) findViewById(R.id.newPass);
        UpdateBtnn = (Button) findViewById(R.id.Update);
        AddBtn = (Button) findViewById(R.id.Add_Info);
        ViewInfoBtn = (Button) findViewById(R.id.ViewInfo);
        AddEmail = (EditText) findViewById(R.id.NewEmail2);
        AddPhone = (EditText) findViewById(R.id.PhoneNo);
        Username = (EditText) findViewById(R.id.NameEdit2);
        NewName = Username.getText().toString();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        UpdateBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUserProfile();

            }
        });
        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    i = 1;
                    UpdateBtnn.setVisibility(View.INVISIBLE);
                    AddPhone.setVisibility(View.VISIBLE);
                    AddEmail.setVisibility(View.VISIBLE);

                } else {
                    i = 0;
                    SaveUserInfo();
                }


            }
        });
        ViewInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent main = new Intent(ProfileActivity.this, Personal_Info_Activity.class);
                startActivity(main);

            }
        });
    }

    private void SaveUserInfo() {
        String NewEmail = AddEmail.getText().toString().trim();
        String NewPhone = AddPhone.getText().toString().trim();
        NewName = Username.getText().toString().trim();
        Log.e(" Save New name :", NewName);


        if (TextUtils.isEmpty(NewEmail) || TextUtils.isEmpty(NewPhone)) {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();

        } else {
            if (user.getDisplayName() != null) {
                DatabaseReference childRef = databaseReference.child(user.getDisplayName()).child("Personal Info");

                Log.e("childRef", childRef.toString());

                Map<String, String> userMap = new HashMap<>();
                userMap.put("username", NewName);
                userMap.put("Email", NewEmail);
                userMap.put("Phone", NewPhone);
                childRef.setValue(userMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(ProfileActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(ProfileActivity.this, "Data Entered SuccessFully", Toast.LENGTH_SHORT).show();
                        Intent main = new Intent(ProfileActivity.this, Login.class);
                        startActivity(main);

                    }
                });
                childRef.setValue(userMap);

            } else {
                Toast.makeText(this, "Fill fields and enter change btn first", Toast.LENGTH_SHORT).show();
            }

        }


    }


    private void UpdateUserProfile() {  //get the signed-in user

        if (user != null) {
            try {


                String name = user.getDisplayName();
                String email = user.getEmail();
                Uri photoUrl = user.getPhotoUrl();
                String uid = user.getUid();
                NewName = Username.getText().toString().toLowerCase();
                Log.e("Update New name :", NewName);


                String NewPass = PasswordText.getText().toString().trim();


                //UPDATE USER PROFILE
                if (TextUtils.isEmpty(NewName) || TextUtils.isEmpty(NewPass)) {

                    Toast.makeText(ProfileActivity.this, "Please Fill The Fields", Toast.LENGTH_SHORT).show();
                } else {

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(NewName)
                            .setPhotoUri((filepath))
                            .build();
                    Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();


                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "User profile updated.");
                                        Toast.makeText(ProfileActivity.this, "User profile updated."+user.getDisplayName() , Toast.LENGTH_SHORT).show();


                                    } else {
                                        Toast.makeText(ProfileActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    user.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileActivity.this, "User display name" + user.getDisplayName(), Toast.LENGTH_SHORT).show();

                        }
                    });
                    //Set a user's password

                    user.updatePassword(NewPass)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "User password updated.");
                                        Toast.makeText(ProfileActivity.this, "User password updated.", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(ProfileActivity.this, "Password Doesn`t Changed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error");
            }
        } else
            Toast.makeText(ProfileActivity.this, "user is null", Toast.LENGTH_SHORT).show();

    }


}





