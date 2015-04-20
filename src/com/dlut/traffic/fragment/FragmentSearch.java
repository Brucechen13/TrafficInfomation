package com.dlut.traffic.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dlut.traffic.R;
import com.dlut.traffic.adapter.UploadInfoAdapter;
import com.dlut.traffic.util.CommonUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
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

	private TextView tv;
	private PullToRefreshListView msgList = null;
	private UploadInfoAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = new UploadInfoAdapter(getView().getContext(), getSimulationNews(10));
		//ListView lt = (ListView)getView().findViewById(R.id.myList);
		//lt.setAdapter(adapter);
		msgList = (PullToRefreshListView) getView()
				.findViewById(R.id.msgList);
		initPullToRefreshListView(msgList, adapter);
		

	}

	private void initPullToRefreshListView(PullToRefreshListView msgList2,
			BaseAdapter adapter) {
		// TODO Auto-generated method stub
		msgList2.setMode(Mode.BOTH);
		msgList2.setOnRefreshListener(new MyOnRefreshListener2(msgList2));
		msgList2.setAdapter(adapter);
		
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
			new GetNewsTask(mPtflv).execute();

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 上拉加载
			new GetNewsTask(mPtflv).execute();
		}

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
				adapter.addNews(getSimulationNews(10));
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
	
	public ArrayList<HashMap<String, String>> getSimulationNews(int n) {
		ArrayList<HashMap<String, String>> ret = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> hm;
		for (int i = 0; i < n; i++) {
			hm = new HashMap<String, String>();
			hm.put("username", "chenchi");
			hm.put("contenttime", "2015-1-3");
			hm.put("content", "halou");
			hm.put("time", "09:30");
			hm.put("place", "某地");
			hm.put("good_num", "13");
			hm.put("comment_num", "31");
			ret.add(hm);
		}
		return ret;
	}

}
