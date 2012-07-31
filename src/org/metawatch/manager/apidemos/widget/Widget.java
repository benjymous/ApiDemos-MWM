package org.metawatch.manager.apidemos.widget;

import java.util.ArrayList;
import java.util.Arrays;

import org.metawatch.manager.apidemos.ApiDemos;
import org.metawatch.manager.apidemos.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

public class Widget {

	// These are the example widget IDs used by this application - you should
	// change them to suit your app.  
	
	final static String id_0 = "mwmApiDemosWidget_24_32";  // Must not contain "," or "|"
	final static String desc_0 = "MWM ApiDemos Widget (24x32)";

	static int currentCount = 1;

	public static void refreshWidgets(Context context, Intent intent) {

		Bundle bundle = intent.getExtras();

		boolean getPreviews = bundle.containsKey("org.metawatch.manager.get_previews");
		if (getPreviews)
			Log.d(ApiDemos.TAG, "get_previews");

		ArrayList<String> widgets_desired = null;

		if (bundle.containsKey("org.metawatch.manager.widgets_desired")) {
			Log.d(ApiDemos.TAG, "widgets_desired");
			widgets_desired = new ArrayList<String>(Arrays.asList(bundle.getStringArray("org.metawatch.manager.widgets_desired")));
		}

		// Check if widgets_desired contains each widget ID you're responsible for
		// and send an update
		boolean active = (widgets_desired!=null && widgets_desired.contains(id_0));
		
		// Always send an update if the broadcast specifies get_previews 
		if (getPreviews || active) {
			genWidget(context);
		}

	}
	
	public synchronized static void update(Context context) {
		Log.d(ApiDemos.TAG, "Updating widget");
		
		// This example just increments a counter - in reality you'd want to
		// pull data from your app or an internet source.
		
		currentCount++;
		if (currentCount==6)
			currentCount=1;
		
		genWidget(context);
	}

	private synchronized static void genWidget(Context context) {
		Log.d(ApiDemos.TAG, "genWidget() start");

		// This example just loads a pre-made image from the app's assets
		// folder - in reality you'd want to generate an image here.
		
		Bitmap bitmap = Utils.loadBitmapFromAssets(context, "image"+currentCount+".bmp");

		Intent intent = Utils.createWidgetUpdateIntent(bitmap, id_0, desc_0, 1);
		context.sendBroadcast(intent);

		Log.d(ApiDemos.TAG, "genWidget() end");
	}

}
