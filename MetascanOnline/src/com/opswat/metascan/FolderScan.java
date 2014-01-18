package com.opswat.metascan;

import java.io.File;

import android.content.Context;

//import com.example.test.MainActivity.FolderScan;
//implementing class to scan folders
public class FolderScan {
		private Context context;
		public FolderScan(Context c){
			context = c;
		}
		public void scan(String path){
			File folder = new File(path);
			if(folder.isDirectory()){
				for(File file:folder.listFiles()){
					if(file.isDirectory()){
						FolderScan inner = new FolderScan(context);
						inner.scan(file.getAbsolutePath());
					}
					else{
						PostDataAsyncTask task = new PostDataAsyncTask(context);
						task.execute(file.getAbsolutePath());
					}
				}
			}
		}
	}