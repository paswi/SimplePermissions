package com.example.simplepermissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    //private Object nfcForegroundUtil;
    public WifiManager wifiManager;
    public ConnectivityManager connManager;
    public NetworkInfo mWifi;
    public TelephonyManager tMgr;
    private final int REQUEST_READ_PHONE_STATE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    }

    public void locationOnClick(View v) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            TextView text = (TextView)findViewById(R.id.textMyNumber);
            String mPhoneNumber = tMgr.getLine1Number();
            text.setText(mPhoneNumber);
        }
    }

    public void wifiOnClick(View v) {
        TextView text = (TextView)findViewById(R.id.textViewWifi);
        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            wifiManager.setWifiEnabled(false);
            text.setText("WiFi is disabled");
        }else{
            wifiManager.setWifiEnabled(true);
            text.setText("WiFi is enabled");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == 1) { //ten sam kod, ktory zostal podany w requestPermission
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationOnClick(findViewById(R.id.buttonLocation));
            } else {
                TextView text = (TextView) findViewById(R.id.textMyNumber);
                text.setText("Permission denied!");
            }
        }
    }
}