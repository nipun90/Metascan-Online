package com.opswat.metascan;

import processing.files.SelectActivity;
import processing.files.SelectMode;

import com.example.test.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Fragment{
	private static final String TAG = "MainActivity.java";
	private static final int PICKFILE_RESULT_CODE = 1;
	Switch autoscan;
	TextView api;
	Button setkey;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        autoscan = (Switch) rootView.findViewById(R.id.autoscan);
        autoscan.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean on) {
				// TODO Auto-generated method stub
				if(MainActivity.key != null){
				if (on) {
			    	getActivity().startService(new Intent(getActivity(), ObserverService.class));
			    	Toast toast;
			    	toast = Toast.makeText(getActivity(), "Auto Scan started", Toast.LENGTH_SHORT);
			    	toast.show();
			    } else {
			    	getActivity().stopService(new Intent(getActivity(), ObserverService.class));
			    	Toast toast;
			    	toast = Toast.makeText(getActivity(), "Auto Scan stopped", Toast.LENGTH_SHORT);
			    	toast.show();
			    }
				}else{
					Toast toast;
			    	toast = Toast.makeText(getActivity(), "Enter the api key in settings", Toast.LENGTH_SHORT);
			    	toast.show();
				}
			}
        	
        });
        api = (TextView)rootView.findViewById(R.id.editText1);
        setkey = (Button)rootView.findViewById(R.id.button1);
        setkey.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				
				String s = api.getText().toString();
				SharedPreferences settings = getActivity().getPreferences(0);
				settings.edit().putString("key", s).commit();
				MainActivity.key = s;
				Log.v(TAG, "key: "+s);
			}});
        Button b2 = (Button)rootView.findViewById(R.id.metalink);
        b2.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://portal.opswat.com"));
	              startActivity(browserIntent);
				
			}});
        return rootView;
    }
	
}
