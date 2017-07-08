package com.quappi.scriptkiddi.getraenke;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.quappi.scriptkiddi.getraenke.utils.InvalidPersonException;
import com.quappi.scriptkiddi.getraenke.utils.PermissionDeniedException;
import com.quappi.scriptkiddi.getraenke.utils.Permissions;
import com.quappi.scriptkiddi.getraenke.utils.Person;
import com.quappi.scriptkiddi.getraenke.utils.TagRegister;

import java.util.Arrays;

public class ManagePersonActivity extends AppCompatActivity {
    private Person person;
    private boolean canAddTag = false;
    private Tag scannedTag = null;
    public static final String TAG = "NfcActivity";
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_person);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Log.e(TAG, "NFC adapter not detected.");
        } else {
            pendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        }

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        this.person = (Person) getIntent().getSerializableExtra("Person");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(person.getFirstName() + " " + person.getLastName());
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please scan your NFC tag", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                canAddTag = true;
                scannedTag = null;
            }
        });
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
            if (canAddTag) {
                if (TagRegister.getInstance().getPersonForTag(tag) != null) {
                    Snackbar.make(findViewById(android.R.id.content), "This NFC tag is already registered.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (scannedTag != null) {
                    if (!Arrays.equals(scannedTag.getId(), tag.getId())) {
                        Snackbar.make(findViewById(android.R.id.content), "NFC tag verification failed! Please scan again", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Person admin = new Person("Admin", "Admin", new Permissions(true, true, true, true, true));
                        try {
                            TagRegister.getInstance().addNfcTag(admin, scannedTag, person);
                            Snackbar.make(findViewById(android.R.id.content), "NFC tag added successfully.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } catch (PermissionDeniedException e) {
                            Snackbar.make(findViewById(android.R.id.content), "No permission to add NFC tag!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } catch (InvalidPersonException e) {
                            Snackbar.make(findViewById(android.R.id.content), "Invalid person!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        scannedTag = null;
                        canAddTag = false;
                    }
                } else {
                    scannedTag = tag;
                    Snackbar.make(findViewById(android.R.id.content), "Please scan NFC tag again to verify.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        }
    }
}
