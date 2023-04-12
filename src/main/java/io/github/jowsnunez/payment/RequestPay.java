/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.jowsnunez.payment;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author el_fr
 */
public class RequestPay {

    private RequestPay instance;
    private URL url;
    private String method;
    private String contentType;
    private String authentication;
    private String body;
    private String requestId;
  

    public RequestPay getInstance() {
        return instance;
    }

    public void setInstance(RequestPay instance) {
        this.instance = instance;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAuthorization() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public RequestPay() {

    }

    public static class RequestBuilder {

        private RequestPay instance;

        public RequestBuilder() {
            this.instance = new RequestPay();
        }

        public RequestBuilder setUrl(String url) throws MalformedURLException {
            URL uri = new URL(url);
            this.instance.setUrl(uri);
            return this;
        }

        public RequestBuilder setMethod(String method) {
            this.instance.setMethod(method);
            return this;
        }

        public RequestBuilder setContentType(String contentType) {
            this.instance.setContentType(contentType);
            return this;
        }

        public RequestBuilder setAuthentication(String authentication) {
            this.instance.setAuthentication(authentication);
            return this;
        }

        public RequestBuilder setBody(String body) {
            this.instance.setBody(body);
            return this;
        }
        
        public RequestBuilder setRequestId(String requestId)
        {
            this.instance.setRequestId(requestId);
            return this;
        }

        public RequestPay build() {
            return instance;
        }

    }

}
