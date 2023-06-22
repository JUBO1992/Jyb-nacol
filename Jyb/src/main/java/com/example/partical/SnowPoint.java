package com.example.partical;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class SnowPoint extends EffectItem{

	private Paint paint = new Paint();
	private final int size = 36; // 长度�?0-50像素
	private Rect point; // 雪点
	private Point speed; // 雪点x,y方向速度

	public SnowPoint(int width, int height) {
		super(width, height);
		point = new Rect();
		speed = new Point();
		paint.setColor(0xffffffff);
		reset();
	}
	public SnowPoint(int width, int height, int color) {
		super(width, height, color);
		point = new Rect();
		speed = new Point();
		paint.setColor(color);
		reset();
	}
	public SnowPoint(int width, int height, int color, boolean randColor) {
		super(width, height, color, randColor);
		point = new Rect();
		speed = new Point();
		reset();
	}
	public void draw(Canvas canvas) {
		//变长小于等于８绘制圆�?
		if(point.width() <= 8){
			canvas.drawCircle(point.left, point.top, point.width() / 2, paint);
		}
		else{
			//绘制雪花形状
			drawSknow(canvas);
		}
	}

	int count = 0;

	public void move() {
		point.left += speed.x;
		point.top += speed.y;
		point.right = point.right + speed.x;
		point.bottom = point.bottom + speed.y;
		count++;
		if (count > 5) {
			count = 0;
			speed.x = rand.nextInt(size);
			speed.x = speed.x > speed.y ? speed.y : speed.x;
			speed.x = rand.nextBoolean() ? -speed.x : speed.x;
			speed.y += rand.nextBoolean() ? 1 : 0;
		}

		if (point.left < 0 || point.bottom > height) {
			reset();
		}
	}

	private void reset() {
		int x = rand.nextInt(width);
		int y = rand.nextInt(height);
		int w = rand.nextInt(size);
		int h = rand.nextInt(size);
		
		if(w > 8){
			//勾３股４弦５（宽是４的�?�数，高是３的�?�数�?
			int mod = w % 4;
			w += mod;
			int mul = w / 4;//倍数
			h = 3 * mul;
			point.left = x;
			point.top = y;
			point.right = x + w;
			point.bottom = y + h;
			
		}
		else{
			point.left = x;
			point.top = y;
			point.right = x + w;
			point.bottom = y + w;
		}

		int speedX = rand.nextInt(size);
		int speedY = rand.nextInt(size);
		/*
		 * int speedX = w; int speedY = h;
		 */

		speedX = speedX == 0 ? 1 : speedX;
		speedY = speedY == 0 ? 1 : speedY;
		speedX = speedX > speedY ? speedY : speedX;

		speed.x = speedX;
		speed.y = speedY;
		randomColor();
		paint.setColor(color);
	}

	private void drawSknow(Canvas canvas){
		int w = point.width();
		int h = point.height();
		int mul = w / 4;//倍数
		float xie = 5 * mul / 2;
		float centerY = point.top + h / 2;
		float centerX =point.left + w / 2;
		
		canvas.drawLine(point.left, point.top, point.right, point.bottom, paint);
		canvas.drawLine(point.left, point.bottom, point.right, point.top, paint);
		canvas.drawLine(centerX, centerY - xie, centerX, centerY + xie, paint);
		
	}
	
	public void printPosition() {
		Log.d("SknowPoint", "x : " + point.left + " y : " + point.top + " r : "
				+ point.right + " b : " + point.bottom);
	}

}
