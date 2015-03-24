package com.dlut.traffic;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TrafficListFragment extends ListFragment {
	
	private Callbacks mCallbacks;
	// ����һ���ص��ӿڣ���Fragment����Activity��Ҫʵ�ָýӿ�
	// ��Fragment��ͨ���ýӿ��������ڵ�Activity����
	public interface Callbacks
	{
		public void onItemSelected(Integer id);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// Ϊ��ListFragment����Adapter
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, new String[]{"��ͼ","·��"}));
	}
	// ����Fragment�����ӡ���ʾ��Activityʱ���ص��÷���
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		// ���Activityû��ʵ��Callbacks�ӿڣ��׳��쳣
		if (!(activity instanceof Callbacks))
		{
			throw new IllegalStateException(
				"BookListFragment���ڵ�Activity����ʵ��Callbacks�ӿ�!");
		}
		// �Ѹ�Activity����Callbacks����
		mCallbacks = (Callbacks)activity;
	}
	// ����Fragment����������Activity�б�ɾ��ʱ�ص��÷���
	@Override
	public void onDetach()
	{
		super.onDetach();
		// ��mCallbacks��Ϊnull��
		mCallbacks = null;
	}
	// ���û����ĳ�б���ʱ�����ûص�����
	@Override
	public void onListItemClick(ListView listView
		, View view, int position, long id)
	{
		super.onListItemClick(listView, view, position, id);
		// ����mCallbacks��onItemSelected����
		mCallbacks.onItemSelected(position);
	}

	public void setActivateOnItemClick(boolean activateOnItemClick)
	{
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

}