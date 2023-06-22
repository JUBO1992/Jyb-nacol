package com.example.jyb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.lang.Math;

public class DrawView extends View {

	final static double PI = 3.14159265;
	private Context mContext;
	
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        
		flowerCenter(width/2, height/2, canvas);
	}
	
	// x,y为花朵中心位置
	private void flowerCenter(int x, int y, Canvas canvas){
		flower(x-195, y-354, canvas);
	}

	// x,y为花朵左上角
	private void flower(int x, int y, Canvas canvas) {
		// 画枝干
		Paint paint = new Paint();
		paint.setColor(Color.GREEN);
		canvas.drawLine(x+189, y+372, x+180, y+400, paint);
		canvas.drawLine(x+310, y+160, x+325, y+68, paint);
		canvas.drawLine(x+310, y+160, x+187, y+374, paint);
		canvas.drawLine(x+150, y+140, x+189, y+374, paint);
		canvas.drawLine(x+430, y+176, x+190, y+374, paint);
		canvas.drawLine(x+370, y+110, x+187, y+374, paint);
		canvas.drawLine(x+250, y+72, x+189, y+372, paint);
		canvas.drawLine(x+253, y+192, x+190, y+374, paint);
		canvas.drawLine(x+189, y+372, x+187, y+400, paint);
		canvas.drawLine(x+189, y+372, x+182, y+400, paint);
		canvas.drawLine(x+189, y+372, x+200, y+120, paint);

		// 画花朵
		flower(x+320, y+160, Color.RED, canvas);
		flower(x+200, y+120, Color.YELLOW, canvas);
		flower(x+150, y+140, Color.MAGENTA, canvas);
		flower(x+430, y+176, Color.rgb(255, 127, 0), canvas);
		flower(x+370, y+110, Color.rgb(239, 179, 52), canvas);
		flower(x+250, y+72, Color.rgb(235, 95, 186), canvas);
		flower(x+325, y+68, Color.rgb(228, 119, 98), canvas);
		flower(x+253, y+190, Color.rgb(247, 169, 117), canvas);

		// 画蝴蝶结
		tie(x+195, y+354, Color.MAGENTA, canvas);
	}

	// 画 花朵
	private void flower(int x, int y, int color, Canvas canvas) {
		int x1, y1, x2, y2;
		int d = 15;
		double e;
		Paint paint = new Paint();
		paint.setColor(color);
		for (double a = 0; a < 2 * PI; a += PI / 360) {
			e = d * (1 + Math.sin(a * 5));
			x1 = (int) (x + e * Math.cos(a));
			y1 = (int) (y + e * Math.sin(a));
			x2 = (int) (x + e * Math.cos(a + PI / 5));
			y2 = (int) (y + e * Math.sin(a + PI / 5));
			canvas.drawLine(x1, y1, x2, y2, paint);
		}
	}

	// 画 蝴蝶结
	private void tie(int x, int y, int color, Canvas canvas) {
		int x1, y1, x2, y2;
		int d = 80;
		double e;
		Paint paint = new Paint();
		paint.setColor(color);
		for (double a = 0; a < 2 * PI; a += PI / 360) {
			e = d * (1 + Math.sin(a * 4));
			x1 = (int) (x + e * Math.cos(a));
			y1 = (int) (y + e * Math.sin(a) / 2);
			x2 = (int) (x + e * Math.cos(a + PI / 9));
			y2 = (int) (y + e * Math.sin(a + PI / 9) / 4.5);
			canvas.drawLine(x1, y1, x2, y2, paint);
		}
	}

}
