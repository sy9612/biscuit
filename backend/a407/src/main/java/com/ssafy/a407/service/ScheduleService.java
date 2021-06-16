package com.ssafy.a407.service;

import java.util.List;

import com.ssafy.a407.dto.ScheduleDto;

public interface ScheduleService {
	public int createSchedule(ScheduleDto schedule) throws Exception;
	public List<ScheduleDto> searchGroupSchedule(int gId) throws Exception;
	public List<ScheduleDto> searchPersonalSchedule(String email) throws Exception;
	public ScheduleDto searchDetail(int sId) throws Exception;
	public int modifySchedule(ScheduleDto schedule) throws Exception;
	public int removeSchedule(int gId) throws Exception;
}
