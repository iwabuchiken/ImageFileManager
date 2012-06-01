package ifm.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//public class ImageFileManagerActivity extends Activity {
public class ImageFileManagerActivity extends ListActivity {
	// Root
	static String rootPath;

	// ListAdapter adapter
//	ListAdapter adapter;
//	static ArrayAdapter adapter;
	static ArrayAdapter<String> adapter;
	
	// List<String> fileNameList
	static List<String> fileNameList;
	
	// Utils
	Utils util;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.v1);
        
//        try1();	// Get the number of files
        try2();	// Get the list of the file names
        
    }//void onCreate(Bundle savedInstanceState)

	private void try2() {
		// Utils
		util = new Utils(this);
		
		// File object
		File mfile=new File("/mnt/sdcard");
		File[] list=mfile.listFiles();
		
		// Set root path
		rootPath = mfile.getAbsolutePath();

		//debug
		// TextView
		TextView tv_debug = (TextView) findViewById(R.id.v1_TV_debug);
		
		// Set text
		tv_debug.setText("rootPath => " + rootPath);
		
		// List object
//		fileNameList = new ArrayList<String>();
		fileNameList = this.getFileList(mfile);
		
		// Sort
		Collections.sort(fileNameList);

		// Adapter
		adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1
                ,fileNameList);

		// Set adapter
		setListAdapter(adapter);
		
		// Set listener to the button
		util.setListener(this, "button", Utils.TagNames.V1_UP, R.id.v1_BT_up);
		
	}//private void try2()

	private void try1() {
		// 
		File mfile=new File("/mnt/sdcard");
		File[] list=mfile.listFiles();
		
		// TextView
		TextView tv_debug = (TextView) findViewById(R.id.v1_TV_debug);
		
		// Set text
		tv_debug.setText(String.valueOf(list.length));
	}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// File object
    	String fileName = ((TextView)v).getText().toString();
    	File new_file = new File(rootPath, fileName);
    	
    	// Is directory ?
    	if (new_file.isDirectory()) {
    		// Reset the list
    		fileNameList.clear();
    		
    		fileNameList.addAll(this.getFileList(new_file));
    		
    		// Notify the adapter
    		adapter.notifyDataSetChanged();
    		
			// Update the root path
			rootPath = new_file.getAbsolutePath();

			// Show message
			this.showDebugMessage("rootPath => " + rootPath);

    	} else if (new_file.isFile()){//if (new_file.isDirectory())
    		this.showBitmapImage(new_file);
    		//
    		Intent intent = new Intent();
    		
    		//
    		intent.setClass(this, ShowImageActivity.class);
    		
    		// Put extra
    		intent.putExtra("filePath", new_file.getAbsolutePath());
    		
    		// Start
    		startActivity(intent);
    		
		} else {//if (new_file.isDirectory())
			
			//debug
//			this.showDebugMessage("new_file => " + new_file.getAbsolutePath());
			this.showDebugMessage("absolute path => " + new_file.getAbsolutePath());
			
		}//if (new_file.isDirectory())
		
    	
    	
    	
//    	// Prepare text
//    	String message = "Position => " + String.valueOf(position) + "\n" +
//    						"getName => " + v.getClass().getName() + "\n" +
////    						"text => " + ((TextView)v).getText().toString();
//							"text => " + fileName + "\n" +
//							"Is directory? => " + String.valueOf(new_file.isDirectory());
    	
//    	// TextView
//    	TextView tv_debug = (TextView) findViewById(R.id.v1_TV_debug);
//    	
//    	// Set text
//    	tv_debug.setText(message);

    }

	private void showBitmapImage(File targetFile) {
		// 
		String filePath = targetFile.getAbsolutePath();
		
		// Intent
		Intent intent = new Intent();
		
		// Set class
		intent.setClass(this, ShowImageActivity.class);
		
		// Set extra
		intent.putExtra("filePath", filePath);
		
		// Start
		startActivity(intent);
	}

	private void showDebugMessage(String message) {
    	// TextView
    	TextView tv_debug = (TextView) findViewById(R.id.v1_TV_debug);
    	
    	// Set text
    	tv_debug.setText(message);
		
	}//private void showDebugMessage(String message)

	private void resetAdapter(List<String> fileNameList) {
		// Adapter
		adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1
                ,fileNameList);
		
		// Set adapter
		setListAdapter(adapter);
		
	}//private void resetAdapter(List<String> fileNameList)

//	protected static void directoryUp() {
	protected void directoryUp() {
		//
		File currentFile = new File(rootPath);
		
		// Reset root path
		String upDirectory = currentFile.getParent();
		
		if (upDirectory == null) {
			
		} else {//if (currentFile.getParent())
			rootPath = upDirectory;
		}//if (currentFile.getParent())
		
		
//		rootPath = currentFile.getParent();
		
//		//debug
//		this.showDebugMessage("currentFile.getParent() => " + currentFile.getParent());
		
	}//protected void directoryUp()
	
	public void refreshList() {
		// File object
		File currentFile = new File(rootPath);
		
		// List
		fileNameList.clear();
		
		fileNameList.addAll(this.getFileList(currentFile));
//		fileNameList = this.getFileList(currentFile);
		
		// Sort
		Collections.sort(fileNameList);
		
		// Notify the adapter
		adapter.notifyDataSetChanged();
	}

	private List<String> getFileList(File new_file) {
		// File object
		File[] list= new_file.listFiles();
		
		// Null?
		if (list == null) {
			return fileNameList;
		}//if (list == null)
		
		// List object
		List<String> fileNameList = new ArrayList<String>();
		
		// Name list
		for (File file : list) {
			if (file != null) {
				fileNameList.add(file.getName());
			} else {//if (file != null)
				fileNameList.add("null");
			}//if (file != null)
			
//			fileNameList.add(file.getName());
		}//for (File file : list)
		
		return fileNameList;
	}//protected void onListItemClick(ListView l, View v, int position, long id)

}//class ImageFileManagerActivity
