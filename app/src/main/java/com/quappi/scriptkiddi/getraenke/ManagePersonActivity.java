package com.quappi.scriptkiddi.getraenke;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.quappi.scriptkiddi.getraenke.adapter.NfcTagListViewAdapter;
import com.quappi.scriptkiddi.getraenke.utils.InvalidPersonException;
import com.quappi.scriptkiddi.getraenke.utils.NfcTagRegister;
import com.quappi.scriptkiddi.getraenke.utils.Person;

import java.util.Arrays;
import java.util.List;

public class ManagePersonActivity extends AppCompatActivity {
    private Person person;
    private Tag scannedTag = null;
    public static final String TAG = "ManagePersonActivity";
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private NfcTagListViewAdapter nfcTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_person);

        this.person = (Person) getIntent().getSerializableExtra("Person");

        mRecyclerView = (RecyclerView) findViewById(R.id.nfc_tag_list_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        List<String> registeredTags = NfcTagRegister.getInstance().getTagIdsForPerson(person);
        Log.d(TAG, registeredTags.toString());
        nfcTagAdapter = new NfcTagListViewAdapter(registeredTags, person);
        mRecyclerView.setAdapter(nfcTagAdapter);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Log.e(TAG, "NFC adapter not detected.");
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        final EditText firstNameEdit = (EditText) findViewById(R.id.firstNameEdit);
        firstNameEdit.setText(person.getFirstName());
        firstNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                person.setFirstName(firstNameEdit.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        final EditText lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);
        lastNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                person.setLastName(lastNameEdit.getText().toString());
            }
        });
        lastNameEdit.setText(person.getLastName());

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
            Log.d(TAG, "scanned tag: " + NfcTagRegister.getInstance().getNfcTagId(tag));
            if (NfcTagRegister.getInstance().getPersonForTag(tag) != null) {
                Snackbar.make(findViewById(android.R.id.content), "This NFC tag is already registered.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (scannedTag == null || !Arrays.equals(scannedTag.getId(), tag.getId())) {
                Snackbar.make(findViewById(android.R.id.content), "Scan NFC tag again to add it.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                scannedTag = tag;
            } else {
                try {
                    NfcTagRegister.getInstance().addNfcTag(scannedTag, person);
                    Snackbar.make(findViewById(android.R.id.content), "NFC tag added successfully.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    nfcTagAdapter.replaceAll(NfcTagRegister.getInstance().getTagIdsForPerson(person));
                    mRecyclerView.scrollToPosition(0);
                } catch (InvalidPersonException e) {
                    Snackbar.make(findViewById(android.R.id.content), "Invalid person!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                scannedTag = null;
            }
        }
    }
}
