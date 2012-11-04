package com.intalker.dz.ui;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

public class Chess extends ImageButton {

	private int normalImageResId = -1;
	private int selectedImageResId = -1;
	public Chess(Context context, int normalResId, int selectedResId) {
		super(context);
		// TODO Auto-generated constructor stub
		normalImageResId = normalResId;
		selectedImageResId = selectedResId;
		
		this.setBackgroundResource(normalImageResId);
		
		this.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundResource(selectedImageResId);
			}
			
		});
	}

}
