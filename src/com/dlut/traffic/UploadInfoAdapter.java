package com.dlut.traffic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UploadInfoAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
    private List<? extends Map<String, ?>> data;
    private Context mContext;
    private View view[] = null;
    
    public UploadInfoAdapter(Context context, List<Map<String, Object>> list)
    {
        this.data = list;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        //Constants.mImageLoader=new ImageLoader(context);
    }
    
    class ImageData
    {
        public long imageID;
        public String imageURL;
        
        public ImageData(long imageID, String imageURL)
        {
            super();
            this.imageID = imageID;
            this.imageURL = imageURL;
        }
    }
    
    public class ViewHolder
    {
    	public ImageView userimg_iv;
        public TextView username;
        public TextView contenttime;
        public TextView time;
        public TextView content;
        public LinearLayout content_ll;
        public TextView good_num;
        public TextView comment_num;
        public ImageView comgood_iv;
        public ImageView good_img;
		public ImageView comment_img;
		public RelativeLayout comgoodhandle;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 final ViewHolder holder;
	        if (convertView == null)
	        {
	            holder = new ViewHolder();
	            
	            convertView = LayoutInflater.from(mContext).inflate(
	                    R.layout.msg_item, parent, false);
	            holder.username = (TextView) convertView
	                    .findViewById(R.id.username);
	            holder.contenttime = (TextView) convertView
	                    .findViewById(R.id.contenttime);
	            holder.time = (TextView) convertView
	                    .findViewById(R.id.time);
	            holder.content = (TextView) convertView
	                    .findViewById(R.id.content);
	            holder.good_num = (TextView) convertView
	                    .findViewById(R.id.good_num);
	            holder.comment_num = (TextView) convertView
	                    .findViewById(R.id.comment_num);
	            
	            holder.userimg_iv = (ImageView) convertView
	            		.findViewById(R.id.userimg_iv);
	            holder.comgood_iv = (ImageView) convertView
	            		.findViewById(R.id.comgood_iv);
	            holder.good_img = (ImageView) convertView
	            		.findViewById(R.id.good_img);
	            holder.comment_img = (ImageView) convertView
	            		.findViewById(R.id.comment_img);
	            holder.comgoodhandle = (RelativeLayout) convertView
	            		.findViewById(R.id.comgoodhandle);
	            convertView.setTag(holder);
	            convertView.setId(position);
	        }
	        else
	        {
	            holder = (ViewHolder) convertView.getTag();
	        }


	        OnClickListener userInfoListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(mContext, UserInfoView.class);
					mContext.startActivity(intent);		
				}
			};
			OnClickListener infoDetailListener = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(mContext, InfoDetail.class);
					
					mContext.startActivity(intent);		
				}
			};
	        holder.username.setText(data.get(position).get("username")
	                .toString());
	        
	        holder.username.setOnClickListener(userInfoListener);
	        holder.userimg_iv.setOnClickListener(userInfoListener);
	        
	        holder.content.setOnClickListener(infoDetailListener);
	        
	        holder.comgood_iv.setOnClickListener(new OnClickListener()
	        {
	            Boolean flag = false;
	            
	            @Override
	            public void onClick(View v)
	            {
	                // TODO Auto-generated method stub
	                
	                if (!flag)
	                {
	                    
	                    holder.comgoodhandle.setVisibility(View.VISIBLE);
	                    flag = true;
	                }
	                else
	                {
	                    holder.comgoodhandle.setVisibility(View.GONE);
	                    flag = false;
	                }
	                
	            }
	        });
	        return convertView;
	}

}
