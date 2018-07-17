package application.mobileforms;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

/**
 * Created by Inas on 14-Feb-18.
 */

public class LastSessionFragment extends Fragment  {
    // Creating StorageReference and DatabaseReference object.

    FirebaseDatabase databaseRef;
    DatabaseReference databaseReference, databaseRefImages, databaseRefAnylsis;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;

    private EditText Area;
    private EditText Color;
    private TextView Obj;
    private TextView Note;
    private TextView Texture;
    private TextView Disease;

    private TextView AreaView;
    private TextView ColorView;
    private TextView ObjView;
    private TextView NoteView;
    private TextView TextureView;
    private TextView DiseaseView;

    private TextView Start;
    private FloatingActionButton Camera;

    private final String TAG = "Message";
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    //Ring Progressbar
    private RingProgressBar AreaprogressBar, ColorprogressBar;

    private LinearLayout progressLayout;
    int progress = 0;
    int progress2 = 0;
    Handler AreaHandler;
    Handler ColorHandler;
    public String ImageName;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter LastImageAdapter;

    // Creating List of ImageUploadInfo class.
    List<ImageUploadInfo> list = new ArrayList<>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lastsession_activity, null);

        //Assigns Edit Text
//        Area = (EditText) view.findViewById(R.id.area);
//        Color = (EditText) view.findViewById(R.id.color);
        Obj = (TextView) view.findViewById(R.id.objects);
        Note = (TextView) view.findViewById(R.id.notes);
        Texture=(TextView) view.findViewById(R.id.texture);
        Disease=(TextView)view.findViewById(R.id.disease);

        AreaView = (TextView) view.findViewById(R.id.AreaView);
        ObjView = (TextView) view.findViewById(R.id.ObjectsFound);
        ColorView = (TextView) view.findViewById(R.id.ColorView);
        NoteView = (TextView) view.findViewById(R.id.NotesView);
        DiseaseView=(TextView) view.findViewById(R.id.diseaseView);
        TextureView=(TextView) view.findViewById(R.id.textureView);

        Start = (TextView) view.findViewById(R.id.startAnaylsis);
        Camera = (FloatingActionButton) view.findViewById(R.id.CameraBtn);


        //Tool bar
        //mToolbar = (Toolbar) view.findViewById(R.id.new_action);

        AreaprogressBar = (RingProgressBar) view.findViewById(R.id.area_progress_bar);
        ColorprogressBar = (RingProgressBar) view.findViewById(R.id.color_progress_bar);
        //progressLayout = (LinearLayout) view.findViewById(R.id.linearLayout2);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) view.findViewById(R.id.LastImageView);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);


        AreaView.setVisibility(View.INVISIBLE);
        ObjView.setVisibility(View.INVISIBLE);
        NoteView.setVisibility(View.INVISIBLE);
        ColorView.setVisibility(View.INVISIBLE);
        //progressLayout.setVisibility(View.INVISIBLE);
        Note.setVisibility(View.INVISIBLE);
        Obj.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        Texture.setVisibility(View.INVISIBLE);
        TextureView.setVisibility(View.INVISIBLE);
        Disease.setVisibility(View.INVISIBLE);
        DiseaseView.setVisibility(View.INVISIBLE);

