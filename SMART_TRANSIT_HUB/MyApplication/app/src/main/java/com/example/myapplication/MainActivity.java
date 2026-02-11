package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private HashMap<String, String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize in-memory user storage
        users = new HashMap<>();

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript
        webView.addJavascriptInterface(this, "Android"); // Add JavaScript interface
        webView.loadUrl("file:///android_asset/index.html"); // Load your HTML login page
    }

    // Method called from JavaScript to handle login
    @JavascriptInterface
    public void login(String username, String password) {
        if (users.containsKey(username) && users.get(username).equals(password)) {
            runOnUiThread(() -> {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                webView.loadUrl("file:///android_asset/timetable.html"); // Load timetable page on success
            });
        } else {
            runOnUiThread(() -> Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show());
        }
    }

    // Method called from JavaScript to handle registration
    @JavascriptInterface
    public void register(String username, String password) {
        if (!users.containsKey(username)) {
            users.put(username, password);
            runOnUiThread(() -> Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show());
        } else {
            runOnUiThread(() -> Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show());
        }
    }

    // Method to submit timetable and load bus schedule
    @JavascriptInterface
    public void submitTimetable() {
        runOnUiThread(() -> {
            Toast.makeText(this, "Timetable submitted successfully!", Toast.LENGTH_SHORT).show();
            webView.loadUrl("file:///android_asset/busschedule.html"); // Load bus schedule
        });
    }

    // Method to show the bus schedule
    @JavascriptInterface
    public void showBusSchedule() {
        runOnUiThread(() -> webView.loadUrl("file:///android_asset/busschedule.html")); // Load bus schedule page
    }

    // Method to navigate to the peer connection module
    @JavascriptInterface
    public void showPeerConnectionModule() {
        runOnUiThread(() -> webView.loadUrl("file:///android_asset/peer_connection.html")); // Load peer connection page
    }

    // Method to navigate to the feedback module
    @JavascriptInterface
    public void showFeedbackModule() {
        runOnUiThread(() -> webView.loadUrl("file:///android_asset/feedback.html")); // Load feedback page
    }
}
