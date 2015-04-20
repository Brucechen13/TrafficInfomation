package com.dlut.traffic.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.dlut.traffic.R;
import com.dlut.traffic.msg.UpdateMsg;
import com.dlut.traffic.user.User;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentUser extends BaseFragment implements OnClickListener{
	
	private RelativeLayout tou_rl;
	private RelativeLayout nick_rl;
	private RelativeLayout signature_rl;
	private RelativeLayout score_rl;
	
	private RelativeLayout guiji_rl;
	private RelativeLayout msg_rl;
	private RelativeLayout comment_rl;
	
	private RelativeLayout gender_rl;
	private RelativeLayout area_rl;
	private RelativeLayout job_rl;
	private RelativeLayout company_rl;
	
	private ImageView detail_img;
	private TextView nick_name;   
	private TextView signature_tv;
	private TextView score_tv;
	private TextView area_tv;
	private TextView gender_tv;
	private TextView company_tv;
	private TextView job_tv;
	
	private String signature;
    private String nickname;
    private String score;
    private String gender;
    private String area;
    private String company;
    private String job;
	
	private static final int PHOTO_SUCCESS = 1;  
	private static final int CAMERA_SUCCESS = 2;   
	private static final int NONE=0;
	private static final int PHOTORESOULT = 3;// 结果	
	public static final String IMAGE_UNSPECIFIED = "image/*";
	
	private User user;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		user = (User)getArguments().get("user");
		Log.d("SDKQQAgentPref", user.getNickName());
		Log.d("SDKQQAgentPref", user.getCity());
		return inflater.inflate(R.layout.fragment_user, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		nick_name = (TextView)view.findViewById(R.id.nick_name);
		nick_name.setText(user.getNickName());
		signature_tv = (TextView)view.findViewById(R.id.signature_tv);
		signature_tv.setText(user.getSignature());
		score_tv = (TextView)view.findViewById(R.id.score_tv);
		score_tv.setText(""+user.getScore());
		area_tv = (TextView)view.findViewById(R.id.area_tv);
		area_tv.setText(user.getCity());
		company_tv = (TextView)view.findViewById(R.id.company_tv);
		company_tv.setText(user.getCompany());
		job_tv = (TextView)view.findViewById(R.id.job_tv);
		job_tv.setText(user.getJob());
		detail_img = (ImageView)view.findViewById(R.id.detail_img);
		
		Ion.with(this)
		.load(user.getPhoto())
		.withBitmap()
		//.placeholder(R.drawable.placeholder_image)
		.error(R.drawable.tou)
		.intoImageView(detail_img).setCallback(new FutureCallback<ImageView>() {

			@Override
			public void onCompleted(Exception arg0, ImageView arg1) {
				// TODO Auto-generated method stub
				Log.d("SDKQQAgentPref", arg0.toString());
			}
			
		});
		
		
		tou_rl = (RelativeLayout)view.findViewById(R.id.tou_rl);
		tou_rl.setOnClickListener(this);
		nick_rl = (RelativeLayout)view.findViewById(R.id.nick_rl);
		nick_rl.setOnClickListener(this);
		signature_rl = (RelativeLayout)view.findViewById(R.id.signature_rl);
		signature_rl.setOnClickListener(this);
		area_rl = (RelativeLayout)view.findViewById(R.id.area_rl);
		area_rl.setOnClickListener(this);
		score_rl = (RelativeLayout)view.findViewById(R.id.score_rl);
		score_rl.setOnClickListener(this);
		company_rl = (RelativeLayout)view.findViewById(R.id.company_rl);
		company_rl.setOnClickListener(this);
		job_rl = (RelativeLayout)view.findViewById(R.id.job_rl);
		job_rl.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) { 
		   case R.id.signature_rl:
			   signature=signature_tv.getText().toString().trim();
		        Intent intent=new Intent(this.getActivity(), UpdateMsg.class);
		        Bundle bundle= new Bundle();
		        bundle.putInt("resultcode", 8);
				bundle.putString("title","个性签名");
				bundle.putString("mimecontent",signature);
				bundle.putInt("num", 75);
				intent.putExtras(bundle);        
			    startActivityForResult(intent, 8);
				
				break;
				
		        case R.id.nick_rl:
		    		nickname=nick_name.getText().toString().trim();
		            Intent intent1=new Intent(this.getActivity(), UpdateMsg.class);
		            Bundle bundle1= new Bundle();
		            bundle1.putInt("resultcode", 9);
		            bundle1.putString("title","昵称");
		    		bundle1.putString("mimecontent",nickname);
		    		bundle1.putInt("num", 15);
		    		intent1.putExtras(bundle1);        
		    		startActivityForResult(intent1, 9);
		    		
		    		break; 

		        
		    		
		        case R.id.company_rl:
		    		String company=company_tv.getText().toString().trim();
		            Intent intent14=new Intent(this.getActivity(), UpdateMsg.class); 
		            Bundle bundle14= new Bundle();
		            bundle14.putInt("resultcode", 14);
		            bundle14.putString("title","公司");
		            bundle14.putString("mimecontent",company);
		            bundle14.putInt("num", 30);
		            intent14.putExtras(bundle14);        
		    		startActivityForResult(intent14, 14);   		
		    		break;
				
		    		
		        case R.id.job_rl:
		    		String work=job_tv.getText().toString().trim();
		            Intent intent15=new Intent(this.getActivity(), UpdateMsg.class); 
		            Bundle bundle15= new Bundle();
		            bundle15.putInt("resultcode", 15);
		            bundle15.putString("title","职业");
		            bundle15.putString("mimecontent",work);
		            bundle15.putInt("num", 30);
		            intent15.putExtras(bundle15);        
		    		startActivityForResult(intent15, 15);   		
		    		break;
		    		
		    		
		    		
		        case R.id.tou_rl:
		    		
		        	 final CharSequence[] items = { "手机相册", "相机拍摄" };  
				        AlertDialog dlg = new AlertDialog.Builder(this.getActivity()).setTitle("选择图片").setItems(items,   
				            new DialogInterface.OnClickListener() {   
				                public void onClick(DialogInterface dialog,int item) {   
				                    //这里item是根据选择的方式,  
				                    //在items数组里面定义了两种方式, 拍照的下标为1所以就调用拍照方法         
				                    if(item==1){   
				                        Intent getImageByCamera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				                        
				                        getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				        						Environment.getExternalStorageDirectory(), "myImage/newtemp.jpg")));
				                        
				                        startActivityForResult(getImageByCamera, CAMERA_SUCCESS);     
				                    }else{ 
				                    	
				                        Intent getImage = new Intent(Intent.ACTION_GET_CONTENT); 		                       		                	                        
				                        getImage.addCategory(Intent.CATEGORY_OPENABLE);   
				                        getImage.setType("image/*");   
				                        startActivityForResult(getImage, PHOTO_SUCCESS);   
				                     }   
				                }   
				            }).create();   
				        dlg.show(); 
		    		
		    		break;	
		
		  
	      
		default:
			break;
		}
	}
}
