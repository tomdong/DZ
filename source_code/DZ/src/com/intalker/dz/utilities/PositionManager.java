package com.intalker.dz.utilities;

import android.graphics.PointF;
import android.util.FloatMath;
import android.view.View;
import android.widget.RelativeLayout;

public class PositionManager {
	private PointF[] mPositions = new PointF[16];
	private float[] mDrawPts = new float[32];
	private int mBoardWidth = 0;
	private int mBoardHeight = 0;
	private final int mTouchTolerance = DensityAdaptor
			.getDensityIndependentValue(48);
	private final int mItemWidth = DensityAdaptor
			.getDensityIndependentValue(48);
	private static PositionManager instance = null;

	public static PositionManager getInstance() {
		if (null == instance) {
			instance = new PositionManager();
		}
		return instance;
	}

	public void initialize(float margin, float cellLength, int boardWidth,
			int boardHeight) {
		float pt11x = margin;
		float pt11y = margin;
		float pt12x = pt11x + cellLength;
		float pt12y = pt11y;
		float pt13x = pt12x + cellLength;
		float pt13y = pt11y;
		float pt14x = pt13x + cellLength;
		float pt14y = pt11y;

		float pt21x = pt11x;
		float pt21y = pt11y + cellLength;
		float pt22x = pt12x;
		float pt22y = pt21y;
		float pt23x = pt13x;
		float pt23y = pt21y;
		float pt24x = pt14x;
		float pt24y = pt21y;

		float pt31x = pt11x;
		float pt31y = pt21y + cellLength;
		float pt32x = pt12x;
		float pt32y = pt31y;
		float pt33x = pt13x;
		float pt33y = pt31y;
		float pt34x = pt14x;
		float pt34y = pt31y;

		float pt41x = pt11x;
		float pt41y = pt31y + cellLength;
		float pt42x = pt12x;
		float pt42y = pt41y;
		float pt43x = pt13x;
		float pt43y = pt41y;
		float pt44x = pt14x;
		float pt44y = pt41y;

		mDrawPts[0] = pt11x;
		mDrawPts[1] = pt11y;
		mDrawPts[2] = pt14x;
		mDrawPts[3] = pt14y;

		mDrawPts[4] = pt21x;
		mDrawPts[5] = pt21y;
		mDrawPts[6] = pt24x;
		mDrawPts[7] = pt24y;

		mDrawPts[8] = pt31x;
		mDrawPts[9] = pt31y;
		mDrawPts[10] = pt34x;
		mDrawPts[11] = pt34y;

		mDrawPts[12] = pt41x;
		mDrawPts[13] = pt41y;
		mDrawPts[14] = pt44x;
		mDrawPts[15] = pt44y;

		mDrawPts[16] = pt11x;
		mDrawPts[17] = pt11y;
		mDrawPts[18] = pt41x;
		mDrawPts[19] = pt41y;

		mDrawPts[20] = pt12x;
		mDrawPts[21] = pt12y;
		mDrawPts[22] = pt42x;
		mDrawPts[23] = pt42y;

		mDrawPts[24] = pt13x;
		mDrawPts[25] = pt13y;
		mDrawPts[26] = pt43x;
		mDrawPts[27] = pt43y;

		mDrawPts[28] = pt14x;
		mDrawPts[29] = pt14y;
		mDrawPts[30] = pt44x;
		mDrawPts[31] = pt44y;

		mPositions[0] = new PointF(pt11x, pt11y);
		mPositions[1] = new PointF(pt12x, pt12y);
		mPositions[2] = new PointF(pt13x, pt13y);
		mPositions[3] = new PointF(pt14x, pt14y);

		mPositions[4] = new PointF(pt21x, pt21y);
		mPositions[5] = new PointF(pt22x, pt22y);
		mPositions[6] = new PointF(pt23x, pt23y);
		mPositions[7] = new PointF(pt24x, pt24y);

		mPositions[8] = new PointF(pt31x, pt31y);
		mPositions[9] = new PointF(pt32x, pt32y);
		mPositions[10] = new PointF(pt33x, pt33y);
		mPositions[11] = new PointF(pt34x, pt34y);

		mPositions[12] = new PointF(pt41x, pt41y);
		mPositions[13] = new PointF(pt42x, pt42y);
		mPositions[14] = new PointF(pt43x, pt43y);
		mPositions[15] = new PointF(pt44x, pt44y);

		mBoardWidth = boardWidth;
		mBoardHeight = boardHeight;
	}

	public PositionManager() {
	}

	public PointF getLocation(int r, int c) {
		int index = r * 4 + c;
		if (index >= 16) {
			return null;
		}
		return mPositions[index];
	}

	public PointF getLocation(int index) {
		return mPositions[index];
	}

	public int getProperRowColIndex(float x, float y) {
		for (int i = 0; i < 16; ++i) {
			PointF pos = mPositions[i];
			float xtemp = (pos.x - x) * (pos.x - x);
			float ytemp = (pos.y - y) * (pos.y - y);
			if (FloatMath.sqrt(xtemp + ytemp) < mTouchTolerance) {
				return i;
			}
		}
		return -1;
	}

	public void positionItem(View item, int r, int c) {
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		PointF pos = PositionManager.getInstance().getLocation(r, c);

		int left = (int) (pos.x - (mItemWidth / 2));
		int top = (int) (pos.y - (mItemWidth / 2));
		int right = mBoardWidth - left + mItemWidth;
		int bottom = mBoardHeight - top + mItemWidth;
		lp.setMargins(left, top, right, bottom);
		item.setLayoutParams(lp);
	}
}
