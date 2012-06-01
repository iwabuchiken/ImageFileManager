package ifm.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

	// Stored path
	private String storedPath = null;
	
	// ListAdapter adapter

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
		
		// Log
		Log.d("ImageFileManagerActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "try2() => text set");
		
		// List object

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
    		// Start intent
    		this.showBitmapImage(new_file);
    		
		} else {//if (new_file.isDirectory())
			
			//debug

			this.showDebugMessage("absolute path => " + new_file.getAbsolutePath());
			
		}//if (new_file.isDirectory())
    }//protected void onListItemClick(ListView l, View v, int position, long id)

	private void showBitmapImage(File targetFile) {
		// 
		String fileAbsolutePath = targetFile.getAbsolutePath();
		
		// Intent
		Intent intent = new Intent();
		
		// Set class
		intent.setClass(this, ShowImageActivity.class);
		
		// Set extra

		intent.putExtra("targetFile", targetFile);
		
		// Root path
		intent.putExtra("rootPath", rootPath);
		
		// Start
		startActivity(intent);
	}//private void showBitmapImage(File targetFile)

	public void showDebugMessage(String message) {
		// Log
		Log.d("ImageFileManagerActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "message => " + message);
		
		// Log
		Log.d("ImageFileManagerActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "R.id.v1_TV_debug => " + String.valueOf(R.id.v1_TV_debug));
    	// TextView

		TextView tv_debug = (TextView) this.findViewById(R.id.v1_TV_debug);
    	
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

	protected void directoryUp() {
		//
		File currentFile = new File(rootPath);
		
		// Reset root path
		String upDirectory = currentFile.getParent();
		
		if (upDirectory == null) {
			
		} else {//if (currentFile.getParent())
			rootPath = upDirectory;
		}//if (currentFile.getParent())

		
	}//protected void directoryUp()
	
	public void refreshList() {
		// File object
		File currentFile = new File(rootPath);
		
		// List
		fileNameList.clear();
		
		fileNameList.addAll(this.getFileList(currentFile));

		
		// Sort
		Collections.sort(fileNameList);
		
		// Notify the adapter
		adapter.notifyDataSetChanged();
		
		// Log
		Log.d("ImageFileManagerActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "rootPath");
	}//public void refreshList()

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
		}//for (File file : list)
		
		return fileNameList;
	}//protected void onListItemClick(ListView l, View v, int position, long id)

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        // メニューアイテム1の追加
        @SuppressWarnings("unused")
		MenuItem item1=menu.add(0,0,0,
				this.getResources().getString(R.string.v2_2_MI_store_path));
        item1.setIcon(android.R.drawable.ic_menu_save);
        
        // メニューアイテム2の追加

        MenuItem item2=menu.add(0,1,0,
        		this.getResources().getString(R.string.v2_2_MI_get_path));
        item2.setIcon(android.R.drawable.ic_menu_set_as);

        
        // メニューアイテム3の追加
        MenuItem item3=menu.add(0,2,0,"item3");
        item3.setIcon(android.R.drawable.ic_menu_save);
		
        return true;
	}//public boolean onCreateOptionsMenu(Menu menu)

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
            	// Set path
            	storedPath = rootPath;
            	
            	// debug
				Toast.makeText(ImageFileManagerActivity.this, "Path stored => " + storedPath,
						Toast.LENGTH_SHORT).show();
            return true;
            
            case 1:
            	/*----------------------------
				 * Retrieve path
					----------------------------*/
				// storedPath => not null ?
            	if (storedPath == null) {
					// debug
					Toast.makeText(ImageFileManagerActivity.this, "Sorry, storedPath is not set",
							Toast.LENGTH_SHORT).show();
					return true;
				}//if (storedPath == null)
            	
            	// Get storedPath
            	rootPath = storedPath;
            	
            	// Refresh list
            	refreshList();
            	
            	showDebugMessage(rootPath);
            	
        	return true;
        	
        }//switch (item.getItemId())
		return true;
    }//public boolean onOptionsItemSelected(MenuItem item)
    
}//class ImageFileManagerActivity
