package com.ssafy.a407.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407.dto.NotificationDto;

@Mapper
public interface NotificationDao {

	public int insertNotification(NotificationDto notification) throws Exception;
	public List<NotificationDto> selectNotification(String receiveEmail) throws Exception;
	public int deleteNotification(int nId) throws Exception;
	public int updateNotification(NotificationDto notification) throws Exception;
	
}
