package com.example.jyb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends Activity {
	Button musicbtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		musicbtn = (Button) findViewById(R.id.musicCtrl_btn);

		musicbtn.setOnClickListener(btnClickListener);
		findViewById(R.id.close_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.test_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.newGraphic_btn).setOnClickListener(btnClickListener);
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (FullscreenActivity.instance.musicFlag) {
			musicbtn.setBackgroundResource(R.drawable.music_on);
		} else {
			musicbtn.setBackgroundResource(R.drawable.music_down);
		}
	};

	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.close_btn:
				closeActivity();
				break;
			case R.id.musicCtrl_btn:
				if (FullscreenActivity.instance.musicFlag) {
					FullscreenActivity.instance.musicFlag = false;
					musicbtn.setBackgroundResource(R.drawable.music_down);
					FullscreenActivity.instance.stopMusic();
				} else {
					FullscreenActivity.instance.musicFlag = true;
					musicbtn.setBackgroundResource(R.drawable.music_on);
					FullscreenActivity.instance.startMusic();
				}
				break;
			case R.id.test_btn:
				startActivity(new Intent(SettingActivity.this, AsciiArt2Activity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
				break;
			case R.id.newGraphic_btn:
				startActivity(new Intent(SettingActivity.this, GraphicActivity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
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
