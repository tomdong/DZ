package com.intalker.dz.ui;

import java.util.ArrayList;

import com.intalker.dz.R;
import com.intalker.dz.utilities.ChessManager;
import com.intalker.dz.utilities.DensityAdaptor;
import com.intalker.dz.utilities.PositionManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class ChessBoard extends RelativeLayout {

	private int mBoardWidth = 0;
	private int mBoardHeight = 0;

	private Paint mForegroundPaint = null;
	private Rect mBoardBound = null;
	
	private ArrayList<Chess> mALiveChessList = new ArrayList<Chess>();

	public ChessBoard(Context context, int w, int h) {
		super(context);
		mBoardWidth = w;
		mBoardHeight = h;
		initializeParams();
		initializeUI();
	}

	private void initializeParams() {
		int margin = mBoardWidth / 8;
		float cellLength = mBoardWidth / 4;
		mBoardBound = new Rect();
		mBoardBound.set(margin, margin, mBoardWidth - margin, mBoardHeight
				- margin);

		mForegroundPaint = new Paint();
		mForegroundPaint.setColor(Color.WHITE);
		mForegroundPaint.setStyle(Paint.Style.STROKE);
		mForegroundPaint.setStrokeWidth(2.0f);

		PositionManager.getInstance().initialize(margin, cellLength, mBoardWidth, mBoardHeight);
	}

	private void initializeUI() {
		this.setBackgroundResource(R.drawable.grid_bg);

		for (int i = 0; i < 4; ++i) {
			Chess btn = new Chess(this.getContext(), R.drawable.chess_a_normal,
					R.drawable.chess_a_selected);
			this.addView(btn);
			//positionItem(btn, 0, i);
			btn.setPos(0, i);
			mALiveChessList.add(btn);
			ChessManager.getInstance().addToAList(btn);
		}

		for (int i = 0; i < 4; ++i) {
			Chess btn = new Chess(this.getContext(), R.drawable.chess_b_normal,
					R.drawable.chess_b_selected);
			this.addView(btn);
			//positionItem(btn, 3, i);
			btn.setPos(3, i);
			mALiveChessList.add(btn);
			ChessManager.getInstance().addToBList(btn);
		}
		
		this.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					int ix = PositionManager.getInstance().getProperRowColIndex(event.getX(), event.getY());
					//Toast.makeText(v.getContext(), String.valueOf(ix), Toast.LENGTH_SHORT).show();
					Chess lastChess = ChessManager.getInstance().getLastSelectedChess();
					if(null != lastChess)
					{
						if (ix >= 0)
						{
							if(!ChessManager.getInstance().isOccupied(ix) && ChessManager.getInstance().canMoveTo(lastChess, ix))
							{
								lastChess.setIndex(ix);
								ChessManager.getInstance().clearLastSelected();
							}
						}
					}
				}
				return false;
			}
			
		});
	}

//	private void positionItem(View item, int r, int c) {
//		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT);
//		PointF pos = PositionManager.getInstance().getLocation(r, c);
//		int itemWidth = DensityAdaptor.getDensityIndependentValue(48);
//		int left = (int) (pos.x - (itemWidth / 2));
//		int top = (int) (pos.y - (itemWidth / 2));
//		int right = mBoardWidth - left + itemWidth;
//		int bottom = mBoardHeight - top + itemWidth;
//		lp.setMargins(left, top, right, bottom);
//		item.setLayoutParams(lp);
//	}
	
	//private double distance
	private boolean isValidPosition(int x, int y)
	{
		return false;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
}