package com.intalker.dz;

import com.intalker.dz.ui.ChessBoard;
import com.intalker.dz.utilities.DensityAdaptor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.RelativeLayout;

public class DZActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeChessBoard();
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
