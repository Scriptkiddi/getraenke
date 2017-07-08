package com.quappi.scriptkiddi.getraenke.utils;

import android.nfc.Tag;

import java.util.HashMap;

/**
 * Created by michel on 7/8/17.
 */

public class TagRegister {
    private HashMap<String, Person> personByTag = new HashMap<>();
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * @param editor the user that attempts to add a NFC tag
     * @param tag    NFC tag object
     * @param target user the tag belongs to
     * @throws PermissionDeniedException if editor user has no permission to modify user
     * @throws InvalidPersonException    if the person does not exist
     */
    private void addNfcTag(Person editor, Tag tag, Person target) throws PermissionDeniedException, InvalidPersonException {
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
}
