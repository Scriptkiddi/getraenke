package com.quappi.scriptkiddi.getraenke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.quappi.scriptkiddi.getraenke.utils.Person;

public class PayActivity extends AppCompatActivity {

    private Person person;
    public static final String TAG = "PayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_person);

        this.person = (Person) getIntent().getSerializableExtra("Person");
    }
}
