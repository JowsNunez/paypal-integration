/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package io.github.jowsnunez.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author el_fr
 */
public class Order {

    private String intent;
    private List<Map<String, Object>> purchase_units;

    Map<String, Object> payment_source;

    private Order(OrderBuilder builder) {
        Map<String, Object> amount = new HashMap<>();
        Map<String, Object> purchaseUnit = new HashMap<>();
        this.purchase_units = new ArrayList<>();
        this.payment_source = new HashMap<>();
        // Add intent
        this.intent = (builder.intent);
        // Create purchase_units object

        amount.put("currency_code", builder.currencyCode);
        amount.put("value", builder.value);
        purchaseUnit.put("description", builder.description);
        purchaseUnit.put("amount", amount);

        purchase_units.add(purchaseUnit);

        // Create payment_source object
        Map<String, Object> paypal = new HashMap<>();
        Map<String, Object> experienceContext = new HashMap<>();
        experienceContext.put("payment_method_preference", builder.paymentMethodPreference);
        experienceContext.put("payment_method_selected", builder.paymentMethodSelected);
        experienceContext.put("brand_name", builder.brandName);
        experienceContext.put("locale", builder.locale);
        experienceContext.put("landing_page", builder.landingPage);
        experienceContext.put("shipping_preference", builder.shippingPreference);
        experienceContext.put("user_action", builder.userAction);
        experienceContext.put("return_url", builder.returnUrl);
        experienceContext.put("cancel_url", builder.cancelUrl);
        paypal.put("experience_context", experienceContext);
        payment_source.put("paypal", paypal);

    }

    public static class OrderBuilder {

        private String currencyCode;
        private String value;
        private String description;
        private String paymentMethodPreference;
        private String paymentMethodSelected;
        private String brandName;
        private String locale;
        private String landingPage;
        private String shippingPreference;
        private String userAction;
        private String returnUrl;
        private String cancelUrl;
        private String intent;

        public OrderBuilder currencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public OrderBuilder value(String value) {
            this.value = value;
            return this;
        }

        public OrderBuilder description(String description) {
            this.description = description;
            return this;
        }

        public OrderBuilder paymentMethodPreference(String paymentMethodPreference) {
            this.paymentMethodPreference = paymentMethodPreference;
            return this;
        }

        public OrderBuilder paymentMethodSelected(String paymentMethodSelected) {
            this.paymentMethodSelected = paymentMethodSelected;
            return this;
        }

        public OrderBuilder brandName(String brandName) {
            this.brandName = brandName;
            return this;
        }

        public OrderBuilder locale(String locale) {
            this.locale = locale;
            return this;
        }

        public OrderBuilder landingPage(String landingPage) {
            this.landingPage = landingPage;
            return this;
        }

        public OrderBuilder shippingPreference(String shippingPreference) {
            this.shippingPreference = shippingPreference;
            return this;
        }

        public OrderBuilder userAction(String userAction) {
            this.userAction = userAction;
            return this;
        }

        public OrderBuilder returnUrl(String returnUrl) {
            this.returnUrl = returnUrl;
            return this;
        }

        public OrderBuilder cancelUrl(String cancelUrl) {
            this.cancelUrl = cancelUrl;
            return this;
        }

        public OrderBuilder intent(String intent) {
            this.intent = intent;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }

}
