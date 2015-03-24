package com.dlut.traffic;



import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class MainActivity extends Activity implements OnClickListener {

	private Fragment[] mFragments;
	private RadioGroup bottomRg;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private RadioButton rbOne, rbTwo, rbThree, rbFour;
	private RelativeLayout mime_layout;
	private RelativeLayout mime_guiji;
	private RelativeLayout mime_shangchuan;
	private RelativeLayout mime_pinglun;
	private RelativeLayout mime_about;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mFragments = new Fragment[3];
		fragmentManager = getFragmentManager();
		mFragments[0] = fragmentManager.findFragmentById(R.id.fragement_main);
		mFragments[1] = fragmentManager.findFragmentById(R.id.fragement_search);
		mFragments[2] = fragmentManager
				.findFragmentById(R.id.fragement_setting);
		fragmentTransaction = fragmentManager.beginTransaction()
				.hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
		fragmentTransaction.show(mFragments[0]).commit();
		setFragmentIndicator();
		
		mime_layout = (RelativeLayout)findViewById(R.id.mime_layout);
		mime_layout.setOnClickListener(this);
		mime_guiji = (RelativeLayout)findViewById(R.id.mime_guiji);
		mime_guiji.setOnClickListener(this);
		mime_shangchuan = (RelativeLayout)findViewById(R.id.mime_shangchuan);
		mime_shangchuan.setOnClickListener(this);
		mime_pinglun = (RelativeLayout)findViewById(R.id.mime_pinglun);
		mime_pinglun.setOnClickListener(this);
		mime_about = (RelativeLayout)findViewById(R.id.mime_about);
		mime_about.setOnClickListener(this);
	}

	private void setFragmentIndicator() {

		bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
		rbOne = (RadioButton) findViewById(R.id.rbOne);
		rbTwo = (RadioButton) findViewById(R.id.rbTwo);
		rbThree = (RadioButton) findViewById(R.id.rbThree);

		bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				fragmentTransaction = fragmentManager.beginTransaction()
						.hide(mFragments[0]).hide(mFragments[1])
						.hide(mFragments[2]);
				switch (checkedId) {
				case R.id.rbOne:
					fragmentTransaction.show(mFragments[0]).commit();
					break;

				case R.id.rbTwo:
					fragmentTransaction.show(mFragments[1]).commit();
					break;

				case R.id.rbThree:
					fragmentTransaction.show(mFragments[2]).commit();
					break;

				default:
					break;
				}
			}
		});
	}
	//上传按钮事件
		public void upload(View v){
			Intent intent = new Intent(this, UploadInfo.class);
			this.startActivity(intent);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.mime_layout:
				Intent intent=new Intent(this, UserInfoView.class);
				startActivity(intent);				
				break;
			default:
					break;
			
			}
			
		}

}
