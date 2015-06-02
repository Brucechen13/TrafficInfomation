package com.dlut.traffic.adapter;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendBean implements Parcelable {
	
	private String firendId;
	private String pic="";
	private String name="";
	private String signature="";

	public FriendBean(String firendId, String pic, String name, String signature) {
		super();
		this.firendId = firendId;
		this.pic = pic;
		this.name = name;
		this.signature = signature;
	}

	public String getFirendId() {
		return firendId;
	}

	public void setFirendId(String firendId) {
		this.firendId = firendId;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(firendId);
		dest.writeString(name);
		dest.writeString(pic);
		dest.writeString(signature);
		
	}
	
	public static final Parcelable.Creator<FriendBean> CREATOR = 
			new Parcelable.Creator<FriendBean>(){

				@Override
				public FriendBean createFromParcel(Parcel source) {
					// TODO Auto-generated method stub
					return new FriendBean(source);
				}

				@Override
				public FriendBean[] newArray(int size) {
					// TODO Auto-generated method stub
					return new FriendBean[size];
				}
		
	};
	
	private FriendBean(Parcel in){
		firendId=in.readString();
		name = in.readString();
		pic = in.readString();;
		signature = in.readString();;
	}


}
