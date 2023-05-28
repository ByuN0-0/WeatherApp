package com.cookandroid.location_show;

import com.google.gson.annotations.SerializedName;

public class GeoResponse {
    @SerializedName("local_names")
    private localName localname;
    public localName getLocalName() { return localname; }

    public static class localName{
        @SerializedName("ko")
        private String local_ko;
        public String getLocal_ko() { return local_ko; }
    }
}