package com.dlut.traffic.user;

import java.io.Serializable;

import com.google.gson.JsonObject;

import android.graphics.Bitmap;
import android.util.Log;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String qq;
	String nickName;
	String photo;
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	public void parseJson(JsonObject result){
		if(result.has("qq")){
			this.setQq(result.get("qq").getAsString());
		}
		if(result.has("name")){
			this.setNickName(result.get("name").getAsString());
		}
		if(result.has("toupic")){
			this.setPhoto(result.get("toupic").getAsString());
		}
		if(result.has("signature")){
			this.setSignature(result.get("signature").getAsString());
		}
		if(result.has("gender")){
			this.setGender(result.get("gender").getAsString());
		}
		if(result.has("score")){
			this.setScore(Integer.parseInt(result.get("score").getAsString()));
		}
		if(result.has("company")){
			this.setCompany(result.get("signature").getAsString());
		}
		if(result.has("city")){
			this.setCity(result.get("city").getAsString());
		}
		if(result.has("job")){
			this.setJob(result.get("job").getAsString());
		}
	}
	
	String signature="";
	int score=0;
	String gender="";
	String city="";
	String company="";
	String job="";
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
}
