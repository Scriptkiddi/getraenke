package com.quappi.scriptkiddi.getraenke.utils;

import android.nfc.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michel on 7/8/17.
 */

public class NfcTagRegister {
    private HashMap<String, Person> personByTag = new HashMap<>();
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static NfcTagRegister instance = null;

    private NfcTagRegister() {

    }

    public static NfcTagRegister getInstance() {
        if (instance == null) {
            instance = new NfcTagRegister();
        }
        return instance;
    }

    /**
     * @param tag    NFC tag object
     * @param target user the tag belongs to
     * @throws InvalidPersonException if the person does not exist
     */
    public void addNfcTag(Tag tag, Person target) throws InvalidPersonException {
        if (target == null) {
            throw new InvalidPersonException();
        }
        personByTag.put(getNfcTagId(tag), target);
    }

    public Person getPersonForTag(Tag tag) {
        return personByTag.get(getNfcTagId(tag));
    }

    public void removeNfcTag(String tagString) {
        personByTag.remove(tagString);
    }

    public String getNfcTagId(Tag tag) {
        return bytesToHex(tag.getId());
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public List<String> getTagIdsForPerson(Person p) {
        ArrayList<String> tagList = new ArrayList<>();
        for (Map.Entry<String, Person> entry : personByTag.entrySet()) {
            if (entry.getValue().equals(p)) {
                tagList.add(entry.getKey());
            }
        }
        return tagList;
    }
}
