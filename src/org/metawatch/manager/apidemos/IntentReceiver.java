package org.metawatch.manager.apidemos;

import org.metawatch.manager.apidemos.widget.Widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IntentReceiver extends BroadcastReceiver  {


	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(ApiDemos.TAG, "onReceive()");

		String action = intent.getAction();
		if (action !=null && action.equals("org.metawatch.manager.REFRESH_WIDGET_REQUEST")) {

			Log.d(ApiDemos.TAG, "Received REFRESH_WIDGET_REQUEST intent");

			Widget.refreshWidgets(context, intent);
		}

	}
	

}
