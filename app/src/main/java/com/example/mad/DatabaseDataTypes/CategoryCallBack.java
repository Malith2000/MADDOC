package com.example.mad.DatabaseDataTypes;

import java.util.ArrayList;

public interface CategoryCallBack {
    /**
     * This interface should be used to handle synchronous API calls.
     * @param products - a product object.
     */
    void onCallback(ArrayList<Product> products);
}
