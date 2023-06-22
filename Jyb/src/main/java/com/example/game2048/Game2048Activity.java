package com.example.game2048;

import com.example.jyb.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Game2048Activity extends Activity {
	private long firstTime = 0;
	private int score = 0;
	private TextView tvScore;
	private static Game2048Activity game2048Activity = null;
	private GameView gameView = null;
	
	public Game2048Activity(){
		game2048Activity = this;
	}
	
	public static Game2048Activity getGame2048Activity() {
		return game2048Activity;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game2048);
		
		tvScore = (TextView)findViewById(R.id.tvScore);
		gameView = (GameView)findViewById(R.id.gameView);
		findViewById(R.id.start_btn).setOnClickListener(btnClickListener);
	}
	
	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.start_btn:
				gameView.startGame();
				break;
			}
		}
	};
	
	public void showScore(){
		tvScore.setText(score+"");
	}
	
	public void clearScore(){
		score = 0;
		showScore();
	}
	
	public void addScore(int s){
		score += s;
		showScore();
	}

	private float startX,startY,offsetX,offsetY;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//继承了Activity的onTouchEvent方法，直接监听点击事件  
		if(event.getAction() == MotionEvent.ACTION_DOWN) {  
			//当手指按下的时候  
			startX = event.getX();
			startY = event.getY(); 
		}  
		if(event.getAction() == MotionEvent.ACTION_UP) {  
			//当手指离开的时候  
			offsetX = event.getX()-startX;
			offsetY = event.getY()-startY;
			if (Math.abs(offsetX)>Math.abs(offsetY)) {
				if (offsetX<-20) {
					gameView.swipeLeft();
				}else if (offsetX>20) {
					gameView.swipeRight();
				}
			}else{
				if (offsetY<-20) {
					gameView.swipeUp();
				}else if (offsetY>20) {
					gameView.swipeDown();
				}
			}
		}
		return super.onTouchEvent(event);
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
