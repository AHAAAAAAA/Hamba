package com.hamba.hambameet;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.security.acl.Acl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "8Os7PVIih2cRDfomi10U64mY07t0sypgNu8RUxWw", "8MWpDTnDLnaVhIjnzhtWSmjv5vIgr2HEc9AIBqN0");
        onCreateToolbar();
        ArrayList<String> eventsList = new ArrayList<>();
        eventsList.add("Hack CU");
        eventsList.add("Party");
        eventsList.add("Protest");
        eventsList.add("Concert");
        eventsList.add("Movie");
        eventsList.add("Picnic");
        listview = (ListView) findViewById(R.id.listViewEvents);
        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventsList));
        onListItemClicked();
    }

    private void onCreateToolbar() {
        final Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mDrawerList = (ListView)findViewById(android.R.id.list);
        ArrayList<String> menuList = new ArrayList();
        menuList.add("Contacts");
        menuList.add("Ali");
        menuList.add("Kay");
        menuList.add("Galean");
        menuList.add("Miguel");
        mDrawerList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuList));
        //Handles toolbar clicks
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else {
                }
            }
        });
        //If I ever need to check for a long click
       /* mDrawerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                }
                return false;
            }
        });*/
        //Toolbar toggle handling
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close){
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
                invalidateOptionsMenu();
                syncState();
            }
            public void onDrawerOpened(View v){
                super.onDrawerOpened(v);
                invalidateOptionsMenu();
                syncState();
            }
        };
        //Listens for drawer item clicks
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }
    private void onListItemClicked() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                startActivity(new Intent(MainActivity.this, EventActivity.class));
            }
        });
    }
    public void onNewEventButtonClicked(View view) {
        LayoutInflater newEventInflater = MainActivity.this.getLayoutInflater();
        final AlertDialog.Builder newEventDialog = new AlertDialog.Builder(MainActivity.this);
        final View albumDialogView = newEventInflater.inflate(R.layout.new_event_dialog, null);
        newEventDialog.setView(albumDialogView);
        newEventDialog.setTitle("Create new Event");
        final EditText eventName = (EditText) albumDialogView.findViewById(R.id.editTextEventName);
        final EditText eventDescription = (EditText) albumDialogView.findViewById(R.id.editTextEventDescription);
        final EditText eventDateAndTime = (EditText) albumDialogView.findViewById(R.id.editTextEventDateAndTime);
        final EditText eventLocation = (EditText) albumDialogView.findViewById(R.id.editTextEventLocation);
        newEventDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = eventName.getText().toString();
                String description = eventDescription.getText().toString();
                String dateAndTime = eventDateAndTime.getText().toString();
                String location = eventLocation.getText().toString();
                //TODO::Implement code to add event here
                Toast.makeText(getApplicationContext(), "Event added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        newEventDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Event not added", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = newEventDialog.create();
        dialog.show();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.about) {
            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("About");
            dialogBuilder.setMessage("Current release: Version 1.0\nRelease date: Jan 7th 2015");
            dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog about = dialogBuilder.create();
            about.show();
            return true;
        }

        //Toolbar Stuff
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                    mDrawerLayout.openDrawer(mDrawerList);
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}


