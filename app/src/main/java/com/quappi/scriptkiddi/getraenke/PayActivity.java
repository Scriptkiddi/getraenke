package com.quappi.scriptkiddi.getraenke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.quappi.scriptkiddi.getraenke.utils.Person;

public class PayActivity extends AppCompatActivity {

    private Person person;
    public static final String TAG = "PayActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        this.person = (Person) getIntent().getSerializableExtra("Person");
        TextView creditTextView = (TextView) findViewById(R.id.current_credit);
        creditTextView.setText(String.format("%.2f €", person.getCredit()));

    }
}
