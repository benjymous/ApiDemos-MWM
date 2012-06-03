package org.metawatch.manager.apidemos;

import org.metawatch.manager.apidemos.R;
import org.metawatch.manager.apidemos.widget.IntentReceiver;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;

public class ApiDemos extends PreferenceActivity {
	
	public static final String TAG = "MWM-ApiDemos";
	
	Context context;
	PreferenceScreen preferenceScreen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		
		addPreferencesFromResource(R.layout.mainmenu);
		preferenceScreen = getPreferenceScreen();
	}
	
	@Override
	protected void onStart() {
		
//		preferenceScreen.findPreference("send_notification").setOnPreferenceClickListener(new OnPreferenceClickListener() {	
//			public boolean onPreferenceClick(Preference arg0) {
//				// TODO: Create example(s) of sending different types of notifications
//		    	return true;
//			}
//		});
		
		
		preferenceScreen.findPreference("update_widget").setOnPreferenceClickListener(new OnPreferenceClickListener() {	
			public boolean onPreferenceClick(Preference arg0) {
				IntentReceiver.update(context);
		    	return true;
			}
		});
		
		super.onStart();
	}
}