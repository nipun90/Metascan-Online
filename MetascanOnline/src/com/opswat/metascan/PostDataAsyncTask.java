package com.opswat.metascan;

import java.io.File;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
//main class that asynchronously uploads files for scanning
public class PostDataAsyncTask extends AsyncTask<String, String, String> {
	private static final String TAG = "MainActivity.java";
	private Context context;
	public PostDataAsyncTask(Context c){
		super();
		context = c;
	}

	protected void onPreExecute() {
		super.onPreExecute();
		// do stuff before posting data

	}

	@Override
	protected String doInBackground(String... strings) {
		try {
			postFile(strings[0]);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String lenghtOfFile) {
		// do stuff after posting data
		TabAdapter.result.adapter.notifyDataSetChanged();
	}
	private void postFile(String textFile){
		try{
			//if file exsists in database directly print the results
			if(MySQLiteDB.getInstance(context).fileStatus(textFile)!=null){
			String[] h = MySQLiteDB.getInstance(context).fileStatus(textFile);
			TabAdapter.result.filelist.add(textFile);
			TabAdapter.result.adapter.addProg(100);
			TabAdapter.result.adapter.addDataid(h[2]);
			TabAdapter.result.adapter.addResult(h[3]);
			Log.v(TAG, "FinalResult: " +" "+h[0]+h[1]+" "+h[2]+" "+h[3]);
			}
			//else upload the file and check for results
			else
			{
			//Log.v(TAG, "FileLocation: " + textFile);
			// the URL where the file will be posted
				String realFileName = textFile;
				int idx = realFileName.lastIndexOf(File.separatorChar);
				String name = realFileName.substring(idx+1, realFileName.length());
			String postReceiverUrl = "https://api.metascan-online.com/v1/file";
			String apikey = MainActivity.key;
			String getUrl = "https://api.metascan-online.com/v1/file/";
			TabAdapter.result.filelist.add(textFile);
			TabAdapter.result.adapter.addProg(0);
			Log.v(TAG, "postURL: " + postReceiverUrl);
			HttpPostReceive httpPost = new HttpPostReceive();
			ParseJSON parse = new ParseJSON();
			String responseStr = httpPost.post(postReceiverUrl, textFile, apikey, name);
			String dataId = parse.getDataID(responseStr);
			Log.v(TAG, "Response: " +  dataId);
			TabAdapter.result.adapter.addDataid(dataId);
			if (dataId != null) {
				
				
				//TabAdapter.result.adapter.notifyDataSetChanged();
				getUrl = getUrl+dataId;
				Log.v(TAG, "getURL: " + getUrl);
				while(parse.progress_percentage!=100){

					parse.resultJSON(httpPost.get(getUrl, apikey));
					Log.v(TAG, "maIn: " + parse.progress_percentage);
					TabAdapter.result.adapter.setProg(parse.progress_percentage);
				}							
				Log.v(TAG, "maIn: " + parse.scan_all_result_a);
				TabAdapter.result.adapter.addResult(parse.scan_all_result_a);
				//calling singleton instance of database and adding the new scan results
				MySQLiteDB.getInstance(context).addFile(textFile, dataId, parse.scan_all_result_a);
				//MySQLiteDB.getInstance(context).getAll();
			}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}