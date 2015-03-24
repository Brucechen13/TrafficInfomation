package com.dlut.traffic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class InfoDetail extends Activity implements OnClickListener {
	
	private TextView mimedetail_back;
	private TextView mimedetail_comment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msg_item_detail);
		mimedetail_back=(TextView)findViewById(R.id.mimedetail_back);
		mimedetail_back.setOnClickListener(this);
		mimedetail_comment=(TextView)findViewById(R.id.mimedetail_comment);
		mimedetail_comment.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		   case R.id.mimedetail_back:
	        	
			   this.finish();
	        
		        break;  
		   case R.id.mimedetail_comment:
			   Intent intent=new Intent(this, UpdateInfo.class);
				startActivity(intent);				
				break;
			   default:
				   break;
		}
	}

}
