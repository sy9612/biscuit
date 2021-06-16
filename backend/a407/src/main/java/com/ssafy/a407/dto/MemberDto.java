package com.ssafy.a407.dto;

public class MemberDto {
	private int mId;
	private int gId;
	private String email;
	private String nickname;
	private int permission;
	
	public MemberDto() {
		// TODO Auto-generated constructor stub
	}

	public MemberDto(int mId, int gId, String email, String nickname, int permission) {
		super();
		this.mId = mId;
		this.gId = gId;
		this.email = email;
		this.nickname = nickname;
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

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "MemberDto [mId=" + mId + ", gId=" + gId + ", email=" + email + ", nickname=" + nickname + ", permission=" + permission + "]";
	}

	
	
}
