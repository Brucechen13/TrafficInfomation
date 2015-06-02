package com.dlut.traffic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.dlut.traffic.fragment.*;
import com.dlut.traffic.msg.UploadMsg;
import com.dlut.traffic.service.BackNetService;
import com.dlut.traffic.user.User;
import com.dlut.traffic.util.FragmentUtils;
import com.dlut.traffic.util.Util;
import com.dlut.traffic.widget.TabView;
import com.dlut.traffic.widget.TabView.OnTabChangeListener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		OnTabChangeListener, FragmentCallback {

	private Fragment mCurrentFragment;
	private FragmentManager fragmentManager;

	private TabView mTabView;
	private TextView mTitleTextView;
	

	/** 上一次的状态 */
	private int mPreviousTabIndex = 1;
	/** 当前状态 */
	private int mCurrentTabIndex = 1;

	private static final int PHOTO_SUCCESS = 1;
	private static final int CAMERA_SUCCESS = 2;
	private static final int NONE = 0;
	private static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";

	private Bundle bundle;
	
	public static boolean tuisong = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bundle = this.getIntent().getExtras();
		fragmentManager = getSupportFragmentManager();
		mCurrentTabIndex = 0;
		mPreviousTabIndex = 0;
		setupViews();
	}

	private void setupViews() {
		// TODO Auto-generated method stub

		mTitleTextView = (TextView) findViewById(R.id.text_title);
		mTitleTextView.setText(R.string.text_tab_message);
		mTabView = (TabView) findViewById(R.id.view_tab);
		mTabView.setOnTabChangeListener(this);
		mTabView.setCurrentTab(mCurrentTabIndex);
		mCurrentFragment = new FragmentMap();
		FragmentUtils.replaceFragment(fragmentManager, R.id.layout_content,
				FragmentMap.class, null, false);
		
		SharedPreferences preferences = getSharedPreferences(Util.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
		tuisong = preferences.getBoolean("tuisong", true);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("zhanghao", true);
		editor.putString("qq", Util.userId);
		editor.commit();
		Log.d("traffic", ""+tuisong);
		if (!BackNetService.isrun) {
			Intent it = new Intent(this, BackNetService.class);
			this.startService(it);
		}

	}

	private void replaceFragment(Class<? extends Fragment> newFragment) {

		mCurrentFragment = FragmentUtils
				.switchFragment(fragmentManager, R.id.layout_content,
						mCurrentFragment, newFragment, null, false);
	}

	private void replaceFragment(Class<? extends Fragment> newFragment,
			Bundle bundle) {

		mCurrentFragment = FragmentUtils.switchFragment(fragmentManager,
				R.id.layout_content, mCurrentFragment, newFragment, bundle,
				false);
	}

	@Override
	public void onFragmentCallback(Fragment fragment, int id, Bundle args) {
		// TODO Auto-generated method stub
		mTabView.setCurrentTab(1);
	}

	@Override
	public void onTabChange(String tag) {
		// TODO Auto-generated method stub

		if (tag != null) {
			if (tag.equals("message")) {
				mPreviousTabIndex = mCurrentTabIndex;
				mCurrentTabIndex = 0;
				mTitleTextView.setText(R.string.text_tab_message);
				replaceFragment(FragmentMap.class);
				// 检查，如果没有登录则跳转到登录界面
				/*
				 * final UserConfigManager manager =
				 * UserConfigManager.getInstance(); if (manager.getId() <= 0) {
				 * startActivityForResult(new Intent(this, LoginActivity.class),
				 * BaseActivity.REQUEST_OK_LOGIN); }
				 */
			} else if ("service".equals(tag)) {
				mPreviousTabIndex = mCurrentTabIndex;
				mCurrentTabIndex = 1;
				mTitleTextView.setText(R.string.text_tab_service);
				replaceFragment(FragmentSearch.class);
			} else if (tag.equals("personal")) {
				mPreviousTabIndex = mCurrentTabIndex;
				mCurrentTabIndex = 2;
				mTitleTextView.setText(R.string.text_tab_profile);
				replaceFragment(FragmentUser.class, bundle);
				// 检查，如果没有登录则跳转到登录界面
				/*
				 * final UserConfigManager manager =
				 * UserConfigManager.getInstance(); if (manager.getId() <= 0) {
				 * startActivityForResult(new Intent(this, LoginActivity.class),
				 * BaseActivity.REQUEST_OK_LOGIN); }
				 */
			} else if (tag.equals("settings")) {
				mPreviousTabIndex = mCurrentTabIndex;
				mCurrentTabIndex = 3;
				mTitleTextView.setText(R.string.text_tab_setting);
				replaceFragment(FragmentSetting.class);
				// 检查，如果没有登录则跳转到登录界面
				/*
				 * final UserConfigManager manager =
				 * UserConfigManager.getInstance(); if (manager.getId() <= 0) {
				 * startActivityForResult(new Intent(this, LoginActivity.class),
				 * BaseActivity.REQUEST_OK_LOGIN); }
				 */
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("traffic", "activity"+requestCode+" "+resultCode);
		getContentResolver();
		if (resultCode != RESULT_OK )
			return;
		// 拍照
		if (requestCode == CAMERA_SUCCESS) {

			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/myImage/newtemp.jpg");
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null) {
			return;
		}
		// 读取相册缩放图片
		if (requestCode == PHOTO_SUCCESS) {

			startPhotoZoom(data.getData());

		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {

				Bitmap photo = extras.getParcelable("data");
				savePicture(photo);
				// 这边要处理提交头像的操作
				// xxxxxxxxxxxxxx
			}

		}
	}

	/*
	 * 将压缩图片保存到自定义的文件中 Bitmap类有一compress成员，可以把bitmap保存到一个stream中。
	 */
	@SuppressLint("SdCardPath")
	public void savePicture(Bitmap bitmap) {

		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			// Log.v("TestFile",
			// "SD card is not avaiable/writeable right now.");
			return;
		}
		FileOutputStream b = null;
		File file = new File("/sdcard/myImage/");
		file.mkdirs();// 创建文件夹
		String fileName = "/sdcard/myImage/temp.jpg";

		try {
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * 进行图片剪裁
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 640);
		intent.putExtra("outputY", 640);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

	private void scanOldImageFile() {
		File file = new File(Environment.getExternalStorageDirectory(),
				"/myImage/newtemp.jpg");
		File file1 = new File(Environment.getExternalStorageDirectory(),
				"/myImage/temp.jpg");
		if (file.exists() || file1.exists()) {
			file.delete();
			file1.delete();
		}
	}

}
