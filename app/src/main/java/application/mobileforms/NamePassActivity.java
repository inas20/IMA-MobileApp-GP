package application.mobileforms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Inas on 09-Mar-18.
 */

public class NamePassActivity extends AppCompatActivity {

    private EditText Username;
    private EditText pass;
    private Button NextBtn;
    private TextView SameName;

    //Firebase Initialization
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    private static final String TAG = "Message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_pass_layout);
        Username = (EditText) findViewById(R.id.patient_name);
        pass = (EditText) findViewById(R.id.patient_pass);
        SameName = (TextView) findViewById(R.id.changeName);
        NextBtn=(Button)findViewById(R.id.Nxt_Btn);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Username.getText())||TextUtils.isEmpty(pass.getText())) {

                    Toast.makeText(NamePassActivity.this, "Please Fill The Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    UpdateUserProfile();
                    Intent intent = new Intent(NamePassActivity.this, Login.class);
                    startActivity(intent);

                    //Toast.makeText(NamePassActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void UpdateUserProfile() {  //get the signed-in user

        if (user != null) {
            try {

                final String Name = Username.getText().toString().trim();
                Log.e("Update New name :", Name);
                String NewPass = pass.getText().toString().trim();

                //UPDATE USER PROFILE
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(Name).build();

                     Toast.makeText(NamePassActivity.this, "Success", Toast.LENGTH_SHORT).show();

                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Log.d(TAG, "User profile updated.");
                                        Toast.makeText(NamePassActivity.this, "Go next to complete", Toast.LENGTH_SHORT).show();
                                        Log.e("User name",user.getDisplayName());
//                                        Intent info=new Intent(NamePassActivity.this,DoctorPersonal_Info.class);
//                                        startActivity(info);

                                    } else {
                                        Toast.makeText(NamePassActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                    //Set a user's password

                    user.updatePassword(NewPass).addOnCompleteListener(new OnCompleteListener<Void>() {
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




            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Error");
            }
        } else
            Toast.makeText(NamePassActivity.this, "user is null", Toast.LENGTH_SHORT).show();

    }
}
