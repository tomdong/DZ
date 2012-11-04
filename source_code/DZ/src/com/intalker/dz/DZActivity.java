package com.intalker.dz;

import com.intalker.dz.ui.ChessBoard;
import com.intalker.dz.utilities.DensityAdaptor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class DZActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dz);
        //this.setContentView(view);
        initializeChessBoard();
    }
    
    private void initializeChessBoard()
    {
//    	LinearLayout layout = new LinearLayout(this.getApplicationContext());
//    	this.setContentView(layout);
//    	layout.setBackgroundColor(0xFF00FF00);
    	
    	Context context = this.getApplicationContext();
    	RelativeLayout layout = new RelativeLayout(context);
    	this.setContentView(layout);
    	layout.setBackgroundResource(R.drawable.board_bg);
    	
    	//LinearLayout
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
		//boardLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		boardLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		
		layout.addView(board, boardLayoutParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dz, menu);
        return true;
    }
}
