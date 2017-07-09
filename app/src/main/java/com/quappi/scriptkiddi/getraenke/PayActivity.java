package com.quappi.scriptkiddi.getraenke;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        final TextView creditTextView = (TextView) findViewById(R.id.current_credit);
        creditTextView.setText(String.format("%.2f €", person.getCredit()));

        final EditText amountPayed = (EditText) findViewById(R.id.balance_payed_field);

        Button payButton = (Button) findViewById(R.id.pay_button);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double amount = Double.parseDouble(amountPayed.getText().toString());
                amountPayed.setText("");
                if (amount <= 0) {
                    Snackbar.make(findViewById(android.R.id.content), "Amount not allowed!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                person.setCredit(person.getCredit() + amount);
                //TODO send to server
                creditTextView.setText(String.format("%.2f €", person.getCredit()));
            }
        });
    }

}
