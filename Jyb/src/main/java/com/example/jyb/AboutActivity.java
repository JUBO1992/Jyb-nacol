package com.example.jyb;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		findViewById(R.id.close_btn).setOnClickListener(btnClickListener);

		AssetManager assets = getAssets();
		Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/consola.ttf");
		TextView authorText = (TextView)findViewById(R.id.author_info);
		TextView versionText = (TextView)findViewById(R.id.version_info);
		authorText.setTypeface(fromAsset);
		versionText.setTypeface(fromAsset);

	}

	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.close_btn:
				closeActivity();
				break;
			default:
				break;
			}
		}
	};

	private void closeActivity() {
		finish();
		overridePendingTransition(R.anim.ap2, R.anim.ap1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			closeActivity();
		}
		return super.onKeyDown(keyCode, event);
	}
}
