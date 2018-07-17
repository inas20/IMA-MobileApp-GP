package application.mobileforms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private Button btnRegister;

    private EditText emailText;
    private EditText passwordText;

    private TextView SigninView;
    private TextView RegisterDr;

    private ProgressDialog progress;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);
        btnRegister = (Button) findViewById(R.id.register_btn);
        emailText = (EditText) findViewById(R.id.EditTextEmail);
        passwordText = (EditText) findViewById(R.id.EditTextpass);
        SigninView = (TextView) findViewById(R.id.Signin_View);
        RegisterDr = (TextView) findViewById(R.id.register_dr);


        //Register as patient
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        // Have Account
        SigninView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent login = new Intent(SignupActivity.this, Login.class);
                startActivity(login);
            }
        });

        //Register as Doctor
        RegisterDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString().trim();
                String password = passwordText.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignupActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progress.setMessage("Creating Account..");
                    progress.show();
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) { // user successfully registered

                                Toast.makeText(SignupActivity.this, "Successfully Entered", Toast.LENGTH_SHORT).show();
                                progress.cancel();
                                Intent login = new Intent(SignupActivity.this, NamePassActivity.class);
                                startActivity(login);

                            } else {
                                Toast.makeText(SignupActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                                progress.cancel();
                            }

                        }
                    });

                }

            }
        });

    }

    private void registerUser() {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
            return;
        }
        // if credentials are ok
        else {
            progress.setMessage("Creating Account..");
            progress.show();
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) { // user successfully registered

                        Toast.makeText(SignupActivity.this, "Successfully Entered", Toast.LENGTH_SHORT).show();
                        progress.cancel();
                      Intent login = new Intent(SignupActivity.this, Patient_personal_info.class);
                       startActivity(login);

                    } else {
                        Toast.makeText(SignupActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                        progress.cancel();
                    }

                }
            });

        }

    }

}
