package com.kareemwaleed.arxicttask.api_request;

/**
 * Created by Kareem Waleed on 7/3/2017.
 */

public class RequestParameters {
    private String url;
    private String methodType;

    public RequestParameters(String url , String methodType) {
        this.url = url;
        this.methodType = methodType;
    }

    public String getMethodType() {
        return methodType;
    }

    public String getUrl() {
        return url;
    }
}
