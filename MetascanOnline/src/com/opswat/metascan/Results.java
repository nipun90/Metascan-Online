package com.opswat.metascan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.test.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
//Displaying the file scanning results
public class Results extends Fragment{
	private static final String TAG = "MainActivity.java";
	private ListView listview;
	MArrayAdapter adapter;
	List<String> filelist;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);
        listview = (ListView)rootView.findViewById(R.id.listview);
        filelist  = new ArrayList<String>();
        adapter = new MArrayAdapter(getActivity(), filelist);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                int position, long id) {
              String data = adapter.dataid.get(position);Log.v(TAG,"data:"+data);
              Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.metascan-online.com/en/scanresult/file/"+data));
              startActivity(browserIntent);
            }

          });
        return rootView;
    }
	


	class MArrayAdapter extends ArrayAdapter<String>{
		List<String> file = new ArrayList<String>();
		public List<String> dataid = new ArrayList<String>();
		List<Integer> prog = new ArrayList<Integer>();
		List<String> result = new ArrayList<String>();
		Context context;
		public MArrayAdapter(Context c, List<String> list){
			super(c, R.layout.listview_row, list);
			context = c;
			file = list;
		}
	
		public View getView(int position, View convertView, ViewGroup parent) {
			/*if(file.size() == 0){
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View rowView = inflater.inflate(R.layout.listview_row, parent, false);
				return rowView;
			}
			else{
			/*if(file.size() == 1){
				this.clear();
			}*/

			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.listview_row, parent, false);
			TextView t1 = (TextView)rowView.findViewById(R.id.name);
			TextView t2 = (TextView)rowView.findViewById(R.id.progress);
			TextView t3 = (TextView)rowView.findViewById(R.id.result);
			String realFileName = file.get(position);
			int idx = realFileName.lastIndexOf(File.separatorChar);
			String name = realFileName.substring(idx+1, realFileName.length());
			t1.setText(name);
			t2.setText(""+prog.get(position));
			if(result.size()>position){
				t3.setText(result.get(position));
				if(result.get(position).equals("Clean")){
					t3.setTextColor(Color.GREEN);
				}
				else
					t3.setTextColor(Color.RED);
			}
	    	return rowView;
		} 
	
		public void addProg(int n){
			prog.add(n);
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
	                notifyDataSetChanged();
				}
			});
		}
	
		public void setProg(int n){
			prog.set(prog.size()-1, n);
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
	                notifyDataSetChanged();
				}
			});
		}
		
		public void addDataid(String s){
			dataid.add(s);
		}
		public void addResult(String s){
			result.add(s);
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
	                notifyDataSetChanged();
				}
			});
		}
	}
}