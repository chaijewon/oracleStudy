package com.sist.dao;
// 오라클 연결하는 프로그램 
import java.util.*;// ArrayList
import java.sql.*;
public class MusicDAO {
   // 연결하는 클래스 
   private Connection conn;//오라클 연결 
   // SQL문장으르 전송하는 클래스 
   private PreparedStatement ps;
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   //1. 연결 드라이버 설치 => 한번만 사용 (생성자)
   public MusicDAO()
   {
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver");
	   }catch(Exception ex){}
   }
   // 2. 연결 
   public void getConnection()
   {
	   try
	   {
		   conn=DriverManager.getConnection(URL,"hr","happy");
		   // 오라클 => conn hr/happy
	   }catch(Exception ex) {}
   }
   // 3. 해제
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close();// 통신(송신:OutputStream,수신:BufferReader)
		   if(conn!=null) conn.close();// 연결기기: Socket
	   }catch(Exception ex) {}
   }
   // 4. 기능 => 뮤직데이터 50개를 받아서 저장 ==> 저장된 데이터 브라우저에서 읽어서 출력 
   public ArrayList<MusicVO> musicListData()
   {
	   // return selectList("sql"); ==> mybatis
	   ArrayList<MusicVO> list=new ArrayList<MusicVO>();
	   try
	   {
		   // 1. 연결 
		   getConnection();
		   // 2. SQL문장을 만든다
		   String sql="SELECT no,title,poster,singer,album,state,idcrement "
				     +"FROM genie_music "
				     +"ORDER BY 1";
				    
		   ps=conn.prepareStatement(sql);// 전송준비 
		   // 3. SQL문장 전송 
		   ResultSet rs=ps.executeQuery();
		   // 4. 결과값을 가지고 온다 
		   // 5. ArrayList에 담는다 
		   /*
		    *    =================
		    *     no title...
		    *    =================
		    *     1    ==          | next()
		    *    =================
		    *     2    ==          | next()
		    *    =================
		    *     3    ==          | next()
		    *    =================
		    *     4    ==          | next()
		    *    =================
		    *                      | next() => false
		    *    
		    */
		   while(rs.next())
		   {
			   MusicVO vo=new MusicVO();
			   vo.setNo(rs.getInt(1));
			   vo.setTitle(rs.getString(2));
			   vo.setPoster(rs.getString(3));
			   vo.setSinger(rs.getString(4));
			   vo.setAlbum(rs.getString(5));
			   vo.setState(rs.getString(6));
			   vo.setIdcrement(rs.getInt(7));
			   list.add(vo);
		   }
		   rs.close();
		   // 6. 대기상태 
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection(); // 닫기 (오류,정상 => 무조건 오라클을 닫는다)
	   }
	   return list;
   }
   
   public void empFindData(String ename)
   {
	   try
	   {
		   getConnection();
		   String sql="SELECT ename,job,hiredate,sal "
				     +"FROM emp "
				     +"WHERE ename LIKE '%'||?||'%'";// LIKE
		   ps=conn.prepareStatement(sql); // 오라클로 전송 
		   // ?에 값을 채운다 
		   ps.setString(1, ename);
		   // 실행한 결과가를 가지고 온다 
		   ResultSet rs=ps.executeQuery();
		   // 데이터 출력 
		   while(rs.next()) //rs.previous()
		   {
			    System.out.println("이름:"+rs.getString(1));
			    System.out.println("직위:"+rs.getString(2));
			    System.out.println("입사일:"+rs.getDate(3));
			    System.out.println("급여:"+rs.getInt(4));
			    System.out.println("==================");
		   }
		   rs.close();
	   }catch(Exception ex)
	   {
		   ex.printStackTrace();
	   }
	   finally
	   {
		   disConnection();
	   }
   }
   
   public static void main(String[] args) {
	  Scanner scan=new Scanner(System.in);
	  System.out.print("이름 입력:");
	  String ename=scan.next();
	  // 출력
	  MusicDAO dao=new MusicDAO();
	  dao.empFindData(ename);
   }
   
}













