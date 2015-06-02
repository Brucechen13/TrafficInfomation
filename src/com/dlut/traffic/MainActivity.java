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
	

	/** ��һ�ε�״̬ */
	private int mPreviousTabIndex = 1;
	/** ��ǰ״̬ */
	private int mCurrentTabIndex = 1;

	private static final int PHOTO_SUCCESS = 1;
	private static final int CAMERA_SUCCESS = 2;
	private static final int NONE = 0;
	private static final int PHOTORESOULT = 3;// ���
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
				// ��飬���û�е�¼����ת����¼����
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
				// ��飬���û�е�¼����ת����¼����
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
				// ��飬���û�е�¼����ת����¼����
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
		// ����
		if (requestCode == CAMERA_SUCCESS) {

			// �����ļ�����·��������ڸ�Ŀ¼��
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/myImage/newtemp.jpg");
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null) {
			return;
		}
		// ��ȡ�������ͼƬ
		if (requestCode == PHOTO_SUCCESS) {

			startPhotoZoom(data.getData());

		}
		// ������
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {

				Bitmap photo = extras.getParcelable("data");
				savePicture(photo);
				// ���Ҫ�����ύͷ��Ĳ���
				// xxxxxxxxxxxxxx
			}

		}
	}

	/*
	 * ��ѹ��ͼƬ���浽�Զ�����ļ��� Bitmap����һcompress��Ա�����԰�bitmap���浽һ��stream�С�
	 */
	@SuppressLint("SdCardPath")
	public void savePicture(Bitmap bitmap) {

		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			// Log.v("TestFile",
			// "SD card is not avaiable/writeable right now.");
			return;
		}
		FileOutputStream b = null;
		File file = new File("/sdcard/myImage/");
		file.mkdirs();// �����ļ���
		String fileName = "/sdcard/myImage/temp.jpg";

		try {
			b = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// ������д���ļ�
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
	 * ����ͼƬ����
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
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
