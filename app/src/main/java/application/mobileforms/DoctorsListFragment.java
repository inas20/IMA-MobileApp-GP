package application.mobileforms;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inas on 10-Jul-18.
 */

public class DoctorsListFragment extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase databaseRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    DatabaseReference databaseReference;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter DoctorsAdapter;

    // Creating List of Doctors class.
    List<Doctors> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState)
    {

        View view = inflater.inflate(R.layout.doctors_list, null);
        recyclerView= (RecyclerView)view.findViewById(R.id.recyclerView_doc);
        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);
        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        databaseRef = FirebaseDatabase.getInstance();
        databaseReference = databaseRef.getReference().child("Users").child("Doctors");
        String x=databaseReference.child("Ahmed").child("Available_time").toString();
        Log.e("Available time",x);
        databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Log.e("datasnapshotValue",dataSnapshot.getValue().toString());
        Log.e("datasnapshotChild",dataSnapshot.getChildren().toString());
        try {
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                Doctors doctors = postSnapshot.getValue(Doctors.class);
                list.add(doctors);

            }
            DoctorsAdapter = new DoctorsViewHolder(getContext(), list);
            recyclerView.setAdapter(DoctorsAdapter);
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }


    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
            databaseError.getMessage();
    }
});

        return view;
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.e("Start","Done" );
//        try {
//            FirebaseRecyclerAdapter<Doctors, DoctorsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Doctors, DoctorsViewHolder>(
//                    Doctors.class,
//                    R.layout.doctors_card_list,
//                    DoctorsViewHolder.class,
//                    databaseReference
//
//            ) {
//                @Override
//                protected void populateViewHolder(DoctorsViewHolder viewHolder, Doctors model, int position) {
//                    try {
//                        viewHolder.setDetails(getContext(), model.getName(), model.getDescription(), model.getAddress(), model.getAvailable_time(), model.getRate(), model.getImage());
//                        Log.e("Holder","Done2")
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        e.getMessage();
//                    }
//                }
//            };
//
//            //set Adapter to recyclerView
//            recyclerView.setAdapter(firebaseRecyclerAdapter);
//            Log.e("RecyclerView","Done3");
//        }catch (Exception e){
//            e.printStackTrace();
//            e.getMessage();
//        }
//    }

    }

