package com.ssafy.a407.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.a407.dao.ScheduleDao;
import com.ssafy.a407.dto.ScheduleDto;

@Service
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleDao dao;
	
	@Override
	public int createSchedule(ScheduleDto schedule) throws Exception {
//		System.out.println("[Service] Schedule create >> "+schedule.toString());
		return dao.insertSchedule(schedule);
	}

	@Override
	public List<ScheduleDto> searchGroupSchedule(int gId) throws Exception {
		return dao.selectGroupSchedule(gId);
	}

	@Override
	public List<ScheduleDto> searchPersonalSchedule(String email) throws Exception {
		return dao.selectPersonalSchedule(email);
	}

	@Override
	public ScheduleDto searchDetail(int sId) throws Exception {
//		System.out.println("[Service] Schedule detail >> "+sId);
		return dao.selectSchedule(sId);
	}

	@Override
	public int modifySchedule(ScheduleDto schedule) throws Exception {
		return dao.updateSchedule(schedule);
	}

	@Override
	public int removeSchedule(int gId) throws Exception {
		return dao.deleteSchedule(gId);
	}

}
