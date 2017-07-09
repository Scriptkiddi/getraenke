package com.quappi.scriptkiddi.getraenke.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by fritz on 09.07.17.
 */

public class Token {
    @SerializedName("token")
    String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
