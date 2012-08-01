package org.metawatch.manager.apidemos.app;

import java.util.Random;

import org.metawatch.manager.apidemos.ApiDemos;
import org.metawatch.manager.apidemos.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

public class App {
	
	final public static String id = "mwmApiDemosApp";
	final static String name = "Api Demo Example";
	
	static Bitmap bitmap = null;
	
	static int currentCount = 1;
	
	static Random rnd = new Random();

	public static void announce(Context context) {
		Intent intent = new Intent("org.metawatch.manager.APPLICATION_ANNOUNCE");
		Bundle b = new Bundle();
		b.putString("id", id);
		b.putString("name", name);
		intent.putExtras(b);
		context.sendBroadcast(intent);
		Log.d(ApiDemos.TAG, "Sent APPLICATION_ANNOUNCE");
	}
	
	public static void start(Context context) {
		Intent intent = new Intent("org.metawatch.manager.APPLICATION_START");
		Bundle b = new Bundle();
		b.putString("id", id);
		b.putString("name", name);
		intent.putExtras(b);
		context.sendBroadcast(intent);
		update(context);
	}
	
	private static void refreshApp(Context context) {
		if (bitmap==null) {
			bitmap = Bitmap.createBitmap(96, 96, Bitmap.Config.RGB_565);
		}
		
		Canvas c = new Canvas(bitmap);
		c.drawColor(Color.WHITE);
		
		Bitmap icon = Utils.loadBitmapFromAssets(context, "image"+currentCount+".bmp");
		
		c.drawBitmap(icon, rnd.nextInt(96-icon.getWidth()), rnd.nextInt(96-icon.getHeight()), null);
		
		currentCount++;
		if (currentCount==6)
			currentCount=1;
	}
	
	public static void update(Context context) {
		refreshApp(context);
		
		Intent intent = new Intent("org.metawatch.manager.APPLICATION_UPDATE");
		Bundle b = new Bundle();
		b.putString("id", id);
		b.putIntArray("array", Utils.makeSendableArray(bitmap));
		intent.putExtras(b);

		context.sendBroadcast(intent);
	}
	
	public static void stop(Context context) {
		Intent intent = new Intent("org.metawatch.manager.APPLICATION_STOP");
		Bundle b = new Bundle();
		b.putString("id", id);
		intent.putExtras(b);
		context.sendBroadcast(intent);
		bitmap = null;
	}
	
	public static void button(Context context, int button, int type) {
		
		currentCount = button;
		update(context);
	}
}
