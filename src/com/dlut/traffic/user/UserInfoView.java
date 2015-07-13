package com.dlut.traffic.user;


import com.dlut.traffic.R;
import com.dlut.traffic.TitleActivity;
import com.dlut.traffic.util.ServerUtil;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoView extends TitleActivity implements OnClickListener {
	
	private User user;
	
	private ImageView detail_img;
	private TextView nick_name;
	private TextView signature_tv;
	private TextView score_tv;
	private TextView area_tv;
	private TextView gender_tv;
	private TextView company_tv;
	private TextView job_tv;
	

	@Override
	protected void onBackward(View backwardView) {
		// TODO Auto-generated method stub
		super.onBackward(backwardView);
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}
	
	private void initView(){
		setContentView(R.layout.user_info_view);
		showBackwardView(R.string.button_backward, true);
		user = new User();
		
		nick_name = (TextView) this.findViewById(R.id.nick_name);
		signature_tv = (TextView) this.findViewById(R.id.signature_tv);
		area_tv = (TextView) this.findViewById(R.id.area_tv);
		score_tv = (TextView) this.findViewById(R.id.score_tv);
		company_tv = (TextView) this.findViewById(R.id.company_tv);
		job_tv = (TextView) this.findViewById(R.id.job_tv);
		gender_tv = (TextView) this.findViewById(R.id.gender_tv);
		detail_img = (ImageView) this.findViewById(R.id.detail_img);
		if((user = (User) getIntent().getSerializableExtra("user")) != null){
			Log.d("traffic", "get:" + user.getNickName());
			showText();
		}else if(getIntent().getStringExtra("userid") != null){
			user = new User();
			Ion.with(UserInfoView.this)
	        .load(String.format("%s?qq=%s",ServerUtil.loginUrl, getIntent().getStringExtra("userid"))).asJsonObject()
			.setCallback(new FutureCallback<JsonObject>() {
				@Override
				public void onCompleted(Exception e, JsonObject result) {
					// TODO Auto-generated method stub
					if (e != null || result==null) {
						Log.d("traffic",e.toString());
						Util.toastMessage(
								UserInfoView.this, "网络出错");

					}else if(result.has("suc")){
						Util.toastMessage(
								UserInfoView.this, "信息出错");
					}else{
						user.parseJson(result);
						showText();
					}
				}

			});
		}
	}
	
	private void showText() {
		setTitle(user.getNickName()+"的"+(String) this.getResources().getString(R.string.userInfo));
		Ion.with(this).load(user.getPhoto()).withBitmap()
		// .placeholder(R.drawable.placeholder_image)
		.error(R.drawable.tou).intoImageView(detail_img)
		.setCallback(new FutureCallback<ImageView>() {

			@Override
			public void onCompleted(Exception arg0, ImageView arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					Log.d("traffic", arg0.toString());
				}
			}

		});
		nick_name.setText(user.getNickName());
		signature_tv.setText(user.getSignature());
		score_tv.setText("" + user.getScore());
		area_tv.setText(user.getCity());
		company_tv.setText(user.getCompany());
		job_tv.setText(user.getJob());
		gender_tv.setText(user.getGender());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) { 
			   default:
				   break;
		}
		
	}

	
}
