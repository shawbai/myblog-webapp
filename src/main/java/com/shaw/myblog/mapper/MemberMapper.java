package com.shaw.myblog.mapper;

import com.shaw.myblog.facade.user.pojo.dto.MemberDto;
import com.shaw.myblog.model.Member;

public interface MemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

	MemberDto queryByTelePhoneOrUserName(String memberName);
}