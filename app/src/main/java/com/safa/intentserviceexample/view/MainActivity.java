package com.safa.intentserviceexample.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.safa.intentserviceexample.R;
import com.safa.intentserviceexample.util.IntentServiceClass;

public class MainActivity extends AppCompatActivity {
    // https://www.facebook.com/serpilsafaa
    TextView loadingAreaTextView, urlTextView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingAreaTextView = findViewById(R.id.loadingAreaTV);
        urlTextView = findViewById(R.id.urlTV);

        handler = new Handler();

        urlTextView.setText("http://safasoftware.com");
    }

    public void onUpload(View view) {

        ResultReceiver customResultReceiver = new CustomResultReceiver(null);

        Intent intent = new Intent(this, IntentServiceClass.class);
        intent.putExtra("url", urlTextView.getText().toString());
        intent.putExtra("receiver", customResultReceiver);
        startService(intent);

    }

    class CustomResultReceiver extends ResultReceiver{

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public CustomResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(final int resultCode, final Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);


            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(resultCode == 1 && resultData != null){
                        String htmlString = resultData.getString("html");
                        loadingAreaTextView.setText(htmlString);
                    }
                }
            });



        }
    }
}
