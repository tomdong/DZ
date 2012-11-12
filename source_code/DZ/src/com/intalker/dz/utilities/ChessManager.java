package com.intalker.dz.utilities;

import java.util.ArrayList;

import com.intalker.dz.R;
import com.intalker.dz.ui.Chess;

public class ChessManager {
	private ArrayList<Chess> mAliveChessAList = new ArrayList<Chess>();
	private ArrayList<Chess> mAliveChessBList = new ArrayList<Chess>();
	private Chess mLastSelectedChess = null;

	private static ChessManager instance = null;

	public static ChessManager getInstance() {
		if (null == instance) {
			instance = new ChessManager();
		}
		return instance;
	}

	public void clearAList() {
		mAliveChessAList.clear();
	}

	public void clearBList() {
		mAliveChessBList.clear();
	}

	public void addToAList(Chess item) {
		if (!mAliveChessAList.contains(item)) {
			mAliveChessAList.add(item);
		}
	}

	public void addToBList(Chess item) {
		if (!mAliveChessBList.contains(item)) {
			mAliveChessBList.add(item);
		}
	}

	public boolean isOccupiedBy(ArrayList<Chess> chessList, int index) {
		return getItemByPos(chessList, index) != null;
	}

	public Chess getItemByPos(ArrayList<Chess> chessList, int index) {
		for (int i = 0; i < chessList.size(); ++i) {
			Chess c = chessList.get(i);
			if (c.getIndex() == index) {
				return c;
			}
		}
		return null;
	}

	public boolean isOccupied(int index) {
		return isOccupiedBy(mAliveChessAList, index)
				|| isOccupiedBy(mAliveChessBList, index);
	}

	public void selectChess(Chess item) {
		if (null != mLastSelectedChess) {
			mLastSelectedChess.unSelect();
		}
		mLastSelectedChess = item;
	}

	public Chess getLastSelectedChess() {
		return mLastSelectedChess;
	}

	public void clearLastSelected() {
		mLastSelectedChess = null;
	}
	
	public boolean canMoveTo(Chess item, int newIndex)
	{
		return isClosePosition(item, newIndex) && !isOccupied(newIndex);
	}

	public boolean isClosePosition(Chess item, int newIndex) {
		int newRow = newIndex / 4;
		int newCol = newIndex % 4;
		int oldRow = item.getRow();
		int oldCol = item.getCol();
		if (Math.abs(newRow - oldRow) + Math.abs(newCol - oldCol) == 1) {
			return true;
		}
		return false;
	}

	public void removeItem(Chess chess) {
		getComrades(chess).remove(chess);
		chess.die();
	}

	public ArrayList<Chess> getNewlyKilledEnemies(Chess newlyMovedChess) {
		ArrayList<Chess> killedList = new ArrayList<Chess>();
		Chess killedItemInRow = checkRow(newlyMovedChess);
		Chess killedItemInCol = checkCol(newlyMovedChess);
		if (null != killedItemInRow) {
			killedList.add(killedItemInRow);
		}
		if (null != killedItemInCol) {
			killedList.add(killedItemInCol);
		}
		return killedList;
	}

