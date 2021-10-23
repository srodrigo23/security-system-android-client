package com.example.socket;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Backend app2.py flask??
 * https://www.geeksforgeeks.org/how-to-build-a-simple-android-app-with-flask-backend/
 */
public class MainActivity extends AppCompatActivity {

    // declare attribute for textview
    private TextView pagenameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pagenameTextView = findViewById(R.id.pagename);

        // creating a client
        OkHttpClient okHttpClient = new OkHttpClient();

        // building a request
        Request request = new Request.Builder().url("http://192.168.100.8:5000/").build();

        // making call asynchronously
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            // called if we get a
            // response from the server
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                pagenameTextView.setText(response.body().string());
            }

            @Override
            // called if server is unreachable
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "server down", Toast.LENGTH_SHORT).show();
                        pagenameTextView.setText("error connecting to the server");
                    }
                });
            }
        });
    }

}