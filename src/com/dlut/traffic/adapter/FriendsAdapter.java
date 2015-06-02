package com.dlut.traffic.adapter;

import java.util.Iterator;
import java.util.List;

import com.dlut.traffic.R;
import com.dlut.traffic.UserFriends;
import com.dlut.traffic.adapter.MsgsAdapter.ViewHolder;
import com.dlut.traffic.user.UserInfoView;
import com.dlut.traffic.util.ServerUtil;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FriendsAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<FriendBean> data;
	private Context mContext;
	private View view[] = null;

	public FriendsAdapter(Context context, List<FriendBean> list) {
		this.data = list;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mContext = context;
		// Constants.mImageLoader=new ImageLoader(context);
	}

	public class ViewHolder {
		public ImageView userimg_iv;
		public TextView name;
		public TextView signature;
		public Button delete;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.friend_item, parent, false);
			holder.name = (TextView) convertView.findViewById(R.id.username);
			holder.userimg_iv = (ImageView) convertView
					.findViewById(R.id.userimg_iv);
			holder.signature = (TextView) convertView
					.findViewById(R.id.signature);
			holder.delete = (Button) convertView.findViewById(R.id.delete);
			convertView.setTag(holder);
			convertView.setId(position);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		OnClickListener userInfoListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, UserInfoView.class);
				intent.putExtra("userid", data.get(position).getFirendId());
				mContext.startActivity(intent);
			}
		};
		holder.userimg_iv.setOnClickListener(userInfoListener);
		holder.name.setOnClickListener(userInfoListener);
		FriendBean friendBean = data.get(position);
		holder.name.setText(friendBean.getName());
		holder.signature.setText(friendBean.getSignature());
		Ion.with(this.mContext).load(data.get(position).getPic()).withBitmap()
				// .placeholder(R.drawable.placeholder_image)
				.error(R.drawable.tou).intoImageView(holder.userimg_iv)
				.setCallback(new FutureCallback<ImageView>() {
					@Override
					public void onCompleted(Exception arg0, ImageView arg1) {
						// TODO Auto-generated method stub
						if (arg0 != null) {
							Log.d("traffic", arg0.toString());
						}
					}

				});
		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog delDialog = new AlertDialog.Builder(
						FriendsAdapter.this.mContext)
						.setTitle("提示")
						.setMessage("是否删除好友？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										removeFriend(data.get(position)
												.getFirendId());
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										return;
									}
								}).create();
				delDialog.show();

			}
		});
		return convertView;

	}

	public void addNews(List<FriendBean> addNews) {
		data.addAll(addNews);
	}

	public void addFirstNews(List<FriendBean> addNews) {
		data.addAll(0, addNews);
	}

	private void removeFriend(final String friendId) {
		Ion.with(FriendsAdapter.this.mContext)
				.load(String.format("%s?qq=%s&friend=%s",
						ServerUtil.removeFriendUrl, Util.userId, friendId))
				.asJsonObject().setCallback(new FutureCallback<JsonObject>() {

					@Override
					public void onCompleted(Exception arg0, JsonObject arg1) {
						// TODO
						// Auto-generated
						// method stub
						if (arg0 != null) {
							Log.d("traffic", arg0.toString());
							Toast.makeText(FriendsAdapter.this.mContext,
									"请检查网络", Toast.LENGTH_SHORT).show();
						} else if ("true".equals(arg1.get("suc").getAsString())) {
							Toast.makeText(FriendsAdapter.this.mContext,
									"删除成功", Toast.LENGTH_SHORT).show();
							Iterator<FriendBean> iterator = data.iterator();
							while(iterator.hasNext()){
								FriendBean bean = iterator.next();
								if(bean.getFirendId().equals(friendId)){
									iterator.remove();
									FriendsAdapter.this.notifyDataSetChanged();
									return;
								}
							}
						} else {
							Toast.makeText(FriendsAdapter.this.mContext,
									"删除失败", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

}
