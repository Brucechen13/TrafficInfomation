package com.dlut.traffic.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class CommentBean implements Parcelable{
	
	private String id;
	private String msgId;
	private String userId;
	private String userName;
	private String content;
	private String time;
	public CommentBean(String id, String msgId, String userId, String userName,
			String content, String time) {
		super();
		this.id = id;
		this.msgId = msgId;
		this.userId = userId;
		this.userName = userName;
		this.content = content;
		this.time = time;
	}
	private CommentBean(Parcel in){
		id=in.readString();
		msgId=in.readString();
		userId=in.readString();
		userName=in.readString();
		content=in.readString();
		time=in.readString();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(msgId);
		dest.writeString(userId);
		dest.writeString(userName);
		dest.writeString(content);
		dest.writeString(time);
	}
	
	public static final Parcelable.Creator<CommentBean> CREATOR = 
			new Parcelable.Creator<CommentBean>(){

				@Override
				public CommentBean createFromParcel(Parcel source) {
					// TODO Auto-generated method stub
					return new CommentBean(source);
				}

				@Override
				public CommentBean[] newArray(int size) {
					// TODO Auto-generated method stub
					return new CommentBean[size];
				}
		
	};
	


}
