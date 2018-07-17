package application.mobileforms;

/**
 * Created by Inas on 24-Feb-18.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Diseases_Activity extends Fragment {
    private DatabaseReference mref;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String TineaValue;
    String VitiligoValue;
    String LupusValue;
    String CancerValue;
    String PsoriasisValue;
    String AcneValue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_expand, null);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);

        // Database reference
        mref = FirebaseDatabase.getInstance().getReference().child("Users").child("Diseases");
        Log.e("Ref", mref.toString());
        try {
            mref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("Tinea") && dataSnapshot.hasChild("Vitiligo")) {
                        try {//Get diseases info from firebase
                            TineaValue = dataSnapshot.child("Tinea").getValue().toString();
                            VitiligoValue = dataSnapshot.child("Vitiligo").getValue().toString();
                            PsoriasisValue = dataSnapshot.child("Psoriasis").getValue().toString();
                            LupusValue = dataSnapshot.child("Lupus").getValue().toString();
                            CancerValue = dataSnapshot.child("Skin Cancer").getValue().toString();
                            AcneValue = dataSnapshot.child("Acne").getValue().toString();
                            // Preparing Expandable list
                            prepareListData(TineaValue, VitiligoValue,PsoriasisValue,LupusValue,CancerValue,AcneValue);
                            listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
                            // setting list adapter
                            expListView.setAdapter(listAdapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else
                        Toast.makeText(getActivity(), "No info", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    /*
     * Preparing the list data
     */
    private void prepareListData(String tinea,String vitiligo,String psoriasis,String lupus,String cancer,String acne) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Vitiligo");
        listDataHeader.add("Tinea");
        listDataHeader.add("Skin Cancer");
        listDataHeader.add("Lupus");
        listDataHeader.add("Acne");
        listDataHeader.add("Psoriasis");


        // Adding child data
        List<String> vitiligolist = new ArrayList<String>();
        vitiligolist.add(vitiligo);


        List<String> tinealist = new ArrayList<String>();
        tinealist.add(tinea);

        List<String> acnelist = new ArrayList<String>();
        acnelist.add(acne);

        List<String> cancerlist = new ArrayList<String>();
        cancerlist.add(cancer);

        List<String> psoriasislist = new ArrayList<String>();
        psoriasislist.add(psoriasis);

        List<String> lupuslist = new ArrayList<String>();
        lupuslist.add(lupus);


        listDataChild.put(listDataHeader.get(0), vitiligolist); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tinealist);
        listDataChild.put(listDataHeader.get(2), cancerlist);
        listDataChild.put(listDataHeader.get(3),lupuslist );
        listDataChild.put(listDataHeader.get(4),acnelist );
        listDataChild.put(listDataHeader.get(5),psoriasislist );

    }
}