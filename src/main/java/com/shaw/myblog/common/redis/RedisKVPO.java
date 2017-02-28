package com.shaw.myblog.common.redis;

import java.io.Serializable;

public class RedisKVPO  implements Serializable{
	
	private static final long serialVersionUID = -2359135062677099127L;
	private String k;
	private String v;
	
	public RedisKVPO(){}
	
	public RedisKVPO(String k, String v) {
		this.k = k;
		this.v = v;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	
	
}
