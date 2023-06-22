package com.example.jyb;

import java.util.Arrays;

import bsh.EvalError;
import bsh.Interpreter;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CalcActivity extends Activity {
	private long firstTime = 0;
	EditText rsText = null; // 公式显示器
	EditText resultText = null; // 结果显示器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calc);

		rsText = (EditText) findViewById(R.id.rsText);
		resultText = (EditText) findViewById(R.id.resultText);
		AssetManager assets = getAssets();
		// Typeface fromAsset = Typeface.createFromAsset(assets,
		// "fonts/jubohandwrite.ttf");
		Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/consola.ttf");
		rsText.setTypeface(fromAsset);
		resultText.setTypeface(fromAsset);
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
		findViewById(R.id.btn_point).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_plus).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_minus).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_multiplay).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_divide).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_equal).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_del).setOnClickListener(btnClickListener);
		findViewById(R.id.btn_clean).setOnClickListener(btnClickListener);
	}

	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Button btn = (Button) v;
			String exp = rsText.getText().toString();
			int lastEnter = exp.lastIndexOf("\n");// 公式中最后一个回车符位置
			switch (btn.getId()) {
			case R.id.btn_0:
				if (lastEnter > 0 && exp.length() >= lastEnter + 2
						&& (exp.substring(lastEnter + 2).equals("0") || exp.substring(lastEnter + 2).equals("-0"))) {
					return;
				} else if (exp.equals("0") || exp.equals("-0")) {
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
				if (getNumlen(exp) >= 14) {
					return;
				}
				if (exp.equals("0") || exp.equals("-0")) {
					rsText.setText(exp.substring(0, exp.length() - 1) + btn.getText());
				} else if (lastEnter > 0 && exp.length() >= lastEnter + 2
						&& (exp.substring(lastEnter + 2).equals("0") || exp.substring(lastEnter + 2).equals("-0"))) {
					rsText.setText(exp.substring(0, exp.length() - 1) + btn.getText());
				} else {
					rsText.setText(exp + "" + btn.getText());
				}
				setResult(rsText.getText().toString());
				break;
			case R.id.btn_point:
				if (getNumlen(exp) >= 14) {
					return;
				}
				if (lastEnter < 0 && exp.indexOf(".") > 0) {
					return;
				} else if (lastEnter > 0 && exp.substring(lastEnter + 1).indexOf(".") > 0) {
					return;
				} else if (exp == null || exp.trim().length() == 0) {
					rsText.setText("0" + btn.getText());
				} else if (lastEnter > 0 && exp.length() == lastEnter + 2) {
					rsText.setText(exp + "0" + btn.getText());
				} else {
					rsText.setText(exp + "" + btn.getText());
				}
				setResult(rsText.getText().toString());
				break;
			case R.id.btn_plus:
			case R.id.btn_multiplay:
			case R.id.btn_divide:
				if (exp == null || exp.trim().length() == 0)
					return;
			case R.id.btn_minus:
				if (exp.trim().equals("-")) {
					return;
				} else if (exp == null || exp.trim().length() == 0) {
					rsText.setText(btn.getText());
				} else {
					if (exp.substring(exp.length() - 1).equals("+") || exp.substring(exp.length() - 1).equals("-")
							|| exp.substring(exp.length() - 1).equals("*")
							|| exp.substring(exp.length() - 1).equals("/")) {

						rsText.setText(exp.substring(0, exp.length() - 1) + btn.getText());
					} else {
						rsText.setText(exp + "\n" + btn.getText());
					}
				}
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

	private int getNumlen(String exp) {
		int lastEnter = exp.lastIndexOf("\n");// 公式中最后一个回车符位置
		if (exp.length() <= 0) {
			return 0;
		} else if (lastEnter < 0) {
			if (exp.substring(0, 1).equals("-"))
				return exp.length() - 1;
			else
				return exp.length();
		} else if (exp.length() >= lastEnter + 2) {
			return exp.substring(lastEnter + 2).length();
		} else {
			return -1;
		}
	}

	private void setResult(String exp) {
		if (exp == null || exp.trim().length() == 0) {
			resultText.setText("");
			return;
		}
		exp = getRs(exp);
		if (exp.endsWith(".0")) {
			exp = exp.substring(0, exp.indexOf(".0"));
		}
		if (exp.indexOf("E") > 0) {
			if (exp.indexOf(".") > 0 && exp.length() >= 15) {
				int l = exp.length() - exp.indexOf("E");
				exp = exp.substring(0, 14 - l) + exp.substring(exp.indexOf("E"));
			}
		} else if (exp.indexOf(".") > 0 && exp.length() >= 15) {
			exp = exp.substring(0, 14);
		}
		resultText.setText("=" + exp);
	}

	/***
	 * @param exp
	 *            算数表达式
	 * @return 根据表达式返回结果
	 */
	private String getRs(String exp) {
		Interpreter bsh = new Interpreter();
		Number result = null;
		try {
			exp = filterExp(exp);
			exp = exp.replaceAll("\n", "");
			result = (Number) bsh.eval(exp);
		} catch (EvalError e) {
			e.printStackTrace();
			return "Formula Error";
		}
		return result.doubleValue() + "";
	}

	/**
	 * @param exp
	 *            算数表达式
	 * @return 因为计算过程中,全程需要有小数参与.
	 */
	private String filterExp(String exp) {
		String num[] = exp.split("");
		String temp = null;
		int begin = 0, end = 0;
		for (int i = 1; i < num.length; i++) {
			temp = num[i];
			if (temp.matches("[+-/()*]")) {
				if (temp.equals("."))
					continue;
				end = i - 1;
				temp = exp.substring(begin, end);
				if (temp.trim().length() > 0 && temp.indexOf(".") < 0)
					num[i - 1] = num[i - 1] + ".0";
				begin = end + 1;
			}
		}
		if (exp.lastIndexOf("\n") < 0 && exp.indexOf(".") < 0) {
			num[num.length - 1] += ".0";
		} else if (exp.lastIndexOf("\n") > 0 && exp.substring(exp.lastIndexOf("\n")).trim().length() >= 2
				&& exp.substring(exp.lastIndexOf("\n")).trim().indexOf(".") < 0) {
			num[num.length - 1] += ".0";
		}
		return Arrays.toString(num).replaceAll("[\\[\\], ]", "");
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
