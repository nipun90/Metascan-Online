package com.opswat.metascan;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DownloadListener extends BroadcastReceiver{
	private static final String TAG = "MainActivity.java";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Download detected");
		String action = intent.getAction();
		if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            Log.v(TAG, intent.getStringExtra(DownloadManager.COLUMN_URI));
        }
	}

}
