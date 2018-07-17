package application.mobileforms;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Inas on 13-Feb-18.
 */

public class logoutActivity extends Fragment {
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.personalinfo_form, null);


            progressDialog = new ProgressDialog(getActivity());
            mAuth = FirebaseAuth.getInstance();
            try {
                mAuth.signOut();
                progressDialog.setMessage("Signing out....");
                progressDialog.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        progressDialog.dismiss();
        return view;
    }
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);

        }
    }



}
