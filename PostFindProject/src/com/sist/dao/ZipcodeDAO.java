package com.sist.dao;
import java.util.*;
import java.sql.*;
public class ZipcodeDAO {
   // 1. 오라클 연결 클래스
   private Connection conn;
   // 2. SQL문장 전송 클래스 
   private PreparedStatement ps;
   // 3. 오라클 주소 
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   
   // 4. 드라이버 등록 
   public ZipcodeDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
   }
   // 5. 오라클 연결 메소드
   public void getConnection()
   {
	   try
	   {
		   // conn hr/happy
		   conn=DriverManager.getConnection(URL,"hr","happy");
	   }catch(Exception ex) {}
   }
   // 6. 오라클 해제 메소드
   public void disConnection()
   {
	   try
	   {
		   // exit
		   if(ps!=null) ps.close();
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   //--------------------------------------------------------- DAO에서 필수로 사용 
   // 7. 기능설정 (우편번호 찾기) => 메소드 : 리턴형(사용자 요청 처리 결과값) , 매개변수(사용자가 보내주는 값)
   public ArrayList<ZipcodeVO> postFind(String dong)
   {
	   ArrayList<ZipcodeVO> list=new ArrayList<ZipcodeVO>();
	   try
	   {
		   // 연결
		   getConnection();
		   // SQL문장 제작 
		   String sql="SELECT zipcode,sido,gugun,dong,NVL(bunji,' ') "
				     +"FROM zipcode "
				     +"WHERE dong LIKE '%'||?||'%'";
		   ps=conn.prepareStatement(sql);
		   ps.setString(1, dong);
		   // 실행=> 결과값 읽기
		   ResultSet rs=ps.executeQuery();
		   while(rs.next())
		   {
			   ZipcodeVO vo=new ZipcodeVO();
			   vo.setZipcode(rs.getString(1));
			   vo.setSido(rs.getString(2));
			   vo.setGugun(rs.getString(3));
			   vo.setDong(rs.getString(4));
			   vo.setBunji(rs.getString(5));
			   
			   list.add(vo);
		   }
		   rs.close();
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   disConnection();
	   }
	   return list;
   }
}










