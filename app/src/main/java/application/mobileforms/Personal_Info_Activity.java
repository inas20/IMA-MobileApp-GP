package application.mobileforms;
//https://www.youtube.com/watch?v=J8GB_b8qyK8

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inas on 09-Feb-18.
 */
//https://www.youtube.com/watch?v=kyGVgrLG3KU&t=56s

public class Personal_Info_Activity extends Fragment {
    // Firebase Declaration
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    FirebaseStorage mStorage;
    DatabaseReference mDataRef;
    FirebaseUser user;

    // Recycle view and Adapter
    private RecyclerView UserView;
    private UserListAdapter userListAdapter;
    private final String TAG = "User Information";
    List<UserAddInformation> usersList;

    // Buttons and text declaration
    Button AddInfo;
    Button Verify;
    TextView VerifyStatus;
    Button ChangePass;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.personalinfo_form, null);

        try {
            //Recycler View
            UserView = (RecyclerView) view.findViewById(R.id.UserRecycler);
            UserView.setHasFixedSize(true);
            UserView.setLayoutManager(new LinearLayoutManager(getActivity()));

            //Adapter
            usersList = new ArrayList<>();
            userListAdapter = new UserListAdapter(usersList);
            UserView.setAdapter(userListAdapter);

            //database
            mDatabase = FirebaseDatabase.getInstance();
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();

            //Views
            AddInfo = (Button) view.findViewById(R.id.addInfo);
            Verify = (Button) view.findViewById(R.id.verification);
            VerifyStatus = (TextView) view.findViewById(R.id.verifyStatus);
            ChangePass = (Button) view.findViewById(R.id.ChangePassword);

            ChangePass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), NamePassActivity.class);
                    startActivity(intent);

                }
            });
            if (user.getDisplayName() != null) {
                UserView.setVisibility(View.VISIBLE);
                Log.e("Name", user.getDisplayName());
                mDataRef = mDatabase.getReference("Users").child(user.getDisplayName()).child("Personal Info");
                Log.e("Database Ref", mDataRef.toString());

                mDataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.hasChildren()) {
                                Log.e("Children", dataSnapshot.getValue().toString());
                                UserAddInformation users = dataSnapshot.getValue(UserAddInformation.class);
                                usersList.add(users);
                                userListAdapter.notifyDataSetChanged();
                                Log.e("Info", users.getGender().toString() + users.getbirthdate().toString() + users.getStatus().toString() );


                            } else {
                                Toast.makeText(getActivity(), "NO information to view", Toast.LENGTH_SHORT).show();
                                AddInfo.setVisibility(View.VISIBLE);
                                AddInfo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(getActivity(),  application.mobileforms.Patient_personal_info.class);
                                        startActivity(intent);
                                    }
                                });

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "Error" + databaseError.getMessage());

                    }
                });
            } else {

                Toast.makeText(getActivity(), "No user name", Toast.LENGTH_SHORT).show();
//                AddInfo.setVisibility(View.VISIBLE);
//                AddInfo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), application.mobileforms.Patient_personal_info.class);
//                        startActivity(intent);
//                    }
//                });

            }
            if (user.isEmailVerified()) {
                VerifyStatus.setText("Verified Email");
//                Enter.setVisibility(View.VISIBLE);
//                Enter.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), application.mobileforms.ProfileActivity.class);
//                        startActivity(intent);
//                    }
//                });

            } else {
                Verify.setVisibility(View.VISIBLE);
                Verify.setEnabled(true);
                //Verification of User Email
                Toast.makeText(getActivity(), "Verify your email please", Toast.LENGTH_SHORT).show();
                Verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "Verify");


                        try {
                            user.sendEmailVerification().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();

                                        Verify.setEnabled(false);
                                    } else {
                                        Log.e(TAG, "sendEmailVerification", task.getException());
                                        Toast.makeText(getActivity(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                        VerifyStatus.setText("Not Verified");
                                    }
                                }
                            });
                        } catch (Exception e)

                        {
                            e.printStackTrace();
                        }

                    }

                });
            }
        } catch (Exception e)

        {
            e.printStackTrace();
        }
        return view;

    }

}
