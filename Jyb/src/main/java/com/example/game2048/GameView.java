package com.example.game2048;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.GridLayout;

public class GameView extends GridLayout {

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}

	public void initGameView() {
		setColumnCount(4);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int cardWidth = (w - 10) / 4;
		int cardHeight = (h - 10) / 4;

		addCard(cardWidth, cardHeight);
		if (Game2048Activity.getGame2048Activity() != null) {
			startGame();
		}
	}

	private void addCard(int cardWidth, int cardHeight) {
		TxtCard c;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				c = new TxtCard(getContext());
				c.setNum(0);
				addView(c, cardWidth, cardHeight);

				cardMap[x][y] = c;
			}
		}
	}

	public void startGame() {
		Game2048Activity.getGame2048Activity().clearScore();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardMap[x][y].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
	}

	private void addRandomNum() {

		emptyPoints.clear();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (cardMap[x][y].getNum() <= 0) {
					emptyPoints.add(new Point(x, y));
				}
			}
		}

		Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
		cardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);

	}

	public void swipeLeft() {
		boolean move = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for (int x1 = x + 1; x1 < 4; x1++) {
					if (cardMap[x1][y].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							x--;
							move = true;
						} else if (cardMap[x][y].equals(cardMap[x1][y])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x1][y].setNum(0);
							Game2048Activity.getGame2048Activity().addScore(cardMap[x][y].getNum());
							move = true;
						}
						break;
					}
				}
			}
		}
		if (move) {
			addRandomNum();
			checkComplete();
		}
	}

	public void swipeRight() {
		boolean move = false;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cardMap[x1][y].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							x++;
							move = true;
						} else if (cardMap[x][y].equals(cardMap[x1][y])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x1][y].setNum(0);
							Game2048Activity.getGame2048Activity().addScore(cardMap[x][y].getNum());
							move = true;
						}
						break;
					}
				}
			}
		}
		if (move) {
			addRandomNum();
			checkComplete();
		}
	}

	public void swipeUp() {
		boolean move = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int y1 = y + 1; y1 < 4; y1++) {
					if (cardMap[x][y1].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							y--;
							move = true;
						} else if (cardMap[x][y].equals(cardMap[x][y1])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x][y1].setNum(0);
							Game2048Activity.getGame2048Activity().addScore(cardMap[x][y].getNum());
							move = true;
						}
						break;
					}
				}
			}
		}
		if (move) {
			addRandomNum();
			checkComplete();
		}
	}

	public void swipeDown() {
		boolean move = false;
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				for (int y1 = y - 1; y1 >= 0; y1--) {
					if (cardMap[x][y1].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							y++;
							move = true;
						} else if (cardMap[x][y].equals(cardMap[x][y1])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							cardMap[x][y1].setNum(0);
							Game2048Activity.getGame2048Activity().addScore(cardMap[x][y].getNum());
							move = true;
						}
						break;
					}
				}
			}
		}
		if (move) {
			addRandomNum();
			checkComplete();
		}
	}

	private void checkComplete() {
		boolean complete = true;
		for (int y = 0; y < 4; y++) {
			if (!complete)
				break;
			for (int x = 0; x < 4; x++) {
				if (cardMap[x][y].getNum() == 0 || (x > 0 && cardMap[x][y].equals(cardMap[x - 1][y]))
						|| (x < 3 && cardMap[x][y].equals(cardMap[x + 1][y]))
						|| (y > 0 && cardMap[x][y].equals(cardMap[x][y - 1]))
						|| (y < 3 && cardMap[x][y].equals(cardMap[x][y + 1]))) {
					complete = false;
					break;
				}
			}
		}
		if (complete) {
			new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("游戏结束")
					.setPositiveButton("重来", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							startGame();
						}
					}).show();
		}

	}

	private TxtCard[][] cardMap = new TxtCard[4][4];
	private List<Point> emptyPoints = new ArrayList<Point>();

}
