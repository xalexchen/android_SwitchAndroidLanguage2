package lab.sodino.language;

import java.util.Locale;

import android.app.Activity;
import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LanguageAct extends Activity {
	private LinearLayout linearLayout;
	private Button btnChinese;
	private Button btnEnglish;
	private BtnListener btnListener;
	private int[] colors = { 0xffff0000, 0xffffA500, 0xffffff00, 0xff00ff00, 0xff0000ff, 0xff9B30FF };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("ANDROID_LAB", "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initViews();
		btnListener = new BtnListener();
		btnChinese = (Button) findViewById(R.id.btnChinese);
		btnChinese.setOnClickListener(btnListener);
		btnEnglish = (Button) findViewById(R.id.btnEnglish);
		btnEnglish.setOnClickListener(btnListener);
	}

	private void initViews() {
		LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
		String[] arr = getResources().getStringArray(R.array.colors);
		for (int i = 0; i < colors.length; i++) {
			TextView txt = new TextView(this);
			txt.setText(arr[i]);
			txt.setBackgroundColor(colors[i]);
			txt.setTextColor(~colors[i] | 0xff000000);
			linearLayout.addView(txt, layParams);
		}
	}

	class BtnListener implements Button.OnClickListener {
		public void onClick(View view) {
			if (view == btnChinese) {
				updateLanguage(Locale.SIMPLIFIED_CHINESE);
			} else if (view == btnEnglish) {
				updateLanguage(Locale.ENGLISH);
			}
		}
	}

	private void updateLanguage(Locale locale) {
		Log.d("ANDROID_LAB", locale.toString());
		IActivityManager iActMag = ActivityManagerNative.getDefault();
		try {
			Configuration config = iActMag.getConfiguration();
			config.locale = locale;
			// android.permission.CHANGE_CONFIGURATION
			iActMag.updateConfiguration(config);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}