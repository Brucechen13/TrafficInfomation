package com.dlut.traffic.fragment;

import java.io.Console;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import com.dlut.traffic.LoginActivity;
import com.dlut.traffic.R;
import com.dlut.traffic.adapter.MsgBean;
import com.dlut.traffic.adapter.MsgsAdapter;
import com.dlut.traffic.util.CommonUtil;
import com.dlut.traffic.util.ServerUtil;
import com.dlut.traffic.util.UrlParse;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.R.integer;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentSearch extends BaseFragment {

	public static final int HTTP_REQUEST_SUCCESS = -1;
	public static final int HTTP_REQUEST_ERROR = 0;

	private TextView hint;
	private PullToRefreshListView msgList = null;
	private MsgsAdapter adapter;
	private int offset = 0;
	
	private Date date;
	private static SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_search, container,
				false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		msgList = (PullToRefreshListView) getView().findViewById(R.id.msgList);
		hint = (TextView) getView().findViewById(R.id.hint);
		
		adapter = new MsgsAdapter(getView().getContext(),
				getMsgs(null));
		initPullToRefreshListView(msgList, adapter);

	}

	private void initPullToRefreshListView(PullToRefreshListView msgList,
			BaseAdapter adapter) {
		// TODO Auto-generated method stub
		Log.d("traffic", "create list");
		msgList.setMode(Mode.BOTH);
		msgList.setOnRefreshListener(new MyOnRefreshListener2(msgList));
		msgList.setAdapter(adapter);
		date = new Date();
		loadData();
	}
	
	private void loadData(){
		hint.setText(R.string.listloading);
		Ion.with(FragmentSearch.this)
		.load(String.format("%s?skip=%s", ServerUtil.getMsgsUrl, offset))
		.asJsonArray().setCallback(new FutureCallback<JsonArray>() {

			@Override
			public void onCompleted(Exception arg0, JsonArray arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					Log.d("traffic", arg0.toString());
					Toast.makeText(getActivity(), "请检查网络",
							Toast.LENGTH_SHORT).show();
				}else if(arg1.size()==0){
					Toast.makeText(getActivity(), "已经没有数据啦",
							Toast.LENGTH_SHORT).show();
				}else{
					adapter.addNews(getMsgs(arg1));
					offset+=arg1.size();
				}
				adapter.notifyDataSetChanged();
				msgList.onRefreshComplete();   
				hint.setText(R.string.listshow);
			}
		});
	}
	
	private void loadNewestData(){
		hint.setText(R.string.listloading);
		String time = sfd.format(date); 
		time = UrlParse.ParseUrl(time);
		Log.d("traffic", time);
		Ion.with(FragmentSearch.this)
		.load(String.format("%s?date=%s", ServerUtil.getNewestMsgsUrl, time))
		.asJsonArray().setCallback(new FutureCallback<JsonArray>(){

			@Override
			public void onCompleted(Exception arg0, JsonArray arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					Log.d("traffic", arg0.toString());
					Toast.makeText(getActivity(), "请检查网络",
							Toast.LENGTH_SHORT).show();
				}else if(arg1.size()==0){
					Toast.makeText(getActivity(), "已经没有数据啦",
							Toast.LENGTH_SHORT).show();
				}else{
					date = new Date();
					offset+=arg1.size();
					adapter.addFirstNews(getMsgs(arg1));
				}
				adapter.notifyDataSetChanged();	
				msgList.onRefreshComplete();  
				hint.setText(R.string.listshow);
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
			String label = DateUtils.formatDateTime(getView().getContext(),
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);

			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			loadNewestData();

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 上拉加载
			// new GetNewsTask(mPtflv).execute();
			loadData();
		}

	}
	
	public ArrayList<MsgBean> getMsgs(JsonArray res) {
		ArrayList<MsgBean> ret = new ArrayList<MsgBean>();
		if(res == null){
			return ret;
		}
		for (int i = 0; i < res.size(); i++) {
			JsonElement je = res.get(i);
			JsonObject jo = je.getAsJsonObject();
			Log.d("traffic", jo.get("user").getAsJsonObject().get("name").getAsString());
			Date date = Util.parseISODate(jo.get("time").getAsString());
			String[] times = date.toLocaleString().split(" ");
			MsgBean msg = new MsgBean(jo.get("_id").getAsString(), 
					jo.get("user").getAsJsonObject().get("_id").getAsString(),
					jo.get("user").getAsJsonObject().get("name").getAsString(), 
					jo.get("user").getAsJsonObject().get("toupic").getAsString(), 
					times[0], 
					jo.get("content").getAsJsonObject().get("city").getAsString()+" "+
					jo.get("content").getAsJsonObject().get("road").getAsString()+" "+
					jo.get("content").getAsJsonObject().get("tra_level").getAsString(), 
					times[1], 
					jo.get("content").getAsJsonObject().get("address").getAsString(), 
					jo.get("upCount").getAsString(),
					jo.get("ctCount").getAsString());
			ret.add(msg);
		}
		return ret;
	}
	
	class GetNewsTask extends AsyncTask<String, Void, Integer> {
		private PullToRefreshListView mPtrlv;

		public GetNewsTask(PullToRefreshListView ptrlv) {
			this.mPtrlv = ptrlv;
		}

		@Override
		protected Integer doInBackground(String... params) {
			if (CommonUtil.checkWifiConnection(getActivity())) {
				try {
					Thread.sleep(1000);
					return HTTP_REQUEST_SUCCESS;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return HTTP_REQUEST_ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case HTTP_REQUEST_SUCCESS:
				//adapter.addNews(getMsgs(res)(10));
				adapter.notifyDataSetChanged();
				break;
			case HTTP_REQUEST_ERROR:
				Toast.makeText(getActivity(), "请检查网络", Toast.LENGTH_SHORT)
						.show();
				break;
			}
			mPtrlv.onRefreshComplete();
		}

	}


}
