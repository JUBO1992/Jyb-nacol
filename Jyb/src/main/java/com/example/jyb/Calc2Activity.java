package com.example.jyb;

import java.util.Arrays;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.security.auth.PrivateCredentialPermission;

import bsh.EvalError;
import bsh.Interpreter;
import android.R.string;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calc2Activity extends Activity {
	private long firstTime = 0;
	EditText rsText = null; // 公式显示器
	EditText hexText = null; // 十六进制结果显示器
	EditText decText = null; // 十进制结果显示器
	EditText octText = null; // 八进制结果显示器
	EditText binText = null; // 二进制结果显示器
	EditText selText = null; // 当前选中的显示器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calc2);

		rsText = (EditText) findViewById(R.id.rsText);
		hexText = (EditText) findViewById(R.id.hexText);
		decText = (EditText) findViewById(R.id.decText);
		octText = (EditText) findViewById(R.id.octText);
		binText = (EditText) findViewById(R.id.binText);
		setSelTextView(decText);
		AssetManager assets = getAssets();
		// Typeface fromAsset = Typeface.createFromAsset(assets,
		// "fonts/jubohandwrite.ttf");
		Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/consola.ttf");
		rsText.setTypeface(fromAsset);
		hexText.setTypeface(fromAsset);
		decText.setTypeface(fromAsset);
		octText.setTypeface(fromAsset);
		binText.setTypeface(fromAsset);
		hexText.setOnClickListener(txtClickListener);
		decText.setOnClickListener(txtClickListener);
		octText.setOnClickListener(txtClickListener);
		binText.setOnClickListener(txtClickListener);
		findViewById(R.id.btn_0).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_1).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_2).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_3).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_4).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_5).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_6).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_7).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_8).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_9).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_A).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_B).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_C).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_D).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_E).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_F).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_comma).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_point).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_plus).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_minus).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_multiplay).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_divide).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_equal).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_del).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_clean).setOnClickListener(btnClickListener);
	}

	View.OnClickListener txtClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			EditText text = (EditText) v;
			switch (text.getId()) {
			case R.id.hexText:
				setSelTextView(hexText);
				break;
			case R.id.decText:
				setSelTextView(decText);
				break;
			case R.id.octText:
				setSelTextView(octText);
				break;
			case R.id.binText:
				setSelTextView(binText);
				break;
			}
		}
	};

	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Button btn = (Button) v;
			String exp = rsText.getText().toString();
			switch (btn.getId()) {
			case R.id.btn_0:
				if (exp.equals("0") || exp.equals("-0")) {
					return;
				}
			case R.id.btn_1:
			case R.id.btn_2:
			case R.id.btn_3:
			case R.id.btn_4:
			case R.id.btn_5:
			case R.id.btn_6:
			case R.id.btn_7:
			case R.id.btn_8:
			case R.id.btn_9:
			case R.id.btn_A:
			case R.id.btn_B:
			case R.id.btn_C:
			case R.id.btn_D:
			case R.id.btn_E:
			case R.id.btn_F:
				String num[] = exp.split(",");
				if (getDecValue(num[num.length - 1] + btn.getText()) > readNumber("FFFFFFFFFFFF", 16)
						&& !exp.substring(exp.length() - 1).equals(",")) {
					return;
				} else if (exp.equals("0") || exp.equals("-0")) {
					rsText.setText(exp.substring(0, exp.length() - 1) + btn.getText());
				} else {
					rsText.setText(exp + btn.getText());
				}
				setResult(rsText.getText().toString());
				break;
			case R.id.btn_comma:
				if (exp == null || exp.length() == 0 || exp.substring(exp.length() - 1).equals(",")) {
					return;
				} else {
					rsText.setText(exp + btn.getText());
				}
				break;
			case R.id.btn_point:
				break;
			case R.id.btn_plus:
			case R.id.btn_multiplay:
			case R.id.btn_divide:
			case R.id.btn_minus:
				break;
			case R.id.btn_del:
				if (exp == null || exp.trim().length() == 0)
					return;
				else
					rsText.setText(exp.substring(0, exp.length() - 1).trim());
				setResult(rsText.getText().toString());
				break;
			case R.id.btn_clean:
				rsText.setText("");
				setResult(rsText.getText().toString());
				break;
			case R.id.btn_equal:
				setResult(exp);
				break;
			}
			// 按键完成后始终保持光标在最后一位
			rsText.setSelection(rsText.getText().length());
		}
	};

	private void setSelTextView(EditText v) {
		selText = v;
		hexText.setTextColor(0xffffffff);
		decText.setTextColor(0xffffffff);
		octText.setTextColor(0xffffffff);
		binText.setTextColor(0xffffffff);
		selText.setTextColor(0xffff6600);
		rsText.setText(selText.getText().toString().trim().substring(4));
		((Button) findViewById(R.id.btn_0)).setEnabled(true);
		((Button) findViewById(R.id.btn_1)).setEnabled(true);
		((Button) findViewById(R.id.btn_2)).setEnabled(true);
		((Button) findViewById(R.id.btn_3)).setEnabled(true);
		((Button) findViewById(R.id.btn_4)).setEnabled(true);
		((Button) findViewById(R.id.btn_5)).setEnabled(true);
		((Button) findViewById(R.id.btn_6)).setEnabled(true);
		((Button) findViewById(R.id.btn_7)).setEnabled(true);
		((Button) findViewById(R.id.btn_8)).setEnabled(true);
		((Button) findViewById(R.id.btn_9)).setEnabled(true);
		((Button) findViewById(R.id.btn_A)).setEnabled(true);
		((Button) findViewById(R.id.btn_B)).setEnabled(true);
		((Button) findViewById(R.id.btn_C)).setEnabled(true);
		((Button) findViewById(R.id.btn_D)).setEnabled(true);
		((Button) findViewById(R.id.btn_E)).setEnabled(true);
		((Button) findViewById(R.id.btn_F)).setEnabled(true);
		if (v.getId() == R.id.decText) {
			((Button) findViewById(R.id.btn_A)).setEnabled(false);
			((Button) findViewById(R.id.btn_B)).setEnabled(false);
			((Button) findViewById(R.id.btn_C)).setEnabled(false);
			((Button) findViewById(R.id.btn_D)).setEnabled(false);
			((Button) findViewById(R.id.btn_E)).setEnabled(false);
			((Button) findViewById(R.id.btn_F)).setEnabled(false);
		} else if (v.getId() == R.id.octText) {
			((Button) findViewById(R.id.btn_8)).setEnabled(false);
			((Button) findViewById(R.id.btn_9)).setEnabled(false);
			((Button) findViewById(R.id.btn_A)).setEnabled(false);
			((Button) findViewById(R.id.btn_B)).setEnabled(false);
			((Button) findViewById(R.id.btn_C)).setEnabled(false);
			((Button) findViewById(R.id.btn_D)).setEnabled(false);
			((Button) findViewById(R.id.btn_E)).setEnabled(false);
			((Button) findViewById(R.id.btn_F)).setEnabled(false);
		} else if (v.getId() == R.id.binText) {
			((Button) findViewById(R.id.btn_2)).setEnabled(false);
			((Button) findViewById(R.id.btn_3)).setEnabled(false);
			((Button) findViewById(R.id.btn_4)).setEnabled(false);
			((Button) findViewById(R.id.btn_5)).setEnabled(false);
			((Button) findViewById(R.id.btn_6)).setEnabled(false);
			((Button) findViewById(R.id.btn_7)).setEnabled(false);
			((Button) findViewById(R.id.btn_8)).setEnabled(false);
			((Button) findViewById(R.id.btn_9)).setEnabled(false);
			((Button) findViewById(R.id.btn_A)).setEnabled(false);
			((Button) findViewById(R.id.btn_B)).setEnabled(false);
			((Button) findViewById(R.id.btn_C)).setEnabled(false);
			((Button) findViewById(R.id.btn_D)).setEnabled(false);
			((Button) findViewById(R.id.btn_E)).setEnabled(false);
			((Button) findViewById(R.id.btn_F)).setEnabled(false);
		}
	}

	private void setResult(String exp) {
		hexText.setText("HEX=");
		decText.setText("DEC=");
		octText.setText("OCT=");
		binText.setText("BIN=");
		if (exp == null || exp.trim().length() == 0) {
			return;
		}
		String num[] = exp.split(",");
		for (int i = 0; i < num.length; i++) {
			long decvalue = getDecValue(num[i]);
			String comma = i == 0 ? "" : ",";
			hexText.setText(hexText.getText() + comma + writeNumber(decvalue, 16));
			decText.setText(decText.getText() + comma + writeNumber(decvalue, 10));
			octText.setText(octText.getText() + comma + writeNumber(decvalue, 8));
			binText.setText(binText.getText() + comma + writeNumber(decvalue, 2));
		}
	}

	private long getDecValue(String exp) {
		long decvalue = 0;
		if (selText.getId() == R.id.hexText) {
			decvalue = readNumber(exp, 16);
		} else if (selText.getId() == R.id.decText) {
			decvalue = readNumber(exp, 10);
		} else if (selText.getId() == R.id.octText) {
			decvalue = readNumber(exp, 8);
		} else if (selText.getId() == R.id.binText) {
			decvalue = readNumber(exp, 2);
		}
		return decvalue;
	}

	/*
	 * 读取数字字符串及进制
	 */
	private long readNumber(String input, int base) {
		int inputLength = input.length();
		if (inputLength == 0) {
			return -1; // 输入字符串不对
		}
		if (base < 2 || base > 16) {
			return -1; // 输入进制不对
		}
		long number = 0;
		for (int i = 0; i < inputLength; i++) {
			char c = input.charAt(i); // 取一个字符
			int d; // 字符转成数字
			if (c >= '0' && c <= '9') {
				d = c - '0';
			} else if (c >= 'A' && c <= 'F') {
				d = c - 'A' + 10;
			} else if (c >= 'a' && c <= 'f') {
				d = c - 'a' + 10;
			} else {
				return -1; // 字符不对
			}

			if (d >= base) {
				return -1; // 输入字符超过进制允许范围
			}

			number *= base;
			number += d;
			if (number < 0) {
				return -1; // 输入数字太大
			}
		}
		return number;
	}

	private String writeNumber(long number, int base) {
		if (base < 2 || base > 16) {
			return null; // 输出进制不对
		}

		char buffer;
		String output = "";
		do {
			long d = number % base;
			number /= base;
			if (d <= 9) {
				buffer = (char) (d + '0');
			} else {
				buffer = (char) (d - 10 + 'a');
			}
			output = buffer + output;
		} while (number != 0);

		return output.toUpperCase();
	}

	private void closeActivity() {
		finish();
		overridePendingTransition(R.anim.ap2, R.anim.ap1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {
				Toast.makeText(this, "再按一次退出页面", Toast.LENGTH_SHORT).show();
				firstTime = secondTime;
				return true;
			} else {
				closeActivity();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
