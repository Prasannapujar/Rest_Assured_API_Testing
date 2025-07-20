package org.example.complexpojo;

import java.util.List;

public class Request {
    private String url;
    private String method;
    private List<Header> header;
    private Body body;
    private String description;

    public Request() {
        // Default constructor
    }

    public Request(String url, String method, List<Header> header, Body body, String description) {
        this.url = url;
        this.method = method;
        this.header = header;
        this.body = body;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Header> getHeader() {
        return header;
    }

    public void setHeader(List<Header> header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
