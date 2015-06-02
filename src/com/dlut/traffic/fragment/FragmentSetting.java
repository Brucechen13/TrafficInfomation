package com.dlut.traffic.fragment;

import com.dlut.traffic.R;
import com.dlut.traffic.service.BackNetService;
import com.dlut.traffic.util.Util;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentSetting extends BaseFragment implements OnCheckedChangeListener {
	
	private CheckBox tuisong;
	private CheckBox zhanghao;
	private RelativeLayout setting;
	
	private Button exit;
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_setting, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		preferences = getActivity().getSharedPreferences(Util.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		editor = preferences.edit();
		
		tuisong = (CheckBox) getActivity().findViewById(R.id.tuisong_checkbox);
		tuisong.setOnCheckedChangeListener(this);
		tuisong.setChecked(preferences.getBoolean("tuisong", true));
		zhanghao = (CheckBox) getActivity().findViewById(R.id.zhanghao_checkbox);
		zhanghao.setOnCheckedChangeListener(this);
		zhanghao.setChecked(preferences.getBoolean("zhanghao", true));
		setting = (RelativeLayout)getActivity().findViewById(R.id.mime_about);
		setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "欢迎使用分享交通",
						Toast.LENGTH_SHORT).show();
			}
		});
		
		getActivity().findViewById(R.id.exit).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SystemExit();
			}
		});
		
	}
	
	public void SystemExit()  
    {  
        AlertDialog exitDialog = new AlertDialog.Builder(this.getActivity()).  
                setTitle("提示").  
                setMessage("是否退出本程序？").  
                setPositiveButton("确定", new DialogInterface.OnClickListener()  
                {  
                    @Override  
                    public void onClick(DialogInterface dialog, int which)  
                    {  
//                        //取消广播  
//                        Intent iRefreshService =new Intent(Intent.ACTION_RUN);  
//                        iRefreshService.setClass(sys_Context, BootReceiver.class);  
//                        iRefreshService.setAction("ASYNCREFRESH");  
//                        PendingIntent sender=PendingIntent.getBroadcast(sys_Context, 0, iRefreshService, 0);  
//                        AlarmManager am=(AlarmManager)sys_Context.getSystemService(sys_Context.ALARM_SERVICE);  
//                        am.cancel(sender);  
                        //退出停止服务  
                    	BackNetService.isrun=false;
                        Intent iRefresh = new Intent(Intent.ACTION_RUN);    
                        iRefresh.setClass( getActivity(), BackNetService.class);  
                        getActivity().stopService(iRefresh);  
//                        //清除通知栏  
//                        CommonUtil common = new CommonUtil();  
//                        common.removeNotification(sys_Context, -1);  
                        Util.clearAllNotification(getActivity());
                          
                        ActivityManager activityManager = (ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE);  
                        activityManager.restartPackage(getActivity().getPackageName());  
                        System.exit(0);  
                    }  
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()  
                {  
                    @Override  
                    public void onClick(DialogInterface dialog, int which)  
                    {  
                        return;  
                    }  
                }).create();  
        exitDialog.show();  
    }  

	@Override
	public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
		// TODO Auto-generated method stub
		switch(buttonView.getId()){
		case R.id.tuisong_checkbox:
			Log.d("traffic", ""+isChecked);
			editor.putBoolean("tuisong", isChecked);
			editor.commit();
			break;
		case R.id.zhanghao_checkbox:
			Log.d("traffic", ""+isChecked);
			AlertDialog exitDialog = new AlertDialog.Builder(this.getActivity()).  
	                setTitle("提示").  
	                setMessage("是否注销保存账号？").  
	                setPositiveButton("确定", new DialogInterface.OnClickListener()  
	                {  
	                    @Override  
	                    public void onClick(DialogInterface dialog, int which)  
	                    {  
							editor.putBoolean("zhanghao", isChecked);
							zhanghao.setEnabled(false);
							editor.commit();
	                    }
	                }).setNegativeButton("取消", new DialogInterface.OnClickListener()  
	                {  
	                    @Override  
	                    public void onClick(DialogInterface dialog, int which)  
	                    {  
	                        return;  
	                    }  
	                }).create();  
	        exitDialog.show();  
			break;
		default:
				break;
		}
		
	}
}
