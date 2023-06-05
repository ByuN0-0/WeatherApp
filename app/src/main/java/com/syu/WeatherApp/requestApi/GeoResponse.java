/*
GeoResponse.java
이 파일은 지리정보 api 데이터를 호출받아서 각각의 선언된 변수에 어노테이션된 함수를 통해 값을 할당하는 클래스이다.
response.get()으로 받은 객체를 클래스명과 get()함수로 파싱하여 원하는 데이터를 변수에 할당하는 방식

그 외 ~response.java 파일도 형태가 동일하다.
 */

package com.syu.WeatherApp.requestApi;

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