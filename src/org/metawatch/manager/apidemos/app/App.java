package org.metawatch.manager.apidemos.app;

import java.util.Random;

import org.metawatch.manager.apidemos.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

public class App {
	
	static Bitmap bitmap = null;
	
	static int currentCount = 1;
	static boolean running = false;
	
	static Random rnd = new Random();

	public static void start(Context context) {
		if (!running) {
			Intent intent = new Intent("org.metawatch.manager.APPLICATION_START");
			context.sendBroadcast(intent);
			running = true;
			update(context);
		}
	}
	
	private static void refreshApp(Context context) {
		if (bitmap==null) {
			bitmap = Bitmap.createBitmap(96, 96, Bitmap.Config.RGB_565);
		}
		
		Canvas c = new Canvas(bitmap);
		
		Bitmap icon = Utils.loadBitmapFromAssets(context, "image"+currentCount+".bmp");
		
		c.drawBitmap(icon, rnd.nextInt(96-icon.getWidth()), rnd.nextInt(96-icon.getHeight()), null);
		
		currentCount++;
		if (currentCount==6)
			currentCount=1;
	}
	
	public static void update(Context context) {
		if (running) {
			refreshApp(context);
			
			Intent intent = new Intent("org.metawatch.manager.APPLICATION_UPDATE");
			Bundle b = new Bundle();
			b.putIntArray("array", Utils.makeSendableArray(bitmap));
			intent.putExtras(b);
	
			context.sendBroadcast(intent);
		}
	}
	
	public static void stop(Context context) {
		if (running) {
			Intent intent = new Intent("org.metawatch.manager.APPLICATION_STOP");
			context.sendBroadcast(intent);
			running = false;
			bitmap = null;
		}
	}
}