	private Chess checkRow(Chess chess) {
		ArrayList<Chess> comradesInSameRow = new ArrayList<Chess>();
		int row = chess.getRow();
		ArrayList<Chess> comrades = getComrades(chess);
		for (Chess c : comrades) {
			if (c.getRow() == row && !c.equals(chess)) {
				comradesInSameRow.add(c);
			}
		}
		// TODO: contains itself, should be optimized
		if (comradesInSameRow.size() != 1) {
			return null;
		}
		ArrayList<Chess> enimies = getEnemies(chess);
		Chess comrade = comradesInSameRow.get(0);
		int c1 = chess.getCol();
		int c2 = comrade.getCol();
		if (Math.abs(c1 - c2) != 1) {
			return null;
		}

		int rowStartIndex = row * 4;
		// TODO: should be optimized
		if (isOccupied(rowStartIndex) && isOccupied(rowStartIndex + 1)
				&& isOccupied(rowStartIndex + 2)
				&& isOccupied(rowStartIndex + 3)) {
			return null;
		}
		// TODO: end

		Chess deadChess = null;

		switch (c1) {
		case 0:
			deadChess = getItemByPos(enimies, rowStartIndex + 2);
			if (null != deadChess) {
				return deadChess;
			}
			break;
		case 1:
			if (0 == c2) {
				deadChess = getItemByPos(enimies, rowStartIndex + 2);
				if (null != deadChess) {
					return deadChess;
				}
			} else if (2 == c2) {
				deadChess = getItemByPos(enimies, rowStartIndex);
				if (null != deadChess) {
					return deadChess;
				} else {
					deadChess = getItemByPos(enimies, rowStartIndex + 3);
					if (null != deadChess) {
						return deadChess;
					}
				}
			}
			break;
		case 2:
			if (3 == c2) {
				deadChess = getItemByPos(enimies, rowStartIndex + 1);
				if (null != deadChess) {
					return deadChess;
				}
			}
			if (1 == c2) {
				deadChess = getItemByPos(enimies, rowStartIndex);
				if (null != deadChess) {
					return deadChess;
				} else {
					deadChess = getItemByPos(enimies, rowStartIndex + 3);
					if (null != deadChess) {
						return deadChess;
					}
				}
			}
			break;
		case 3:
			deadChess = getItemByPos(enimies, rowStartIndex + 1);
			if (null != deadChess) {
				return deadChess;
			}
			break;
		default:
			break;
		}
		return null;
	}

	private Chess checkCol(Chess chess) {
		ArrayList<Chess> comradesInSameCol = new ArrayList<Chess>();
		int col = chess.getCol();
		ArrayList<Chess> comrades = getComrades(chess);
		for (Chess c : comrades) {
			if (c.getCol() == col && !c.equals(chess)) {
				comradesInSameCol.add(c);
			}
		}
		// TODO: contains itself, should be optimized
		if (comradesInSameCol.size() != 1) {
			return null;
		}
		ArrayList<Chess> enimies = getEnemies(chess);
		Chess comrade = comradesInSameCol.get(0);
		int r1 = chess.getRow();
		int r2 = comrade.getRow();
		if (Math.abs(r1 - r2) != 1) {
			return null;
		}

		int colStartIndex = col;
		// TODO: should be optimized
		if (isOccupied(colStartIndex) && isOccupied(colStartIndex + 4)
				&& isOccupied(colStartIndex + 8)
				&& isOccupied(colStartIndex + 12)) {
			return null;
		}
		// TODO: end

		Chess deadChess = null;

		switch (r1) {
		case 0:
			deadChess = getItemByPos(enimies, colStartIndex + 8);
			if (null != deadChess) {
				return deadChess;
			}
			break;
		case 1:
			if (0 == r2) {
				deadChess = getItemByPos(enimies, colStartIndex + 8);
				if (null != deadChess) {
					return deadChess;
				}
			} else if (2 == r2) {
				deadChess = getItemByPos(enimies, colStartIndex);
				if (null != deadChess) {
					return deadChess;
				} else {
					deadChess = getItemByPos(enimies, colStartIndex + 12);
					if (null != deadChess) {
						return deadChess;
					}
				}
			}
			break;
		case 2:
			if (3 == r2) {
				deadChess = getItemByPos(enimies, colStartIndex + 4);
				if (null != deadChess) {
					return deadChess;
				}
			}
			if (1 == r2) {
				deadChess = getItemByPos(enimies, colStartIndex);
				if (null != deadChess) {
					return deadChess;
				} else {
					deadChess = getItemByPos(enimies, colStartIndex + 12);
					if (null != deadChess) {
						return deadChess;
					}
				}
			}
			break;
		case 3:
			deadChess = getItemByPos(enimies, colStartIndex + 4);
			if (null != deadChess) {
				return deadChess;
			}
			break;
		default:
			break;
		}
		return null;
	}

	private ArrayList<Chess> getComrades(Chess chess) {
		switch (chess.getRole()) {
		case Chess.Role_A:
			return mAliveChessAList;
		case Chess.Role_B:
			return mAliveChessBList;
		default:
			break;
		}
		return null;
	}

	private ArrayList<Chess> getEnemies(Chess chess) {
		switch (chess.getRole()) {
		case Chess.Role_A:
			return mAliveChessBList;
		case Chess.Role_B:
			return mAliveChessAList;
		default:
			break;
		}
		return null;
	}

	public ChessManager() {
	}
}