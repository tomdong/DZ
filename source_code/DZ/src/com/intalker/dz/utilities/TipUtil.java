package com.intalker.dz.utilities;

import com.intalker.dz.DZActivity;

import android.widget.Toast;

public class TipUtil {
	private Toast mTip = null;
	private static TipUtil instance = null;

	public TipUtil()
	{
		mTip = Toast.makeText(DZActivity.getApp(), "", Toast.LENGTH_SHORT);
	}
	
	public static TipUtil getInstance() {
		if (null == instance) {
			instance = new TipUtil();
		}
		return instance;
	}
	
	public void showTip(int tipResId)
	{
		mTip.cancel();
		mTip.setText(tipResId);
		mTip.show();
	}
}
