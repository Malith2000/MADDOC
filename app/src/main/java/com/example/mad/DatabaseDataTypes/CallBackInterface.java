package com.example.mad.DatabaseDataTypes;

public interface CallBackInterface {
    /**
     * This interface should be used to handle synchronous API calls.
     * @param value - value to return
     */
    void onCallback(boolean value);

}

