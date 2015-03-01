package com.iteso.marco.tarea1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {
    private String[] cars;
    private String[] options;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerRelativeLayout;
    private ListView mDrawerList;
    private ListView mDrawerOptions;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle = "";
    private CharSequence mDrawerTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ArrayAdapter<String> adapter;
        ArrayAdapter<String> options_adapter;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cars = getResources().getStringArray(R.array.car_list);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerRelativeLayout = (LinearLayout)findViewById(R.id.left_drawer);
        mDrawerList = (ListView)findViewById(R.id.left_drawer_list);
        mDrawerOptions = (ListView)findViewById(R.id.left_drawer_options);

        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close){
          public void onDrawerClosed(View view)
          {
              super.onDrawerClosed(view);
              //getActionBar().setTitle(mTitle);
              invalidateOptionsMenu();
          }

          public void onDrawerOpened(View view)
          {
             super.onDrawerOpened(view);
             //getActionBar().setTitle(mDrawerTitle); <- Null pointer Exception? How is the action bar null?
             invalidateOptionsMenu();
          }
        };

        //Set the drawer toggle as DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);  <- Null reference again?

        //Set the list data
        adapter = new ArrayAdapter<>(this, R.layout.drawer_list_item, cars);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        //Set the options data
        options = getResources().getStringArray(R.array.options);
        options_adapter = new ArrayAdapter<>(this, R.layout.target_item, options);
        mDrawerOptions.setAdapter(options_adapter);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //If the nav drawer is open, hide action items related to the content view.
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerRelativeLayout);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private void selectItem(int position)
    {
        Fragment fragment = new CarFragment();
        Bundle args = new Bundle();
        args.putInt(CarFragment.ARG_CAR_NUMBER, position);
        fragment.setArguments(args);

        //Replace current fragment with new fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                        .replace(R.id.contentFrame, fragment)
                        .commit();

        // Highlight selected item, update title, close drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(cars[position]);
        mDrawerLayout.closeDrawer(mDrawerRelativeLayout);

    }

    public void setTile(String title)
    {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
}
