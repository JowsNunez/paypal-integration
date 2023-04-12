/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.jowsnunez.payment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.jowsnunez.controller.PaymentController;
import io.github.jowsnunez.util.Constant;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author el_fr
 */
public class RequestHandler implements IRequestPayHandler {

    private String token;

    @Override
    public String requestCreateOrder(RequestPay req) {

        try {
            Order or = new Order.OrderBuilder()
                    .currencyCode("USD")
                    .value("5000.00")
                    .description("Pantalla Plasma")
                    .intent("CAPTURE")
                    .paymentMethodPreference("IMMEDIATE_PAYMENT_REQUIRED")
                    .paymentMethodSelected("PAYPAL")
                    .brandName("EXAMPLE INC")
                    .locale("en-US")
                    .landingPage("LOGIN")
                    .userAction("PAY_NOW")
                    .shippingPreference("SET_PROVIDED_ADDRESS")
                    .returnUrl("http://localhost:5000/payment/captureOrder?requestId=" + req.getRequestId())
                    .cancelUrl("http://localhost:4200/cancel").build();

            //URL url = new URL("https://api.sandbox.paypal.com/v2/checkout/orders");
            HttpURLConnection httpConn = (HttpURLConnection) req.getUrl().openConnection();
            httpConn.setRequestMethod(req.getMethod());

            httpConn.setRequestProperty("Content-Type", req.getContentType());
            httpConn.setRequestProperty("PayPal-Request-Id", req.getRequestId());
            httpConn.setRequestProperty("Authorization", req.getAuthorization());

            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            Gson gson = new Gson();

            String cs = gson.toJson(or, Order.class);
            writer.write(cs);
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();

            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";

        } catch (MalformedURLException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }
    }

    @Override
    public String requestCaptureOrder(RequestPay req) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) req.getUrl().openConnection();
            httpConn.setRequestMethod(req.getMethod());

            httpConn.setRequestProperty("Content-Type", req.getContentType());
            httpConn.setRequestProperty("PayPal-Request-Id", req.getRequestId());
            httpConn.setRequestProperty("Authorization", req.getAuthorization());

            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public String requestToken() {

        OutputStreamWriter writer;
        try {
            String clientId = Constant.clientId;
            String clientSecret = Constant.secret;
            String body = "grant_type=client_credentials";
            String authString = clientId + ":" + clientSecret;
            byte[] authBytes = authString.getBytes();
            String auth = Base64.getEncoder().encodeToString(authBytes);
            
            URL url = new URL("https://api-m.sandbox.paypal.com/v1/oauth2/token");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            
            httpConn.setRequestProperty("Authorization", "Basic " + auth);
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setDoOutput(true);
            writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();
            InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                    ? httpConn.getInputStream()
                    : httpConn.getErrorStream();
            Scanner s = new Scanner(responseStream).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";Gson gson = new Gson();
     JsonObject responseJson = gson.fromJson(response, JsonObject.class);
     String accessToken = responseJson.get("access_token").getAsString();
     return accessToken;
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            return null;

        }

    }

}
