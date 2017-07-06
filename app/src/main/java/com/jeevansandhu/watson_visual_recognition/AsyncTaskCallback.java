package com.jeevansandhu.watson_visual_recognition;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.IntentCompat;
import android.util.Log;

import org.json.JSONException;

/**
 * Created by Jeevan Sandhu on 04-Jul-17.
 */

public class AsyncTaskCallback extends AsyncTask<Void, Integer, String> {
    public AsyncTaskCallbackInterface mAsyncTaskCallbackInterface;
    private String tag = getClass().getSimpleName();
    private Context context;
    private ProgressDialog pDialog;

    public AsyncTaskCallback(Context context, AsyncTaskCallbackInterface mAsyncTaskCallbackInterface) {
        this.context = context;
        this.mAsyncTaskCallbackInterface = mAsyncTaskCallbackInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                // runs on UI thread

                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Please Wait...");
                pDialog.setIndeterminate(true);
                pDialog.setCancelable(false);
                pDialog.show();
            }
        });
    }

    @Override
    protected String doInBackground(Void... params) {
        try {

        } catch (Exception e) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            e.printStackTrace();
        }
        try {
            return mAsyncTaskCallbackInterface.backGroundCallback();
        } catch (JSONException e) {
            Intent logoutIntent = new Intent(context, MainActivity.class);
            ComponentName cn = logoutIntent.getComponent();
            Intent intent = IntentCompat.makeRestartActivityTask(cn);
            context.startActivity(intent);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (result != null) {
            mAsyncTaskCallbackInterface.foregroundCallback(result);
        } else {
            Log.d(tag, "foregroundCallback result" + result);
        }

    }

    public interface AsyncTaskCallbackInterface {
        public void foregroundCallback(String result);

        public String backGroundCallback() throws JSONException;
    }
}