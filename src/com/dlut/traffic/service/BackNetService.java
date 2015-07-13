package com.dlut.traffic.service;

import com.dlut.traffic.R;
import com.dlut.traffic.util.ServerUtil;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class BackNetService extends Service{
	
	public static boolean isrun = false;
	public static int notifyId = 1;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        isrun = false;
    }


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		int i = super.onStartCommand(intent, flags, startId);
		isrun = true;
		LoadTasks();
        return i;
		
	}
	
	private void LoadTasks(){
		Log.d("traffic", "接受推送");
		Ion.with(this)
		.load(String.format("%s?qq=%s", ServerUtil.getTasksUrl, Util.userId))
		.asJsonArray().setCallback(new FutureCallback<JsonArray>() {
		   @Override
		    public void onCompleted(Exception e, JsonArray result) {
		        // do stuff with the result or error
			   if(e != null){
				   Log.d("traffic", "Service"+e.toString());
				   return ;
			   }
			   Log.d("traffic","Service"+result.toString());	 
			   for (int i = 0; i < result.size(); i++) {
					JsonElement je = result.get(i);
					JsonObject jo = je.getAsJsonObject();
					Util.showIntentActivityNotify(BackNetService.this.getApplicationContext(),
							jo.get("user").getAsString(),
							jo.get("type").getAsString(),
							jo.has("content")?jo.get("content").getAsString():"内容为空");
			   }
			   
			   if(isrun){
				   try {
		                Thread.sleep(2000);
		            } catch (Exception a) {} 
				   LoadTasks();
			   }
		    }
		});
	}


}
