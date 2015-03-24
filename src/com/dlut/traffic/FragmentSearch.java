package com.dlut.traffic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FragmentSearch extends Fragment {

	private TextView tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tv = (TextView) getView().findViewById(R.id.titleTv);
		tv.setText("ËÑË÷");
		
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		Map<String, Object> listItem = new HashMap<String, Object>();
		listItem.put("username", "chenchi");
		listItem.put("contenttime", "2015-1-3");
		listItem.put("content", "halou");
		listItem.put("time", "09:30");
		listItem.put("place", "Ä³µØ");
		listItem.put("good_num", "13");
		listItem.put("comment_num", "31");
		listItems.add(listItem);
		listItems.add(listItem);
	//	SimpleAdapter adapter = new SimpleAdapter(getView().getContext(), listItems, 
		//		R.layout.msg_item, new String[]{"username", "contenttime","time", "content",
			//"place", "good_num", "comment_num"},
				//new int[]{R.id.username, R.id.contenttime, R.id.time,
	//		R.id.content, R.id.place_tv, R.id.good_num, R.id.comment_num});
		BaseAdapter adapter = new UploadInfoAdapter(getView().getContext(), listItems);
		ListView lt = (ListView)getView().findViewById(R.id.myList);
		lt.setAdapter(adapter);
		

	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
