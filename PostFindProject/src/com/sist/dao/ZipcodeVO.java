package com.sist.dao;

public class ZipcodeVO {
    private String zipcode;// 우편번호
    private String sido;// 시,도
    private String gugun; // 구,군
    private String dong; // 동
    private String bunji;// 번지 
    private String addrss; // 시 구 동 번지 
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getSido() {
		return sido;
	}
	public void setSido(String sido) {
		this.sido = sido;
	}
	public String getGugun() {
		return gugun;
	}
	public void setGugun(String gugun) {
		this.gugun = gugun;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getBunji() {
		return bunji;
	}
	public void setBunji(String bunji) {
		this.bunji = bunji;
	}
	public String getAddrss() {
		return sido+" "+gugun+" "+dong+" "+bunji;
	}
	   
   
}
