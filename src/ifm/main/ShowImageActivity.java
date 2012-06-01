package ifm.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowImageActivity extends Activity {


	// Utils
	Utils util;

	// numOfFiles
	static int numOfFiles = -1;

	// File[] files	=> Files that are in the same directory as the one intended
	static File[] files;

	// current file index
	static int currentFileIndex;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.v2);
        setContentView(R.layout.v2_2);
        

        try1();	// 

//        try1();	// Get the number of files

        
    }//void onCreate(Bundle savedInstanceState)

	private void try1() {

		/*----------------------------
		 * Utils
			----------------------------*/
		util = new Utils(this);
		
		/*----------------------------
		 * Get intent data
			----------------------------*/
		Intent intent = getIntent();
		
//		String fileAbsolutePath = intent.getStringExtra("fileAbsolutePath");
		File targetFile = (File) intent.getSerializableExtra("targetFile");
		
		// Root path
		String rootPath = intent.getStringExtra("rootPath");
		
		/*----------------------------
		 * Prepare data
			----------------------------*/
		// Set fileNameList
//		File[] files = new File(rootPath).listFiles();
		files = new File(rootPath).listFiles();
		
		// Set the max number
		numOfFiles = files.length;
		
		// 
//		List<String> fileNameList = getFileList(new File(rootPath));
		List<String> fileNameList = getFileList(new File(rootPath));
		
		// Get the index of targetFile
		int targetFileIndex = fileNameList.indexOf(targetFile.getName());
		
		// Set current index
		currentFileIndex = targetFileIndex;
		
//		// debug
//		Toast.makeText(ShowImageActivity.this, 
//						"targetFileIndex => " + String.valueOf(targetFileIndex), 
//						Toast.LENGTH_SHORT).show();
		
		// Get path
//		String fileName = targetFile.getName();
//		String fileAbsolutePath = intent.getStringExtra("fileAbsolutePath");
		
		// Set message
//		Utils.setMessage(this, R.id.v2_2_TV_message, fileAbsolutePath);
//		Utils.setMessage(this, R.id.v2_2_TV_message, fileName);
		Utils.setMessage(this, R.id.v2_2_TV_message, targetFile.getName());
		
		
		/*----------------------------
		 * Set listener => Back
			----------------------------*/
		this.setListener(this, "imagebutton", Utils.TagNames.V2_BACK, R.id.v2_2_IB_back);
		
		/*----------------------------
		 * Set listener => Forward
			----------------------------*/
		this.setListener(this, "imagebutton", Utils.TagNames.V2_FORWARD, R.id.v2_2_IB_forward);

		/*----------------------------
		 * Set listener => Return
			----------------------------*/
		this.setListener(this, "imagebutton", Utils.TagNames.V2_RETURN, R.id.v2_2_IB_return);

		/*----------------------------
		 * Show image
			----------------------------*/
//		showImage(fileAbsolutePath);
		showImage(targetFile.getAbsolutePath());
	}//private void try1()

	private void showImage(String fileAbsolutePath) {
		// 
		Bitmap bm = BitmapFactory.decodeFile(fileAbsolutePath);
		
		// ImageView
		ImageView iv = (ImageView) findViewById(R.id.v2_2_IV_image);
		
		// Set image
		iv.setImageBitmap(bm);
	}

	public void setListener(Activity activity, String viewName, 
			Utils.TagNames tagName, int resourceId) {
		// Log
		Log.d("ShowImageActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Setting listener...");
		// Log
		Log.d("ShowImageActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "tagName => " + tagName.name());
		//
		if (viewName.equals("textview")) {
			// Get the view
			TextView tv = (TextView) activity.findViewById(resourceId);
			  
			// Set a tag
			tv.setTag(tagName);
			  
			// Set a listener
			tv.setOnClickListener(new V2_ButtonClickListener(activity));
			
		} else if (viewName.equals("button")) {//if (viewName.equals("textview"))
			// Get the view
			Button bt = (Button) activity.findViewById(resourceId);
			  
			// Set a tag
			bt.setTag(tagName);
			  
			// Set a listener
			bt.setOnClickListener(new V2_ButtonClickListener(activity));
		} else if (viewName.equals("imagebutton")) {//if (viewName.equals("textview"))
			// Get the view
			ImageButton ib = (ImageButton) activity.findViewById(resourceId);
			  
			// Set a tag
			ib.setTag(tagName);
			  
			// Set a listener
			ib.setOnClickListener(new V2_ButtonClickListener(activity));
		}//if (viewName.equals("textview"))
		
		
	}//private void setLister(String viewName, int resourceId)


	public void setMessage(Activity activity, int resourceId, String message) {
		// Get view
		TextView tv = (TextView) activity.findViewById(resourceId);
		
		// Set text
		tv.setText(message);

	}//public static void setMessage(Activity activity, int resourceId, String message)

	private void showDebugMessage(String message) {
    	// TextView
    	TextView tv_debug = (TextView) findViewById(R.id.v2_2_TV_message);
    	
    	// Set text
    	tv_debug.setText(message);
		
	}//private void showDebugMessage(String message)


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        // メニューアイテム1の追加
        @SuppressWarnings("unused")
		MenuItem item1=menu.add(0,0,0,"item1");
        
        // メニューアイテム2の追加
        MenuItem item2=menu.add(0,1,0,"item2");
        item2.setIcon(android.R.drawable.ic_menu_search);
        
        // メニューアイテム3の追加
        MenuItem item3=menu.add(0,2,0,"item3");
        item3.setIcon(android.R.drawable.ic_menu_save);
		
        return true;
	}//public boolean onCreateOptionsMenu(Menu menu)

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
    
                return true;
        }//switch (item.getItemId())
		return true;
    }//public boolean onOptionsItemSelected(MenuItem item)

	private List<String> getFileList(File new_file) {
		// File object
		File[] list= new_file.listFiles();
		
		// Null?
		if (list == null) {
			return null;
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
		
//		//debug
//		String item = fileNameList.get(0);
//		
//		showDebugMessage(item + "[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]");
		
		return fileNameList;
	}//protected void onListItemClick(ListView l, View v, int position, long id)

	
	class ShowToast {
		// Activity activity
		Activity activity;
		
		// Constructor
		public ShowToast(Activity activity) {
			this.activity = activity;
		}
		
		public void showMessage(String message) {
			Toast.makeText(activity, 
					message,
					Toast.LENGTH_SHORT).show();

		}//showMessage(String string)
		
		public void showMessage(int data1, int data2, String[] labels) {
			String message = labels[0] + " => " + String.valueOf(data1) +
							"\n" +
							labels[1] + " => " + String.valueOf(data2);
							
			Toast.makeText(activity, 
					message,
					Toast.LENGTH_SHORT).show();

		}//showMessage(int, int)

		public void showMessage(String message, int type) {
			// 
			if (type == 0) {
				Toast.makeText(activity, 
						message,
						Toast.LENGTH_SHORT).show();
			} else {//if (type == 0)
				Toast.makeText(activity, 
						message,
						Toast.LENGTH_LONG).show();
			}//if (type == 0)
			
			
		}

	}//class ShowToast


	class V2_ButtonClickListener implements OnClickListener {

    	// Activity
    	Activity activity;
    	
		public V2_ButtonClickListener(
				Activity activity) {
			// 
			this.activity = activity;
		}

		@Override
		public void onClick(View v) {
			/*----------------------------
			 * 1. Get the tag
				----------------------------*/
			Utils.TagNames tagName = (Utils.TagNames) v.getTag(); 
			
			// Log
			Log.d("ShowImageActivity.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "onClick: tagName => " + tagName.name());
			
			// debug
			Toast.makeText(ShowImageActivity.this, 
								"currentFileIndex => " + String.valueOf(currentFileIndex), 
								Toast.LENGTH_SHORT).show();
			
			/*----------------------------
			 * 2. Dispatch
				----------------------------*/
			// Temp File
			File currentFile;
			
			switch (tagName) {
				case V2_BACK:
					// Log
					Log.d("ShowImageActivity.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "tagName => V2_BACK");
					
					// Log
					Log.d("ShowImageActivity.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", 
									"currentFileIndex => " + String.valueOf(currentFileIndex));
					
					// Index at the head?
					if(currentFileIndex < 1)
						return;
					
					// Log
					Log.d("ShowImageActivity.java"
								+ "["
								+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
							"files[currentFileIndex] => " + files[currentFileIndex].getName());
					
					// Log
					Log.d("ShowImageActivity.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "currentFileIndex -= 1 => Starting...");
					
					// Modify the index
					currentFileIndex -= 1;
					
					// Get the file
					currentFile = files[currentFileIndex];
					
					// Log
					Log.d("ShowImageActivity.java"
								+ "["
								+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
							"files[currentFileIndex] => " + files[currentFileIndex].getName());
					
					// Log
					Log.d("ShowImageActivity.java"
								+ "["
								+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
							"currentFile => " + currentFile.getName());
					
					// Log
					Log.d("ShowImageActivity.java"
								+ "["
								+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
							"currentFile.getAbsolutePath() => " + currentFile.getAbsolutePath());
					
					// Show image
					showImage(currentFile.getAbsolutePath());
					
					// Set the new file name
					Utils.setMessage(activity, R.id.v2_2_TV_message, currentFile.getName());
					
					// Break
					break;
					
//					// Log
//					Log.d("ShowImageActivity.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "V2_BACK");
////					ShowImageActivity.this.finish();
////					activity.finish();
//					finish();
//					
//					break;
				
				case V2_FORWARD:
					// Index at the head?
//					if(currentFileIndex > (numOfFiles - 1))
					if(currentFileIndex >= (numOfFiles - 1))
						return;
					
					// Modify the index
					currentFileIndex += 1;
					
					// Get the file
					currentFile = files[currentFileIndex];
					
					// Show image
					showImage(currentFile.getAbsolutePath());
					
					// Set the new file name
					Utils.setMessage(activity, R.id.v2_2_TV_message, currentFile.getName());

//					int[] size = Utils.getScreenSize(activity);
//					
//					String message = "size[0] => " + String.valueOf(size[0]) + "\n" +
//										"size[0] => " + String.valueOf(size[1]);
//					
//					showDebugMessage(message);
//					
					break;
				case V2_RETURN:
					finish();
					
					break;
				default:
					break;
			}//switch (tagName)
			
		}//public void onClick(View v)
    	
    }//class V2_ButtonClickListener
}//public class ShowImageActivity extends Activity
