package com.intalker.dz.utilities;

import com.intalker.dz.ui.Chess;

public class RecordManager {
	public final static String storeKeyCurPlayer = "CurPlayer";
	private int mCurPlayer = -1;
	private static RecordManager instance = null;

	public void setCurPlayer(int playerRole) {
		mCurPlayer = playerRole;
	}

	public int getCurPlayer() {
		return mCurPlayer;
	}

	public void finishMove(Chess c) {
		switch (c.getRole()) {
		case Chess.Role_A:
			mCurPlayer = Chess.Role_B;
			break;
		case Chess.Role_B:
			mCurPlayer = Chess.Role_A;
			break;
		default:
			break;
		}
	}

	public boolean isCurPlayer(Chess item) {
		if (item.getRole() == mCurPlayer) {
			return true;
		}
		return false;
	}

	public static RecordManager getInstance() {
		if (null == instance) {
			instance = new RecordManager();
		}
		return instance;
	}
}
