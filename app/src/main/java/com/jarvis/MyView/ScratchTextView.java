package com.jarvis.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class ScratchTextView extends TextView {

	private float TOUCH_TOLERANCE;
	private Bitmap mBitmap;//
	private Canvas mCanvas;//
	private Paint mPaint;//
	private Path mPath;//
	private float mX, mY;
	private boolean isInited = false;

	public ScratchTextView(Context context) {
		super(context);

	}

	public ScratchTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScratchTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isInited) {
			mCanvas.drawPath(mPath, mPaint);
			canvas.drawBitmap(mBitmap, 0, 0, null);//
		}
	}

	public void initScratchCard(final int bgColor, final int paintStrokeWidth, float touchTolerance) {
		TOUCH_TOLERANCE = touchTolerance;
		mPaint = new Paint();

		mPaint.setAlpha(240);//
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//
		mPaint.setAntiAlias(true);//
		mPaint.setDither(true);//
		mPaint.setStyle(Paint.Style.STROKE);//
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);//
		mPaint.setStrokeWidth(paintStrokeWidth);//
		mPath = new Path();

		mBitmap = Bitmap.createBitmap(getLayoutParams().width, getLayoutParams().height, Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		//Ϊ���ͼƬ������ɫ
		Paint paint=new Paint();
		paint.setColor(Color.parseColor("#A79F9F"));
		paint.setTextSize(30);
		mCanvas.drawColor(bgColor);
		mCanvas.drawText("�ο���ͼ��",getLayoutParams().width/ 4, getLayoutParams().height/2+15, paint);
		isInited = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isInited) {
			return true;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchDown(event.getX(), event.getY());
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touchMove(event.getX(), event.getY());
			invalidate();
			break;
		}
		return true;
	}

	private void touchDown(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
	}

	private void touchMove(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}

	}

}
