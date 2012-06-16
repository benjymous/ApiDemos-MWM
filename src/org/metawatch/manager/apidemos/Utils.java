package org.metawatch.manager.apidemos;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class Utils {
	public static Bitmap loadBitmapFromAssets(Context context, String path) {
		try {
			InputStream inputStream = context.getAssets().open(path);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			inputStream.close();
			return bitmap;
		} catch (IOException e) {
			return null;
		}
	}
	
	public static int[] makeSendableArray(final Bitmap bitmap) {
		int pixelArray[] = new int[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getPixels(pixelArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
		return pixelArray;
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
	public static Intent createWidgetUpdateIntent(Bitmap bitmap, String id, String description, int priority) {

		Intent intent = new Intent("org.metawatch.manager.WIDGET_UPDATE");
		Bundle b = new Bundle();
		b.putString("id", id);
		b.putString("desc", description);
		b.putInt("width", bitmap.getWidth());
		b.putInt("height", bitmap.getHeight());
		b.putInt("priority", priority);
		b.putIntArray("array", Utils.makeSendableArray(bitmap));
		intent.putExtras(b);

		return intent;
	}
}
