package com.sist.dao;
/*
 * 1. 오라클 연결 
 *     Connection 
 *    오라클로 SQL을 전송  
 *     PreparedStatement : SQL전송 ,실행결과값을 받는다 
 *    오라클에서 받은 결과값을 저장 
 *     ResultSet
 *    # 오라클이 실행 => SQL => 자바에서 SQL문장을 만들어서 전송
 *    # 브라우저가 실행 => HTML => 자바에서 HTML만들어서 브러우저로 전송 
 *    
 *           사용자 요청        SQL문장을 전송
 *    브라우저   ======   자바  ======= 오라클
 *           HTML로 전송     실행 결과를 보내준다   ==> JSP
 *           ==== 기능(브라우저에 실행하는 언어)
 * 2. 오라클 연결전에 드라이버를 설치 Class.forName("오라클 드라이버")
 *    자바 <=> 오라클 (X) (oci=> 유료)
 *    자바 <=> 드라이버 <=> 오라클 (thin)
 * 3. 결과값을 받는 경우 
 *    ===== 단위가 Record (컬럼값 여러개가 들어 온다) => while문 한번 수행이
 *          전체 데이터를 받아서 저장 (VO) => VO여러개 모아서 저장(컬렉션)
 *    # 라이브러리 => 표준화(모든 개발자가 동일하게 코딩) => 누가 SQL문장을 잘 만드는지...
 */
