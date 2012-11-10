package com.intalker.dz.utilities;

import java.util.ArrayList;

import com.intalker.dz.ui.Chess;

public class ChessManager {
	private ArrayList<Chess> mAliveChessAList = new ArrayList<Chess>();
	private ArrayList<Chess> mAliveChessBList = new ArrayList<Chess>();
	private Chess mLastSelectedChess = null;
	private Chess mNewlyMovedChess = null;
	
	private static ChessManager instance = null;
	
	public static ChessManager getInstance()
	{
		if(null == instance)
		{
			instance = new ChessManager();
		}
		return instance;
	}
	
	public void clearAList()
	{
		mAliveChessAList.clear();
	}
	
	public void clearBList()
	{
		mAliveChessBList.clear();
	}
	
	public void addToAList(Chess item)
	{
		if(!mAliveChessAList.contains(item))
		{
			mAliveChessAList.add(item);
		}
	}
	
	public void addToBList(Chess item)
	{
		if(!mAliveChessBList.contains(item))
		{
			mAliveChessBList.add(item);
		}
	}
	
	public boolean isOccupiedBy(ArrayList<Chess> chessList, int index)
	{
		for(int i = 0; i < chessList.size(); ++i)
		{
			if(chessList.get(i).getIndex() == index)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isOccupiedBy(int index)
	{
		return isOccupiedBy(mAliveChessAList, index)
				|| isOccupiedBy(mAliveChessBList, index);
	}
	
	public void selectChess(Chess item)
	{
		if(null != mLastSelectedChess)
		{
			mLastSelectedChess.unSelect();
		}
		mLastSelectedChess = item;
	}
	
	public Chess getLastSelectedChess()
	{
		return mLastSelectedChess;
	}
	
	public void clearLastSelected()
	{
		mLastSelectedChess = null;
	}
	
	public boolean canMoveTo(Chess item, int newIndex)
	{
		int newRow = newIndex / 4;
		int newCol = newIndex % 4;
		int oldRow = item.getRow();
		int oldCol = item.getCol();
		if (Math.abs(newRow - oldRow) + Math.abs(newCol - oldCol) == 1)
		{
			return true;
		}
		return false;
	}
	
	public void setNewlyMovedChess(Chess chess)
	{
		mNewlyMovedChess = chess;
	}
	
//	public void checkResult()
//	{
//		if(null == mNewlyMovedChess)
//		{
//			return;
//		}
//		
//		int r = mNewlyMovedChess.getRow();
//		int c = mNewlyMovedChess.getCol();
//		
//		ArrayList<Chess> comrades = getComrades(mNewlyMovedChess);
//		ArrayList<Chess> enimies = getEnemies(mNewlyMovedChess);
//		
//		
//	}
	
	public boolean checkStatus(Chess newlyMovedChess)
	{
		return checkRow(newlyMovedChess) || checkCol(newlyMovedChess);
	}
	
	private boolean checkRow(Chess chess)
	{
		ArrayList<Chess> comradesInSameRow = new ArrayList<Chess>();
		int row = chess.getRow();
		ArrayList<Chess> comrades = getComrades(chess);
		for (Chess c : comrades)
		{
			if(c.getRow() == row && !c.equals(chess))
			{
				comradesInSameRow.add(c);
			}
		}
		//TODO: contains itself, should be optimized
		if(comradesInSameRow.size() != 1)
		{
			return false;
		}
		ArrayList<Chess> enimies = getEnemies(chess);
		Chess comrade = comradesInSameRow.get(0);
		int c1 = chess.getCol();
		int c2 = comrade.getCol();
		if(Math.abs(c1 - c2) != 1)
		{
			return false;
		}
		
		int rowStartIndex = row * 4;
		//TODO: should be optimized
		if(isOccupiedBy(rowStartIndex)
				&& isOccupiedBy(rowStartIndex + 1)
				&& isOccupiedBy(rowStartIndex + 2)
				&& isOccupiedBy(rowStartIndex + 3))
		{
			return false;
		}
		//TODO: end
		
		switch(c1)
		{
		case 0:
			if(isOccupiedBy(enimies, rowStartIndex + 2))
			{
				return true;
			}
			break;
		case 1:
			if(0 == c2)
			{
				if(isOccupiedBy(enimies, rowStartIndex + 2))
				{
					return true;
				}
			}
			else if(2 == c2)
			{
				if(isOccupiedBy(enimies, rowStartIndex))
				{
					return true;
				}
				else if(isOccupiedBy(enimies, rowStartIndex + 3))
				{
					return true;
				}
			}
			break;
		case 2:
			if(3 == c2)
			{
				if(isOccupiedBy(enimies, rowStartIndex + 1))
				{
					return true;
				}
			}
			if(1 == c2)
			{
				if(isOccupiedBy(enimies, rowStartIndex))
				{
					return true;
				}
				else if(isOccupiedBy(enimies, rowStartIndex + 3))
				{
					return true;
				}
			}
			break;
		case 3:
			if(isOccupiedBy(enimies, rowStartIndex + 1))
			{
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	private boolean checkCol(Chess chess)
	{
		ArrayList<Chess> comradesInSameCol = new ArrayList<Chess>();
		int col = chess.getCol();
		ArrayList<Chess> comrades = getComrades(chess);
		for (Chess c : comrades)
		{
			if(c.getCol() == col && !c.equals(chess))
			{
				comradesInSameCol.add(c);
			}
		}
		//TODO: contains itself, should be optimized
		if(comradesInSameCol.size() != 1)
		{
			return false;
		}
		ArrayList<Chess> enimies = getEnemies(chess);
		Chess comrade = comradesInSameCol.get(0);
		int r1 = chess.getRow();
		int r2 = comrade.getRow();
		if(Math.abs(r1 - r2) != 1)
		{
			return false;
		}
		
		int colStartIndex = col;
		//TODO: should be optimized
		if(isOccupiedBy(colStartIndex)
				&& isOccupiedBy(colStartIndex + 4)
				&& isOccupiedBy(colStartIndex + 8)
				&& isOccupiedBy(colStartIndex + 12))
		{
			return false;
		}
		//TODO: end
		
		switch(r1)
		{
		case 0:
			if(isOccupiedBy(enimies, colStartIndex + 8))
			{
				return true;
			}
			break;
		case 1:
			if(0 == r2)
			{
				if(isOccupiedBy(enimies, colStartIndex + 8))
				{
					return true;
				}
			}
			else if(2 == r2)
			{
				if(isOccupiedBy(enimies, colStartIndex))
				{
					return true;
				}
				else if(isOccupiedBy(enimies, colStartIndex + 12))
				{
					return true;
				}
			}
			break;
		case 2:
			if(3 == r2)
			{
				if(isOccupiedBy(enimies, colStartIndex + 4))
				{
					return true;
				}
			}
			if(1 == r2)
			{
				if(isOccupiedBy(enimies, colStartIndex))
				{
					return true;
				}
				else if(isOccupiedBy(enimies, colStartIndex + 12))
				{
					return true;
				}
			}
			break;
		case 3:
			if(isOccupiedBy(enimies, colStartIndex + 4))
			{
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	private ArrayList<Chess> getComrades(Chess chess)
	{
		switch(chess.getRole())
		{
		case Chess.Role_A:
			return mAliveChessAList;
		case Chess.Role_B:
			return mAliveChessBList;
		default:
			break;
		}
		return null;
	}
	
	private ArrayList<Chess> getEnemies(Chess chess)
	{
		switch(chess.getRole())
		{
		case Chess.Role_A:
			return mAliveChessBList;
		case Chess.Role_B:
			return mAliveChessAList;
		default:
			break;
		}
		return null;
	}
	
	public ChessManager()
	{
	}
}