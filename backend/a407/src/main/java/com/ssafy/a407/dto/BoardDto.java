package com.ssafy.a407.dto;



public class BoardDto {
	private int bId;
	private String email;
	private String nickname;
	private String title;
	private String contents;
	private boolean noticeFlag;
	private String date;
	private int category;
	
	public BoardDto() {
		
	}
	
	public BoardDto(int bId, String email, String nickname, String title, String contents, boolean noticeFlag, String date, int category) {
		super();
		this.bId = bId;
		this.email = email;
		this.nickname = nickname;
		this.title = title;
		this.contents = contents;
		this.noticeFlag = noticeFlag;
		this.date = date;
		this.category = category;
	}
	
	public int getBId() {
		return bId;
	}
	
	public void setBId(int bId) {
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
	
	public boolean getNoticeFlag() {
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
		return "BoardDto [bId=" + bId + ", email=" + email + ", nickname=" + nickname + ", title=" + title + ", contents=" + contents + ", noticeFlag=" + noticeFlag + ", date=" + date + ", category=" + category + "]";
	}
}