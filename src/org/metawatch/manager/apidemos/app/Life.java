package org.metawatch.manager.apidemos.app;

import java.util.Random;

import org.metawatch.manager.apidemos.ApiDemos;
import org.metawatch.manager.apidemos.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

public class Life {
	
	final public static String id = "mwmApiDemosLife";
	final static String name = "Game of Life";
	
	static Bitmap bitmap = null;
		
	static Random rnd = new Random();
	
	static int[] buffer = new int[96*96];
	
	static boolean isRunning = false;
	static Runnable thread = null;

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
	
	private static void reset() {
		for (int y=0; y<96; ++y) {
			for (int x=0; x<96; ++x) {
				final int c = rnd.nextInt(4);
				buffer[x+y*96] = c==0 ? Color.BLACK : Color.WHITE;
			}
		}
	}
	
	private static int neighbours(final int x, final int y) {
		int count=0;
		for (int y1 = y-1; y1<y+2; ++y1) {
			for (int x1 = x-1; x1<x+2; ++x1) {
				if( x1>=0 && x1<96 && y1>=0 && y1<96 && (x!=x1 || y!=y1)) {
					count += ( buffer[x1+y1*96] == Color.BLACK ? 1 : 0 );
				}
			}
		}
		return count;
	}
	
	private static void refreshApp(Context context) {

		int changeCount = 0;
		
		int[] nextGen = new int[96*96];
		
		for (int y=0; y<96; ++y) {
			for (int x=0; x<96; ++x) {
				final int pos = x+y*96;
				final int neighbours = neighbours(x,y);
				if (buffer[pos] == Color.BLACK) {
					if (neighbours>=2 && neighbours<=3) {
						nextGen[pos] = Color.BLACK;
					} else {
						nextGen[pos] = Color.WHITE;
						changeCount++;
					}
					
				} else {
					if (neighbours==3) {
						nextGen[pos] = Color.BLACK;
						changeCount++;
					} else {
						nextGen[pos] = Color.WHITE;
					}
					
				}
			}
		}
		buffer = nextGen;
		
		if (changeCount<10)
			reset();
		
		bitmap = Bitmap.createBitmap(buffer, 96, 96, Bitmap.Config.RGB_565);
		
	}
	
	private static void update(Context context) {
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
	
	public static void startUpdate(final Context context) {
		if (thread==null) {
			thread = new Runnable() {

				public void run() {
				
					while(isRunning) {
						try {
							update(context);
							
							Thread.sleep(500);
						} catch (InterruptedException e) {
							return;
						}
					}
					
				}
				
			};
			
		}
		
		if (!isRunning) {
			isRunning = true;
			thread.run();
		}

	}
	
	public static void stopUpdate() {
		isRunning = false;
	}
}
