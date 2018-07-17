package application.mobileforms;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class History extends Activity {

    private ListView UserList;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseStorage storage;
    FirebaseUser user;
    DatabaseReference databaseReference;
    private static final String TAG = "ViewUserInfo";
    public String Uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        UserList = (ListView) findViewById(R.id.user_list);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Uid= user.getUid();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "OnAuthStatChanged" + user.getUid());
                    Toast.makeText(History.this, " User Successfully signed in " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(History.this, " User is signed out", Toast.LENGTH_SHORT).show();
            }
        };

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG,"Add Value Cancelled",databaseError.toException());
            }
        });
    }

/*

    private void showData(DataSnapshot dataSnapshot) {
        try {


            for (DataSnapshot ds : dataSnapshot.getChildren()) {


                ArrayList<String> array = new ArrayList<>();
                array.add(uInfo.getAddress());
                array.add(uInfo.getName());
                array.add(uInfo.getPhone());
                Log.e("Address",array.get(0));
                Log.e("Name",array.get(1));
                Log.e("Phone",array.get(2));

               ArrayAdapter arrayadapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, array);
                UserList.setAdapter(arrayadapter);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);

    }
/*
    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
*/

}
