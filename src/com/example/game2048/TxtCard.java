package com.example.game2048;

import com.example.jyb.R;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TxtCard extends FrameLayout {

	private TextView label;

	public TxtCard(Context context) {
		super(context);
		label = new TextView(getContext());
		label.setTextSize(48);
		label.setTextColor(0xccffffff);
		label.setGravity(Gravity.CENTER);

		LayoutParams lp = new LayoutParams(-1, -1);
		lp.setMargins(10, 10, 0, 0);
		addView(label, lp);

		setNum(0);
	}

	private int num = 0;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		if (num <= 0) {
			label.setText("");
			label.setBackgroundResource(R.drawable.rectbg_default);
		} else {
			if (num >= 1024) {
				label.setTextSize(32);
				label.setBackgroundResource(R.drawable.rectbg_1024);
			} else if (num == 512) {
				label.setTextSize(40);
				label.setBackgroundResource(R.drawable.rectbg_512);
			} else if (num == 256) {
				label.setTextSize(40);
				label.setBackgroundResource(R.drawable.rectbg_256);
			} else if (num == 128) {
				label.setTextSize(40);
				label.setBackgroundResource(R.drawable.rectbg_128);
			} else if (num == 64) {
				label.setTextSize(48);
				label.setBackgroundResource(R.drawable.rectbg_64);
			} else if (num == 32) {
				label.setTextSize(48);
				label.setBackgroundResource(R.drawable.rectbg_32);
			} else if (num == 16) {
				label.setTextSize(48);
				label.setBackgroundResource(R.drawable.rectbg_16);
			} else if (num == 8) {
				label.setTextSize(48);
				label.setBackgroundResource(R.drawable.rectbg_8);
			} else if (num == 4) {
				label.setTextSize(48);
				label.setBackgroundResource(R.drawable.rectbg_4);
			} else if (num == 2) {
				label.setTextSize(48);
				label.setBackgroundResource(R.drawable.rectbg_2);
			}
			label.setText(num + "");
		}
	}

	public boolean equals(TxtCard o) {
		return this.getNum() == o.getNum();
	}

}
