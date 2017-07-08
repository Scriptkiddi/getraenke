package com.quappi.scriptkiddi.getraenke;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.quappi.scriptkiddi.getraenke.adapter.PeopleListViewAdapter;
import com.quappi.scriptkiddi.getraenke.controller.personController;
import com.quappi.scriptkiddi.getraenke.caches.PersonCache;
import com.quappi.scriptkiddi.getraenke.events.PersonUpdated;
import com.quappi.scriptkiddi.getraenke.utils.Person;
import com.quappi.scriptkiddi.getraenke.utils.TagRegister;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class ListViewPeople extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private PeopleListViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private static final String TAG = "ListViewPeople";
    private ArrayList<Person> people = new ArrayList<>();
    private PersonCache cache = PersonCache.getInstance();


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

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);




        mAdapter = new PeopleListViewAdapter(people);
        mRecyclerView.setAdapter(mAdapter);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Log.e(TAG, "NFC adapter not detected.");
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }

        PersonCache.getInstance().refreshUserList();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            //TODO check if adapter is enabled
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
        ArrayList<Person> personArrayList = new ArrayList<>();
        personArrayList.addAll(personController.getAll());
        mAdapter.add(personArrayList);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PersonUpdated event) {
        mAdapter.add(event.getPerson());
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.d(TAG, "scanned tag: " + TagRegister.getInstance().getNfcTagId(tag));
            Person detectedPerson = TagRegister.getInstance().getPersonForTag(tag);
            if (detectedPerson != null) {
                Log.d(TAG, "detected person: " + detectedPerson);
                Intent listViewIntent = new Intent(this.getApplicationContext(), ListViewDrinks.class);
                listViewIntent.putExtra("Person", detectedPerson);
                this.startActivity(listViewIntent);
            }
        }
    }
}
