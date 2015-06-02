package com.dlut.traffic.adapter;

import java.util.HashMap;
import java.util.List;

import com.dlut.traffic.R;
import com.dlut.traffic.adapter.MsgsAdapter.ViewHolder;
import com.dlut.traffic.msg.TraffficMsgDetail;
import com.dlut.traffic.user.UserInfoView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MsgCommentsAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
    private List<CommentBean> data;
    private Context mContext;
    private View view[] = null;
    
    public MsgCommentsAdapter(Context context, List<CommentBean> list)
    {
        this.data = list;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        //Constants.mImageLoader=new ImageLoader(context);
    }

    public class ViewHolder
    {
        public TextView username;
        public TextView content;
        public TextView time;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
        if (convertView == null)
        {
            holder = new ViewHolder();
            
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.comment_item, parent, false);
            holder.username = (TextView) convertView
                    .findViewById(R.id.username);
            holder.content = (TextView) convertView
                    .findViewById(R.id.content);
            holder.time = (TextView) convertView
                    .findViewById(R.id.time);
            convertView.setTag(holder);
            convertView.setId(position);
        }else{
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
        holder.username.setText(data.get(position).getUserName()); 
        holder.username.setOnClickListener(userInfoListener);   
        holder.content.setText(data.get(position).getContent());
        holder.time.setText(data.get(position).getTime());
        
        return convertView;
	}
	
	public void addNews(List<CommentBean> addNews) {
		data.addAll(addNews);
	}
	
	public void addFirstNews(List<CommentBean> addNews) {
		data.addAll(0, addNews);
	}

}
