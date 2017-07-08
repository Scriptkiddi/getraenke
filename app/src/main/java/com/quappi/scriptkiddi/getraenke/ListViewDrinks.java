package com.quappi.scriptkiddi.getraenke;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.quappi.scriptkiddi.getraenke.adapter.DrinksListViewAdapter;
import com.quappi.scriptkiddi.getraenke.utils.Drink;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.util.ArrayList;

public class ListViewDrinks extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Person person;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_drinks);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        //Set Toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        ArrayList<Drink> drinks = new ArrayList<>();
        drinks.add(new Drink("Club Mate", 1.00, 0.5, getDrawable(R.drawable.club_mate)));
        drinks.add(new Drink("Paulaner Spezi", 0.70, 0.5, getDrawable(R.drawable.paulaner_spezi)));
        drinks.add(new Drink("Schönbuch Radler", 0.70, 0.33, getDrawable(R.drawable.schoenbuch_naturparkradler)));
        drinks.add(new Drink("CD Helles", 0.70, 0.33, getDrawable(R.drawable.cd_helles)));

        this.person = (Person)getIntent().getSerializableExtra("Person");
        TextView user = (TextView) findViewById(R.id.user);
        user.setText(String.format("User: %s %s",person.getFirstName(), person.getLastName()));
        TextView moneyOwed = (TextView) findViewById(R.id.money_owed);
        moneyOwed.setText("Guthaben: 90€");

        mAdapter = new DrinksListViewAdapter(drinks, this.person);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
