package com.example.jyb;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class TestActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		findViewById(R.id.close_btn).setOnClickListener(btnClickListener);
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

	// Æô¶¯Í¼Æ¬Ñ¡ÔñActivity
	public void getPhonePhoto(View view) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				Uri uri = data.getData();
				try {
					InputStream inputStream = getContentResolver().openInputStream(uri);
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
					imageView1.setImageBitmap(bitmap);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

}
