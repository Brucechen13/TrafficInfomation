package com.dlut.traffic.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.dlut.traffic.LoginActivity;
import com.dlut.traffic.R;
import com.dlut.traffic.UserFriends;
import com.dlut.traffic.R.id;
import com.dlut.traffic.msg.UpdateInfo;
import com.dlut.traffic.user.User;
import com.dlut.traffic.user.UserInfoView;
import com.dlut.traffic.util.ServerUtil;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentUser extends BaseFragment implements OnClickListener {

	private RelativeLayout tou_rl;
	private RelativeLayout nick_rl;
	private RelativeLayout signature_rl;
	private RelativeLayout score_rl;

	private RelativeLayout friends_rl;
	private RelativeLayout msg_rl;

	private RelativeLayout gender_rl;
	private RelativeLayout area_rl;
	private RelativeLayout job_rl;
	private RelativeLayout company_rl;

	private ImageView detail_img;
	private TextView nick_name;
	private TextView signature_tv;
	private TextView score_tv;
	private TextView area_tv;
	private TextView gender_tv;
	private TextView company_tv;
	private TextView job_tv;
	private Button refresh;

	private String signature;
	private String nickname;
	private String score;
	private String gender;
	private String area;
	private String company;
	private String job;

	private static final int PHOTO_SUCCESS = 1;
	private static final int CAMERA_SUCCESS = 2;
	private static final int NONE = 0;
	private static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";

	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		user = (User) getArguments().get("user");
		Log.d("SDKQQAgentPref", user.getNickName());
		Log.d("SDKQQAgentPref", user.getCity());
		return inflater.inflate(R.layout.fragment_user, container, false);
	}

	private void showText() {
		signature_tv.setText(user.getSignature());
		score_tv.setText("" + user.getScore());
		area_tv.setText(user.getCity());
		company_tv.setText(user.getCompany());
		job_tv.setText(user.getJob());
		gender_tv.setText(user.getGender());
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		nick_name = (TextView) view.findViewById(R.id.nick_name);
		nick_name.setText(user.getNickName());
		signature_tv = (TextView) view.findViewById(R.id.signature_tv);
		score_tv = (TextView) view.findViewById(R.id.score_tv);
		area_tv = (TextView) view.findViewById(R.id.area_tv);
		company_tv = (TextView) view.findViewById(R.id.company_tv);
		job_tv = (TextView) view.findViewById(R.id.job_tv);
		gender_tv = (TextView) view.findViewById(R.id.gender_tv);
		detail_img = (ImageView) view.findViewById(R.id.detail_img);
		showText();

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

		tou_rl = (RelativeLayout) view.findViewById(R.id.tou_rl);
		tou_rl.setOnClickListener(this);
		nick_rl = (RelativeLayout) view.findViewById(R.id.nick_rl);
		nick_rl.setOnClickListener(this);
		signature_rl = (RelativeLayout) view.findViewById(R.id.signature_rl);
		signature_rl.setOnClickListener(this);
		area_rl = (RelativeLayout) view.findViewById(R.id.area_rl);
		area_rl.setOnClickListener(this);
		score_rl = (RelativeLayout) view.findViewById(R.id.score_rl);
		score_rl.setOnClickListener(this);
		company_rl = (RelativeLayout) view.findViewById(R.id.company_rl);
		company_rl.setOnClickListener(this);
		job_rl = (RelativeLayout) view.findViewById(R.id.job_rl);
		job_rl.setOnClickListener(this);
		gender_rl = (RelativeLayout) view.findViewById(R.id.gender_rl);
		gender_rl.setOnClickListener(this);
		refresh = (Button) view.findViewById(R.id.refresh);
		refresh.setOnClickListener(this);
		
		friends_rl = (RelativeLayout) view.findViewById(R.id.friends_rl);
		friends_rl.setOnClickListener(this);
		
		msg_rl = (RelativeLayout) view.findViewById(R.id.msg_rl);
		msg_rl.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final Intent intent = new Intent(this.getActivity(), UpdateInfo.class);
		final Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.signature_rl:
			signature = signature_tv.getText().toString().trim();
			bundle.putString("title", "个性签名");
			bundle.putString("mimecontent", signature);
			bundle.putInt("num", 75);
			intent.putExtras(bundle);
			startActivityForResult(intent, Util.SIGNATURE);

			break;

		case R.id.gender_rl:
			nickname = nick_name.getText().toString().trim();
			bundle.putString("title", "性别");
			bundle.putString("mimecontent", nickname);
			bundle.putInt("num", 15);
			intent.putExtras(bundle);
			startActivityForResult(intent, Util.GENDER);
			break;

		case R.id.area_rl:
			String area = area_tv.getText().toString().trim();
			bundle.putString("title", "居住城市");
			bundle.putString("mimecontent", area);
			bundle.putInt("num", 30);
			intent.putExtras(bundle);
			startActivityForResult(intent, Util.AREA);
			break;

		case R.id.company_rl:
			String company = company_tv.getText().toString().trim();
			bundle.putString("title", "公司");
			bundle.putString("mimecontent", company);
			bundle.putInt("num", 30);
			intent.putExtras(bundle);
			startActivityForResult(intent, Util.COMPANY);
			break;

		case R.id.job_rl:
			String work = job_tv.getText().toString().trim();
			bundle.putString("title", "职业");
			bundle.putString("mimecontent", work);
			bundle.putInt("num", 30);
			intent.putExtras(bundle);
			startActivityForResult(intent, Util.JOB);
			break;
		case R.id.score_rl:
			if (user.getScore() < 10) {
				Util.toastMessage(this.getActivity(), "积分不足");
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						FragmentUser.this.getActivity());

				builder.setIcon(R.drawable.ic_launcher);
				builder.setTitle("提示");
				builder.setMessage("请选择你要进行的操作");
				// 第一个按钮
				builder.setPositiveButton("搜索好友",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								Ion.with(FragmentUser.this)
								.load(String.format("%s?qq=%s", ServerUtil.addFriendUrl,
										Util.userId)).asJsonObject()
								.setCallback(new FutureCallback<JsonObject>() {
									@Override
									public void onCompleted(Exception e, JsonObject result) {
										// TODO Auto-generated method stub
										if (e != null) {
											Log.d("traffic", e.toString());
											Util.toastMessage(
													FragmentUser.this.getActivity(), "网络出错");
											return;
										}else if(result.has("suc")){
											Util.toastMessage(
													FragmentUser.this.getActivity(), "暂无好友");
										}
										User friend = new User();
										friend.parseJson(result);
										Log.d("traffic", "login:" + friend.getNickName());
										Intent intent=new Intent(FragmentUser.this.getActivity(),
												UserInfoView.class);
										intent.putExtra("user", friend);
										FragmentUser.this.startActivity(intent);		
									}
								});
							}
						});
				// 中间的按钮
				builder.setNeutralButton("发布任务",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								// 提示信息
								Util.toastMessage(
										FragmentUser.this.getActivity(), "发布任务");
								bundle.putString("title", "发布任务");
								bundle.putInt("num", 30);
								intent.putExtras(bundle);
								startActivityForResult(intent, Util.TASK);
							}
						});
				// 第三个按钮
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								// 提示信息
							}
						});

				// Diglog的显示
				builder.create().show();
			}
			break;
		case R.id.friends_rl:
			Intent intent2 = new Intent(this.getActivity(), UserFriends.class);
			startActivity(intent2);
			break;
		case R.id.msg_rl:
			break;
		case R.id.refresh:
			Ion.with(FragmentUser.this)
					.load(String.format("%s?qq=%s", ServerUtil.loginUrl,
							Util.userId)).asJsonObject()
					.setCallback(new FutureCallback<JsonObject>() {
						@Override
						public void onCompleted(Exception e, JsonObject result) {
							// TODO Auto-generated method stub
							if (e != null) {
								Log.d("traffic", e.toString());
								return;
							}
							Log.d("traffic", "login:" + result);
							user.parseJson(result);
							showText();
						}
					});
			break;
		case R.id.tou_rl:

			final CharSequence[] items = { "手机相册", "相机拍摄" };
			AlertDialog dlg = new AlertDialog.Builder(this.getActivity())
					.setTitle("选择图片")
					.setItems(items, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							// 这里item是根据选择的方式,
							// 在items数组里面定义了两种方式, 拍照的下标为1所以就调用拍照方法
							if (item == 1) {
								Intent getImageByCamera = new Intent(
										MediaStore.ACTION_IMAGE_CAPTURE);

								getImageByCamera.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												"myImage/newtemp.jpg")));

								startActivityForResult(getImageByCamera,
										CAMERA_SUCCESS);
							} else {

								Intent getImage = new Intent(
										Intent.ACTION_GET_CONTENT);
								getImage.addCategory(Intent.CATEGORY_OPENABLE);
								getImage.setType("image/*");
								startActivityForResult(getImage, PHOTO_SUCCESS);
							}
						}
					}).create();
			dlg.show();

			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != getActivity().RESULT_OK) {
			return;
		}
		String info = data.getExtras().getString("result");
		Log.d("traffic", "fragment" + requestCode + " " + resultCode + info);
		switch (requestCode) {
		case Util.SIGNATURE:
			user.setSignature(info);
			signature_tv.setText(info);
			UpdateInfo("signature", info);
			break;
		case Util.GENDER:
			user.setGender(info);
			gender_tv.setText(info);
			UpdateInfo("gender", info);
			break;
		case Util.AREA:
			user.setCity(info);
			area_tv.setText(info);
			UpdateInfo("city", info);
			break;
		case Util.JOB:
			user.setJob(info);
			job_tv.setText(info);
			UpdateInfo("job", info);
			break;
		case Util.COMPANY:
			user.setCompany(info);
			company_tv.setText(info);
			UpdateInfo("company", info);
			break;
		default:
			break;
		}
	}

	private void UpdateInfo(String name, String value) {
		Ion.with(this)
				.load(String.format("%s?qq=%s&name=%s&value=%s",
						ServerUtil.updateUerUrl, Util.userId, name, value))
				.asJsonObject().setCallback(new FutureCallback<JsonObject>() {
					@Override
					public void onCompleted(Exception e, JsonObject result) {
						// TODO Auto-generated method stub
						if (e != null) {
							Log.d("traffic", e.toString());
							Util.toastMessage(FragmentUser.this.getActivity(),
									"网络异常: " + e.toString());
							return;
						}
						Log.d("traffic", "login:" + result);
						if (result.get("suc").getAsString().equals("true")) {
							Util.toastMessage(FragmentUser.this.getActivity(),
									"更新成功");
						} else {
							Util.toastMessage(FragmentUser.this.getActivity(),
									"更新失败：" + result.get("suc").getAsString());
						}
					}

				});
	}
}
