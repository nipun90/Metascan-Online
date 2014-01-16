package com.opswat.metascan;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ObserverService extends Service{
	//private static final String TAG = "MainActivity.java";
	RecursiveFileObserver ob;
	@Override
	public void onCreate(){
		ob = new RecursiveFileObserver("/storage/emulated/0");
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startid){
		ob.startWatching();
		return START_NOT_STICKY;		
	}
	@Override
	public void onDestroy(){
		ob.stopWatching();
		stopSelf();
	}
}
