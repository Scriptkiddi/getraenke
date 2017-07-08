package com.quappi.scriptkiddi.getraenke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.quappi.scriptkiddi.getraenke.adapter.PeopleListViewAdapter;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.util.ArrayList;

public class ListViewPeople extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_people);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Michel", "weitbrecht"));
        people.add(new Person("Fritz", "weitbrecht"));
        people.add(new Person("Janne", "weitbrecht"));
        people.add(new Person("Pi", "weitbrecht"));
        people.add(new Person("Jonas", "weitbrecht"));
        mAdapter = new PeopleListViewAdapter(people);
        mRecyclerView.setAdapter(mAdapter);

    }


}
