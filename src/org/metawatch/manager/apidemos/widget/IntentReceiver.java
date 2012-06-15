package org.metawatch.manager.apidemos.widget;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.metawatch.manager.apidemos.ApiDemos;
import org.metawatch.manager.apidemos.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class IntentReceiver extends BroadcastReceiver  {

	// These are the example widget IDs used by this application - you should
	// change them to suit your app.  
	
	final static String id_0 = "mwmApiDemosWidget_24_32";  // Must not contain "," or "|"
	final static String desc_0 = "MWM ApiDemos Widget (24x32)";

	static int currentCount = 1;

	static boolean active = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(ApiDemos.TAG, "onReceive()");

		String action = intent.getAction();
		if (action !=null && action.equals("org.metawatch.manager.REFRESH_WIDGET_REQUEST")) {

			Log.d(ApiDemos.TAG, "Received intent");

			Bundle bundle = intent.getExtras();

			boolean getPreviews = bundle.containsKey("org.metawatch.manager.get_previews");
			if (getPreviews)
				Log.d(ApiDemos.TAG, "get_previews");

			ArrayList<String> widgets_desired = null;

			if (bundle.containsKey("org.metawatch.manager.widgets_desired")) {
				Log.d(ApiDemos.TAG, "widgets_desired");
				widgets_desired = new ArrayList<String>(Arrays.asList(bundle.getStringArray("org.metawatch.manager.widgets_desired")));
			}

			active = (widgets_desired!=null && widgets_desired.contains(id_0));

			if (getPreviews || active) {
				genWidget(context);
			}
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

		Intent intent = createUpdateIntent(bitmap, id_0, desc_0, 1);
		context.sendBroadcast(intent);

		//if (active)
		//	createAlarm(context);

		Log.d(ApiDemos.TAG, "genWidget() end");
	}

	
	/**
	 * @param bitmap Widget image to send
	 * @param id ID of this widget - should be unique, and sensibly identify
	 *        the widget
	 * @param description User friendly widget name (will be displayed in the
	 * 		  widget picker)
	 * @param priority A value that indicates how important this widget is, for
	 * 		  use when deciding which widgets to discard.  Lower values are
	 *        more likely to be discarded.
	 * @return Filled-in intent, ready for broadcast.
	 */
	private static Intent createUpdateIntent(Bitmap bitmap, String id, String description, int priority) {
		int pixelArray[] = new int[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getPixels(pixelArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

		Intent intent = new Intent("org.metawatch.manager.WIDGET_UPDATE");
		Bundle b = new Bundle();
		b.putString("id", id);
		b.putString("desc", description);
		b.putInt("width", bitmap.getWidth());
		b.putInt("height", bitmap.getHeight());
		b.putInt("priority", priority);
		b.putIntArray("array", pixelArray);
		intent.putExtras(b);

		return intent;
	}

}
