package com.dlut.traffic.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class MsgBean implements Parcelable{
	
	private String id;
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String userName;
	private String touPic;
	private String contentTime;//日期
	private String content;
	private String time;//具体时间
	public MsgBean(String id, String userId, String userName, String touPic,
			String contentTime, String content, String time, String place,
			String goodNum, String commentNum) {
		super();
		this.id = id;
		this.userId = userId;
		this.userName = userName;
		this.touPic = touPic;
		this.contentTime = contentTime;
		this.content = content;
		this.time = time;
		this.place = place;
		this.goodNum = goodNum;
		this.commentNum = commentNum;
	}
	private String place;
	private String goodNum;
	private String commentNum;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTouPic() {
		return touPic;
	}
	public void setTouPic(String touPic) {
		this.touPic = touPic;
	}
	public String getContentTime() {
		return contentTime;
	}
	public void setContentTime(String contentTime) {
		this.contentTime = contentTime;
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
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getGoodNum() {
		return goodNum;
	}
	public void setGoodNum(String goodNum) {
		this.goodNum = goodNum;
	}
	public String getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
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
		dest.writeString(userId);
		dest.writeString(userName);
		dest.writeString(touPic);
		dest.writeString(content);
		dest.writeString(commentNum);
		dest.writeString(contentTime);
		dest.writeString(time);
		dest.writeString(place);
		dest.writeString(goodNum);
		dest.writeString(commentNum);
	} 
	
	public static final Parcelable.Creator<MsgBean> CREATOR = 
			new Parcelable.Creator<MsgBean>(){

				@Override
				public MsgBean createFromParcel(Parcel source) {
					// TODO Auto-generated method stub
					return new MsgBean(source);
				}

				@Override
				public MsgBean[] newArray(int size) {
					// TODO Auto-generated method stub
					return new MsgBean[size];
				}
		
	};
	
	private MsgBean(Parcel in){
		id=in.readString();
		userId=in.readString();
		userName=in.readString();
		touPic=in.readString();
		content=in.readString();
		commentNum=in.readString();
		contentTime=in.readString();
		time=in.readString();
		place=in.readString();
		goodNum=in.readString();
		commentNum=in.readString();
	}
	

}
