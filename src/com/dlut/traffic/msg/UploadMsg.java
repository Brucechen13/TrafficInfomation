package com.dlut.traffic.msg;

import com.dlut.traffic.R;
import com.dlut.traffic.TitleActivity;
import com.dlut.traffic.util.ServerUtil;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UploadMsg extends TitleActivity implements OnClickListener {
	
	private RelativeLayout lukuang;
	private RelativeLayout shigu;
	
	private Button send;
	
	private Bundle bundle;
	
	private String tra_sel;
	
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
	
	private void initView(){
		
		setContentView(R.layout.activity_upload_info);
		setTitle(R.string.edit_traffic);
		showBackwardView(R.string.button_backward, true);
		//showForwardView(R.string.button_submit,true);
		
		bundle = this.getIntent().getExtras();
		
		((TextView)findViewById(R.id.place)).setText("��ǰλ��:"+bundle.getString("address"));
		
		send = (Button)findViewById(R.id.send);
		send.setOnClickListener(this);
		
		lukuang = (RelativeLayout)findViewById(R.id.lukuang);
		shigu = (RelativeLayout)findViewById(R.id.shigu);
		
		//����ID�ҵ�RadioGroupʵ��
		         RadioGroup group = (RadioGroup)this.findViewById(R.id.lk_info);
		         //��һ������������
		         group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		             
		             @Override
		             public void onCheckedChanged(RadioGroup arg0, int arg1) {
		                 // TODO Auto-generated method stub
		                 //��ȡ������ѡ�����ID
		                 int radioButtonId = arg0.getCheckedRadioButtonId();
		                 //����ID��ȡRadioButton��ʵ��
		                RadioButton rb = (RadioButton)UploadMsg.this.findViewById(radioButtonId);
		                 //�����ı����ݣ��Է���ѡ����
		                tra_sel = rb.getText().toString();
		             }
		         });
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		   case R.id.send:
			   if(tra_sel == null){
				   Util.toastMessage(this, "��ѡ��ǰ��ͨ״����");
				   break;
			   }
			   send.setClickable(false);
			   pushMsg();
			   break;
		   default:
			   break;
		}
	}
	private void pushMsg(){
		Log.i("traffic", "upload traffic msg");
		JsonObject json = new JsonObject();
		json.addProperty("qq", Util.userId);
		json.addProperty("country", bundle.getString("country"));
		json.addProperty("province", bundle.getString("province"));
		json.addProperty("city", bundle.getString("city"));
		json.addProperty("road", bundle.getString("road"));
		json.addProperty("address", bundle.getString("address"));
		json.addProperty("tra_level", tra_sel);

		Ion.with(this)
		.load(ServerUtil.addMsgUrl)
		.setJsonObjectBody(json)
		.asJsonObject()
		.setCallback(new FutureCallback<JsonObject>() {
		   @Override
		    public void onCompleted(Exception e, JsonObject result) {
		        // do stuff with the result or error
			   if(e != null){
				   Log.i("traffic", e.toString());
				   return ;
			   }
			   Util.toastMessage(UploadMsg.this, "�ϴ��ɹ�");
			   UploadMsg.this.finish();
		    }
		});
	}
}
