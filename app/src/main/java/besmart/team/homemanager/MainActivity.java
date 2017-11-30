package besmart.team.homemanager;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import besmart.team.homemanager.logic.Task;

public class MainActivity extends AppCompatActivity {

    private boolean onlyMyTasks = false;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(user == null) {
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

        if(id ==  R.id.checkable_menu) {
            onlyMyTasks = !item.isChecked();
            item.setChecked(onlyMyTasks);
            return true;
        }

        if(id == R.id.logOutButton) {
            return true;
        }

        // return super.onOptionsItemSelected(item);
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem checkable = menu.findItem(R.id.checkable_menu);
        checkable.setChecked(onlyMyTasks);
        return true;
    }

    public void logOut(MenuItem view) {
        FirebaseAuth.getInstance().signOut();
        System.out.println("Signing out");
        finish();
        startActivity(new Intent(this, SignInActivity.class));
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
            // Create a switch case to return the fragments
            switch (position) {
                case 0:
                    fragment_main_shopping tab1 = new fragment_main_shopping();
                    return tab1;
                case 1:
                    fragment_main_tasks tab2 = new fragment_main_tasks();
                    return tab2;
                case 2:
                    fragment_main_family tab3 = new fragment_main_family();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Shopping";
                case 1:
                    return "Tasks";
                case 2:
                    return "Family";
            }
            return null;
        }
    }

}
