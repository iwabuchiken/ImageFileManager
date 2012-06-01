package ifm.main;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowImageActivity extends Activity {


	// Utils
	Utils util;



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
		
		String fileAbsolutePath = intent.getStringExtra("fileAbsolutePath");
		
		// Set message
		Utils.setMessage(this, R.id.v2_2_TV_message, fileAbsolutePath);
		
		
		/*----------------------------
		 * Set listener => Back
			----------------------------*/
		this.setListener(this, "button", Utils.TagNames.V2_BACK, R.id.v2_2_BT_back);
		
		/*----------------------------
		 * Set listener => Button 2
			----------------------------*/
		this.setListener(this, "button", Utils.TagNames.V2_BT_2, R.id.v2_2_BT_btn_2);

		/*----------------------------
		 * Show image
			----------------------------*/
		showImage(fileAbsolutePath);
		
	}

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
			// Log
			Log.d("ShowImageActivity.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "onClick");
			/*----------------------------
			 * 1. Get the tag
				----------------------------*/
			Utils.TagNames tagName = (Utils.TagNames) v.getTag(); 
			
			/*----------------------------
			 * 2. Dispatch
				----------------------------*/
			
			switch (tagName) {
				case V2_BACK:
					// Log
					Log.d("ShowImageActivity.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "V2_BACK");
//					ShowImageActivity.this.finish();
//					activity.finish();
					finish();
					
					break;
				
				case V2_BT_2:
					int[] size = Utils.getScreenSize(activity);
					
					String message = "size[0] => " + String.valueOf(size[0]) + "\n" +
										"size[0] => " + String.valueOf(size[1]);
					
					showDebugMessage(message);
					
					break;
					
				default:
					break;
			}//switch (tagName)
			
		}//public void onClick(View v)
    	
    }//class V2_ButtonClickListener
}//public class ShowImageActivity extends Activity
