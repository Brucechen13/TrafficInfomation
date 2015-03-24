package com.dlut.traffic;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserInfo extends Activity implements OnClickListener {
	
	private RelativeLayout tou_rl;
	private RelativeLayout nick_rl;
	private RelativeLayout password_rl;
	private RelativeLayout area_rl;
	private RelativeLayout signatrue_rl;
	private RelativeLayout school_rl;
	private RelativeLayout company_rl;
	private RelativeLayout job_rl;
	private RelativeLayout interest_rl;
	
	private TextView mimedetail_back;
	private ImageView detail_img;
	private TextView nick_name;   
	private TextView password_tv;
	private TextView score_tv;
	private TextView place_tv;
	private TextView write_tv;
	private TextView school_tv;
	private TextView company_tv;
	private TextView job_tv;
	private TextView interest_tv;


	private String password;
    private String nickname;
    private String score;
    private String sex;
    private String place;
    private String write;
    private String school;
    private String company;
    private String job;
    private String interest;
    
    private static final int PHOTO_SUCCESS = 1;  
	private static final int CAMERA_SUCCESS = 2;   
	private static final int NONE=0;
	private static final int PHOTORESOULT = 3;// 结果	
	public static final String IMAGE_UNSPECIFIED = "image/*";
    
    
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		initView();
		 
	}
	
	private void initView(){
		mimedetail_back=(TextView)findViewById(R.id.mimedetail_back);
		mimedetail_back.setOnClickListener(this);
		
		nick_name = (TextView)findViewById(R.id.nick_name);
		password_tv = (TextView)findViewById(R.id.password_tv);
		score_tv = (TextView)findViewById(R.id.score_tv);
		place_tv = (TextView)findViewById(R.id.place_tv);
		write_tv = (TextView)findViewById(R.id.write_tv);
		school_tv = (TextView)findViewById(R.id.school_tv);
		company_tv = (TextView)findViewById(R.id.company_tv);
		job_tv = (TextView)findViewById(R.id.job_tv);
		interest_tv = (TextView)findViewById(R.id.interest_tv);
		
		
		tou_rl = (RelativeLayout)findViewById(R.id.tou_rl);
		tou_rl.setOnClickListener(this);
		nick_rl = (RelativeLayout)findViewById(R.id.nick_rl);
		nick_rl.setOnClickListener(this);
		password_rl = (RelativeLayout)findViewById(R.id.password_rl);
		password_rl.setOnClickListener(this);
		area_rl = (RelativeLayout)findViewById(R.id.area_rl);
		area_rl.setOnClickListener(this);
		signatrue_rl = (RelativeLayout)findViewById(R.id.signature_rl);
		signatrue_rl.setOnClickListener(this);
		school_rl = (RelativeLayout)findViewById(R.id.school_rl);
		school_rl.setOnClickListener(this);
		company_rl = (RelativeLayout)findViewById(R.id.company_rl);
		company_rl.setOnClickListener(this);
		job_rl = (RelativeLayout)findViewById(R.id.job_rl);
		job_rl.setOnClickListener(this);
		interest_rl = (RelativeLayout)findViewById(R.id.interest_rl);
		interest_rl.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		   case R.id.mimedetail_back:
	        	
			   this.finish();
	        
		        break;  
		   case R.id.signature_rl:
			   write=write_tv.getText().toString().trim();
		        Intent intent=new Intent(this, UpdateInfo.class);
		        Bundle bundle= new Bundle();
		        bundle.putInt("resultcode", 8);
				bundle.putString("title","个性签名");
				bundle.putString("mimecontent",write);
				bundle.putInt("num", 75);
				intent.putExtras(bundle);        
			    startActivityForResult(intent, 8);
				
				break;
				
		        case R.id.nick_rl:
		    		nickname=nick_name.getText().toString().trim();
		            Intent intent1=new Intent(this, UpdateInfo.class);
		            Bundle bundle1= new Bundle();
		            bundle1.putInt("resultcode", 9);
		            bundle1.putString("title","昵称");
		    		bundle1.putString("mimecontent",nickname);
		    		bundle1.putInt("num", 15);
		    		intent1.putExtras(bundle1);        
		    		startActivityForResult(intent1, 9);
		    		
		    		break; 
		    		
		        case R.id.school_rl:
		    		String school=school_tv.getText().toString().trim();
		            Intent intent13=new Intent(this, UpdateInfo.class); 
		            Bundle bundle13= new Bundle();
		            bundle13.putInt("resultcode", 13);
		            bundle13.putString("title","学校");
		            bundle13.putString("mimecontent",school);
		            bundle13.putInt("num", 30);
		            intent13.putExtras(bundle13);        
		    		startActivityForResult(intent13, 13);   		
		    		break;	
		        
		    		
		        case R.id.company_rl:
		    		String company=company_tv.getText().toString().trim();
		            Intent intent14=new Intent(this, UpdateInfo.class); 
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
		            Intent intent15=new Intent(this, UpdateInfo.class); 
		            Bundle bundle15= new Bundle();
		            bundle15.putInt("resultcode", 15);
		            bundle15.putString("title","职业");
		            bundle15.putString("mimecontent",work);
		            bundle15.putInt("num", 30);
		            intent15.putExtras(bundle15);        
		    		startActivityForResult(intent15, 15);   		
		    		break;
		    		
		        case R.id.interest_rl:
		    		String love=interest_tv.getText().toString().trim();
		            Intent intent16=new Intent(this, UpdateInfo.class); 
		            Bundle bundle16= new Bundle();
		            bundle16.putInt("resultcode", 16);
		            bundle16.putString("title","兴趣");
		            bundle16.putString("mimecontent",love);
		            bundle16.putInt("num", 100);
		            intent16.putExtras(bundle16);        
		    		startActivityForResult(intent16, 16);   		
		    		break;
		    		
		    		
		        case R.id.tou_rl:
		    		
		        	 final CharSequence[] items = { "手机相册", "相机拍摄" };  
				        AlertDialog dlg = new AlertDialog.Builder(this).setTitle("选择图片").setItems(items,   
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
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		 getContentResolver(); 
		
		
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == CAMERA_SUCCESS) {
			
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/myImage/newtemp.jpg");
			//System.out.println("------------------------" + picture.getPath());
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
		{				
			return;
		}
		// 读取相册缩放图片
		if (requestCode ==PHOTO_SUCCESS) {
			
			startPhotoZoom(data.getData());
	          
		}
		  // 处理结果
		  if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			if (extras != null) {
				
				Bitmap photo = extras.getParcelable("data");				
				savePicture(photo);				
				//这边要处理提交头像的操作
			//	xxxxxxxxxxxxxx
			}

		}
		  
		  if (requestCode ==5) {
			 String cityname;
			 String provincename;
			 provincename=data.getStringExtra("provincename");
			 cityname = data.getStringExtra("cityname");
			 if (cityname.equals("北京市")||cityname.equals("天津市")||cityname.equals("上海市")||cityname.equals("重庆市")||cityname.equals("台湾省")||cityname.equals("香港特别行政区")||cityname.equals("澳门特别行政区")) {
				
				 place_tv.setText(cityname);
				}
				else {

					place_tv.setText(provincename+" "+cityname);	
				}
			   
				
				//成功修改之后才将省市改掉
				
				

			}		  
		  
		  
		  if (requestCode ==8) {
			  
			   String intro;				
			   intro=data.getStringExtra("result");
			   write_tv.setText(intro);

			}
		  
		  if (requestCode ==9) {
			  
			   String nickname;				
			   nickname=data.getStringExtra("result");
			   nick_name.setText(nickname);

			}
		
		  if (requestCode ==13) {
			  
			   String school;				
			   school=data.getStringExtra("result");
			   school_tv.setText(school);

			}
		  
		  if (requestCode ==14) {
			  
			   String company;				
			   company=data.getStringExtra("result");
			   company_tv.setText(company);

			}
		  
		  if (requestCode ==15) {
			  
			   String work;				
			   work=data.getStringExtra("result");
			   job_tv.setText(work);

			}
		  
		  if (requestCode ==16) {
			  
			   String love;				
			   love=data.getStringExtra("result");
			   interest_tv.setText(love);

			}
		  		  
		super.onActivityResult(requestCode, resultCode, data);
		
		
	}
	
	
	/* 将压缩图片保存到自定义的文件中
	 * Bitmap类有一compress成员，可以把bitmap保存到一个stream中。
	 */
	@SuppressLint("SdCardPath")
	public void savePicture(Bitmap bitmap) { 
		     
		
		
	    String sdStatus = Environment.getExternalStorageState(); 
	    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用 
	      //  Log.v("TestFile", "SD card is not avaiable/writeable right now."); 
	        return; 
	    } 
	    FileOutputStream b = null; 
	    File file = new File("/sdcard/myImage/"); 
	    file.mkdirs();// 创建文件夹 
	    String fileName = "/sdcard/myImage/temp.jpg"; 
	  
	    try { 
	        b = new FileOutputStream(fileName); 
	        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件 
	    } catch (FileNotFoundException e) { 
	        e.printStackTrace(); 
         } 
	    finally { 
	        try { 
	            b.flush(); 
	            b.close();     
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	        } 
	    } 
	}
	
	 /*
	   * 进行图片剪裁
	   */
		public void startPhotoZoom(Uri uri) {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX",640);
			intent.putExtra("outputY",640);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, PHOTORESOULT);
		}
	
	
	
	private	void scanOldImageFile(){
		File file = new File(Environment.getExternalStorageDirectory(), "/myImage/newtemp.jpg");
		File file1 = new File(Environment.getExternalStorageDirectory(), "/myImage/temp.jpg");
		if(file.exists()||file1.exists()){
			file.delete();
			file1.delete();
		}
		
		
	}
	
	
	//获取文件手机路径
	private String getImagePath(){
		
		
		File file=new File(Environment.getExternalStorageDirectory(), "/myImage/temp.jpg");			
		return file.getAbsolutePath();
	}

	

	
	
	  @Override
	   public void onDestroy(){
		   
		   scanOldImageFile();
		   
		   super.onDestroy();
	   }
}
