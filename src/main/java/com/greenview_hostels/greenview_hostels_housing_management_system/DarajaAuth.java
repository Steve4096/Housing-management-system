package com.greenview_hostels.greenview_hostels_housing_management_system;

import com.fasterxml.jackson.databind.util.JSONPObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Base64;

public class DarajaAuth {
    private static final String CONSUMER_KEY = "GK2JpQ6RL4BnyJnkM7rgq85Q5TbJDG5lfp0yr2Ku2fqZgg9N";
    private static final String CONSUMER_SECRET = "ZVLHWbfV3hPVcZ9HsfIkdhPEpRYRKQpOR5Xi9YcMaxiLPtug92Q76JnzGQadHOVF";
    private static final String TOKEN_URL = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";

    public static String getAccessToken() throws IOException {
        String credentials = CONSUMER_KEY + ":" + CONSUMER_SECRET;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(TOKEN_URL)
                .get()
                .addHeader("Authorization", "Basic " + encodedCredentials)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected response: " + response);
            assert response.body() != null;
            return response.body().string();


        }
    }

    public static void main(String[] args) {
        try {
            String tokenResponse = getAccessToken();
            System.out.println("Access Token Response: " + tokenResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
