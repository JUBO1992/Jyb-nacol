package com.example.partical;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Star extends EffectItem{

	private final int NORMAL = 0;
	private final int LIGHT = 1;
	private final int METEOR = 2;
	private int state = NORMAL;

	private Paint paint = new Paint();
	private final int size = 10; // 长度�?0-size像素
	private int radius;
	private Point point; // 星星

	private int light = 100;// 闪烁
	private int meteor = 10000;// 流星
	
	//星星闪烁类型
	private final int LIGHT_FULL = 0;
	private final int LIGHT_HALF = 1;
	private final int LIGHT_HALF_ALPHA = 2;
	private int lightState = 0;
	private int lightAlpha = 80;
	
	//流星移动�?
	private int meteorSpeedX;
	private int meteorSpeedY;
	private int meteorState = 0;
	private int meteorAlpha = 255;
	private int meteorStep;
	

	public Star(int width, int height) {
		super(width, height);
		point = new Point();
		paint.setColor(0xffffffff);
		reset();
	}
	public Star(int width, int height, int color) {
		super(width, height, color);
		point = new Point();
		paint.setColor(color);
		reset();
	}
	public Star(int width, int height, int color, boolean randColor) {
		super(width, height, color, randColor);
		point = new Point();
		reset();
	}
	public void draw(Canvas canvas) {
		// 变长小于等于８绘制圆�?
		switch (state) {
		case NORMAL:
			canvas.drawCircle(point.x, point.y, radius / 2, paint);
			break;
		case LIGHT:
			canvas.drawCircle(point.x, point.y, radius / 2, paint);
			drawLightStar(canvas);
			break;
		case METEOR:
			drawMeteor(canvas);
			break;
		}
	}

	public void move() {

		switch (state) {
		case NORMAL:
			while (point.x < 0 || point.x > width || point.y > height) {
				reset();
			}
			int mod = rand.nextInt(light + 1) % light;
			if (mod == 0) {
				// 闪烁
				state = LIGHT;
				lightState = rand.nextInt(10) % 3;
				return;
			}
			mod = rand.nextInt(meteor + 1) % meteor;
			if (mod == 0) {
				// 流星
				state = METEOR;
				meteorSpeedY = 1 + rand.nextInt(height / 20);
				meteorSpeedX = rand.nextInt(width / 20);
				meteorSpeedX *= rand.nextBoolean() ? 1 : -1;
				meteorStep = 1;
				return;
			}
			break;
		case LIGHT:
			lightAlpha -= 20;
			if(lightAlpha < 0){
				state = NORMAL;
				lightAlpha = 80;
			}
			break;
		case METEOR:
			meteorAlpha -= 20;
			if(meteorAlpha < 0){
				state = NORMAL;
				meteorAlpha = 255;
				meteorStep = 1;
				return;
			}
			meteorState = rand.nextInt(10) % 3;
			meteorStep ++;
			break;
		}

	}

	private void reset() {
		point.x = rand.nextInt(width);
		point.y = rand.nextInt(height / 2);
		radius = rand.nextInt(size);
		
		randomColor();
		paint.setColor(color);
	}

	private void drawLightStar(Canvas canvas) {
		
		switch(lightState){
		case LIGHT_HALF:
			//左右交叉
			canvas.drawLine(point.x - radius, point.y - radius, point.x + radius, point.y + radius, paint);
			canvas.drawLine(point.x - radius, point.y + radius, point.x + radius, point.y - radius, paint);
			break;
		case LIGHT_FULL:
			paint.setAlpha(255 - lightAlpha);
			//绘制横竖�?
			canvas.drawLine(point.x - 2 * radius, point.y, point.x + 2 * radius, point.y, paint);
			canvas.drawLine(point.x, point.y - 2 * radius, point.x, point.y + 2 * radius, paint);
		case LIGHT_HALF_ALPHA:
			paint.setAlpha(lightAlpha);
			//左右交叉
			canvas.drawLine(point.x - radius, point.y - radius, point.x + radius, point.y + radius, paint);
			canvas.drawLine(point.x - radius, point.y + radius, point.x + radius, point.y - radius, paint);
			paint.setAlpha(255);
			break;
		}
		
	}
	
	private void drawMeteor(Canvas canvas){
		
		int trimX = meteorStep * meteorSpeedX;
		int trimY = meteorStep * meteorSpeedY;
		paint.setAlpha(lightAlpha);
		//绘制流行轨迹
		canvas.drawLine(point.x, point.y, trimX + point.x, trimY + point.y, paint);
		paint.setAlpha(255);
		canvas.drawCircle(trimX + point.x, trimY + point.y, radius / 2, paint);
		switch(meteorState){
		case LIGHT_HALF:
			//左右交叉
			canvas.drawLine(trimX + point.x - radius, trimY + point.y - radius, trimX + point.x + radius, trimY + point.y + radius, paint);
			canvas.drawLine(trimX + point.x - radius, trimY + point.y + radius, trimX + point.x + radius, trimY + point.y - radius, paint);
			break;
		case LIGHT_FULL:
			paint.setAlpha(255 - lightAlpha);
			//绘制横竖�?
			canvas.drawLine(trimX + point.x - 2 * radius, trimY + point.y, trimX + point.x + 2 * radius, trimY + point.y, paint);
			canvas.drawLine(trimX + point.x, trimY + point.y - 2 * radius, trimX + point.x, trimY + point.y + 2 * radius, paint);
		case LIGHT_HALF_ALPHA:
			paint.setAlpha(lightAlpha);
			//左右交叉
			canvas.drawLine(trimX + point.x - radius, trimY + point.y - radius, trimX + point.x + radius, trimY + point.y + radius, paint);
			canvas.drawLine(trimX + point.x - radius, trimY + point.y + radius, trimX + point.x + radius, trimY + point.y - radius, paint);
			paint.setAlpha(255);
			break;
		}
	}

}
