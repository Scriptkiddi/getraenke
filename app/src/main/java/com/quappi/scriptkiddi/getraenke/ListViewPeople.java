package com.quappi.scriptkiddi.getraenke;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.quappi.scriptkiddi.getraenke.adapter.PeopleListViewAdapter;
import com.quappi.scriptkiddi.getraenke.utils.Permissions;
import com.quappi.scriptkiddi.getraenke.utils.Person;
import com.quappi.scriptkiddi.getraenke.utils.TagRegister;

import java.util.ArrayList;

public class ListViewPeople extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private static final String TAG = "ListViewPeople";

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
        Permissions userPermission = new Permissions(false, false, false, false, true);
        people.add(new Person("Michel", "weitbrecht", userPermission));
        people.add(new Person("Fritz", "weitbrecht", userPermission));
        people.add(new Person("Janne", "weitbrecht", userPermission));
        people.add(new Person("Pi", "weitbrecht", userPermission));
        people.add(new Person("Jonas", "weitbrecht", userPermission));
        mAdapter = new PeopleListViewAdapter(people);
        mRecyclerView.setAdapter(mAdapter);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Log.e(TAG, "NFC adapter not detected.");
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            //TODO check if adapter is enabled
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
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
