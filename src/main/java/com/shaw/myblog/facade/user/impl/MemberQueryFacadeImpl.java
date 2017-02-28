package com.shaw.myblog.facade.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shaw.myblog.facade.user.pojo.dto.MemberDto;
import com.shaw.myblog.facade.user.service.MemberQueryFacade;
import com.shaw.myblog.mapper.MemberMapper;

@Component(value = "memberQueryFacadeImpl")
public class MemberQueryFacadeImpl implements MemberQueryFacade {

	
    @Autowired
    private MemberMapper memberMapper;
	
	public MemberDto queryByTelePhoneOrUserName(String memberName) {
		// TODO Auto-generated method stub
		return memberMapper.queryByTelePhoneOrUserName(memberName);
	}

   

}
