package com.intalker.dz;

import java.util.ArrayList;

import com.intalker.dz.ui.Chess;
import com.intalker.dz.ui.ChessBoard;
import com.intalker.dz.utilities.ChessManager;
import com.intalker.dz.utilities.DensityAdaptor;
import com.intalker.dz.utilities.RecordManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class DZActivity extends Activity {

	private static DZActivity app = null;
	
	public static DZActivity getApp()
	{
		return app;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        if(null != savedInstanceState)
        {
        	ChessManager chessMgr = ChessManager.getInstance();
        	chessMgr.clearListA();
        	chessMgr.clearListB();
        	
			ArrayList<Chess> listA = savedInstanceState
					.getParcelableArrayList(Chess.storeKeyRoleA);
			chessMgr.getListA().addAll(listA);

			ArrayList<Chess> listB = savedInstanceState
					.getParcelableArrayList(Chess.storeKeyRoleB);
			chessMgr.getListB().addAll(listB);
			
			RecordManager.getInstance().setCurPlayer(
					savedInstanceState.getInt(RecordManager.storeKeyCurPlayer));
        }

        initializeChessBoard();
        app = this;
    }
    
    @Override
	protected void onSaveInstanceState(Bundle outState) {
    	
		ArrayList<Chess> listA = ChessManager.getInstance().getListA();
		ArrayList<Chess> listB = ChessManager.getInstance().getListB();
		outState.putParcelableArrayList(Chess.storeKeyRoleA, listA);
		outState.putParcelableArrayList(Chess.storeKeyRoleB, listB);
		outState.putInt(RecordManager.storeKeyCurPlayer, RecordManager
				.getInstance().getCurPlayer());

		super.onSaveInstanceState(outState);
	}

	private void initializeChessBoard()
    {
    	Context context = this.getApplicationContext();
    	RelativeLayout layout = new RelativeLayout(context);
    	this.setContentView(layout);
    	layout.setBackgroundResource(R.drawable.board_bg);

    	DensityAdaptor.init(this);
		int width  = DensityAdaptor.getScreenWidth();
		int height = DensityAdaptor.getScreenHeight();
		int length = width < height ? width : height;
    	
    	ChessBoard board = new ChessBoard(context, length, length);
    	
		RelativeLayout.LayoutParams boardLayoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		boardLayoutParams.width = length;
		boardLayoutParams.height = length;
		boardLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		layout.addView(board, boardLayoutParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dz, menu);
        return true;
    }
}
