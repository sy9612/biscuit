package com.ssafy.a407.dto;

public class UserDto {
	private String email;
	private String password;
	private String nickname;
	private String picture; //나중에 추가
	private String phone;
	private String region;
	private int admin; // 0 : 일반 유저, 1: 관리자
	
	public UserDto() {}

	public UserDto(String email, String password, String nickname, String picture, String phone, String region,
			int admin) {
		super();
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.picture = picture;
		this.phone = phone;
		this.region = region;
		this.admin = admin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "UserDto [email=" + email + ", password=" + password + ", nickname=" + nickname + ", picture=" + picture
				+ ", phone=" + phone + ", region=" + region + ", admin=" + admin + "]";
	}
	
}
