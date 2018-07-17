package application.mobileforms;
//https://www.youtube.com/watch?v=b_tz8kbFUsU&t=1679s
//https://www.youtube.com/watch?v=HPNHb0NW6AI
//https://www.youtube.com/watch?v=SAvQszo-npg

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Inas on 16-Feb-18.
 */

public class DoctorSearchFragment extends Fragment {

    private EditText SearchNameField;
    //private SearchView SearchBtn;
    private Button SearchButton;
    private ImageButton SearchBtn;

    String Name;
    FirebaseDatabase mDatabase;

    DatabaseReference mDataRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    private RecyclerView DisplayInfo;
    private UserListAdapter userListAdapter;
    private static final String TAG = "Doctor Searching";

    ArrayList<Users> usersList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_doctor_form, null);

        //SearchButton = (Button) view.findViewById(R.id.SearchButton);
        SearchBtn = (ImageButton) view.findViewById(R.id.imageButton2);

        //Recycler View
        DisplayInfo = (RecyclerView) view.findViewById(R.id.result_list);
        DisplayInfo.setHasFixedSize(true);
        DisplayInfo.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Adapter
        usersList = new ArrayList<>();
        //userListAdapter = new UserListAdapter(usersList);


        //database
        //mDatabase = FirebaseDatabase.getInstance();
        mDataRef = FirebaseDatabase.getInstance().getReference("Users").child("Usernames");
        Log.e("mDataRef", mDataRef.getKey().toString());
        SearchNameField = (EditText) view.findViewById(R.id.SearchNames);
        //Name = SearchNameField.getText().toString();
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = SearchNameField.getText().toString();
                getUsers(searchText);
                FirebaseSearchUsers(searchText);
                Toast.makeText(getActivity(), "Button is clicked", Toast.LENGTH_SHORT).show();
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(getActivity(), "User equal null", Toast.LENGTH_SHORT).show();
                } else {
                    //getUsers(SearchNameField.getText().toString());
                    Toast.makeText(getActivity(), "Get Users", Toast.LENGTH_SHORT).show();
                }
            }
        };
        return view;
    }

    private void getUsers(String Name) {
        mDataRef.orderByChild("name").startAt(Name).endAt(Name + "\uf88f").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()) {
                    DataSnapshot item = items.next();

                    String name;
                    name = item.child("name").getValue().toString();
                    Log.e("name", name);

                    Users entry = new Users(name);
                    usersList.add(entry);
                    DisplayInfo.setAdapter(new UserNamesAdapter(usersList));
                    DisplayInfo.getAdapter().notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void FirebaseSearchUsers(String searchName) {


        Query firebaseSearchQuery = mDataRef.orderByChild("name").startAt(searchName).endAt(searchName + "\uf88f");
        try {
            FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(
                    Users.class,
                    R.layout.usersnames,
                    UsersViewHolder.class,
                    firebaseSearchQuery) {
                @Override
                protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {

                    viewHolder.setDetails(model.getName());
//                    viewHolder.mView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getActivity(), "You clicked", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                    viewHolder.setItemClick(new OnItemClick() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            try {
                                if (isLongClick) {
                                    Toast.makeText(getContext(), "Long Click : " + usersList.get(position), Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(getContext(), " " + usersList.get(position), Toast.LENGTH_SHORT).show();
                            } catch (Exception e)

                            {
                                e.printStackTrace();
                                e.getMessage();
                            }

                        }

                    });

                }


            };
            DisplayInfo.setAdapter(firebaseRecyclerAdapter);

            Log.e(TAG, "Success");
        } catch (
                Exception e)

        {
            e.printStackTrace();
            e.getMessage();
        }


    }


    public static class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        View mView;
        TextView Name;
        private OnItemClick onItemClick;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            Name = (TextView) mView.findViewById(R.id.patient_name);
            mView.setOnClickListener(this);
            mView.setOnLongClickListener(this);
//            mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        Log.v("Holder", "You clicked");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
        }

        public void setItemClick(OnItemClick onItemClick) {
            this.onItemClick = onItemClick;
        }

        @Override
        public void onClick(View v) {
            onItemClick.onClick(v, getAdapterPosition(), false);

        }

        @Override
        public boolean onLongClick(View v) {
            onItemClick.onClick(v, getAdapterPosition(), true);
            return true;
        }

        public void setDetails(String username) {

            //Name = (TextView) mView.findViewById(R.id.patient_name);

            Name.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        Log.e(TAG, Name.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            Name.setText(username);
            Log.e("Details name is", username);

        }


    }


}


