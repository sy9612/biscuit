package com.ssafy.a407.dto;

public class GroupReplyDto {
	private int rId;
	private int bId;
	private String email;
	private String nickname;
	private String contents;
	private String date;
	
	public GroupReplyDto() {
		// TODO Auto-generated constructor stub
	}

	public GroupReplyDto(int rId, int bId, String email, String nickname, String contents, String date) {
		super();
		this.rId = rId;
		this.bId = bId;
		this.email = email;
		this.nickname = nickname;
		this.contents = contents;
		this.date = date;
	}

	public int getrId() {
		return rId;
	}

	public void setrId(int rId) {
		this.rId = rId;
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

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GroupReplyDto [rId=");
		builder.append(rId);
		builder.append(", bId=");
		builder.append(bId);
		builder.append(", email=");
		builder.append(email);
		builder.append(", nickname=");
		builder.append(nickname);
		builder.append(", contents=");
		builder.append(contents);
		builder.append(", date=");
		builder.append(date);
		builder.append("]");
		return builder.toString();
	}
	
	
}
