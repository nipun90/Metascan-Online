package com.opswat.metascan;

import com.example.test.R;

import processing.files.SelectActivity;
import processing.files.SelectMode;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
//Main screen of the application containing various buttons for scanning files, folders and full system scan
public class Scan extends Fragment{
	
	private static final String TAG = "MainActivity.java";
	//private static final int PICKFILE_RESULT_CODE = 1;
	 private static final int RESULT_OK = -1;
	 protected static final int PATH_RESULT = 123;
	 protected static final int PATH_RESULT1 = 234;

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.fragment_scan, container, false);
	        Button buttonPick = (Button)rootView.findViewById(R.id.buttonpick);
			buttonPick.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					/*FolderScan f = new FolderScan();
					f.scan("/storage/emulated/0/temp");*/
					//Log.v(TAG, "starting");
					if(MainActivity.key != null){
					  Intent i = new Intent(getActivity(), SelectActivity.class);
				        i.putExtra(SelectActivity.EX_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
				        i.putExtra(SelectActivity.EX_STYLE, SelectMode.SELECT_FILE);
				        startActivityForResult(i, PATH_RESULT);
					}else{
						Toast toast;
				    	toast = Toast.makeText(getActivity(), "Enter the api key in settings", Toast.LENGTH_SHORT);
				    	toast.show();
					}
				}}); 
			Button folderPick = (Button)rootView.findViewById(R.id.folderpick);
			folderPick.setOnClickListener(new Button.OnClickListener() 
	        {
	        
	            @Override
	            public void onClick(View v) 
	            {
	            	if(MainActivity.key!=null){
	            	 Intent i = new Intent(getActivity(), SelectActivity.class);
	     	        i.putExtra(SelectActivity.EX_PATH, Environment.getExternalStorageDirectory().getAbsolutePath());
	     	        i.putExtra(SelectActivity.EX_STYLE, SelectMode.SELECT_FOLDER);
	     	        startActivityForResult(i, PATH_RESULT1);
	            	}
	            	else{
	            		Toast toast;
				    	toast = Toast.makeText(getActivity(), "Enter the api key in settings", Toast.LENGTH_SHORT);
				    	toast.show();
	            	}
	            }});
			Button fullscan = (Button)rootView.findViewById(R.id.fullscan);
			fullscan.setOnClickListener(new Button.OnClickListener() 
	        {
	        
	            @Override
	            public void onClick(View v) 
	            {
	            	if(MainActivity.key!=null){
	            		FolderScan fscan = new FolderScan(getActivity().getApplicationContext());
	   				 	fscan.scan("/storage/emulated/0");
	            	}
	            	else{
	            		Toast toast;
				    	toast = Toast.makeText(getActivity(), "Enter the api key in settings", Toast.LENGTH_SHORT);
				    	toast.show();
	            	}
	            }});
	        return rootView;
	    }
	
	 @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			Log.v(TAG, "requestcode: " + requestCode);
			 if (requestCode == PATH_RESULT && resultCode == RESULT_OK) {
				 MainActivity.viewPager.setCurrentItem(1);
			     String FilePath = data.getStringExtra(SelectActivity.EX_PATH_RESULT);
			     PostDataAsyncTask task = new PostDataAsyncTask(getActivity().getApplicationContext());
				 task.execute(FilePath);
			    } 
			 else if (requestCode == PATH_RESULT1 && resultCode == RESULT_OK) {
				 MainActivity.viewPager.setCurrentItem(1);
			     String FilePath = data.getStringExtra(SelectActivity.EX_PATH_RESULT);
				 FolderScan fscan = new FolderScan(getActivity().getApplicationContext());
				 fscan.scan(FilePath);
			 }else{
			      super.onActivityResult(requestCode, resultCode, data);
			 }
			
		}
	 
}
