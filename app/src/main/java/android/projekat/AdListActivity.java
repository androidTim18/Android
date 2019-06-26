package android.projekat;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;

import static android.projekat.MainActivity.userDbHelper;

public class AdListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Intent i;
    DrawerLayout drawer;
    NavigationView navigationView;
    ImageView nav_menu_icon;
    Toast toast;

    HttpHelper httpHelper;
    static String BASE_URL = "localhost:8000";
    String GET_ALL_ADS = BASE_URL + "/ads";
    int isRefreshed;
    Ad[] adsRefreshed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_list);

        nav_menu_icon = findViewById(R.id.nav_menu_icon);
        nav_menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(false);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.bAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(i);
            }
        });

        httpHelper = new HttpHelper();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            SharedPreferences.Editor editor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
            ((SharedPreferences.Editor) editor).remove("userFullName");
            ((SharedPreferences.Editor) editor).remove("userId");
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Menu m = navigationView.getMenu();
        MenuItem rating_display = m.findItem(R.id.nav_rating);


        User[] users = userDbHelper.readRatings();
        Double average = 0.0;
        if (users != null) {
            Double sum = 0.0;
            for (int i = 0; i < users.length; i++) {
                sum += users[i].rating;
                Log.i("Ocene", users[i].rating.toString());
            }
            average = sum / users.length;
            Log.i("suma", sum.toString());
        }
        DecimalFormat formatNum = new DecimalFormat("#.#");

        rating_display.setTitle(rating_display.getTitle()+ formatNum.format(average));
        Log.i("RATING", average.toString());
        MenuItem dispAverage = findViewById(R.id.nav_rating);
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.nav_menu_icon) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch(item.getItemId()){
            case R.id.nav_settings:
                i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.nav_add:
                i = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(i);
                break;
            case R.id.nav_rating:
                i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                break;
            case R.id.nav_logout:
                //TODO: Logout properly
                SharedPreferences.Editor editor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
                ((SharedPreferences.Editor) editor).remove("userFullName");
                ((SharedPreferences.Editor) editor).remove("userId");
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                break;
            case R.id.nav_info:
                i = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(i);
                break;

            case R.id.nav_refresh:
                toast = Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT);
                toast.show();

                if (refreshData()){

                    ListAll.adapter.update(adsRefreshed);
                }
                else{
                    Toast.makeText(AdListActivity.this, "Can not refresh right now.", Toast.LENGTH_LONG).show();
                };

                break;
        };



        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    ListAll tab1 = new ListAll();
                    return tab1;
                case 1:
                    ListFav tab2 = new ListFav();
                    return tab2;
                case 2:
                    Search tab3 = new Search();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = getSharedPreferences("preferences", MODE_PRIVATE).edit();
        ((SharedPreferences.Editor) editor).remove("userFullName");
        ((SharedPreferences.Editor) editor).remove("userId");
        super.onDestroy();
    }


    public boolean refreshData(){
        isRefreshed = 0;
        new Thread(new Runnable() {
            public void run() {
                try {
                    JSONArray jsonArray = httpHelper.getJSONArrayFromURL(GET_ALL_ADS);
                    if (jsonArray == null) {
                        isRefreshed = 0;

                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            adsRefreshed= new Ad[jsonArray.length()];
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Ad newAd = new Ad(jsonObject.getString("species").toLowerCase(),
                                    jsonObject.getString("breed").toLowerCase(),
                                    jsonObject.getString("name"),
                                    jsonObject.getString("birthday"),
                                    jsonObject.getString("sex"),
                                    jsonObject.getString("location"),
                                    jsonObject.getString("owner"),
                                    jsonObject.getString("info"),
                                    jsonObject.getString("price"),
                                    jsonObject.getInt("available"),
                                    jsonObject.getInt("favorite"),
                                    jsonObject.getString("image").getBytes(),
                                    jsonObject.getString("adId"),
                                    jsonObject.getString("dateAdded"));

                                adsRefreshed[i] = newAd;
                        }
                        isRefreshed = 1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                }).start();
        if(isRefreshed == 0){
            return false;
        }
        else{
            return true;
        }
    };
}




