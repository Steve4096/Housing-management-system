package com.greenview_hostels.greenview_hostels_housing_management_system.Controllers.Tenant;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.google.gson.JsonObject;
import com.greenview_hostels.greenview_hostels_housing_management_system.DarajaAuth;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import okhttp3.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Base64;
import java.util.Locale;
import java.util.ResourceBundle;

public class PayRentController implements Initializable {
    public Label HouseStore_lbl;
    public ComboBox HouseStoreSelector;
    public Label Balance_lbl;
    public TextField Amount_txtfield;
    public Label AmountErrorLbl;
    public TextField Payee_number_txtfield;
    public Button Continue_btn;
    public Button Cancel_btn;
    public ComboBox Monthselector;

    private static final String CONSUMER_KEY="GK2JpQ6RL4BnyJnkM7rgq85Q5TbJDG5lfp0yr2Ku2fqZgg9N";
    private static final String CONSUMER_SECRET="ZVLHWbfV3hPVcZ9HsfIkdhPEpRYRKQpOR5Xi9YcMaxiLPtug92Q76JnzGQadHOVF";
    private static final String TOKEN_URL="https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";

    //private final static String ACCESS_TOKEN= getAccessToken();
    private static final String STK_PUSH_URL="https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Monthselector.setItems(FXCollections.observableArrayList(
                Month.JANUARY.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                Month.FEBRUARY.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.MARCH.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.APRIL.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.MAY.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.JUNE.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.JULY.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.AUGUST.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.SEPTEMBER.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.OCTOBER.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.NOVEMBER.getDisplayName(TextStyle.FULL,Locale.ENGLISH),
                Month.DECEMBER.getDisplayName(TextStyle.FULL,Locale.ENGLISH)
        ));
        Continue_btn.setOnAction(actionEvent -> makePayment());
    }

    private static void sendSTKPush(String phoneNumber, String amount) throws IOException{
        String ACCESS_TOKEN=DarajaAuth.getAccessToken();

        OkHttpClient client=new OkHttpClient();

        JsonObject json = new JsonObject();
        json.addProperty("BusinessShortCode", "174379");
        json.addProperty("Password", "YOUR_ENCODED_PASSWORD");
        json.addProperty("Timestamp", "20240318010101"); // Use actual timestamp
        json.addProperty("TransactionType", "CustomerPayBillOnline");
        json.addProperty("Amount", amount);
        json.addProperty("PartyA", phoneNumber);
        json.addProperty("PartyB", "174379");
        json.addProperty("PhoneNumber", phoneNumber);
        json.addProperty("CallBackURL", "https://yourwebsite.com/callback");
        json.addProperty("AccountReference", "TestPayment");
        json.addProperty("TransactionDesc", "Payment for services");

        RequestBody body=RequestBody.create(json.toString(), MediaType.get("application/json;charset=utf-8"));
        Request request=new Request.Builder()
                .url(STK_PUSH_URL)
                .post(body)
                .addHeader("Authorization","Bearer"+ACCESS_TOKEN.trim())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected response: " + response);
            assert response.body() != null;
            System.out.println("STK Push Response: " + response.body().string());
        }
    }

    /*public static String getAccessToken() throws IOException {
        String credentials=CONSUMER_KEY+":"+CONSUMER_SECRET;
        String encodedCredentials= Base64.getEncoder().encodeToString(credentials.getBytes());

        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(TOKEN_URL)
                .get()
                .addHeader("Authorization", "Basic" +encodedCredentials)
                .build();

        try(Response response=client.newCall(request).execute()){
            if(!response.isSuccessful()) throw new IOException("Unexpected response"+response);
            assert response.body()!=null;
            return response.body().toString();
        }
    }*/

    private void makePayment(){
        String phoneNumber=Payee_number_txtfield.getText();
        String amount=Amount_txtfield.getText();
        try {
            sendSTKPush(phoneNumber,amount);
        }catch (IOException e){
            e.printStackTrace();
        }

    }


}
