package com.quappi.scriptkiddi.getraenke.utils;

import android.nfc.Tag;

import com.quappi.scriptkiddi.getraenke.utils.exception.InvalidPersonException;
import com.quappi.scriptkiddi.getraenke.utils.exception.PermissionDeniedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by michel on 7/8/17.
 */

public class TagRegister {
    private HashMap<String, Person> personByTag = new HashMap<>();
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static TagRegister instance = null;

    private TagRegister() {

    }

    public static TagRegister getInstance() {
        if (instance == null) {
            instance = new TagRegister();
        }
        return instance;
    }

    /**
     * @param editor the user that attempts to add a NFC tag
     * @param tag    NFC tag object
     * @param target user the tag belongs to
     * @throws PermissionDeniedException if editor user has no permission to modify user
     * @throws InvalidPersonException    if the person does not exist
     */
    public void addNfcTag(Person editor, Tag tag, Person target) throws PermissionDeniedException, InvalidPersonException {
        if (!editor.getPermissions().canModifyUsers()) {
            throw new PermissionDeniedException();
        } else if (target == null) {
            throw new InvalidPersonException();
        }
        personByTag.put(getNfcTagId(tag), target);
    }

    public Person getPersonForTag(Tag tag) {
        return personByTag.get(getNfcTagId(tag));
    }

    public void removeNfcTag(Person editor, Tag tag) throws PermissionDeniedException {
        if (!editor.getPermissions().canModifyUsers()) {
            throw new PermissionDeniedException();
        }
        personByTag.remove(getNfcTagId(tag));
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
