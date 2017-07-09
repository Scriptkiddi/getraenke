package com.quappi.scriptkiddi.getraenke;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.quappi.scriptkiddi.getraenke.api.DosService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Starting DosService and doing API auth
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(sharedPref.getString(Constants.username, null) != null && sharedPref.getString(Constants.userpassword, null) != null){
            DosService.getInstance(getApplicationContext());
            Intent intent = new Intent(this, ListViewPeople.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }




    }
}
