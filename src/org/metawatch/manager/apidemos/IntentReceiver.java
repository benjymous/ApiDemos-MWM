package org.metawatch.manager.apidemos;

import org.metawatch.manager.apidemos.app.App;
import org.metawatch.manager.apidemos.app.Life;
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
		if (action==null)
			return;
		
		if (action.equals("org.metawatch.manager.REFRESH_WIDGET_REQUEST")) {
			Log.d(ApiDemos.TAG, "Received REFRESH_WIDGET_REQUEST intent");

			Widget.refreshWidgets(context, intent);
		} else if (action.equals("org.metawatch.manager.APPLICATION_DISCOVERY")) {
			Log.d(ApiDemos.TAG, "Received APPLICATION_DISCOVERY intent");

			App.announce(context);
			Life.announce(context);

		} else if (action.equals("org.metawatch.manager.APPLICATION_ACTIVATE")) {
			Log.d(ApiDemos.TAG, "Received APPLICATION_ACTIVATE intent");

			String id = intent.getStringExtra("id");
			
			if (id!=null && id.equals(App.id))
				App.update(context);
			else if (id!=null && id.equals(Life.id))
				Life.startUpdate(context);
			
		} else if (action.equals("org.metawatch.manager.APPLICATION_DEACTIVATE")) {
			Log.d(ApiDemos.TAG, "Received APPLICATION_DEACTIVATE intent");

			String id = intent.getStringExtra("id");
			
			if (id!=null && id.equals(Life.id))
				Life.stopUpdate();
			
		} else if (action.equals("org.metawatch.manager.BUTTON_PRESS")) {
			Log.d(ApiDemos.TAG, "Received BUTTON_PRESS intent");
			
			String id = intent.getStringExtra("id");
			int button = intent.getIntExtra("button", 0); // button index
			int type = intent.getIntExtra("type", 0); // type: 0=pressed, 1=held short, 1=held long
			
			if (id!=null && id.equals(App.id))
				App.button(context, button, type);
		}
	}
}
