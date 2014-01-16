package com.opswat.metascan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter{
	static Results result;
	public TabAdapter(FragmentManager f){
		super(f);
	}
	@Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
        	return new Scan();
        case 1:
        	Results r = new Results();
        	result = r;
        	return r;
        case 2:
        	return new Settings();
        default:	
        	return new Scan();
        }
 
        //return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
}
