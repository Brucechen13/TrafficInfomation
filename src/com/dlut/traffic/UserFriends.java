package com.dlut.traffic;

import java.util.ArrayList;
import java.util.Date;

import com.dlut.traffic.adapter.FriendsAdapter;
import com.dlut.traffic.adapter.FriendBean;
import com.dlut.traffic.util.ServerUtil;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserFriends extends TitleActivity implements OnClickListener {

	private PullToRefreshListView friendList = null;
	private FriendsAdapter adapter;
	private int offset = 0;
	private TextView hint;

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

	private void initView() {
		setContentView(R.layout.activity_user_friends);
		showBackwardView(R.string.button_backward, true);
		setTitle(R.string.listfriend);

		friendList = (PullToRefreshListView) findViewById(R.id.friendList);
		hint = (TextView) findViewById(R.id.hint);

		adapter = new FriendsAdapter(this, getFriends(null));
		initPullToRefreshListView(friendList, adapter);
	}

	private void initPullToRefreshListView(PullToRefreshListView msgList,
			BaseAdapter adapter) {
		// TODO Auto-generated method stub
		Log.d("traffic", "create list");
		msgList.setMode(Mode.BOTH);
		msgList.setOnRefreshListener(new MyOnRefreshListener2(msgList));
		msgList.setAdapter(adapter);
		loadData();
	}

	private void loadData() {
		hint.setText(R.string.listloading);
		Ion.with(this)
				.load(String
						.format("%s?qq=%s", ServerUtil.getFriendsUrl, Util.userId))
				.asJsonObject().setCallback(new FutureCallback<JsonObject>() {

					@Override
					public void onCompleted(Exception arg0, JsonObject arg1) {
						// TODO Auto-generated method stub
						if (arg0 != null) {
							Log.d("traffic", arg0.toString());
							Toast.makeText(UserFriends.this, "请检查网络",
									Toast.LENGTH_SHORT).show();
						} else{
							JsonArray jaArray = arg1.getAsJsonArray("friends");
							if (jaArray.size() == 0) {
							Toast.makeText(UserFriends.this, "已经没有数据啦",
									Toast.LENGTH_SHORT).show();
							} else {
								adapter.addNews(getFriends(jaArray));
								offset += jaArray.size();
							}
						}
						adapter.notifyDataSetChanged();
						friendList.onRefreshComplete();
						hint.setText(R.string.listfriend);
					}
				});
	}

	class MyOnRefreshListener2 implements OnRefreshListener2<ListView> {

		private PullToRefreshListView mPtflv;

		public MyOnRefreshListener2(PullToRefreshListView ptflv) {
			this.mPtflv = ptflv;
		}

		@Override
		public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 下拉刷新
			String label = DateUtils.formatDateTime(UserFriends.this,
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);

			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 上拉加载
			// new GetNewsTask(mPtflv).execute();
			loadData();
		}

	}

	public ArrayList<FriendBean> getFriends(JsonArray res) {
		ArrayList<FriendBean> ret = new ArrayList<FriendBean>();
		if (res == null) {
			return ret;
		}
		for (int i = 0; i < res.size(); i++) {
			JsonElement je = res.get(i);
			JsonObject jo = je.getAsJsonObject();
			Log.d("traffic", jo.toString());
			FriendBean friendBean = new FriendBean(jo.get("_id").getAsString(), 
					jo.has("toupic")?jo.get("toupic").getAsString():"", 
							jo.has("name")?jo.get("name").getAsString():"",
						jo.has("signature")?jo.get("signature").getAsString():"用户还没有留下签名");
			ret.add(friendBean);
		}
		return ret;
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
