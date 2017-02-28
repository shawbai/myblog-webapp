package com.shaw.myblog.mapper;

import com.shaw.myblog.model.MemberAccount;

public interface MemberAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberAccount record);

    int insertSelective(MemberAccount record);

    MemberAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberAccount record);

    int updateByPrimaryKey(MemberAccount record);
}