package com.intalker.dz.ui;

import java.util.ArrayList;

import com.intalker.dz.R;
import com.intalker.dz.utilities.ChessManager;
import com.intalker.dz.utilities.PositionManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
			btn.setPos(3, i);
			mALiveChessList.add(btn);
			ChessManager.getInstance().addToBList(btn);
		}
		
		this.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					int ix = PositionManager.getInstance().getProperRowColIndex(event.getX(), event.getY());
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

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
}