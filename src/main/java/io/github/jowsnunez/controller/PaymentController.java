/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.jowsnunez.controller;

import io.github.jowsnunez.payment.Order;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.Gson;
import io.github.jowsnunez.payment.IRequestPayHandler;
import io.github.jowsnunez.payment.RequestHandler;
import io.github.jowsnunez.payment.RequestPay;
import java.io.InputStream;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author el_fr
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("payment")
public class PaymentController {

    @PostMapping("/createOrder")
    public ResponseEntity createOrder() {
        try {

            IRequestPayHandler handler = new RequestHandler();
            RequestPay req = new RequestPay.RequestBuilder()
                    .setUrl("https://api.sandbox.paypal.com/v2/checkout/orders")
                    .setContentType("application/json")
                    .setRequestId(UUID.randomUUID().toString())
                    .setAuthentication("Bearer " + handler.requestToken())
                    .setMethod("POST")
                    .build();
            
            System.out.println("auth" +req.getAuthorization());

            return ResponseEntity.ok(handler.requestCreateOrder(req));
        } catch (MalformedURLException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @GetMapping("/captureOrder")
    public RedirectView captureOrder(@RequestParam String token, @RequestParam String requestId) {

        try {
            IRequestPayHandler handler = new RequestHandler();
            RequestPay req = new RequestPay.RequestBuilder()
                    .setUrl("https://api-m.sandbox.paypal.com/v2/checkout/orders/" + token + "/capture")
                    .setContentType("application/json")
                    .setRequestId(requestId)
                    .setAuthentication("Bearer " + handler.requestToken())
                    .setMethod("POST")
                    .build();

            handler.requestCaptureOrder(req);

            return new RedirectView("http://localhost:4200/succes?token=" + token);

        } catch (MalformedURLException ex) {
            Logger.getLogger(PaymentController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

}
