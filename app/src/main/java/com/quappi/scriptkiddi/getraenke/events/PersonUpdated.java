package com.quappi.scriptkiddi.getraenke.events;

import com.quappi.scriptkiddi.getraenke.utils.Person;

/**
 * Created by fritz on 08.07.17.
 */

public class PersonUpdated {
    private Person person;

    public PersonUpdated(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }
}
