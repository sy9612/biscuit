package com.ssafy.a407.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407.dto.ScheduleDto;

@Mapper
public interface ScheduleDao {
	public int insertSchedule(ScheduleDto schedule) throws Exception;
	public List<ScheduleDto> selectGroupSchedule(int gId) throws Exception;
	public List<ScheduleDto> selectPersonalSchedule(String email) throws Exception;
	public ScheduleDto selectSchedule(int sId) throws Exception;
	public int updateSchedule(ScheduleDto schedule) throws Exception;
	public int deleteSchedule(int gId) throws Exception;
}
