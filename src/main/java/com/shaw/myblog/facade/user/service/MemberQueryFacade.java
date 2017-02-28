package com.shaw.myblog.facade.user.service;

import com.shaw.myblog.facade.user.pojo.dto.MemberDto;

public interface MemberQueryFacade {

	MemberDto queryByTelePhoneOrUserName(String memberName);

}
