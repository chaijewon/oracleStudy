package com.sist.music;
/*
 *  NO           NUMBER(3)     
	CNO          NUMBER(1)     
	TITLE        VARCHAR2(300) 
	SINGER       VARCHAR2(200) 
	ALBUM        VARCHAR2(200) 
	POSTER       VARCHAR2(260) 
	STATE        CHAR(4)      
	IDCREMENT    NUMBER(3) 
	
	데이터 여러개 => 관리하기 어렵다 
	========== 한개로 모아서 처리 
	데이터형 같다  ==> 배열
	데이터형 틀리다 ==> 클래스
	
 */
public class GenieMusicVO {
    private int no,cno,idcrement;
    private String title,singer,album,poster,state;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getCno() {
		return cno;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public int getIdcrement() {
		return idcrement;
	}
	public void setIdcrement(int idcrement) {
		this.idcrement = idcrement;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	   
}
