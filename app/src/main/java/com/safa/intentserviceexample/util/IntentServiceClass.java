package com.safa.intentserviceexample.util;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import androidx.annotation.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class IntentServiceClass extends IntentService {

    public IntentServiceClass() {
        super("Intent Service Class");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String urlString = intent.getStringExtra("url");
        ResultReceiver receiver = intent.getParcelableExtra("receiver");

        String result = "";
        HttpURLConnection httpURLConnection;
        URL url;

        try {
            url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();
            while (data > 0){
                 char c = (char) data;
                 result += c;
                 data = inputStreamReader.read();

            }

            Bundle bundle = new Bundle();
            System.out.println(result + result);
            bundle.putString("html", result);
            receiver.send(1,bundle);

        }catch (Exception e){
            Bundle bundle = new Bundle();
            bundle.putString("html", "Error");
            receiver.send(1,bundle);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
