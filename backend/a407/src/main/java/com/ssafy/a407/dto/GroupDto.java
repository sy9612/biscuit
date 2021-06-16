package com.ssafy.a407.dto;

public class GroupDto {
	private int gId; //그룹 아이디
	private String email; //그룹장 아이디
	private String nickname; //그룹장 닉네임
	private int max; //제한 인원
	private String sdate; //그룹 생성 날짜
	private String edate; //그룹 모집 완료 날짜
	private String groupName; //그룹명
	private String groupDesc; //그룹 설명
	private String category; // 분류
	private int onoff; // 온라인or 오프라인  : 0 - 온라인 , 1 - 오프라인 , 2 - 온라인+오프라인
	private String region; //지역 (시/도)
	private String img;  //그룹 이미지
	private int groupFlag; // 그룹 모집중 : 0 - 모집완료, 1 - 모집중
	private String cycle; // 모임 주기
	
	public GroupDto() {
		// TODO Auto-generated constructor stub
	}

	public GroupDto(int gId, String email, String nickname, int max, String sdate, String edate, String groupName, String groupDesc,
			String category, int onoff, String region, String img, int groupFlag, String cycle) {
		super();
		this.gId = gId;
		this.email = email;
		this.nickname = nickname;
		this.max = max;
		this.sdate = sdate;
		this.edate = edate;
		this.groupName = groupName;
		this.groupDesc = groupDesc;
		this.category = category;
		this.onoff = onoff;
		this.region = region;
		this.img = img;
		this.groupFlag = groupFlag;
		this.cycle = cycle;
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

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
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

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getOnoff() {
		return onoff;
	}

	public void setOnoff(int onoff) {
		this.onoff = onoff;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(int groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	@Override
	public String toString() {
		return "GroupDto [gId=" + gId + ", email=" + email + ", nickname=" + nickname + ", max=" + max + ", sdate=" + sdate + ", edate=" + edate
				+ ", groupName=" + groupName + ", groupDesc=" + groupDesc + ", category=" + category + ", onoff="
				+ onoff + ", region=" + region + ", img=" + img + ", groupFlag=" + groupFlag + ", cycle=" + cycle + "]";
	}
	
}
