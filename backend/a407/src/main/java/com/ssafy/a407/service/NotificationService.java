package com.ssafy.a407.service;

import java.util.List;

import com.ssafy.a407.dto.NotificationDto;

public interface NotificationService {
	public int createNotification(NotificationDto notification) throws Exception;
	public List<NotificationDto> searchPersonalNotification(String receiveEmail) throws Exception;
	public int removeNotification(int nId) throws Exception;
	public int modifyNotification(NotificationDto notification) throws Exception;
	
}
