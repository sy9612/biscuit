package com.ssafy.a407.dto;

public class ScheduleDto {
	private int sId;
	private int pgFlag;
	private String email;
	private int gId;
	private String sdate;
	private String edate;
	private String title;
	private String contents;

	public ScheduleDto() {}

	public ScheduleDto(int sId, int pgFlag, String email, int gId, String sdate, String edate, String title,
			String contents) {
		super();
		this.sId = sId;
		this.pgFlag = pgFlag;
		this.email = email;
		this.gId = gId;
		this.sdate = sdate;
		this.edate = edate;
		this.title = title;
		this.contents = contents;
	}

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public int getPgFlag() {
		return pgFlag;
	}

	public void setPgFlag(int pgFlag) {
		this.pgFlag = pgFlag;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getgId() {
		return gId;
	}

	public void setgId(int gId) {
		this.gId = gId;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
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

	@Override
	public String toString() {
		return "ScheduleDto [sId=" + sId + ", pgFlag=" + pgFlag + ", email=" + email + ", gId=" + gId + ", sdate="
				+ sdate + ", edate=" + edate + ", title=" + title + ", contents=" + contents + "]";
	}
	
}
