package com.shaw.myblog.facade.user.pojo.dto;

import java.io.Serializable;
import java.util.Date;

public class MemberDto implements Serializable {
    private static final long serialVersionUID = -3326367801971536705L;

    private Long id;

    private Long shopId;

    private String memberName;

    private String password;

    private Double amount = 0.00;

    private Integer score = 0;

    private String status = "1";

    private String telephone;

    private String qqOpenid;

    private String nickname;
    
    private String salt;

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getMemberName() {
        return memberName;
    }

    public void setNickName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


}