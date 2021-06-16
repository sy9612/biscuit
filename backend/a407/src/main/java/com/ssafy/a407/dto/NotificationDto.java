package com.ssafy.a407.dto;

public class NotificationDto {

	private int nId;
	private String receiveEmail;
	private String sendEmail;
	private int isRead;
	private String type;
	private int contentId;
	private String message;
	private String notiUrl;
	
	
	public NotificationDto() {
		// TODO Auto-generated constructor stub
	}


	public NotificationDto(int nId, String receiveEmail, String sendEmail, int isRead, String type, int contentId,
			String message, String notiUrl) {
		super();
		this.nId = nId;
		this.receiveEmail = receiveEmail;
		this.sendEmail = sendEmail;
		this.isRead = isRead;
		this.type = type;
		this.contentId = contentId;
		this.message = message;
		this.notiUrl = notiUrl;
	}


	public int getnId() {
		return nId;
	}


	public void setnId(int nId) {
		this.nId = nId;
	}


	public String getReceiveEmail() {
		return receiveEmail;
	}


	public void setReceiveEmail(String receiveEmail) {
		this.receiveEmail = receiveEmail;
	}


	public String getSendEmail() {
		return sendEmail;
	}


	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}


	public int getIsRead() {
		return isRead;
	}


	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public int getContentId() {
		return contentId;
	}


	public void setContentId(int contentId) {
		this.contentId = contentId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getNotiUrl() {
		return notiUrl;
	}


	public void setNotiUrl(String notiUrl) {
		this.notiUrl = notiUrl;
	}


	@Override
	public String toString() {
		return "NotificationDto [nId=" + nId + ", receiveEmail=" + receiveEmail + ", sendEmail=" + sendEmail
				+ ", isRead=" + isRead + ", type=" + type + ", contentId=" + contentId + ", message=" + message
				+ ", notiUrl=" + notiUrl + "]";
	}
	
	
}
