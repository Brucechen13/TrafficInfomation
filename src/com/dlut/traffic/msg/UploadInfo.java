package com.dlut.traffic.msg;

import com.dlut.traffic.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UploadInfo extends Activity implements OnClickListener {
	
	private RelativeLayout lukuang;
	private RelativeLayout shigu;
	
	private TextView mimedetail_back;
	
	private Button lkButton;
	private Button sgButton;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_info);
		initView();
	}
	
	private void initView(){
		mimedetail_back=(TextView)findViewById(R.id.mimedetail_back);
		mimedetail_back.setOnClickListener(this);
		
		lkButton=(Button)findViewById(R.id.lk);
		lkButton.setOnClickListener(this);
		sgButton=(Button)findViewById(R.id.sg);
		sgButton.setOnClickListener(this);
		
		lukuang = (RelativeLayout)findViewById(R.id.lukuang);
		shigu = (RelativeLayout)findViewById(R.id.shigu);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		   case R.id.mimedetail_back:
	        	
			   this.finish();
	        
		        break;  
		   case R.id.lk:
			   shigu.setVisibility(View.GONE);
			   lukuang.setVisibility(View.VISIBLE);
			   break;
		   case R.id.sg:
			   lukuang.setVisibility(View.GONE);
			   shigu.setVisibility(View.VISIBLE);
			   default:
				   break;
		}
	}
}
