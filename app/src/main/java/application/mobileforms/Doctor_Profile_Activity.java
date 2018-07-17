package application.mobileforms;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Inas on 10-Mar-18.
 */

public class Doctor_Profile_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_profile_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout2);
        mToolbar = (Toolbar) findViewById(R.id.new_action);
        setSupportActionBar(mToolbar);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        // Set the drawer toggle as the DrawerListener
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.Navigation2);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fragment = null;
        fragment = new ViewClinic_InfoFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.Framelayout2, fragment);
        ft.commit();
        //displaySelectedScreen(R.id.LastSessionItem);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
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
            case R.id.PersonalInformation2:

                try {
                    fragment = new Personal_Info_Activity();

                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            case R.id.Home:
                try {
                    fragment = new DisplayImagesFragment();

                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case R.id.InboxItem:

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

            case R.id.DiseasesItem:
                try {
                    fragment = new Diseases_Activity();
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
            case R.id.SearchItem:
                try {
                    fragment = new DoctorSearchFragment();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
        try {
            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.Framelayout2, fragment);
                ft.commit();

            } else
                Toast.makeText(this, "Fragment is null", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);

    }
}
