package com.example.jyb;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lz on 2016/7/24.
 * github��ַ��https://github.com/lzuntalented/BackgroundView
 * �Զ��屳����ͼ
 *
 * �ο�canvas�о��ǻ�ͼ����ַ��https://github.com/hustcc/canvas-nest.js
 */
public class BackgroundView extends View implements View.OnTouchListener,GestureDetector.OnGestureListener {

    private List<OnTouchListener> mlist = new ArrayList<>();//�洢��ε���¼�����Ӧ

    private int lineCount = 99;//��Ļ���ֵĵ�����
    private Context mContext;
    private List<LineConfig> random_lines = new ArrayList<>();//��Ļ���ֵĵ㼯��

    private GestureDetector mGestureDetector = null;

    private LineConfig currentDown = new LineConfig();//������

    private int color_point = Color.argb(100, 255, 255, 255);//�����ɫ
    private int color_line = Color.argb(100,255,0,0);//�ߵ���ɫ

    public BackgroundView(Context context) {
        super(context);

        mContext = context;
        init();
    }

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public BackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    /**
     * ��ʼ��
     */
    private void init(){
        currentDown.max = 100000;//�����������������ߵ�������

        /*������Ƽ���*/
        mGestureDetector = new GestureDetector(mContext, this);
        this.setOnTouchListener(this);
        this.setLongClickable(true);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        /*��ʼ���㼯��*/
        for(int i=0; i < lineCount ; ++i){
            LineConfig l = new LineConfig();
            l.x = (float) (Math.random() * width);
            l.y = (float) (Math.random() * height);
            l.xa = (float) (Math.random() * 2 - 1);
            l.ya = (float) (Math.random() * 2 - 1);
            l.max = 15000;

            random_lines.add(l);
        }

        /*��Ӵ����㵽����*/
        random_lines.add(currentDown);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        int canvas_width = wm.getDefaultDisplay().getWidth();
        int canvas_height = wm.getDefaultDisplay().getHeight();

        Paint paint_blue = new Paint();
        paint_blue.setStyle(Paint.Style.STROKE);
        paint_blue.setStrokeWidth(3);


        float d, x_dist, y_dist, dist;
        for(int i = 0; i < random_lines.size() ; i ++ ){
            LineConfig r = random_lines.get(i);
            r.x += r.xa;
            r.y += r.ya; //�ƶ�
            r.xa *= r.x > canvas_width || r.x < 0 ? -1 : 1;
            r.ya *= r.y > canvas_height || r.y < 0 ? -1 : 1; //�����߽磬���򷴵�

            paint_blue.setColor(color_point);
            canvas.drawCircle(r.x, r.y, 1, paint_blue);

            for(int j = i + 1; j < random_lines.size() ; j ++ ){
                LineConfig e = random_lines.get(j);
                x_dist = r.x - e.x; //x�����
                y_dist = r.y - e.y; //y�����
                dist = x_dist * x_dist + y_dist * y_dist; //�ܾ���
                if(dist < e.max){
                    if(e.touch && dist >= e.max / 2){
                        r.x -= 0.03 * x_dist;
                        r.y -= 0.03 * y_dist; //������ʱ�����
                    }

                    paint_blue.setColor(color_line);
                    canvas.drawLine(r.x,r.y,e.x,e.y,paint_blue);
                }
            }
        }

        new DrawThread().start();//������ʱ�̻߳���

    }

    /**
     * ��д���������¼���
     * ����ӵĴ����¼���ӵ�������
     * ��ֹ�ⲿ�Դ���ͼ�ٴ�����¼����´�������Ч
     */
    @Override
    public void setOnTouchListener(OnTouchListener l) {
        mlist.add(l);
        super.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                for (int i = 0; i < mlist.size(); ++i) {
                    OnTouchListener ml = mlist.get(i);
                    if (ml.onTouch(v, event)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);//�ַ�����֪ͨ
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        currentDown.touch = true;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        currentDown.touch = false;
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        currentDown.x = e2.getX();//��¼����������
        currentDown.y = e2.getY();//��¼����������
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    /**
     * ��ʱ֪ͨ�����߳�
     * */
    private class DrawThread extends Thread{

        @Override
        public void run() {
            super.run();
            try {
                sleep(1000 / 45);//ÿ�����45��
                mHandler.sendEmptyMessage(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**��ͼ֪ͨhandler*/
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) draw_canvas();
        }
    };

    /**
     * �ػ���ͼ֪ͨ
     */
    private void draw_canvas() {
        this.invalidate();
    }

    /**
     * ��Ļ�����
     * */
    private class LineConfig{
        public float x;//x���
        public float y;//y���
        public float xa;//x����
        public float ya;//y����
        public float max;//����������룬�����˾��벻�ٻ����߶�
        public boolean touch = false;//��ʾ�Ƿ�Ϊ������

    }

}