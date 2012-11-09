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
import android.widget.Toast;

public class ChessBoard extends RelativeLayout {

	private int mBoardWidth = 0;
	private int mBoardHeight = 0;

	private Paint mForegroundPaint = null;
	private Rect mBoardBound = null;

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
		
		ChessManager chessMgr = ChessManager.getInstance();
		chessMgr.clearAList();
		chessMgr.clearBList();

		for (int i = 0; i < 4; ++i) {
			Chess btn = new Chess(this.getContext(), R.drawable.chess_a_normal,
					R.drawable.chess_a_selected, Chess.Role_A);
			this.addView(btn);
			btn.setPos(0, i);
			chessMgr.addToAList(btn);
		}

		for (int i = 0; i < 4; ++i) {
			Chess btn = new Chess(this.getContext(), R.drawable.chess_b_normal,
					R.drawable.chess_b_selected, Chess.Role_B);
			this.addView(btn);
			btn.setPos(3, i);
			chessMgr.addToBList(btn);
		}
		
		this.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					ChessManager chessMgr = ChessManager.getInstance();
					PositionManager posMgr = PositionManager.getInstance();
					int ix = posMgr.getProperRowColIndex(event.getX(), event.getY());
					Chess lastChess = chessMgr.getLastSelectedChess();
					if(null != lastChess)
					{
						if (ix >= 0)
						{
							if(!chessMgr.isOccupiedBy(ix) && chessMgr.canMoveTo(lastChess, ix))
							{
								lastChess.setIndex(ix);
								chessMgr.clearLastSelected();
								//chessMgr.setNewlyMovedChess(lastChess);
								boolean result = chessMgr.checkStatus(lastChess);
								if(result)
								{
									Toast.makeText(v.getContext(), "Kill", Toast.LENGTH_SHORT).show();
								}
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