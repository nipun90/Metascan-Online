package com.opswat.metascan;



import com.example.test.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener{

	private static final String TAG = "MainActivity.java";
	private static final int PICKFILE_RESULT_CODE = 1;
	TextView textFile;
	static ViewPager viewPager;
	private TabAdapter mAdapter;
	private ActionBar actionBar;
	static String key;
	static Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = getApplicationContext();
		SharedPreferences settings = getPreferences(0);
		key = settings.getString("key", null);
		if(key == null)
			Log.v(TAG, "null");
		else
			Log.v(TAG, key);
		viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabAdapter(getSupportFragmentManager());
 
        viewPager.setAdapter(mAdapter);
        //actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Scan")
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Results")
                .setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText("Settings")
                .setTabListener(this));
        viewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        actionBar.setSelectedNavigationItem(0);
		// we are going to use asynctask to prevent network on main thread exception
		//textFile = (TextView)findViewById(R.id.textfile);

			  
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
		
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public void onToggleClicked(View view) {
	    // Is the toggle on?
	    boolean on = ((Switch) view).isChecked();
	    
	    if (on) {
	    	startService(new Intent(getApplicationContext(), ObserverService.class));
	    	Toast toast;
	    	toast = Toast.makeText(getApplicationContext(), "Auto Scan started", Toast.LENGTH_SHORT);
	    	toast.show();
	    } else {
	    	stopService(new Intent(getApplicationContext(), ObserverService.class));
	    	Toast toast;
	    	toast = Toast.makeText(getApplicationContext(), "Auto Scan stopped", Toast.LENGTH_SHORT);
	    	toast.show();
	    }
	}


}
