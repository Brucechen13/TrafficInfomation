package com.dlut.traffic;



import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.dlut.traffic.user.User;
import com.dlut.traffic.util.ConstantsUtil;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{
	
	private ImageView qqLoginButton;
	private TextView dataTips;
	
	static Tencent mTencent;
	String APP_ID = "1104503342";
	private UserInfo mInfo;
	private User user;
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = View.inflate(this, R.layout.activity_login, null);
		setContentView(view);
		// Tencent类是SDK的主要实现类，开发者可通过Tencent类访问腾讯开放的OpenAPI。
		// 其中APP_ID是分配给第三方应用的appid，类型为String。
		mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		qqLoginButton = (ImageView)this.findViewById(R.id.main_qq_imageview);
		qqLoginButton.setOnClickListener(this);
		dataTips = (TextView) findViewById(R.id.dataloading); // 显示1.5秒钟后自动消失
		user = new User();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.main_qq_imageview){
			onClickLogin();
		}
	}
	private void validLogin(String qq){
		Log.d("SDKQQAgentPref",qq);
		Ion.with(LoginActivity.this)
        .load(String.format("%s?qq=%s",ConstantsUtil.loginUrl, qq)).asJsonObject()
		.setCallback(new FutureCallback<JsonObject>() {

			@Override
			public void onCompleted(Exception e, JsonObject result) {
				// TODO Auto-generated method stub
				// 渐变展示启动屏
				AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
				aa.setDuration(2000);
				view.startAnimation(aa);
				aa.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationEnd(Animation arg0) {
						dataTips.setVisibility(View.VISIBLE);
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
					}

					@Override
					public void onAnimationStart(Animation animation) {
					}

				});
				
				if (e != null) {
					Log.d("SDKQQAgentPref",e.toString());
					return;
				}
				Log.d("SDKQQAgentPref","login:"+result);
				String qqString = result.get("qq").getAsString();
				if(qqString.equals("false")){
					dataTips.setText(R.string.userloadend);
					updateUserInfo();
				}else{
					dataTips.setText(R.string.dataloadend);
					user.parseJson(result);
					loginActivity();
				}
			}

		});
    }
	
	private void loginActivity(){
		LoginActivity.this.finish();
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("user", user);
		intent.putExtras(bundle);
		LoginActivity.this.startActivity(intent);
	}
	
	public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
	
	private void onClickLogin() {
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", loginListener);
			Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
		} else {
		    mTencent.logout(this);
			updateUserInfo();
			//updateLoginButton();
		}
	}
	
	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {

				@Override
				public void onError(UiError e) {

				}

				@Override
				public void onComplete(final Object response) {
					
					/*	Bitmap bitmap = null;
					try {
						bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
					} catch (JSONException e) {

					}*/
					JSONObject json = (JSONObject)response;
					try {
						if(json.has("figureurl")){
							user.setPhoto(json.getString("figureurl_qq_2"));
							Log.d("SDKQQAgentPref",json.getString("figureurl_qq_2"));
						}
						if (json.has("nickname")) {
							user.setNickName(json.getString("nickname"));
							Log.d("SDKQQAgentPref",json.getString("nickname"));
						}
						
						Ion.with(LoginActivity.this)
				        .load(String.format("%s?qq=%s&name=%s&pic=%s",ConstantsUtil.newUerUrl, user.getQq(), user.getNickName(), user.getPhoto())).asJsonObject()
						.setCallback(new FutureCallback<JsonObject>() {

							@Override
							public void onCompleted(Exception e,
									JsonObject result) {
								// TODO Auto-generated method stub
								if (e != null) {
									Log.d("SDKQQAgentPref",e.toString());
									return;
								}
								String suc = result.get("suc").getAsString();
								if(suc.equals("true")){
									loginActivity();
								}else{
									Log.d("SDKQQAgentPref",suc);
								}
							}
						});
						
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				@Override
				public void onCancel() {

				}
			};
			mInfo = new UserInfo(this, mTencent.getQQToken());
			mInfo.getUserInfo(listener);
			Log.d("SDKQQAgentPref","nickname");
		} else {
		}
	}
	
	IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
        	Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            validLogin(user.getQq());
        }
    };
    
    
    
    private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(LoginActivity.this, "返回为空", "登录失败");
                return;
            }
            String openId;
			try {
				openId = jsonResponse.getString(Constants.PARAM_OPEN_ID);
				 user.setQq(openId);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Util.showResultDialog(LoginActivity.this, response.toString(), "登录成功");
            // 有奖分享处理
            // handlePrizeShare();
			doComplete((JSONObject)response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Util.toastMessage(LoginActivity.this, "onError: " + e.errorDetail);
			Util.dismissDialog();
		}

		@Override
		public void onCancel() {
			Util.toastMessage(LoginActivity.this, "onCancel: ");
			Util.dismissDialog();
		}
	}
}
