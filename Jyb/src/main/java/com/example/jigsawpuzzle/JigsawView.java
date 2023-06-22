package com.example.jigsawpuzzle;

import com.example.jyb.R;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class JigsawView extends RelativeLayout implements View.OnClickListener {
	/**
	 * 设置Item的数量n*n；默认为3
	 */
	private int mColumn = 3;
	/**
	 * 布局的宽度
	 */
	private int mWidth;
	/**
	 * 布局的padding
	 */
	private int mPadding;
	/**
	 * 存放所有的Item
	 */
	private ImageView[] mGamePintuItems;
	/**
	 * Item的宽度
	 */
	private int mItemWidth;

	/**
	 * Item横向与纵向的边距
	 */
	private int mMargin = 1;

	/**
	 * 拼图的图片
	 */
	private Bitmap mBitmap;
	/**
	 * 存放切完以后的图片bean
	 */
	private List<ImagePiece> mItemBitmaps;

	private boolean once;

	private int steps = 0;// 步数

	public JigsawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public JigsawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public JigsawView(Context context) {
		super(context);
		initGameView();
	}

	public void initGameView() {
		// 把设置的margin值转换为dp
		mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMargin,
				getResources().getDisplayMetrics());
		// 设置Layout的内边距，四边一致，设置为四内边距中的最小值
		int paddings[] = { getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom() };
		mPadding = min(paddings);
	}

	private int min(int[] array) {
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (min < array[i]) {
				min = array[i];
			}
		}
		return min;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 获得游戏布局的边长
		mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());

		if (!once) {
			initBitmap();
			initItem();
		}
		once = true;
		setMeasuredDimension(mWidth, mWidth);
	}

	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
	}

	private void recyclerBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}

	/**
	 * 初始化bitmap
	 */
	private void initBitmap() {
		mFirst = null;
		steps = 0;
		JigsawPuzzleActivity.getJPActivity().setSteps(steps);
		JigsawPuzzleActivity.getJPActivity().resetTime();
		if (mBitmap == null)
			mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.whale2);
		if (mItemBitmaps != null)
			mItemBitmaps.clear();
		mItemBitmaps = ImageSplitter.split(mBitmap, mColumn);

		// 对图片进行排序
		Collections.sort(mItemBitmaps, new Comparator<ImagePiece>() {
			@Override
			public int compare(ImagePiece lhs, ImagePiece rhs) {
				// 我们使用random随机比较大小
				return Math.random() > 0.5 ? 1 : -1;
			}
		});
	}

	/**
	 * 初始化每一个item
	 */
	private void initItem() {
		// 获得Item的宽度
		int childWidth = (mWidth - mPadding * 2 - mMargin * (mColumn - 1)) / mColumn;
		mItemWidth = childWidth;

		mGamePintuItems = new ImageView[mColumn * mColumn];
		// 放置Item
		for (int i = 0; i < mGamePintuItems.length; i++) {
			ImageView item = new ImageView(getContext());

			item.setOnClickListener(this);

			item.setImageBitmap(mItemBitmaps.get(i).bitmap);
			mGamePintuItems[i] = item;
			item.setId(i + 1);
			item.setTag(i + "_" + mItemBitmaps.get(i).index);

			RelativeLayout.LayoutParams lp = new LayoutParams(mItemWidth, mItemWidth);
			// 设置横向边距,不是最后一列
			if ((i + 1) % mColumn != 0) {
				lp.rightMargin = mMargin;
			}
			// 如果不是第一列
			if (i % mColumn != 0) {
				lp.addRule(RelativeLayout.RIGHT_OF, //
						mGamePintuItems[i - 1].getId());
			}
			// 如果不是第一行，//设置纵向边距，非最后一行
			if ((i + 1) > mColumn) {
				lp.topMargin = mMargin;
				lp.addRule(RelativeLayout.BELOW, //
						mGamePintuItems[i - mColumn].getId());
			}
			addView(item, lp);
		}
	}

	/**
	 * 记录第一次点击的ImageView
	 */
	private ImageView mFirst;
	/**
	 * 记录第二次点击的ImageView
	 */
	private ImageView mSecond;

	/**
	 * 点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		if (isAniming)
			return;
		// 如果两次点击是同一个
		if (mFirst == v) {
			mFirst.setColorFilter(null);
			mFirst = null;
			return;
		}
		// 点击第一个Item
		if (mFirst == null) {
			mFirst = (ImageView) v;
			mFirst.setColorFilter(Color.parseColor("#55FF0000"));
		} else// 点击第二个Item
		{
			mSecond = (ImageView) v;
			exchangeView();
			steps++;
			JigsawPuzzleActivity.getJPActivity().setSteps(steps);
			JigsawPuzzleActivity.getJPActivity().startTime();
		}
	}

	/**
	 * 用来判断游戏是否成功
	 */
	private void checkSuccess() {
		boolean isSuccess = true;
		for (int i = 0; i < mGamePintuItems.length; i++) {
			ImageView first = mGamePintuItems[i];
			Log.e("TAG", getIndexByTag((String) first.getTag()) + "");
			if (getIndexByTag((String) first.getTag()) != i) {
				isSuccess = false;
			}
		}

		if (isSuccess) {
			Toast.makeText(getContext(), "Success , Level Up !", Toast.LENGTH_SHORT).show();
			nextLevel();
			JigsawPuzzleActivity.getJPActivity().stopTime();
		}
	}

	/**
	 * 获得图片的真正索引
	 * 
	 * @param tag
	 * @return
	 */
	private int getIndexByTag(String tag) {
		String[] split = tag.split("_");
		return Integer.parseInt(split[1]);
	}

	public void nextLevel() {
		this.removeAllViews();
		mAnimLayout = null;
		if (mColumn == 9)
			mColumn = 3;
		else
			mColumn++;
		initBitmap();
		initItem();
	}

	public void restart() {
		this.removeAllViews();
		mAnimLayout = null;
		initBitmap();
		initItem();
	}

	/**
	 * 动画运行的标志位
	 */
	private boolean isAniming;
	/**
	 * 动画层
	 */
	private RelativeLayout mAnimLayout;

	/**
	 * 交换两个Item图片
	 */
	private void exchangeView() {
		mFirst.setColorFilter(null);
		setUpAnimLayout();
		// 添加FirstView
		ImageView first = new ImageView(getContext());
		first.setImageBitmap(mItemBitmaps.get(getImageIndexByTag((String) mFirst.getTag())).bitmap);
		LayoutParams lp = new LayoutParams(mItemWidth, mItemWidth);
		lp.leftMargin = mFirst.getLeft() - mPadding;
		lp.topMargin = mFirst.getTop() - mPadding;
		first.setLayoutParams(lp);
		mAnimLayout.addView(first);
		// 添加SecondView
		ImageView second = new ImageView(getContext());
		second.setImageBitmap(mItemBitmaps.get(getImageIndexByTag((String) mSecond.getTag())).bitmap);
		LayoutParams lp2 = new LayoutParams(mItemWidth, mItemWidth);
		lp2.leftMargin = mSecond.getLeft() - mPadding;
		lp2.topMargin = mSecond.getTop() - mPadding;
		second.setLayoutParams(lp2);
		mAnimLayout.addView(second);

		// 设置动画
		TranslateAnimation anim = new TranslateAnimation(0, mSecond.getLeft() - mFirst.getLeft(), 0,
				mSecond.getTop() - mFirst.getTop());
		anim.setDuration(300);
		anim.setFillAfter(true);
		first.startAnimation(anim);

		TranslateAnimation animSecond = new TranslateAnimation(0, mFirst.getLeft() - mSecond.getLeft(), 0,
				mFirst.getTop() - mSecond.getTop());
		animSecond.setDuration(300);
		animSecond.setFillAfter(true);
		second.startAnimation(animSecond);
		// 添加动画监听
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				isAniming = true;
				mFirst.setVisibility(INVISIBLE);
				mSecond.setVisibility(INVISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				String firstTag = (String) mFirst.getTag();
				String secondTag = (String) mSecond.getTag();

				String[] firstParams = firstTag.split("_");
				String[] secondParams = secondTag.split("_");

				mFirst.setImageBitmap(mItemBitmaps.get(Integer.parseInt(secondParams[0])).bitmap);
				mSecond.setImageBitmap(mItemBitmaps.get(Integer.parseInt(firstParams[0])).bitmap);

				mFirst.setTag(secondTag);
				mSecond.setTag(firstTag);
				mFirst.setVisibility(VISIBLE);
				mSecond.setVisibility(VISIBLE);
				mFirst = mSecond = null;
				mAnimLayout.removeAllViews();
				checkSuccess();
				isAniming = false;
			}
		});

	}

	/**
	 * 创建动画层
	 */
	private void setUpAnimLayout() {
		if (mAnimLayout == null) {
			mAnimLayout = new RelativeLayout(getContext());
			addView(mAnimLayout);
		}

	}

	private int getImageIndexByTag(String tag) {
		String[] split = tag.split("_");
		return Integer.parseInt(split[0]);

	}

}
