package com.app.libandroidapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.libandroidapp.LocalStorage.Requests;
import com.app.libandroidapp.LocalStorage.SharedPref;
import com.app.libandroidapp.Plugin.Alerts;

public class LoginActivity extends AppCompatActivity {



    String username,password;
    private Button signInBtn,signUpBtn;
    private EditText usernameTxt,passwordTxt;
    private LinearLayout myCoverLinerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUiConst();
        setEvents();
        createNotificationChannel();
        checkExistsLogin();


    }

    private void setUiConst(){
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        myCoverLinerLayout = findViewById(R.id.myCoverLinerLayout);
        passwordTxt = findViewById(R.id.passwordEditTxt);
        usernameTxt = findViewById(R.id.userEditTxt);

    }

    private void setUiVars(){
        username = usernameTxt.getText().toString();
        password = passwordTxt.getText().toString();
    }



    private void checkExistsLogin(){

        String username = SharedPref.getString(this,"username");

        if(!username.matches("")){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }


    }


    private void  setEvents(){
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCoverLinerLayout.setVisibility(View.VISIBLE);
                setUiVars();
                if(validation()){




                   if( Requests.LoginRequest(LoginActivity.this,username,password) ) {

//                       Alerts.showDilog(LoginActivity.this, String.valueOf(R.string.login_success));

                       Toast.makeText(LoginActivity.this,R.string.login_success,Toast.LENGTH_LONG).show();

                       Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                       startActivity(intent);

                   }else{
                       Toast.makeText(LoginActivity.this,R.string.login_fail,Toast.LENGTH_LONG).show();
                       myCoverLinerLayout.setVisibility(View.GONE);
                   }
                }else{
                    myCoverLinerLayout.setVisibility(View.GONE);
                }


            }
        });



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myCoverLinerLayout.setVisibility(View.VISIBLE);
                setUiVars();
                if(validation()){
                    if( Requests.RegisterRequest(LoginActivity.this,username,password) ) {
                       // Alerts.showDilog(LoginActivity.this, String.valueOf(R.string.register_success));
//
                        Toast.makeText(LoginActivity.this,R.string.register_success,Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(LoginActivity.this,R.string.username_exists,Toast.LENGTH_LONG).show();
                        myCoverLinerLayout.setVisibility(View.GONE);
                    }
                }else{
                    myCoverLinerLayout.setVisibility(View.GONE);
                }




            }
        });
    }


    private  boolean validation(){

//        if(1==1){
//            Toast.makeText(LoginActivity.this,R.string.invalid_username,Toast.LENGTH_LONG).show();
//            return  false;
//        }
        return  true;
    }



    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notification chanel";
            String description = "notification ";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("0", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}
