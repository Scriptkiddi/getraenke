package com.quappi.scriptkiddi.getraenke;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.quappi.scriptkiddi.getraenke.adapter.PeopleListViewAdapter;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.util.ArrayList;
import java.util.List;

public class ListViewPeople extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private PeopleListViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Person> people = new ArrayList<>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        // specify an adapter

        people.add(new Person("Michel", "Weitbrecht"));
        people.add(new Person("Fritz", "Otlinghaus"));
        people.add(new Person("Janne", "Heß"));
        people.add(new Person("Markus", "Mroch"));
        people.add(new Person("Jonas", "Auer"));
        mAdapter = new PeopleListViewAdapter(people);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Person> filteredModelList = filter(people, query);
        mAdapter.replaceAll(filteredModelList);
        mRecyclerView.scrollToPosition(0);
        return true;
    }

    private static List<Person> filter(List<Person> models, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Person> filteredModelList = new ArrayList<>();
        for (Person model : models) {
            final String text = model.getFirstName().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}
