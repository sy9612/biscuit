package com.ssafy.a407.dto;

public class UserMemberDto {

	private int mId;
	private int gId;
	private String email;
	private String nickname;
	private String phone;
	private String picture;
	private String permission;
	
	public UserMemberDto() {
		// TODO Auto-generated constructor stub
	}

	public UserMemberDto(int mId, int gId, String email, String nickname, String phone, String picture,
			String permission) {
		super();
		this.mId = mId;
		this.gId = gId;
		this.email = email;
		this.nickname = nickname;
		this.phone = phone;
		this.picture = picture;
		this.permission = permission;
	}

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	
}
