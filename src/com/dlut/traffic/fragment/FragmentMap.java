package com.dlut.traffic.fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.LocationSource.OnLocationChangedListener;
import com.amap.api.maps2d.MapView;
import com.dlut.traffic.R;
import com.dlut.traffic.msg.UploadMsg;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class FragmentMap extends BaseFragment implements LocationSource,
		AMapLocationListener, OnClickListener {

	private TextView tv;
	private MapView mapView;
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private LocationManagerProxy mAMapLocationManager;
	
	private AMapLocation curLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// ����/res/layout/Ŀ¼�µ�fragment_book_detail.xml�����ļ�
		View rootView = inflater.inflate(R.layout.fragment_map, container,
				false);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d("traffic", "Map create");
		super.onActivityCreated(savedInstanceState);
		mapView = (MapView) getView().findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);// �˷���������д

		getView().findViewById(R.id.edit_info).setOnClickListener(this);
		init();
	}

	/**
	 * ��ʼ��AMap����
	 */
	private void init() {
		if (aMap == null) {
			Log.d("traffic", "Map init");
			aMap = mapView.getMap();
			aMap.setLocationSource(this);// ���ö�λ����
			aMap.getUiSettings().setMyLocationButtonEnabled(true);// ����Ĭ�϶�λ��ť�Ƿ���ʾ
			aMap.moveCamera(CameraUpdateFactory.zoomTo(20));// �������ű���
			aMap.setMyLocationEnabled(true);// ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
			// ���ö�λ������Ϊ��λģʽ����λ��AMap.LOCATION_TYPE_LOCATE�������棨AMap.LOCATION_TYPE_MAP_FOLLOW��
			// ��ͼ������������ת��AMap.LOCATION_TYPE_MAP_ROTATE������ģʽ
			//aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		}

	}

	/**
	 * ����������д
	 */ 
	@Override
	public void onResume() {
		Log.d("traffic", "Map Onresume");
		super.onResume();
		mapView.onResume();
	}

	/**
	 * ����������д
	 */
	@Override
	public void onPause() {
		Log.d("traffic", "Map onPause");
		super.onPause();
		mapView.onPause();
		deactivate();
	}

	/**
	 * ����������д
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * ����������д
	 */
	@Override
	public void onDestroy() {
		Log.d("traffic", "Map onDEstroy");
		super.onDestroy();
//		mapView.onDestroy();
	}

	@Override
	public void onLocationChanged(AMapLocation arg0) {
		// TODO Auto-generated method stub
		if (mListener != null && arg0 != null) {
			if (arg0.getAMapException().getErrorCode() == 0) {
				mListener.onLocationChanged(arg0);// ��ʾϵͳС����
				curLocation = arg0;			}
		}
	}

	/**
	 * ���λ
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
		Log.d("traffic", "Map activate");
		if (mAMapLocationManager == null) {
			mAMapLocationManager = LocationManagerProxy.getInstance(this
					.getActivity());
			// �˷���Ϊÿ���̶�ʱ��ᷢ��һ�ζ�λ����Ϊ�˼��ٵ������Ļ������������ģ�
			// ע�����ú��ʵĶ�λʱ��ļ���������ں���ʱ�����removeUpdates()������ȡ����λ����
			// �ڶ�λ�������ں��ʵ��������ڵ���destroy()����
			// ����������ʱ��Ϊ-1����λֻ��һ��
			mAMapLocationManager.requestLocationData(
					LocationProviderProxy.AMapNetwork, 60 * 1000, 10, this);
		}
	}

	/**
	 * ֹͣ��λ
	 */
	@Override
	public void deactivate() {
		mListener = null;
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_info:
			Bundle bundle = new Bundle();
			Log.i("traffic", curLocation.getStreet());
			bundle.putString("country", "�й�");
			bundle.putString("province", curLocation.getProvince());
			bundle.putString("city", curLocation.getCity());
			bundle.putString("road", curLocation.getStreet());
			bundle.putString("address", curLocation.getAddress());
			Log.i("info", curLocation.getAddress() + " "+
					curLocation.getProvince() + " " + curLocation.getCity() 
					+ " " + curLocation.getStreet());

			Intent intent = new Intent(this.getActivity(), UploadMsg.class);
			intent.putExtras(bundle);
			this.startActivity(intent);
			break;
		default:
			break;
		}

	}
}
