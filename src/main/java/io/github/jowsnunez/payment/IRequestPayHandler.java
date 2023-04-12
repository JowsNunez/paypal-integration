/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.jowsnunez.payment;

/**
 *
 * @author el_fr
 */
public interface IRequestPayHandler {
    
     String requestCreateOrder(RequestPay req);
     String requestCaptureOrder(RequestPay req);
     String requestToken();
    
}
