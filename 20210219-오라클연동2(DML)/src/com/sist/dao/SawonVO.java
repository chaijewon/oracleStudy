package com.sist.dao;
/*
 *      SABUN    NOT NULL NUMBER(4)   => int  
		NAME     NOT NULL VARCHAR2(30) => String
		SEX               VARCHAR2(10) 
		DEPT              VARCHAR2(30) 
		JOB               VARCHAR2(30) 
		HIREDATE          DATE         => java.util.Date
		PAY      NOT NULL NUMBER       => int
 */
// JDBC (자바로 데이터베이스(오라클) 연결) => WEB 프로그램의 핵심
/*
 *   ~VO(~DTO,~Bean) : 오라클에 있는 데이터를 모아서 전송 (브라우저,모바일)
 *   ~DAO : 오라클에 연결 => SQL문장 전송 , 결과값 
 *   =========== 70~80%를 해결 (20~30% => 추천 , 예측) => 자바로 자체
 *                                      ==========
 *                                      카페,블로그,트위터 => 추출 
 *   아이템 
 *     = 추천 , 예측 : 여행 (경로) , 범죄 예측 (공공포털,CCTV) 
 *     = 예약 : 영화 , 항공기, 버스... (직거래)
 *     = 스트리밍 : 음악(멜론..) , 아프리카 TV
 *     = 쇼핑몰 , 영화 (X) 
 *     = 냉장고 => 레시피 => 맛집
 */
import java.util.*;
// 사원 한명에 대한 정보 => new를 사용하면 사원 여러개명을 저장 할 수 있다 
// 오라클에 있는 데이터를 저장할 때 사용하는 클래스 (Value Object)
public class SawonVO {
    private int sabun;
    private String name;
    private String sex;
    private String dept;
    private String job;
    private Date hiredate;
    private int pay;
	public int getSabun() {
		return sabun;
	}
	public void setSabun(int sabun) {
		this.sabun = sabun;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	public int getPay() {
		return pay;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
	  
  
}












