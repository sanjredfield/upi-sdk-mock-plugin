package com.easyplan.cordova.plugin.mocks;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.lang.Runnable;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;


public class UpiSdkMockPlugin extends CordovaPlugin {
    public final int REQUEST_CODE_UPI = 100;
    public final int PERMISSION_REQUEST = 101;

    String action = null;
    CallbackContext callbackContext = null;

    // public UpiSdkMockPlugin() {
    //     this.upiListener = new UpiListener(this, this.cordova.getActivity());
    // }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("UpiSdkMockPlugin::onActivityResult", "function called with: " + resultCode);
        Log.d("UpiSdkMockPlugin::onActivityResult", "data is: " + data);
        Log.d("UpiSdkMockPlugin::onActivityResult", "callbackContext is: " + this.callbackContext);

        if (requestCode != REQUEST_CODE_UPI) return;
        if (this.callbackContext == null) return;
        if (this.action == null) return;

        if (this.action.equals("SUCCESS")) {
            JSONObject jsonData = new JSONObject();
            try {
                jsonData.put("data", 1234);
            } catch (JSONException e) {
            }
            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, jsonData);
            this.callbackContext.sendPluginResult(pluginResult);
        } else if (this.action.equals("ERROR")) {
            this.callbackContext.error("Activity Failed");
        }

        Log.d("UpiSdkMockPlugin::onActivityResult", "setting callback context to null");
        this.callbackContext = null;
        return;
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = new Bundle();
        state.putString("action", this.action);
        return state;
    }

    @Override
    public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {
        Log.d("UpiSdkMockPlugin::onRestoreStateForActivityResult", "called");
        this.action = state.getString("action");
        this.callbackContext = callbackContext;
    }

    private void startActivity() {
        Context context = cordova.getActivity().getApplicationContext();
        Intent i = new Intent(context, TestActivity.class);
        Log.d("UpiSdkMockPlugin::performAction", "starting new activity");
        this.cordova.setActivityResultCallback(this);
        this.cordova.getActivity().startActivityForResult(i, REQUEST_CODE_UPI);
        return;
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
        if (this.callbackContext != null) {
            return false;
        }

        this.callbackContext = callbackContext;
        this.action = action;

        final UpiSdkMockPlugin that = this;
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // that.performAction();
                that.startActivity();
            }
        });
        return true;
    }
}
