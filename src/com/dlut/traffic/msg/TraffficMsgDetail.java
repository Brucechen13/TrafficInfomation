package com.dlut.traffic.msg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.dlut.traffic.LoginActivity;
import com.dlut.traffic.R;
import com.dlut.traffic.TitleActivity;
import com.dlut.traffic.adapter.CommentBean;
import com.dlut.traffic.adapter.MsgBean;
import com.dlut.traffic.adapter.MsgCommentsAdapter;
import com.dlut.traffic.adapter.MsgsAdapter;
import com.dlut.traffic.util.ServerUtil;
import com.dlut.traffic.util.UrlParse;
import com.dlut.traffic.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TraffficMsgDetail extends TitleActivity implements OnClickListener {
	
	private PullToRefreshListView commentsList = null;
	private MsgCommentsAdapter adapter;
	private MsgBean msg;
	
	private int offset = 0;
	private Date date;
	private static SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private boolean start = true;
	
	private ImageView userimg_iv;
    private TextView username;
    private TextView contenttime;
    private TextView time;
    private TextView content;
    private TextView area;
    private TextView good_num;
    private TextView comment_num;
    private ImageView comgood_iv;
    private ImageView good_img;
	private ImageView comment_img;
	private RelativeLayout comgoodhandle;
	
	private EditText text;
	
	
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
		setContentView(R.layout.msg_item_detail);
		setTitle(R.string.tra_msg);
		showBackwardView(R.string.button_backward, true);
		
		msg = (MsgBean)getIntent().getParcelableExtra("msg");
		Log.d("traffic", "getMseeage:"+msg.getUserName());
		
		username = (TextView) this
                .findViewById(R.id.username);
        contenttime = (TextView) this
                .findViewById(R.id.contenttime);
        time = (TextView) this
                .findViewById(R.id.time);
        content = (TextView) this
                .findViewById(R.id.content);
        area = (TextView) this
                .findViewById(R.id.area_tv);
        good_num = (TextView) this
                .findViewById(R.id.good_num);
        comment_num = (TextView) this
                .findViewById(R.id.comment_num);      
        userimg_iv = (ImageView) this
        		.findViewById(R.id.userimg_iv);
        comgood_iv = (ImageView) this
        		.findViewById(R.id.comgood_iv);
        good_img = (ImageView) this
        		.findViewById(R.id.good_img);
        comment_img = (ImageView) this
        		.findViewById(R.id.comment_img);
        comgoodhandle = (RelativeLayout)this
        		.findViewById(R.id.comgoodhandle);
        
        this.findViewById(R.id.send).setOnClickListener(this);
        
        text=(EditText)this.findViewById(R.id.text);
        
        username.setText(msg.getUserName());
        contenttime.setText(msg.getContentTime());
        time.setText(msg.getTime());
        content.setText("在"+msg.getContent());
        area.setText(msg.getPlace());
        good_num.setText(msg.getGoodNum());
        comment_num.setText(msg.getCommentNum());username.setText(msg.getUserName());
        contenttime.setText(msg.getContentTime());
        time.setText(msg.getTime());
        content.setText("在"+msg.getContent());
        area.setText(msg.getPlace());
        good_num.setText(msg.getGoodNum());
        comment_num.setText(msg.getCommentNum());
        
        Ion.with(this)
		.load(msg.getTouPic())
		.withBitmap()
		//.placeholder(R.drawable.placeholder_image)
		.error(R.drawable.tou)
		.intoImageView(userimg_iv).setCallback(new FutureCallback<ImageView>() {
			@Override
			public void onCompleted(Exception arg0, ImageView arg1) {
				// TODO Auto-generated method stub
				if(arg0 != null){
					Log.d("traffic", arg0.toString());
				}
			}
			
		});
        
        this.comgood_iv.setOnClickListener(new OnClickListener()
        {
            Boolean flag = false;
            
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                
                if (!flag)
                {
                    
                	comgoodhandle.setVisibility(View.VISIBLE);
                    flag = true;
                }
                else
                {
                    comgoodhandle.setVisibility(View.GONE);
                    flag = false;
                }
                
            }
        });
        
        this.good_img.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("traffic", "good num");
				String num = msg.getGoodNum();
				msg.setGoodNum(String.valueOf(Integer.parseInt(num)+1));
				TraffficMsgDetail.this.good_num.setText(msg.getGoodNum());
				TraffficMsgDetail.this.good_img.setVisibility(View.GONE);
				Ion.with(TraffficMsgDetail.this)
		        .load(String.format("%s?msg=%s",ServerUtil.addUpMsgUrl, 
		        		msg.getId())).asJsonObject()
				.setCallback(new FutureCallback<JsonObject>() {

					@Override
					public void onCompleted(Exception arg0, JsonObject arg1) {
						// TODO Auto-generated method stub
						if(arg0!=null || !"true".equals(arg1.get("toupic").getAsString())){
							Log.i("traffic", "failed");
						}
					}
					
				});
			}
        });
        
        this.comment_img.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View v) {
				Toast.makeText(TraffficMsgDetail.this, "点击下方文本框输入评论",
						Toast.LENGTH_SHORT).show();
			}
        });
        
		
		adapter = new MsgCommentsAdapter(this, getComments(null));
		commentsList = (PullToRefreshListView)findViewById(R.id.commentsList);
		initPullToRefreshListView(commentsList, adapter);
	}
	
	private void initPullToRefreshListView(PullToRefreshListView commentsList2,
			BaseAdapter adapter) {
		// TODO Auto-generated method stub
		commentsList2.setMode(Mode.BOTH);
		commentsList2.setOnRefreshListener(new MyOnRefreshListener2(commentsList2));
		commentsList2.setAdapter(adapter);
		date = new Date();
		loadData();
	}
	
	private void loadData(){
		Ion.with(TraffficMsgDetail.this)
		.load(String.format("%s?msg=%s&skip=%s", ServerUtil.getCommentsUrl, msg.getId(), 
				offset))
		.asJsonObject().setCallback(new FutureCallback<JsonObject>(){

			@Override
			public void onCompleted(Exception arg0, JsonObject arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					Log.d("traffic", arg0.toString());
					Toast.makeText(TraffficMsgDetail.this, "请检查网络",
							Toast.LENGTH_SHORT).show();
					commentsList.onRefreshComplete();  
					return;
				}
				JsonArray comments = arg1.get("comments").getAsJsonArray();
				if(!start && comments.size()==0){
					Toast.makeText(TraffficMsgDetail.this, "已经没有数据啦",
							Toast.LENGTH_SHORT).show();
					commentsList.onRefreshComplete(); 
					return;
				}
				Log.d("traffic", comments.toString());
				adapter.addNews(getComments(comments));
				adapter.notifyDataSetChanged();
				commentsList.onRefreshComplete();   
				offset+=comments.size();
				start = false;
			}
		});
	}
	
	private void loadNewestData(){
		String time = sfd.format(date); 
		time = UrlParse.ParseUrl(time);
		Log.d("traffic", time);
		Ion.with(TraffficMsgDetail.this)
		.load(String.format("%s?msg=%s&date=%s", ServerUtil.getCommentsUrl, msg.getId(), 
				time))
		.asJsonObject().setCallback(new FutureCallback<JsonObject>(){

			@Override
			public void onCompleted(Exception arg0, JsonObject arg1) {
				// TODO Auto-generated method stub
				if (arg0 != null) {
					Log.d("traffic", arg0.toString());
					Toast.makeText(TraffficMsgDetail.this, "请检查网络",
							Toast.LENGTH_SHORT).show();
					commentsList.onRefreshComplete();  
					return;
				}
				JsonArray comments = arg1.get("comments").getAsJsonArray();
				if(comments.size()==0){
					Toast.makeText(TraffficMsgDetail.this, "已经没有数据啦",
							Toast.LENGTH_SHORT).show();
					commentsList.onRefreshComplete(); 
					return;
				}
				Log.d("traffic", comments.toString());
				date = new Date();
				offset+=comments.size();
				adapter.addFirstNews(getComments(comments));
				adapter.notifyDataSetChanged();	
				commentsList.onRefreshComplete();
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
			String label = DateUtils.formatDateTime(TraffficMsgDetail.this, 
					System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
							| DateUtils.FORMAT_SHOW_DATE
							| DateUtils.FORMAT_ABBREV_ALL);

			refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
			loadNewestData();

		}

		@Override
		public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
			// 上拉加载
			//new GetNewsTask(mPtflv).execute();
			loadData();
		}

	}
	
	public ArrayList<CommentBean> getComments(JsonArray res) {
		ArrayList<CommentBean> ret = new ArrayList<CommentBean>();
		if(res == null){
			return ret;
		}
		CommentBean hm;
		for (int i = 0; i < res.size(); i++) {
			JsonElement je = res.get(i);
			JsonObject jo = je.getAsJsonObject();
			Log.d("traffic", jo.toString());
			Date date = Util.parseISODate(jo.get("time").getAsString());
			hm=new CommentBean(jo.get("_id").getAsString(), 
					msg.getId(), jo.get("user").getAsString(), 
					jo.get("userName").getAsString(), 
					jo.get("content").getAsString(), date.toLocaleString());
			ret.add(hm);
		}
		return ret;
	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
			//   Intent intent=new Intent(this, UpdateMsg.class);
		case R.id.send:
			String cText = text.getText().toString().trim();
			Log.d("traffic", cText);
			this.comment_num.setText(String.valueOf(Integer.parseInt(
					msg.getCommentNum())+1));
			Ion.with(TraffficMsgDetail.this)
	        .load(String.format("%s?user=%s&msg=%s&content=%s",ServerUtil.addCommentUrl, 
	        		Util.userId, msg.getId(), cText)).asJsonObject()
			.setCallback(new FutureCallback<JsonObject>() {
				@Override
				public void onCompleted(Exception arg0, JsonObject arg1) {
					// TODO Auto-generated method stub
					if (arg0 != null || !arg1.get("suc").getAsString().equals("true")) {
						Log.d("traffic",arg0.toString());
						Util.toastMessage(TraffficMsgDetail.this, "添加失败");
					}else{
						Util.toastMessage(TraffficMsgDetail.this, "添加成功");
					}
					text.setText("");
					text.setFocusable(false);
					
				}
			});
			break;
		default:
			break;
		}
	}
}