import java.sql.*;
import java.util.*;
public class SawonDAO {
   // 오라클 연결에 필요한 클래스를 가지고 온다 
   // 1. 연결
   private Connection conn; // Socket이용 => TCP
   // 2. 전송 (SQL문장을 전송) 
   private PreparedStatement ps; // OutputStrem (전송), BufferedReader(수신)
   // 3. 오라클 주소 => jdbc:업체명:IP:PORT:데이터베이스(테이블 저장되어 있는 데이터베이스(폴더)) => XE
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   
   // 1. 드라이버 등록 => 한번만 수행이 가능 (한번만 호출이 가능 메소드 => 생성자)
   public SawonDAO()
   {
	   // JDBC (Network) => CheckException => 반드시 예외처리를 해야된다
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver"); // 오라클에서 연결이 가능하게 만든 클래스
		   // 대소문자 구분 ==> 방식 = 패키지.클래스명 => 클래스의 정보를 읽어서 메모리 할당 
		   // 등록 : 패키지.클래스 => 메모리 할당을 요청 
		   // 1. new , 2. Class.forName() => 스프링(사용자 정의 클래스 관리자)
		   // new를 사용하면 => 결합성이 높은 프로그램 
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
   }
   // 2. 연결,해제를 반복 => 한사람당 1개의 Connection만 사용이 가능하게 만든다  (XE:연습용=>50개이상)
   public void getConnection()
   {
	   try
	   {
		   conn=DriverManager.getConnection(URL,"hr","happy");
		   // conn hr/happy
	   }catch(Exception ex) {}
   }
   // 3. 해제
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close();
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   // 싱글턴 => Web을 사용하기전에 디자인패턴 
   // 4. 각 테이블마다 기능 (SELECT , INSERT , UPDATE ,DELETE) => CURD
   // 기능 1 => 전체 목록을 출력  VO를 여러개 저장 ArrayList
   public ArrayList<SawonVO> selectList()
   {
	   ArrayList<SawonVO> list=new ArrayList<SawonVO>();
	   try
	   {
		   // 목록 => 페이지 나누기 (인라인뷰)
		   getConnection();//오라클 연결 
		   // SQL문장을 만들어서 오라클 전송할 준비 
		   String sql="SELECT sabun,name,sex,dept,job,hiredate "
				     +"FROM sawon "
				     +"ORDER BY 1"; // sql문장은 무조건 문자열로 만든다
		   ps=conn.prepareStatement(sql);
		   // 오라클에 요청 => SQL문장을 실행한 후에 결과값을 달라 
		   ResultSet rs=ps.executeQuery();
		   // 처음부터 끝까지 => 데이터를 읽어오기 시작 => next()
		   while(rs.next())
		   {
			   SawonVO vo=new SawonVO();
			   vo.setSabun(rs.getInt(1));
			   vo.setName(rs.getString(2));
			   vo.setSex(rs.getString(3));
			   vo.setDept(rs.getString(4));
			   vo.setJob(rs.getString(5));
			   vo.setHiredate(rs.getDate(6)); 
			   
			   list.add(vo);
		   }
		   rs.close();
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   disConnection();//오라클 연결 해제
	   }
	   return list;
   }
   // 기능 2 => 사원정보 상세보기 VO한개에 값을 채운다 
   public SawonVO selectOne(int sabun) // 반드시 중복이 없는 데이터(Primary Key)
   {
	   SawonVO vo=new SawonVO();
	   try
	   {
		   // 연결
		   getConnection();
		   // SQL문장 제작
		   String sql="SELECT * "
				     +"FROM sawon "
				     +"WHERE sabun=?";
		   ps=conn.prepareStatement(sql);
		   // ?에 값을 채운다 
		   ps.setInt(1, sabun); // 문자열 => setString() , 날짜 => setDate()
		   //      번호 => ?의 순서  => 오라클(번호가 1번부터 시작한다)
		   //  ?,?,? => 1,2,3
		   // 실행요청
		   ResultSet rs=ps.executeQuery();
		   rs.next();
		   // 결과값 받기
		   vo.setSabun(rs.getInt(1));
		   vo.setName(rs.getString(2));
		   vo.setSex(rs.getString(3));
		   vo.setDept(rs.getString(4));
		   vo.setJob(rs.getString(5));
		   vo.setHiredate(rs.getDate(6));
		   vo.setPay(rs.getInt(7));
		   
		   rs.close();
	   }catch(Exception ex)
	   {
		   // 오류처리 
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   // 오라클 해제 
		   disConnection();
	   }
	   return vo;
   }
   // 기능 3 => 신입사원 추가 => 매개변수  INSERT => void (오라클 자체에서 처리)
   public void insert(SawonVO vo) // 전체 데이터 가지고 있는 클래스를 매개변수로 사용 (매개변수는 3개이상이면 => 클래스)
   {
	   try
	   {
		   // 정상 수행시 처리 
		   // 1. 연결 
		   getConnection();
		   // 2. SQL문장 생성 
		   String sql="INSERT INTO sawon VALUES("
				     +"(SELECT NVL(MAX(sabun)+1,1) FROM sawon)," // 자동 증가 (Primary Key를 해결) = SEQUENCE
				     +"?,?,?,?,SYSDATE,?)";
		   // 전송 (오라클)
		   ps=conn.prepareStatement(sql);
		   // 실행전에 ?에 값을 채운다 
		   ps.setString(1, vo.getName());
		   ps.setString(2, vo.getSex());
		   ps.setString(3, vo.getDept());
		   ps.setString(4, vo.getJob());
		   ps.setInt(5, vo.getPay());
		   
		   // 실행 명령
		   ps.executeUpdate();
		   
	   }catch(Exception ex)
	   {
		   // 오류 확인 
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   // try , catch와 관련없이 무조건 수행하는 문장 (데이터베이스,네트워크)
		   // 오라클 닫기
		   disConnection();
	   }
   }
   // 기타 
   public ArrayList<String> sawonGetDept()
   {
	   ArrayList<String> list=new ArrayList<String>();
	   try
	   {
		   getConnection();
		   String sql="SELECT DISTINCT dept FROM sawon";
		   ps=conn.prepareStatement(sql);
		   ResultSet rs=ps.executeQuery();
		   while(rs.next())
		   {
			   String dept=rs.getString(1);
			   list.add(dept);
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
   public ArrayList<String> sawonGetJob()
   {
	   ArrayList<String> list=new ArrayList<String>();
	   try
	   {
		   getConnection();
		   String sql="SELECT DISTINCT job FROM sawon";
		   ps=conn.prepareStatement(sql);
		   ResultSet rs=ps.executeQuery();
		   while(rs.next())
		   {
			   String job=rs.getString(1);
			   list.add(job);
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
   // 기능 4 => 진급,급여변동 => 매개변수  UPDATE => void (오라클 자체 처리)
   public void update(int sabun,String dept,String job,int pay)
   {
	   try
	   {
		   getConnection();
		   // SQL문장 제작 
		   /*
		    *    String sql="UPDATE sawon SET "
		    *              +"dept='"+dept+"',"+job="+job+"',pay="+pay;
		    */
		   String sql="UPDATE sawon SET "
				     +"dept=?,job=?,pay=? "
				     +"WHERE sabun=?";
		   // default 
		   // setString() => ''   setInt()
		   ps=conn.prepareStatement(sql);
		   // ?에 값을 채운다 
		   ps.setString(1, dept);
		   ps.setString(2, job);
		   ps.setInt(3, pay);
		   ps.setInt(4, sabun);
		   
		   // 수정 명령 
		   ps.executeUpdate();
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   disConnection();
	   }
   }
   // 기능 5 => 퇴사        => 매개변수  DELEET => void (오라클 자체 처리)
   public void delete(int sabun)
   {
	   try
	   {
		   // 연결
		   getConnection();
		   // SQL문장 제작
		   String sql="DELETE FROM sawon "
				     +"WHERE sabun=?"; // COMMIT을 수행 
		   // 오라클 전송 
		   ps=conn.prepareStatement(sql);
		   // 실행전에 ?가 있으면 반드시 값을 채워준다 (인덱스에서 누락된 IN 또는 OUT 매개변수:: 1)
		   ps.setInt(1, sabun);
		   // 실행
		   ps.executeUpdate(); 
		   /*
		    *   executeUpdate() => COMMIT ==> INSERT,UPDATE , DELETE(오라클 데이터가 변경시)
		    *   executeQuery() => COMMIT이 없다 => SELECT
		    */
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   // 해제
		   disConnection();
	   }
   }
   // 기능 6 => 찾기        => ArrsyList SELECT => 리턴형 (목록:ArrayList,한개 : VO)
   
   
}








