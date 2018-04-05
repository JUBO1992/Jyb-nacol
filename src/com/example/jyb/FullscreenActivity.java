package com.example.jyb;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.jyb.TypeTextView.OnTypeViewListener;
import com.example.partical.LightFragment;
import com.example.partical.MusicFragment;
import com.example.partical.RainFragment;
import com.example.partical.SnowFragment;
import com.example.partical.StarFragment;

public class FullscreenActivity extends Activity {

	public static FullscreenActivity instance = null;
	public boolean musicFlag = false;// 默认不开启背景音乐
	private long firstTime = 0;

	FrameLayout container;
	LightFragment lightFM;
	MusicFragment musicFM;
	RainFragment rainFM;
	SnowFragment snowFM;
	StarFragment starFM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen);
		instance = this;

		container = (FrameLayout) findViewById(R.id.container);
		container.setOnClickListener(ttvClickListener);
		container.setOnTouchListener(ttvTouchListener);

		findViewById(R.id.about_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.favorite_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.setting_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.switchText_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.switchBG_btn).setOnClickListener(btnClickListener);
		findTypeTextViews();

		getFragmentManager().beginTransaction().replace(R.id.container, new StarFragment()).commit();
	}

	public void startMusic() {
		if (musicFlag) {
			startService(new Intent(FullscreenActivity.this, MusicServer.class));
		}
	}

	public void stopMusic() {
		stopService(new Intent(FullscreenActivity.this, MusicServer.class));
	}

	@Override
	protected void onStart() {
		super.onStart();
		startMusic();
	};

	@Override
	protected void onPause() {
		super.onPause();
		findViewById(R.id.about_btn).setVisibility(View.INVISIBLE);
		findViewById(R.id.favorite_btn).setVisibility(View.INVISIBLE);
		findViewById(R.id.setting_btn).setVisibility(View.INVISIBLE);
		findViewById(R.id.switchText_btn).setVisibility(View.INVISIBLE);
		findViewById(R.id.switchBG_btn).setVisibility(View.INVISIBLE);
	};

	@Override
	protected void onResume() {
		super.onResume();
		findViewById(R.id.about_btn).setVisibility(View.VISIBLE);
		findViewById(R.id.favorite_btn).setVisibility(View.VISIBLE);
		findViewById(R.id.setting_btn).setVisibility(View.VISIBLE);
		findViewById(R.id.switchText_btn).setVisibility(View.VISIBLE);
		findViewById(R.id.switchBG_btn).setVisibility(View.VISIBLE);
	};

	@Override
	protected void onStop() {
		super.onStop();
		stopMusic();
	}

	@Override
	public void finish() {
		super.finish();
	}

	private long clickTimes1 = 0;// 字符切换按钮点击次数
	private long clickTimes2 = 1;// 背景切换按钮点击次数
	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.about_btn:
				startActivity(new Intent(FullscreenActivity.this, AboutActivity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
				break;
			case R.id.favorite_btn:
				startActivity(new Intent(FullscreenActivity.this, FavouriteActivity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
				break;
			case R.id.setting_btn:
				startActivity(new Intent(FullscreenActivity.this, SettingActivity.class));
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
				break;
			case R.id.switchText_btn:
				startTextView(clickTimes1);
				clickTimes1++;
				break;
			case R.id.switchBG_btn:
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				if (clickTimes2 % 5 == 0) {
					if (starFM == null) {
						starFM = new StarFragment();
					}
					fragmentTransaction.replace(R.id.container, starFM);
				}
				if (clickTimes2 % 5 == 1) {
					if (rainFM == null) {
						rainFM = new RainFragment();
					}
					fragmentTransaction.replace(R.id.container, rainFM);
				}
				if (clickTimes2 % 5 == 2) {
					if (snowFM == null) {
						snowFM = new SnowFragment();
					}
					fragmentTransaction.replace(R.id.container, snowFM);
				}
				if (clickTimes2 % 5 == 3) {
					if (musicFM == null) {
						musicFM = new MusicFragment();
					}
					fragmentTransaction.replace(R.id.container, musicFM);
				}
				if (clickTimes2 % 5 == 4) {
					if (lightFM == null) {
						lightFM = new LightFragment();
					}
					fragmentTransaction.replace(R.id.container, lightFM);
				}
				fragmentTransaction.commit();
				clickTimes2++;
				break;
			default:
				break;
			}
		}
	};

	/*
	 * 监听返回事件
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {
				Toast.makeText(FullscreenActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				firstTime = secondTime;
				return true;
			} else {
				finish();
				overridePendingTransition(R.anim.ap2, R.anim.ap1);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private TypeTextView mTypeTextView = null;
	private String earth = "" //
			+ ",,,,,,\n" //
			+ "o#'9MMHb':'-,o,\n" //
			+ ".oH\":HH$' \"' ' -*R&o,\n" //
			+ "dMMM*\"\"'`'      .oM\"HM?.\n" //
			+ ",MMM'          \"HLbd< ?&H\\\n" //
			+ ".:MH .\"\\          ` MM  MM&b\n" //
			+ ". \"*H    -        &MMMMMMMMMH:\n" //
			+ ".    dboo        MMMMMMMMMMMM.\n" //
			+ ".   dMMMMMMb      *MMMMMMMMMP.\n" //
			+ ".    MMMMMMMP        *MMMMMP .\n" //
			+ "    `#MMMMM           MM6P ,\n" //
			+ "'    `MMMP\"           HM*`,\n" //
			+ "'    :MM             .- ,\n" //
			+ "'.   `#?..  .       ..'\n" //
			+ "-.   .         .-\n" //
			+ "''-.oo,oo.-''"; //
	private String ilikeu = "" //
			+ "  j&=    y+ y*     jv+   yy-v    v &   \n" //
			+ " wE!\"    j17$T    7MPC   NU$E-   Ej&v- \n" //
			+ " O*K^   yHH:Ovm+  UMMk   BMnTO: H1=\"7' \n" //
			+ "jO&OH:  \"OH7\"E~   UOH1   BB71' jCf'U:  \n" //
			+ "vM1H1    jB-j1   wHhHh*-/$B)B-    BkJUk\n" //
			+ "^HI'OH  j\"\"N1   \"OHOK~  H$H\"Da  jP'N^  \n" //
			+ " \"'  OI     \"     jvHT   T ~ \"\"     \"  ";
	private String heart = "" //
			+ "******         ******\n" //
			+ "**********     **********\n" //
			+ "************** **************\n" //
			+ "*******************************\n" //
			+ "*******************************\n" //
			+ "*******************************\n" //
			+ "*****************************\n" //
			+ "*************************\n" //
			+ "*********************\n" //
			+ "***************\n" //
			+ "***********\n" //
			+ "*******\n" //
			+ "***\n" //
			+ "*"; //

	private String jyb = "" //
			+ "                                                           . , ,.:.,                                                    \n" //
			+ "                                                    ::vvUYULUYuL1u0XZUL:,                                               \n" //
			+ "                                                 :iYjSUFU5uSu2Luvjvu1ZXX7i.                                             \n" //
			+ "                                              .,7v22kU5u1j1uUL2LuvLiLUSFEuL:.                                           \n" //
			+ "                                             i75uSUFj1u5j1j2Y1j2JUrrrFuUUN2ur:                                          \n" //
			+ "                                           ,LuX1k1kU1jkuFUFJ1USu1Lj7LuSuuuqUFL7,.                                       \n" //
			+ "                                          ,J1N1P5kUFuk2F2kUFUkj5uF2jvuUX11YPu17r..                                      \n" //
			+ "                                         .v2P5PFXUF2X1SUk2P5XUSUS15LuLFuP52JSvJr;.                                      \n" //
			+ "                                        ,L1N5NkP1F1E5q1P10kP5X2P5u7u7YLFuPS1uFJ5rr                                      \n" //
			+ "                                       :jSGSNkE5SFESEk0X8S5YX10FJiv77rLv5Jq51j1ju;:                                     \n" //
			+ "                                       ruOXESZFPSGPZXZP812YPXquv:r:ri7;LJFJXuUuSUY:                                     \n" //
			+ "                                      .iSXEqZSqPGPGXOPPJU5GNS7r:i:i:ri7r225uquFuFj7.                                    \n" //
			+ "                                      ,vuPq8F0qONMZ852JSNO2Yii::,:,i:7iLvqFSuP2qjNJr                                    \n" //
			+ "                                     .7vkPOSGGMZOS5L25OP2ri,:.:,::i:::7rvUZkFUPkX5qYi                                   \n" //
			+ "                                     i75XOS88MkFY1UXU27;:i:ii77Li7:;:ii7iLuEPFUZ5kSPv:                                  \n" //
			+ "                                    ,ru5GX8MBZE2277:i:;i77LvJ;r:i:::i:::ri7jGFkPE2q12:                                  \n" //
			+ "                                    :JUEXOEELvrLr7:r:i:rivr7rvrL7Yr7ri.,,7jGO82ENX5SJ7                                  \n" //
			+ "                                   .7uENOGZuUvuJjvL:i,iiL7u1XXBZ0217jqPPPM@0O8P28FXUur.                                 \n" //
			+ "                                   i7qZGkBOMZPq8P2YEqFXZ7YvF7vr7i;ir:OM877:iuBSFqNuSJv.                                 \n" //
			+ "                                  .:5qOLjMB1YrvrLirGZiLkJY7:r:i:i:::71i ..::uOM2OXSu27:                                 \n" //
			+ "                                   r5887:Uj7:i:i:ivZ:..iLui;:i:i:7;v7i ..:,iLMGkPZuSLY,                                 \n" //
			+ "                                  .;Pq@Oqu5vLrL7YvY::.:.:ivvLrvr7ir::.,.:.::UOM2XkSU2vi                                 \n" //
			+ "                                   L1OM@Xuiriri7rr,:,:.:...ii;::,:,:.:.:,:,iuBO0182XU1i.                                \n" //
			+ "                                  ,rSE@G1:i,:,iii.:::::,i:::7i7:i:i:i:i::,::2MMqXEP2PUv,                                \n" //
			+ "                                  .71MM@17:;:777;U22vY75kqYvirivirir:i:i::,:L@GO1ZXkuSvi                                \n" //
			+ "                                  .rqE@B8YvrLLurrrY7Y7Lrr:i:i:rruvvi;:i::,:.UMONqkE2PLJi.                               \n" //
			+ "                                  .JPOEBMELvLXJL:i:i:i:iir:i:ii2u1JLir:i:i,:X@ZOS0kkkFL7.                               \n" //
			+ "                                  ,Y8qOG@E5iLYSFOY7Lj7uvuvLr7vPkYr7;vir:i::r@GON0X0u051r:                               \n" //
			+ "                                  .2qENMOMkuir72k0UFii:i:iiJv2ii:ri7;7:i:::MM8EOkGk1SNuJ:.                              \n" //
			+ "                                  ,jGkGEMMM5ji7r7vurr:i:ri;:i,::i:rir:;:::8MME8NENZjqSkJ7.                              \n" //
			+ "                                  ,S0G0GEMBMUjrvrLiri7;7i7ir:i:i:;:;ir:irMMB0OqMP8Pk1PU57;                              \n" //
			+ "                                  i2M08POZMB@qu;L7Lr7rLr7;r:i:i:i:i:i:r2@BB0O0ZZOXG10SSu57:                             \n" //
			+ "                                 .iPZME0PMZ@M@MSrvrvirir:r:r:;:i:i:iiuN@MB880MXOZ0PXk015US7:                            \n" //
			+ "                                  r5M8Ok8ZM8BM@BBLririi:i:i:i:::i:7LNEMEB8ONMZZEMSNUq1PUS21r,                           \n" //
			+ "                                  iqZBEZX8ZMO@M@B@qYii:i:i:i:rivvF5P50XO8Mq8OB0OEN5F5XU2JS1u:.                          \n" //
			+ "                                  7PMOOqZqM8@OBM@M@MM21LuLjv2uk1P2Fuk50EB0GEB88GOX02X5qJr:jYY:                          \n" //
			+ "                                 ,v88M0MN8GB8BOBM@M@M@M@M@ZkuFUFj1Y2u0EBG80MOMNONEXqUkS2r772Yv:                         \n" //
			+ "                                 :50M0MZO0MOMO@OBM@M@M@M@B@FULuLULuJNEB8OXOZBGGN8XE5X1P2P5Sju77,.                       \n" //
			+ "                                 rUM8OZMZO8B8MMBOBO@MBM@M@BB5ULUYUY0EOOMEZEM8OP8kEFq1qk0SNU2L1LYi:..                    \n" //
			+ "                                 :FGMZBZMZBOM8@OBM@MBO@M@M@MMFSYUJXPG0MZOSOEME8PGPZkS20P8PXUSuX5NFkLLi:                 \n" //
			+ "                                 :YMGB8MEMOM8MOBO@M@MBM@M@M@G0U1L5SEXOGMEPqOGOqEXEqGS0kGEGXZS0SSLvi;:i:i..              \n" //
			+ "                    . . .        ,1ZMOBGMO@OBG@OBM@MBO@M@MBOMFX221X20ZMEMFEZMZ8PGSGNO00qGkG0ONJ:::i:i,:.,..             \n" //
			+ "  .:::,:::,::77r:rivrvrL77::     rFMGBGMGBOB8MOBO@O@O@M@MB8MPFuP5kJXXMGM8GFOGOq8qGN8EMq8qZXGPU;7i7iri7i;::..            \n" //
			+ "  ..iir:rr7:rv8ZGXGEGF2vY7Lr;,i:L1MZMOMZMOBOMZ@OBOBMBM@M@OO5u7FSSLFkOGMZ@EEPME8kGqM0OZOPZXZESr7;7;7;r:r:ri:..           \n" //
			+ "     ::vr7v2rriL7YLUuu7LrL7YvJu00MOMGMZMOB8BZMMBO@OBM@M@MBNU;USq7u58ZOZMOMPZ0O0ZqOGOEOqGqOZq;vrLr7irir:i:i:i.,          \n" //
			+ "       .i:rr1uLirir:i,riLrv7YvkPBOBZMGMGBGBOMO@OBM@OBM@M@8M5jj1LYLPEOGBGMGZEM0OPZNM0M0M0G0MYvrY7Lr7irir:i:i::.,         \n" //
			+ "         . ,.rrY7Yrvrriririvrv7X8MEOGMGB8MOM8BOBOBOBO@MBOMOMSU7vv5PM8@OM8MkMZM08XOGOEG0OPMqL7j7LrL77:i:rir:;:i.,        \n" //
			+ "                ::;rYY27vi7rLrvXMZMEMGMOBOB8BO@OBOBO@M@MBOOqON05GO@ZO8M8M8EEMEO0GNME8XGG8Oq71jJ7YLU7r:ri;ir:i:i..       \n" //
			+ "                     i;LLJ7YvYu0ZMGMZMGMOM8@OBO@O@OBM@O@GM08k0POEM8OEBGMGMkOEOPOPOGMEZ0BGB5UJuYuLUYjrr:i:r:r:;::..      \n" //
			+ "                       .iiLvJJE58ZMGMEMOBO@M@O@8BO@M@OBO8qES0F0qOG8EMZOZMGZqO8GZG0BGOqO8MM0U2u2LUY2vLrr:i:;irir::..     \n" //
			+ "                         .:v7q2jFOZMZOOB8BM@M@OBOBO@MBOMkOXNFPSEP80MG8NM0MXZ0MNMNO8MNGqBOBPSLku1Y1JJ7Lir:i:i:rir::.     \n" //
			+ "                           ,7Yvi1SM8M0BOBOBM@8MOBMB8B8BNEXEkXFEk8EM8O0ME80NXOZOE8EMZ8EG8@OMUU2kY1uuvL77ir:i:::rir::     \n" //
			+ "                            ,7ii7uZBOM8B8BO@8MOB8BGMZM8O1X5NFNSE0OGMNOEMGZ50EM0MEO88qONMOBMXLq12u5vJvYr7ir:i:::i:r::    \n" //
			+ "                            .i;:rr8OBGBOMMBGM8MGBGMEMGOSFuqXE1Xk8EB0OEOZB0NkM8M8MqM0OEOZBO@SUFXu2j2vJ7Lr7ir:iir:iir:: . \n" //
			+ "                             iiLLuk@8OGM8BGBOMOBGOEOEOXX2XFEkP1NXMZ80O0M8OPZGMZMG8ZMO@OMO@MOJE52jFLJ7L7L7ri;:i:i,::r::::\n" //
			+ "                              iir:LMMPM8@8O8BOMGO0ONZkXUFuXFN5NSO8O0O0MZB0GNBGOZMEOGOqG2qM@05kPu5JULJvY7virii:riririr:ri\n" //
			+ "                             .,;ii;@GEOBMM0MOMEM0ES0FSUku52qk0kGZM0M0OEO88P88MEM8OF1YkFN5BM8uNU5uULUYU7v7v7L7L77ir::,::r\n" //
			+ "                              ::r:rNBFMMB0OGMZONOSq5kj1u2jSS808POEOEG0O0MN80MZM8M8MS8q8PPF@ZSSkU5L2u2vL7L;7ir:i,:,:.:.,,\n" //
			+ "                              .iir:SOkZBEGNMZMXEPqU1J5j1jS2EXGN8qZqM0OqOZ8NMGMEBGMMEU1j57L0@UP21YFU2LJiri;:;:i::::,:,:.,"; //
	private String batman = "" //
			+ "       _==/          i     i          \\==_       \n" //
			+ "     /XX/            |\\___/|            \\XX\\     \n" //
			+ "   /XXXX\\            |XXXXX|            /XXXX\\   \n" //
			+ "  |XXXXXX\\_         _XXXXXXX_         _/XXXXXX|  \n" //
			+ " XXXXXXXXXXXxxxxxxxXXXXXXXXXXXxxxxxxxXXXXXXXXXXX \n" //
			+ "|XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX|\n" //
			+ "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n" //
			+ "|XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX|\n" //
			+ " XXXXXX/^^^^\"\\XXXXXXXXXXXXXXXXXXXXX/^^^^^\\XXXXXX \n" //
			+ "  |XXX|       \\XXX/^^\\XXXXX/^^\\XXX/       |XXX|  \n" //
			+ "    \\XX\\       \\X/    \\XXX/    \\X/       /XX/    \n" //
			+ "       \"\\       \"      \\X/      \"      /\"        "; //


	public void findTypeTextViews() {
		mTypeTextView = (TypeTextView) findViewById(R.id.typeTxtId);
		mTypeTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
		AssetManager assets = getAssets();
		Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/consola.ttf");
		mTypeTextView.setTypeface(fromAsset);

		mTypeTextView.setOnTypeViewListener(new OnTypeViewListener() {
			@Override
			public void onTypeStart() {
				System.out.println("onTypeStart");
			}

			@Override
			public void onTypeOver() {
				System.out.println("onTypeOver");
			}
		});
	}

	View.OnClickListener ttvClickListener = new View.OnClickListener() {
		long[] doubleHints = new long[2];
		long[] threeHints = new long[3];

		@Override
		public void onClick(View v) {
			// 将数组内的所有元素左移一个位置
			System.arraycopy(doubleHints, 1, doubleHints, 0, doubleHints.length - 1);
			System.arraycopy(threeHints, 1, threeHints, 0, threeHints.length - 1);
			// 获得当前系统已经启动的时间
			doubleHints[doubleHints.length - 1] = SystemClock.uptimeMillis();
			threeHints[threeHints.length - 1] = SystemClock.uptimeMillis();
			if (SystemClock.uptimeMillis() - threeHints[0] <= 700) {// 三连击

			} else if (SystemClock.uptimeMillis() - doubleHints[0] <= 500) {// 双击
				startTextView(clickTimes1);
				clickTimes1++;
			}
		}
	};

	private float startX, startY, offsetX, offsetY;

	View.OnTouchListener ttvTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			// TODO Auto-generated method stub
			// 继承了Activity的onTouchEvent方法，直接监听点击事件
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// 当手指按下的时候
				startX = event.getX();
				startY = event.getY();
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				// 当手指离开的时候
				offsetX = Math.abs(event.getX() - startX);
				offsetY = Math.abs(event.getY() - startY);
				if (offsetX >= 150 && offsetY >= 150) {
					mTypeTextView.finish();
				}
			}
			return false;
		}
	};

	private void startTextView(long times) {
		mTypeTextView.setGravity(Gravity.CENTER);
		if (times % 4 == 0) {
			mTypeTextView.setBackgroundColor(0x00ffffff);
			mTypeTextView.setTextColor(0xffffffff);
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
			mTypeTextView.start(earth);
		} 
		else if (times % 4 == 1) {
			mTypeTextView.setBackgroundColor(0x00ffffff);
			mTypeTextView.setTextColor(0xffd81e06);
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			mTypeTextView.start(ilikeu);
		} 
		else if (times % 4 == 2) {
			mTypeTextView.setBackgroundColor(0x00ffffff);
			mTypeTextView.setTextColor(0xffd81e06);
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
			mTypeTextView.start(heart);
		} 
		else if (times % 4 == 3) {
			mTypeTextView.setBackgroundResource(R.drawable.bg_asciiart);
			mTypeTextView.setTextColor(0xff000000);
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, (float) 4.6);
			mTypeTextView.start(jyb);
		} 
//		else if (times % 4 == 4) {
//			mTypeTextView.setGravity(Gravity.LEFT);
//			mTypeTextView.setBackgroundColor(0x00ffffff);
//			mTypeTextView.setTextColor(0xffffffff);
//			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//			mTypeTextView.start(something);
//		}
	}

}
