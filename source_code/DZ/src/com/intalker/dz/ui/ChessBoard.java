package com.intalker.dz.ui;

import java.util.ArrayList;

import com.intalker.dz.R;
import com.intalker.dz.utilities.ChessManager;
import com.intalker.dz.utilities.PositionManager;
import com.intalker.dz.utilities.RecordManager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
		RecordManager.getInstance().setCurPlayer(Chess.Role_A);
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

		PositionManager.getInstance().initialize(margin, cellLength,
				mBoardWidth, mBoardHeight);
	}

	private void initializeUI() {
		this.setBackgroundResource(R.drawable.grid_bg);

		ChessManager chessMgr = ChessManager.getInstance();

		if (chessMgr.getListA().size() > 0 || chessMgr.getListB().size() > 0) {
			for (Chess chess : chessMgr.getListA()) {
				this.addView(chess);
			}

			for (Chess chess : chessMgr.getListB()) {
				this.addView(chess);
			}
		} else {
			for (int i = 0; i < 4; ++i) {
				Chess btn = new Chess(this.getContext(),
						R.drawable.chess_a_normal, R.drawable.chess_a_selected,
						Chess.Role_A);
				this.addView(btn);
				btn.setPos(0, i);
				chessMgr.addToAList(btn);
			}

			for (int i = 0; i < 4; ++i) {
				Chess btn = new Chess(this.getContext(),
						R.drawable.chess_b_normal, R.drawable.chess_b_selected,
						Chess.Role_B);
				this.addView(btn);
				btn.setPos(3, i);
				chessMgr.addToBList(btn);
			}
		}

		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					ChessManager chessMgr = ChessManager.getInstance();
					PositionManager posMgr = PositionManager.getInstance();
					int ix = posMgr.getProperRowColIndex(event.getX(),
							event.getY());
					Chess lastChess = chessMgr.getLastSelectedChess();
					if (null != lastChess) {
						if (ix >= 0) {
							if (chessMgr.canMoveTo(lastChess, ix)) {
								lastChess.setIndex(ix);
								RecordManager.getInstance().finishMove(
										lastChess);
								chessMgr.clearLastSelected();
								ArrayList<Chess> killedEnemies = chessMgr
										.getNewlyKilledEnemies(lastChess);
								for (Chess enemy : killedEnemies) {
									chessMgr.removeItem(enemy);
								}
							}
						}
					}
				}
				return false;
			}

		});

		TextView tv = new TextView(this.getContext());
		tv.setText(R.string.wrong_player);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		this.addView(tv, lp);
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
}