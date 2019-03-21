package com.app.libandroidapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.libandroidapp.LocalStorage.Requests;
import com.app.libandroidapp.LocalStorage.SharedPref;
import com.app.libandroidapp.Plugin.FileAdapter;
import com.app.libandroidapp.Plugin.FileModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView fileListView;
    private List<FileModel> files;
    private TextView userNameID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        setupddsui();
        handelAllFiles();





       // cursor.moveToFirst();



        Log.i("testestest","testestest");



        // cursor.close();







        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this,uploadNewFile.class);
                startActivity(intent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
            }
        });








        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }




    private void handelAllFiles(){


    }

    private  void  setupddsui(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        String username = SharedPref.getString(this,"username");
        TextView navUsername = (TextView) headerView.findViewById(R.id.userNameID);
        navUsername.setText(username);



        files = Requests.getAllFiles(HomeActivity.this);

        Log.i("Files",files.toString());
        fileListView = findViewById(R.id.fileListView);


        FileAdapter fileAdapter = new FileAdapter(HomeActivity.this,files);

        fileListView.setAdapter(fileAdapter);


    }


    @Override
    protected void onStart() {
        super.onStart();
        checkifnotlogin();
    }





    private  void  checkifnotlogin(){
        String username = SharedPref.getString(this,"username");
        String user_id = SharedPref.getString(this,"user_id");

        if(username.matches("") || user_id.matches("")){
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }




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
        getMenuInflater().inflate(R.menu.home, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else

        if (id == R.id.nav_logout) {


            SharedPref.removeAll(this);

            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add) {
            Intent intent = new Intent(HomeActivity.this,uploadNewFile.class);
            startActivity(intent);

        }
//        else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
