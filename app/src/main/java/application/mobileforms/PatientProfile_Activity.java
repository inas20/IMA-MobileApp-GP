package application.mobileforms;

import android.content.res.Configuration;
import android.os.Bundle;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import android.support.v4.view.GravityCompat;


import android.view.Menu;
import android.widget.Toast;


/**
 * Created by Inas on 11-Feb-18.
 */

public class PatientProfile_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.new_action);
        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        // Set the drawer toggle as the DrawerListener
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.Navigation);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fragment = null;
        fragment = new Recieve_Images_Activity();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayout, fragment);
        ft.commit();
        //displaySelectedScreen(R.id.LastSessionItem);

    }
//    @Override
//    public void sendData(String message) {
//
//        LastSessionFragment f = (LastSessionFragment) getSupportFragmentManager().findFragmentById(R.id.LastSessionItem);
//        f.displayReceivedData(message);
//    }

    //
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mToggle.syncState();
//    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_settings, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        displaySelectedScreen(item.getItemId());
        // Handle navigation view item clicks here.

        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.PersonalInformationn:

                try {
                    fragment = new Personal_Info_Activity();

                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            case R.id.Images:
                try {
                    fragment = new DisplayImagesFragment();

                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case R.id.Contact:

                try {
                    fragment = new Recieve_Images_Activity();

                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case R.id.Logout:
                try {
                    fragment = new logoutActivity();

                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case R.id.LastSessionItem:
                try {
                    fragment = new LastSessionFragment();

                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case R.id.DiseasesItem:
                try {
                    fragment = new Diseases_Activity();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case R.id.DoctorsList:
                try {
                    fragment = new DoctorsListFragment();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case R.id.ClinicItem:
                try {
                    fragment = new ViewClinic_InfoFragment();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
        try {
            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayout, fragment);
                ft.commit();

            } else
                Toast.makeText(this, "Fragment is null", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }

/*
    public void selectItemDrawer(MenuItem menuitem) {
        Fragment myfragment = null;
        Class FragmentClass;
        switch (menuitem.getItemId()) {
            case R.id.home:


                FragmentClass = HomeFragment.class;
                Toast.makeText(this, "This is Home", Toast.LENGTH_SHORT).show();
                break;


            case R.id.ProfileInfo:
                Toast.makeText(this, "Patient Form", Toast.LENGTH_SHORT).show();

                FragmentClass = Patient_form_fragment.class;
                break;


            default:
                FragmentClass = HomeFragment.class;


        }
        try {
            myfragment = (Fragment) FragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayout, myfragment);
        ft.commit();
        menuitem.setChecked(true);
    }
    */
}