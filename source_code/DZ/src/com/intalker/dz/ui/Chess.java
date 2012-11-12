package com.intalker.dz.ui;

import com.intalker.dz.R;
import com.intalker.dz.utilities.ChessManager;
import com.intalker.dz.utilities.PositionManager;
import com.intalker.dz.utilities.RecordManager;
import com.intalker.dz.utilities.TipUtil;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class Chess extends ImageButton {

	public final static int Role_A = 0;
	public final static int Role_B = 1;
	private int normalImageResId = -1;
	private int selectedImageResId = -1;
	private int curRow = 0;
	private int curCol = 0;
	private int role = -1;

	public Chess(Context context, int normalResId, int selectedResId, int role) {
		super(context);
		normalImageResId = normalResId;
		selectedImageResId = selectedResId;
		this.role = role;

		this.setBackgroundResource(normalImageResId);

		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}

		});

		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Chess chess = (Chess) v;
					chess.select();
				}
				return false;
			}

		});
	}

	public void setPos(int r, int c) {
		curRow = r;
		curCol = c;
		updatePos();
	}

	public void setIndex(int index) {
		curRow = index / 4;
		curCol = index % 4;
		updatePos();
	}

	private void updatePos() {
		PositionManager.getInstance().positionItem(this, curRow, curCol);
		unSelect();
	}

	public int getRow() {
		return curRow;
	}

	public int getCol() {
		return curCol;
	}

	public int getIndex() {
		return curRow * 4 + curCol;
	}

	public PointF getPosition() {
		return PositionManager.getInstance().getLocation(curRow, curCol);
	}

	public void select() {
		if(!RecordManager.getInstance().isCurPlayer(this))
		{
			TipUtil.getInstance().showTip(R.string.wrong_player);
			return;
		}
		ChessManager.getInstance().selectChess(this);
		this.setBackgroundResource(selectedImageResId);
	}

	public void unSelect() {
		this.setBackgroundResource(normalImageResId);
	}

	public boolean isComrade(Chess other) {
		return this.role == other.role;
	}

	public int getRole() {
		return role;
	}

	public void die() {
		this.setVisibility(GONE);
	}
}