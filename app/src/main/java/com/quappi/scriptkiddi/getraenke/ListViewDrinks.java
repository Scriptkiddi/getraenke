package com.quappi.scriptkiddi.getraenke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_people);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        ArrayList<Drink> drinks = new ArrayList<>();
        drinks.add(new Drink("Club Mate", 0.70, 0.5, getDrawable(R.drawable.club_mate)));
        drinks.add(new Drink("Paulaner Spezi", 0.70, 0.5, getDrawable(R.drawable.paulaner_spezi)));
        drinks.add(new Drink("Schönbuch Radler", 0.70, 0.33, getDrawable(R.drawable.schoenbuch_naturparkradler)));

        this.person = (Person)getIntent().getSerializableExtra("Person");

        mAdapter = new DrinksListViewAdapter(drinks, this.person);
        mRecyclerView.setAdapter(mAdapter);
    }
}
