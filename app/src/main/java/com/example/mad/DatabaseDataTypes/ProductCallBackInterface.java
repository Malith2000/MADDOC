package com.example.mad.DatabaseDataTypes;

public interface ProductCallBackInterface {

    /**
     * This interface should be used to handle synchronous API calls.
     * @param product - a product object.
     */
 void onCallback(Product product);



}
