package com.ssafy.a407.dto;

public class GroupMemberDto {
	private int mId;
	private int gId;
	private String email;
	private String groupName;
	private int permission;
	private int max;
	private String img;
	private String region;
	private String category;
	private String edate;
	private String nickname;
	
	public GroupMemberDto() {
		// TODO Auto-generated constructor stub
	}

	public GroupMemberDto(int mId, int gId, String email, String groupName, int permission, String img, String region, int max, String edate, String nickname) {
		super();
		this.mId = mId;
		this.gId = gId;
		this.email = email;
		this.groupName = groupName;
		this.permission = permission;
		this.img = img;
		this.region = region;
		this.max= max;
		this.edate= edate;
		this.nickname= nickname;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname= nickname;
	}
	
	public String getEdate() {
		return edate;
	}
	
	public void setEdate(String edate) {
		this.edate = edate;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getMax() {
		return max;
	}
	
	public void setMax(int max) {
		this.max = max;
	}
	
	public String getRegion() {
		return region;
	}
	
	public void setRegion(String region) {
		this.region = region;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
