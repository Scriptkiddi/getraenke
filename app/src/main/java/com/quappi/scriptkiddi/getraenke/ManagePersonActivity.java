package com.quappi.scriptkiddi.getraenke;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.quappi.scriptkiddi.getraenke.utils.Person;

public class ManagePersonActivity extends AppCompatActivity {
    private Person person;
    private boolean canAddTag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_person);

        this.person = (Person) getIntent().getSerializableExtra("Person");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(person.getFirstName() + " " + person.getLastName());
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please scan your NFC tag twice", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                canAddTag = true;
            }
        });
    }
}
