package com.opswat.metascan;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpPostReceive {
	String responseStr;
	String finalResult;
	HttpClient httpClient = new DefaultHttpClient();

	public String post(String postReceiverUrl, String textFile, String apikey, String name){
		// post header
		HttpPost httpPost = new HttpPost(postReceiverUrl);
		File file = new File(textFile);
		FileBody fileBody = new FileBody(file);

		MultipartEntity reqEntity = new MultipartEntity();
		httpPost.setHeader("apikey",(apikey));
		httpPost.setHeader("filename",name);
		reqEntity.addPart("filename", fileBody);
		httpPost.setEntity(reqEntity);

		// execute HTTP post request
		HttpResponse response;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity resEntity = response.getEntity();
		 responseStr = EntityUtils.toString(resEntity).trim();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseStr;
	}
	public String get(String getUrl, String apikey){
		URI website;
		try {
			website = new URI(getUrl);
		
		HttpGet request = new HttpGet();
		request.setHeader("apikey", apikey);
		request.setURI(website);
		HttpResponse getResponse = httpClient.execute(request);
		HttpEntity finalEntity = getResponse.getEntity();
		finalResult = EntityUtils.toString(finalEntity);

	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (ClientProtocolException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return finalResult;

	}}
