package com.opswat.metascan;

import org.json.JSONException;
import org.json.JSONObject;
//This class parses json results that we receive from server
public class ParseJSON {
int progress_percentage=0;
int in_queue = 0;
long file_size = 0;
String scan_all_result_a;
String start_time;
String total_time;
String display_name;
String dataId;

	public void resultJSON(String result){
		try {
			JSONObject resultJson = new JSONObject(result);
			//getting the scan results JSON
			JSONObject scan_results = resultJson.getJSONObject("scan_results");
			progress_percentage = scan_results.getInt("progress_percentage");
			scan_all_result_a = scan_results.getString("scan_all_result_a");
			start_time = scan_results.getString("start_time");
			total_time = scan_results.getString("total_time");
			in_queue = scan_results.getInt("in_queue");
			//getting file info JSON
			JSONObject file_info = resultJson.getJSONObject("file_info");
			file_size = file_info.getLong("file_size");
			display_name = file_info.getString("display_name");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//extracting data id from the response
	public String getDataID(String response){
		try {
			JSONObject responseJSON = new JSONObject(response);
			dataId = responseJSON.getString("data_id");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataId;

	}
}
