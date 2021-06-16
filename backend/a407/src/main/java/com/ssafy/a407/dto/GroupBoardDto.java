package com.ssafy.a407.dto;

public class GroupBoardDto {
	private int gId;
	private int bId;
	private String email;
	private String nickname;
	private String title;
	private String contents;
	private boolean noticeFlag;
	private String date;
	private int category;
	
	public GroupBoardDto() {
		// TODO Auto-generated constructor stub
	}

	public GroupBoardDto(int gId, int bId, String email, String nickname, String title, String contents, boolean noticeFlag,
			String date, int category) {
		super();
		this.gId = gId;
		this.bId = bId;
		this.email = email;
		this.nickname = nickname;
		this.title = title;
		this.contents = contents;
		this.noticeFlag = noticeFlag;
		this.date = date;
		this.category = category;
	}

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public int getbId() {
		return bId;
	}

	public void setbId(int bId) {
		this.bId = bId;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public boolean isNoticeFlag() {
		return noticeFlag;
	}

	public void setNoticeFlag(boolean noticeFlag) {
		this.noticeFlag = noticeFlag;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("gBoardDto [gId=");
		builder.append(gId);
		builder.append(", bId=");
		builder.append(bId);
		builder.append(", email=");
		builder.append(email);
		builder.append(", nickname=");
		builder.append(nickname);
		builder.append(", title=");
		builder.append(title);
		builder.append(", contents=");
		builder.append(contents);
		builder.append(", noticeFlag=");
		builder.append(noticeFlag);
		builder.append(", date=");
		builder.append(date);
		builder.append(", category=");
		builder.append(category);
		builder.append("]");
		return builder.toString();
	}
	
	
}
