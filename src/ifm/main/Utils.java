package ifm.main;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import cpg4.main.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//import sub7.main.ChineseReaderActivity_sub7_edit;
//import sub7.main.Utils;
//import sub7.main.ChineseReaderActivity_sub7.V1_ButtonClickListener;

//import cpg4.main.*;

//import sub7.main.ChineseReaderActivity_sub7_edit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Utils {

	/********************************
	 * Fields
	 *********************************/
	// Activity activity
	Activity activity;
	
	// Intent library
	Object[] intentClasses = {

	};

	// ImageFileManagerActivity
	ImageFileManagerActivity fm;
	
	/********************************
	 * enums
	 *********************************/
	enum TagNames {
		V1_UP, V2_BACK, V2_BT_2
	}//enum TagName

	/*----------------------------
	 * Constructors
		----------------------------*/
	public Utils(Activity activity) {
		// activity		=> ChinesePlayGround3Activity
		this.activity = activity;
	}

	private TextView buildTextView(Activity activity, int textSize, int textColor,
			int backgroudColor) {
		// TextView
		TextView tv = new TextView(activity);
		
		// Size
		tv.setTextSize(textSize);
		
		// Color
		tv.setTextColor(textColor);
		
		// Background
		tv.setBackgroundColor(backgroudColor);
		
		return tv;
	}

	/*----------------------------
	 * C:\WORKS\WORKSPACES_ANDROID\Sample\06_various\formattedtext\src\com\androidengineer\formatted\FormattedText.java
		----------------------------*/
	/****************************************
	 * getIndex() 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 
	 * 		0 ~ x		=> Index of the target word in the word sets
	 * 		-1			=> The word doesn't exist in the sets
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static int getIndex(String targetWord, String[] wordSets) {
		// index
		int i = 0;
		
		for (String string : wordSets) {
			if (targetWord.equals(string)) {
				return i;
			}//if (targetWord.equals(string))
			
			i += 1;
		}//for (String string : wordSets)
		
		return -1;
	}//public static int getIndex(String targetWord, String[] wordSets)

	/****************************************
	 * getScreenSize(Activity)
	 *  
	 * <Desc> 1. 
	 * <Params> 1.
	 * 
	 * <Return>
	 * 		1. Order => width, height
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static int[] getScreenSize(Activity activity) {
		//
		Display display = activity.getWindowManager().getDefaultDisplay();
		
		//
		int w = display.getWidth();
		int h = display.getHeight();

		// int[]
		int[] data = new int[2];
		
		data[0] = w; data[1] = h;
		
		return data;
	}//getScreenSize()

	public void setListener(Activity activity, String viewName, 
			Utils.TagNames tagName, int resourceId) {
	//
	if (viewName.equals("textview")) {
		// Get the view
		TextView tv_start = (TextView) activity.findViewById(resourceId);
		  
		// Set a tag
		tv_start.setTag(tagName);
		  
		// Set a listener
		tv_start.setOnClickListener(new V1_ButtonClickListener(activity));
		
	} else if (viewName.equals("button")) {//if (viewName.equals("textview"))
		// Get the view
		Button bt = (Button) activity.findViewById(resourceId);
		  
		// Set a tag
		bt.setTag(tagName);
		  
		// Set a listener
		bt.setOnClickListener(new V1_ButtonClickListener(activity));
	}//if (viewName.equals("textview"))
	
	
}//private void setLister(String viewName, int resourceId)

	public static void setMessage(Activity activity, int resourceId, String message) {
		// Get view
		TextView tv = (TextView) activity.findViewById(resourceId);
		
		// Set text
		tv.setText(message);

	}//public static void setMessage(Activity activity, int resourceId, String message)
	
	/********************************************************************************
	 * class JsoupManager
	 ********************************************************************************/
	class JsoupManager {
		// Activity activity
		Activity activity;
		
		// Document
		Document doc;
		
		public JsoupManager(Activity activity) {
			// activity		=> ChinesePlayGround3Activity
			this.activity = activity;
			
		}//public JsoupManager(Activity activity)

		/********************************************************************************
		 * Methods
		 ********************************************************************************/
		public Document getDocumentFromURL(String url) {
			
			// Get doc
//			doc = Jsoup.parse(url);
			try {
				doc = Jsoup.connect(url).get();

				// Log
				Log.d("Utils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Done => document");
				
				return doc;
				
			} catch (IOException e) {
				// debug
				Toast.makeText(activity, "IOException!", Toast.LENGTH_SHORT).show();
				
				// Log
				Log.d("Utils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Jsoup exception" + e.toString());
				
				return null;
			}//try
			
//			return doc;
			
		}//public Document getDocument(String url)

		/********************************************************************************
		 * Methods
		 ********************************************************************************/
		public Document getDocumentFromURL(String url, String original, String replacement) {
			// Prepare url
			url = url.replaceAll(original, replacement);
			
			// Get doc
//			doc = Jsoup.parse(url);
			try {
//				doc = Jsoup.connect(url).get();
				
				//debug
				doc = Jsoup.connect(url).post();

				// Log
				Log.d("Utils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Done => document");
				
				return doc;
				
			} catch (IOException e) {
				// debug
				Toast.makeText(activity, "IOException!", Toast.LENGTH_SHORT).show();
				
				// Log
				Log.d("Utils.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "Jsoup exception");
				
//				//debug
//				TextView tv = (TextView) activity.findViewById(R.id.sub7_v2_tv_debug);
//				
//				tv.setText(e.toString());
				
				return null;
			}//try
			
//			return doc;
			
		}//public Document getDocument(String url)
		
	}//public class JsoupManager
	
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

	class ShowMessage {
		// Activity activity
		Activity activity;
		
		// Constructor
		public ShowMessage(Activity activity) {
			this.activity = activity;
		}
		
		public void showMessage(Activity activity, int resourceId, String message) {
			// Get view
			TextView tv = (TextView) activity.findViewById(resourceId);
			
			// Set text
			tv.setText(message);
			
		}//public showMessage(Activity activity, int resourceId, String message)
		
	}//public class ShowMessage
	
	class V1_ButtonClickListener implements OnClickListener {

    	// Activity
    	Activity activity;
    	
		public V1_ButtonClickListener(
				Activity activity) {
			// 
			this.activity = activity;
		}

		/****************************************
		 * onClick() 
		 * <Params> 1.
		 * 
		 * <Return> 1.
		 * 
		 * <Notices> 1. 
		 * <Steps> 
		 * 		1. Get the tag
		 * 		2. Dispatch
		 * 
		 ****************************************/
		@Override
		public void onClick(View v) {
			/*----------------------------
			 * ImageFileManagerActivity
				----------------------------*/
			fm = new ImageFileManagerActivity();
			/*----------------------------
			 * 1. Get the tag
				----------------------------*/
			Utils.TagNames tagName = (Utils.TagNames) v.getTag(); 
			
			/*----------------------------
			 * 2. Dispatch
				----------------------------*/
			
			switch (tagName) {
				case V1_UP:
					// ShowToast
					ShowToast ST = new ShowToast(activity);
					
					// Show
					ST.showMessage("tagName => " + tagName.name(), 0);
					
//					Utils.setMessage(activity, R.id.v1_BT_up, tagName.name());
//					new ShowMessage(activity).showMessage(activity, R.id.v1_TV_debug, TagNames.V1_UP.name());
					
					// Up the directory
//					ImageFileManagerActivity.directoryUp();
					fm.directoryUp();
					
					// Refresh the list
					fm.refreshList();
					
					//debug
					new ShowMessage(activity)
							.showMessage(activity, 
												R.id.v1_TV_debug, 
												"rootPath => " + ImageFileManagerActivity.rootPath);
					
//					ImageFileManagerActivity.rootPath = 
					
					break;
				case V2_BACK:
					
					break;
				default:
					break;
			}//switch (tagName)
			
		}//public void onClick(View v)
    	
    }//class V1_ButtonClickListener

}//public class Utils
