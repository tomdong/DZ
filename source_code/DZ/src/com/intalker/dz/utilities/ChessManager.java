package com.intalker.dz.utilities;

import java.util.ArrayList;

import com.intalker.dz.ui.Chess;

public class ChessManager {
	private ArrayList<Chess> mAliveChessAList = new ArrayList<Chess>();
	private ArrayList<Chess> mAliveChessBList = new ArrayList<Chess>();
	private Chess mLastSelectedChess = null;
	
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
	
	public boolean isOccupied(int index)
	{
		for(int i = 0; i < mAliveChessAList.size(); ++i)
		{
			if(mAliveChessAList.get(i).getIndex() == index)
			{
				return true;
			}
		}
		
		for(int i = 0; i < mAliveChessBList.size(); ++i)
		{
			if(mAliveChessBList.get(i).getIndex() == index)
			{
				return true;
			}
		}
		
		return false;
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
	
	public ChessManager()
	{
	}
}