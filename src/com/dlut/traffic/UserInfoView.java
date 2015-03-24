package com.dlut.traffic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UserInfoView extends Activity implements OnClickListener {
	
	private TextView mimedetail_back;
	private TextView mimedetail_modify;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info_view);
		mimedetail_back=(TextView)findViewById(R.id.mimedetail_back);
		mimedetail_back.setOnClickListener(this);
		mimedetail_modify=(TextView)findViewById(R.id.mimedetail_modify);
		mimedetail_modify.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		   case R.id.mimedetail_back:
	        	
			   this.finish();
	        
		        break;  
		   case R.id.mimedetail_modify:
			   Intent intent=new Intent(this, UserInfo.class);
				startActivity(intent);				
				break;
			   default:
				   break;
		}
		
	}

	
}
