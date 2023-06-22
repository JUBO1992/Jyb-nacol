package com.example.jyb;

import com.example.jyb.TypeTextView.OnTypeViewListener;
import com.example.partical.StarFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class AsciiArt2Activity extends Activity {
	StarFragment starFM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asciiart2);
		getFragmentManager().beginTransaction().replace(R.id.container, new StarFragment()).commit();

		findViewById(R.id.finishType_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.restartType_btn).setOnClickListener(btnClickListener);
		findViewById(R.id.switchType_btn).setOnClickListener(btnClickListener);
		findTypeTextViews();
		startTextView(clickTimes1++);
	}

	View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.finishType_btn:
				mTypeTextView.finish();
				break;
			case R.id.restartType_btn:
				mTypeTextView.start();
				break;
			case R.id.switchType_btn:
				// startTextView(clickTimes1++);
				ShowChoise();
				break;
			default:
				break;
			}
		}
	};

	private void closeActivity() {
		finish();
		overridePendingTransition(R.anim.ap2, R.anim.ap1);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			closeActivity();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void ShowChoise() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog);
		builder.setIcon(R.drawable.ic_launcher_ice);
		builder.setTitle("Choose a item!");
		// 指定下拉列表的显示数据
		final String[] asciis = { "鼠", "牛", "猫", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };
		// 设置一个下拉的列表选择项
		builder.setItems(asciis, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				clickTimes1 = which;
				startTextView(clickTimes1++);
			}
		});
		builder.show();
	}

	private long clickTimes1 = 0;// 字符切换按钮点击次数
	private TypeTextView mTypeTextView = null;
	private String mouse = "" //
			+ " .--,       .--,         \n" //
			+ "( (  \\.---./  ) )        \n" //
			+ " '.__/o   o\\__.'         \n" //
			+ "    {=  ^  =}            \n" //
			+ "     >  -  <             \n" //
			+ "    /       \\            \n" //
			+ "   //       \\\\           \n" //
			+ "  //|   .   |\\\\          \n" //
			+ "  \"'\\       /'\"_.-~^`'-. \n" //
			+ "     \\  _  /--'         `\n" //
			+ "   ___)( )(___           \n" //
			+ "  (((__) (__)))          "; //
	private String moocow = "" //
			+ "  /~~~~~\\        /~~~~~\\ \n" // 前后各删除10个空格
			+ " |    (~'        ~~~)   |\n" //
			+ "  \\    \\__________/    / \n" //
			+ "  /~::::::::         ~\\  \n" //
			+ " /~~~~~~~-_| ::::::::             |_-~~~~~~~\\\n" //
			+ "\\ ======= /|  ::A::;      A     :|\\ ====== / \n" //
			+ " ~-_____-~ |  _----------------_::| ~-____-~ \n" //
			+ " |/~                  ~\\|\n" //
			+ " /                      \\\n" //
			+ " (        ()    ()        )\n" //
			+ " `\\                   ./'\n" //
			+ "   ~-_______________-~   "; //
	private String cat = "" //
			+ "               ,   \n" //
			+ "             _/((  \n" //
			+ "    _.---. .'   `\\  \n" //
			+ "  .'      `     ^ T=\n" //
			+ " /     \\       .--' \n" //
			+ "|      /       )'-. \n" //
			+ "; ,   <__..-(   '-.)\n" //
			+ " \\ \\-.__)    ``--._)\n" //
			+ "  '.'-.__.-.        \n" //
			+ "    '-...-'         "; //
	private String rabbit = "" //
			+ "      ,-.,-.                  \n" //
			+ "     (  (  (                  \n" //
			+ "      \\  )  ) _..-.._         \n" //
			+ "     __)/ ,','       `.       \n" //
			+ "   ,\"     `.     ,--.  `.     \n" //
			+ " ,\"   @        .'    `   \\    \n" //
			+ "(Y            (           ;''.\n" //
			+ " `--.____,     \\          ,  ;\n" //
			+ " ((_ ,----' ,---'      _,'_,' \n" //
			+ "     (((_,- (((______,-'      "; //
	private String dragon = "" //
			+ "         4&(                                       \n" //
			+ "       ` ~&&\\yM#1                                  \n" //
			+ "        ,_'Q!!NMW&                                 \n" //
			+ "        WCb 7N@4D Q%,,                             \n" //
			+ "        PM'*MDk#M0p,                               \n" //
			+ "            ]@J0&e~~4r' ,+bQEQ                     \n" //
			+ "             F8I&#'   _&B$$bW#&$                   \n" //
			+ "              &0A1   L#DE&E~!Q&Q,                  \n" //
			+ " _=,        ,#0RN1  _T@0$'   ZN$Q.   grNq5         \n" //
			+ " ^ 'd     ,0K0pK^  g*Q0g'    #Q4p&,/g9X*&#,_/ (q   \n" //
			+ "  TA1   ,sDQWh4^  x&NM0` _   #FQ#K#fA#   `*K#XWP~- \n" //
			+ "   ^&p,wNMM0qD: /HE#EN' ..#g)~ '@NG0Qx,    `=X*    \n" //
			+ "  '  '43$'hEk##m0D04f_g  ~^ ~   `-00**0            \n" //
			+ "           =0#ONq2W0BF^#, _            p,,         \n" //
			+ "             `  ^''~    ~b''        **R3`          \n" //
			+ "                      ow,F         +#F~'           \n" //
			+ "                      /-9!          ` \\            \n" //
			+ "                       R                           "; //
	private String snake = "" //
			+ "           /^\\/^\\                                     \n" //
			+ "         _|__|  O|                                    \n" //
			+ "\\/     /~     \\_/ \\                                   \n" //
			+ " \\____|__________/  \\                                 \n" //
			+ "        \\_______      \\                               \n" //
			+ "               `\\     \\                 \\             \n" //
			+ "                  |     |                  \\          \n" //
			+ "                 /      /                    \\        \n" //
			+ "                /     /                       \\\\      \n" //
			+ "              /      /                         \\ \\    \n" //
			+ "             /     /                            \\  \\  \n" //
			+ "           /     /             _----_            \\   \\\n" //
			+ "          /     /           _-~      ~-_         |   |\n" //
			+ "         (      (        _-~    _--_    ~-_     _/   |\n" //
			+ "          \\      ~-____-~    _-~    ~-_    ~-_-~    / \n" //
			+ "            ~-_           _-~          ~-_       _-~  \n" //
			+ "               ~--______-~                ~-___-~     "; //
	private String horse = "" //
			+ "                          _(\\_/)     \n" //
			+ "                        ,((((^`\\     \n" //
			+ "                       ((((  (6 \\    \n" //
			+ "                     ,((((( ,    \\   \n" //
			+ "  ,,,_              ,(((((  /\"._ ,`, \n" //
			+ " ((((\\ ,...       ,((((   /    `-.-'\n" //
			+ " )))  ;'    `\"'\"'\"\"((((   (          \n" //
			+ "(((  /            (((      \\         \n" //
			+ " )) |                      |         \n" //
			+ "((  |        .       '     |         \n" //
			+ "))  \\     _ '      `t   ,.')         \n" //
			+ "(   |   y;- -,-\"\"'\"-.\\   \\/          \n" //
			+ ")   / ./  ) /         `\\  \\          \n" //
			+ "   |./   ( (           / /'          \n" //
			+ "   ||     \\\\          //'|           \n" //
			+ "   ||      \\\\       _//'||           \n" //
			+ "   ||       ))     |_/  ||           \n" //
			+ "   \\_\\     |_/          ||           \n" //
			+ "   `'\"                  \\_\\          \n" //
			+ "                        `'\"          "; //
	private String sheep = "" //
			+ "                 _,._   \n" // 0
			+ "             __.'   _)  \n" // 0
			+ "            <_,)'.-\"a\\  \n" // 2
			+ "              /' (    \\ \n" // 1
			+ "  _.-----..,-'   (`\"--^ \n" // 1
			+ " //              |      \n" // 0
			+ "(|   `;      ,   |      \n" // 0
			+ "  \\   ;.----/  ,/       \n" // 1
			+ "   ) // /   | |\\ \\      \n" // 2
			+ "   \\ \\\\`\\   | |/ /      \n" // 4
			+ "    \\ \\\\ \\  | |\\/       \n" // 5
			+ "     `\" `\"  `\"`         "; // 3
	private String monkey = "" //
			+ "                __------__         \n"//
			+ "              /~          ~\\       \n"//
			+ "             |    //^\\\\//^\\|       \n"//
			+ "           /~~\\  ||  o| |o|:~\\     \n"//
			+ "          | |6   ||___|_|_||:|     \n"//
			+ "           \\__.  /      o  \\/'     \n"//
			+ "            |   (       O   )      \n"//
			+ "   /~~~~\\    `\\  \\         /       \n"//
			+ "  | |~~\\ |     )  ~------~`\\       \n"//
			+ " /' |  | |   /     ____ /~~~)\\     \n"//
			+ "(_/'   | | |     /'    |    ( |    \n"//
			+ "       | | |     \\    /   __)/ \\   \n"//
			+ "       \\  \\ \\      \\/    /' \\   `\\ \n"//
			+ "         \\  \\|\\        /   | |\\___|\n"//
			+ "           \\ |  \\____/     | |     \n"//
			+ "           /^~>  \\        _/ <     \n"//
			+ "          |  |         \\       \\   \n"//
			+ "          |  | \\        \\        \\ \n"//
			+ "          -^-\\  \\       |        ) \n"//
			+ "               `\\_______/^\\______/ ";//
	private String chick = "" //
			+ "     .=\"\"=.                          \n" //
			+ "    / _  _ \\                         \n" //
			+ "   |  d  b  |                        \n" //
			+ "   \\   /\\   /             ,          \n" //
			+ "  ,/'-=\\/=-'\\,    |\\   /\\/ \\/|   ,_  \n" //
			+ " / /        \\ \\   ; \\/`     '; , \\_',\n" //
			+ "| /          \\ |   \\        /        \n" //
			+ "\\/ \\        / \\/    '.    .'    /`.  \n" //
			+ "    '.    .'          `~~` , /\\ `\"`  \n" //
			+ "    _|`~~`|_              .  `\"      \n" //
			+ "    /|\\  /|\\                         "; //
	private String dog = "" //
			+ "          .----.     \n" //
			+ "       _.'__    `.   \n" //
			+ "   .--(#)(##)---/#\\  \n"//
			+ " .' @          /###\\ \n" //
			+ " :         ,   ##### \n" //
			+ "  `-..__.-' _.-\\###/ \n" //
			+ "        `;_:    `\"'  \n" //
			+ "      .'\"\"\"\"\"`.      \n" //
			+ "     /,       ,\\     \n" //
			+ "    //         \\\\    \n" //
			+ "    `-._______.-'    \n" //
			+ "    ___`. | .'___    \n"//
			+ "   (______|______)   \n\n" //
			+ "             .-._    \n" //
			+ "            {_}^ )o  \n" //
			+ "   {\\________//~`    \n" //
			+ "    (         )      \n" //
			+ "    /||~~~~~||\\      \n" //
			+ "   |_\\\\_    \\\\_\\_    \n" //
			+ "   \"' \"\"'    \"\"'\"'   \n\n"; //
	private String pig = "" //
			+ "╭ ︿︿︿ ╮\n" //
			+ "{/ o  o /}\n" //
			+ "( (oo) )\n" //
			+ "︶︶︶"; //

	public void findTypeTextViews() {
		mTypeTextView = (TypeTextView) findViewById(R.id.typeTxtId);
		AssetManager assets = getAssets();
		Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/consola.ttf");
		mTypeTextView.setTypeface(fromAsset);
		mTypeTextView.setOnClickListener(ttvClickListener);
		mTypeTextView.setOnTouchListener(ttvTouchListener);

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
				startTextView(clickTimes1++);
				// mTypeTextView.finish();
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
		if (times % 12 <= 0) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
			mTypeTextView.start(mouse);
		} else if (times % 12 <= 1) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
			mTypeTextView.start(moocow);
		} else if (times % 12 <= 2) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
			mTypeTextView.start(cat);
		} else if (times % 12 <= 3) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
			mTypeTextView.start(rabbit);
		} else if (times % 12 <= 4) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
			mTypeTextView.start(dragon);
		} else if (times % 12 <= 5) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
			mTypeTextView.start(snake);
		} else if (times % 12 <= 6) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
			mTypeTextView.start(horse);
		} else if (times % 12 <= 7) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
			mTypeTextView.start(sheep);
		} else if (times % 12 <= 8) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
			mTypeTextView.start(monkey);
		} else if (times % 12 <= 9) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
			mTypeTextView.start(chick);
		} else if (times % 12 <= 10) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
			mTypeTextView.start(dog);
		} else if (times % 12 <= 11) {
			mTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
			mTypeTextView.start(pig);
		}
	}
}
