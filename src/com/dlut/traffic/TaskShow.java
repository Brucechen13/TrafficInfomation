package com.dlut.traffic;

import java.util.Date;

import com.dlut.traffic.user.User;
import com.dlut.traffic.user.UserInfoView;
import com.dlut.traffic.util.ServerUtil;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskShow extends TitleActivity {
	
	private User user;
	
	private ImageView detail_img;
	private TextView name;
	private TextView content_tv;
	private TextView date_tv;
	private TextView type_tv;
	private TextView time_tv;

	
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
		setContentView(R.layout.activity_task_show);
		showBackwardView(R.string.button_backward, true);
		user = new User();
		
		name = (TextView) this.findViewById(R.id.username);
		content_tv = (TextView) this.findViewById(R.id.content);
		date_tv = (TextView) this.findViewById(R.id.date);
		type_tv = (TextView) this.findViewById(R.id.type_tv);
		time_tv = (TextView) this.findViewById(R.id.time);
		detail_img = (ImageView) this.findViewById(R.id.userimg_iv);
		
		Date date = Util.parseISODate(getIntent().getStringExtra("time"));
		String[] times = date.toLocaleString().split(" ");
		
		content_tv.setText(getIntent().getStringExtra("content"));
		type_tv.setText(getIntent().getStringExtra("type"));
		date_tv.setText(times[0]);
		time_tv.setText(times[1]);
		
		Ion.with(TaskShow.this)
        .load(String.format("%s?qq=%s",ServerUtil.loginUrl, getIntent().getStringExtra("user"))).asJsonObject()
		.setCallback(new FutureCallback<JsonObject>() {
			@Override
			public void onCompleted(Exception e, JsonObject result) {
				// TODO Auto-generated method stub
				if (e != null || result==null) {
					Log.d("traffic",e.toString());
					Util.toastMessage(
							TaskShow.this, "网络出错");

				}else if(result.has("suc")){
					Util.toastMessage(
							TaskShow.this, "信息出错");
				}else{
					user.parseJson(result);
					showText();
				}
			}

		});
	}
	
	private void showText() {
		setTitle(user.getNickName()+"的发布的任务");
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
		name.setText(user.getNickName());
	}
}