//        AreaView.setVisibility(View.VISIBLE);
//        ObjView.setVisibility(View.VISIBLE);
//        NoteView.setVisibility(View.VISIBLE);
//        ColorView.setVisibility(View.VISIBLE);
//        //progressLayout.setVisibility(View.VISIBLE);
//        Note.setVisibility(View.VISIBLE);
//        Obj.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.VISIBLE);
//        TextureView.setVisibility(View.VISIBLE);
//        DiseaseView.setVisibility(View.VISIBLE);
//        Disease.setVisibility(View.VISIBLE);
//        Texture.setVisibility(View.VISIBLE);
//        AreaprogressBar.setVisibility(View.VISIBLE);
//        ColorprogressBar.setVisibility(View.VISIBLE);
//        Start.setVisibility(View.INVISIBLE);
//        Camera.setVisibility(View.INVISIBLE);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseRef = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getDisplayName());

       //DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getDisplayName());
        databaseRefImages = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser.getDisplayName()).child(Recieve_Images_Activity.Database_Path);

        Log.e("RefImages", databaseRefImages.toString());


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ( dataSnapshot.hasChild("Photo Analysis")) {
                    try {
                        AreaView.setVisibility(View.VISIBLE);
                        ObjView.setVisibility(View.VISIBLE);
                        NoteView.setVisibility(View.VISIBLE);
                        ColorView.setVisibility(View.VISIBLE);
                        //progressLayout.setVisibility(View.VISIBLE);
                        Note.setVisibility(View.VISIBLE);
                        Obj.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        TextureView.setVisibility(View.VISIBLE);
                        DiseaseView.setVisibility(View.VISIBLE);
                        Disease.setVisibility(View.VISIBLE);
                        Texture.setVisibility(View.VISIBLE);
                        ColorprogressBar.setVisibility(View.VISIBLE);
                        AreaprogressBar.setVisibility(View.VISIBLE);

                        Start.setVisibility(View.INVISIBLE);
                        Camera.setVisibility(View.INVISIBLE);
                        String areaValue = dataSnapshot.child("Photo Analysis").child("Area").getValue().toString();
                        //final int percnt = Integer.parseInt(areaValue);

                        Log.e("Area", areaValue);
                        //Area.setText(dataSnapshot.child("Area").getValue().toString());
                        AreaProgress( Float.parseFloat(areaValue));
                        Obj.setText(dataSnapshot.child("Photo Analysis").child("Objects").getValue().toString());
                        Log.e("Obj", Obj.getText().toString());
                        Note.setText(dataSnapshot.child("Photo Analysis").child("Notes").getValue().toString());
                        Texture.setText(dataSnapshot.child("Photo Analysis").child("Texture").getValue().toString());
                        Disease.setText(dataSnapshot.child("Photo Analysis").child("Disease").getValue().toString());
                        String n = Note.getText().toString().trim();
                        Log.e("Notes", n);
                        colorprogressbar(Float.parseFloat(dataSnapshot.child("Photo Analysis").child("Color").getValue().toString()));

                    }catch (Exception e){
                        e.getMessage();
                        e.printStackTrace();
                    }

                } else {

                    Log.e(TAG, "Go to Start Analysis" +  dataSnapshot.getChildrenCount());
                    MoveToStartAnalysis();
                    //Toast.makeText( ,"No  info to view", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "NO CHILDREN");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseRefImages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Log.e(TAG,"hi".toString());
                    try {

                        if (dataSnapshot.hasChild("Last Image")) {
                            try {
//                                AreaView.setVisibility(View.VISIBLE);
//                                ObjView.setVisibility(View.VISIBLE);
//                                NoteView.setVisibility(View.VISIBLE);
//                                ColorView.setVisibility(View.VISIBLE);
//                                progressLayout.setVisibility(View.VISIBLE);
//                                Note.setVisibility(View.VISIBLE);
//                                Obj.setVisibility(View.VISIBLE);
                                Start.setVisibility(View.INVISIBLE);
                                Camera.setVisibility(View.INVISIBLE);

                                recyclerView.setVisibility(View.VISIBLE);
                                ImageUploadInfo lastImageInfo = dataSnapshot.child("Last Image").getValue(ImageUploadInfo.class);
                                list.add(lastImageInfo);
                                LastImageAdapter = new RecyclerViewAdapterDisplayImages(getContext(), list);
                                recyclerView.setAdapter(LastImageAdapter);
                                Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }else {

                            Log.e(TAG, "No session start");
                            Toast.makeText(getActivity(), "Start Session", Toast.LENGTH_SHORT).show();
                            MoveToStartAnalysis();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }else {
                    Toast.makeText(getActivity(), "dataSnapshot equal null", Toast.LENGTH_SHORT).show();

                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;

    }
//    @Override
//    public void sendData(String message) {
//
//        LastSessionFragment f = (LastSessionFragment) getSupportFragmentManager().findFragmentById(R.id.LastSessionItem);
//        f.displayReceivedData(message);
//
//    }


     private void MoveToStartAnalysis(){

         Start.setVisibility(View.VISIBLE);
         Camera.setVisibility(View.VISIBLE);
         Camera.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Fragment fragment = new Recieve_Images_Activity();

                 FragmentManager fragmentManager = getFragmentManager();

                 fragmentManager.beginTransaction().replace(R.id.Framelayout, fragment).commit();
             }
         });

     }


//    protected String displayReceivedData(String message)
//    {
//        return message;
//    }
//    private void ChangePicInfo() {
//        String AreaNew = Area.getText().toString();
//        String NotesNew = Note.getText().toString();
//        String ColorNew = Color.getText().toString();
//        String ObjNew = Obj.getText().toString();
//        databaseReference.child("Area").setValue(AreaNew);
//        databaseReference.child("Notes").setValue(NotesNew);
//        databaseReference.child("Color").setValue(ColorNew);
//        databaseReference.child("Objects").setValue(ObjNew);
//
//    }

    private void colorprogressbar(final float number) {
        final float percnt = number;

        ColorHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    if (progress < Math.round(percnt)) {
                        progress++;
                        ColorprogressBar.setProgress(progress);
                        //AreaprogressBar.setProgress(10);
                        //AreaprogressBar.setProgress(progress);
                    }
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < percnt; i++) {
                    try {
                        Thread.sleep(Math.round(percnt));
                        ColorHandler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    private void AreaProgress(final float value) {
        final float percnt = value;

        AreaHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    if (progress2 < Math.round(percnt)) {
                        progress2++;
                        AreaprogressBar.setProgress(progress2);

                    }
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < percnt; i++) {
                    try {
                        Thread.sleep(Math.round(percnt));
                        AreaHandler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

}
