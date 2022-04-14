package com.example.restaurantv2;

import org.json.JSONException;

public interface RequestHandler {
    public abstract void processResponse(String response);
}
