package com.ssafy.a407.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.a407.dao.NotificationDao;
import com.ssafy.a407.dto.NotificationDto;

@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private NotificationDao dao;
	
	@Override
	public int createNotification(NotificationDto notification) throws Exception {
		return dao.insertNotification(notification);
	}

	@Override
	public List<NotificationDto> searchPersonalNotification(String receiveEmail) throws Exception {
		return dao.selectNotification(receiveEmail);
	}

	@Override
	public int removeNotification(int nId) throws Exception {
		return dao.deleteNotification(nId);
	}

	@Override
	public int modifyNotification(NotificationDto notification) throws Exception {
		return dao.updateNotification(notification);
	}

}
