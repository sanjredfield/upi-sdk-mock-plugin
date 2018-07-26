package com.easyplan.cordova.plugin.mocks;

import java.util.ArrayList;
import java.lang.Runnable;
import java.lang.Thread;
import java.lang.InterruptedException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._finish();
    }

    private void _finish() {
        final TestActivity that = this;
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {

                }
                Intent resultIntent = new Intent();
                resultIntent.putExtra("data", "hello");
                that.setResult(Activity.RESULT_OK, resultIntent);
                Log.d("TestActivity::finish", "called");
                that.finish();
            }
        }).start();

    }

}
