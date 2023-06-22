package com.example.jyb;

import com.example.game2048.Game2048Activity;
import com.example.jigsawpuzzle.JigsawPuzzleActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class FavouriteActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favourite);

		findViewById(R.id.close_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.asciiArt_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.test1_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.test2_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.test3_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.test4_btn).setOnClickListener(btnClickListener);
	}
	
	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.close_btn:
				closeActivity();
				break;
			case R.id.asciiArt_btn:
				startActivity(new Intent(FavouriteActivity.this, AsciiArtActivity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
				break;
			case R.id.test1_btn:
				startActivity(new Intent(FavouriteActivity.this, CalcActivity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
				break;
			case R.id.test2_btn:
				startActivity(new Intent(FavouriteActivity.this, Game2048Activity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
				break;
			case R.id.test3_btn:
				startActivity(new Intent(FavouriteActivity.this, Calc2Activity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
				break;
			case R.id.test4_btn:
				startActivity(new Intent(FavouriteActivity.this, JigsawPuzzleActivity.class));
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
