package application.mobileforms;
//https://stackoverflow.com/questions/46372780/android-firebase-different-types-of-users-login?rq=1
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    private static Button login_doctor;
    private static Button login_patient;
    private EditText EmailField;
    private EditText PassField;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EmailField = (EditText) findViewById(R.id.email_field);
        PassField = (EditText) findViewById(R.id.password_field);
        login_doctor = (Button) findViewById(R.id.signindoctor);
        progressDialog = new ProgressDialog(this);


        login_doctor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = EmailField.getText().toString();
                        String pass = PassField.getText().toString();
                        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {

                            Toast.makeText(Login.this, "Fields are empty", Toast.LENGTH_SHORT).show();

                        } else {
                            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Sign in Error", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();

                                    } else {
                                        progressDialog.dismiss();
                                        Intent user_account = new Intent(Login.this, Doctor_Profile_Activity.class);
                                        startActivity(user_account);
                                    }
                                }
                            });
                        }
//                        Intent intent = new Intent(Login.this, DoctorSearchActivity.class);
//
//                        startActivity(intent);


                    }

                }

        );


        login_patient = (Button) findViewById(R.id.SigninPatient);
        login_patient.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.setTitle("Sigining in...");
                        progressDialog.show();
                        startLogin();


                    }

                }

        );

        mAuth = FirebaseAuth.getInstance();
       user=mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    //Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(Login.this, "No user", Toast.LENGTH_SHORT).show();
                }

            }
        };

    }


    private void startLogin() {
        String email = EmailField.getText().toString();
        String pass = PassField.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {

            Toast.makeText(Login.this, "Fields are empty", Toast.LENGTH_SHORT).show();

        } else {
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Sign in Error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    } else {
                            progressDialog.dismiss();
                        Intent user_account = new Intent(Login.this, PatientProfile_Activity.class);
                        startActivity(user_account);
                    }
                }
            });
        }
    }
}




