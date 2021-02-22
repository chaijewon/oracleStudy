package com.sist.dao;
import java.sql.*;
import java.util.*;
// 언제 뷰생성하는 것이 적당하지 여부
public class ViewDAO {
    // 연결
	private Connection conn;
	// SQL문장을 전송 
	private PreparedStatement ps;
	// 오라클 연결 
	private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
	// 드라이버 등록
	public ViewDAO()
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Class.forName() => 등록된 클래스의 메모리 할당 (new) => 클래스 정보를 읽어서 관리 : 리플렉션
			// 메소드 제어 , 멤버변수,생성자, 매개변수를 제어 할 수 있다 
			// 예외처리(CheckException:컴파일시 검사=반드시 예외 처리) => ClassNotFoundException
		}catch(Exception ex){}
	}
	// 오라클에 연결 
	public void getConnection()
	{
		// conn hr/happy
		try
		{
			conn=DriverManager.getConnection(URL,"hr","happy");
		}catch(Exception ex) {}
	}
	// 오라클 연결 종료
	public void disConnection()
	{
		// exit
		try
		{
			if(ps!=null) ps.close(); // 연결중이면 
			if(conn!=null) conn.close();
		}catch(Exception ex) {}
	}
	// 기능 (목록) => SQL문장 , View를 이용 
	/*
	 *   SELECT empno,ename,job,hiredate,sal,dname,loc,grade
		  FROM emp,dept,salgrade
		  WHERE emp.deptno=dept.deptno
		  AND sal BETWEEN losal AND hisal
	 */
	public void empAllData()
	{
		try
		{
			// 1. 연결
			getConnection();
			// 2. SQL문장 제작 
			/*
			 * String sql="SELECT empno,ename,job,hiredate,sal,dname,loc,grade " +
			 * "FROM emp,dept,salgrade " + "WHERE emp.deptno=dept.deptno " +
			 * "AND sal BETWEEN losal AND hisal";
			 */
			String sql="SELECT * FROM emp_dept_view ORDER BY empno ASC";
			// 3. 오라클에 전송 
			ps=conn.prepareStatement(sql); // conn이 오라클하고 연결된 객체
			// 4. 오라클 실행 결과를 받아 온다 
			ResultSet rs=ps.executeQuery();
			// 5. 결과값 출력 => ResultSet => 단위가 Record => empno,ename,job,hiredate,sal,dname,loc,grade
		    while(rs.next())
		    {
		    	System.out.println(rs.getInt(1)+" "
		    			+rs.getString(2)+" "
		    			+rs.getString(3)+" "
		    			+rs.getDate(4)+" "
		    			+rs.getInt(5)+" "
		    			+rs.getString(6)+" "
		    			+rs.getString(7)+" "
		    			+rs.getInt(8));
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
	}
	// 상세보기 
	public void empDetailData(int empno)
	{
		try
		{
			// 1. 연결
			getConnection();
			// 2. SQL문장 제작
			/*String sql="SELECT empno,ename,job,hiredate,sal,dname,loc,grade "
					+ "FROM emp,dept,salgrade "
					+ "WHERE emp.deptno=dept.deptno "
					+ "AND sal BETWEEN losal AND hisal "
					+ "AND empno=?";*/
			String sql="SELECT * FROM emp_dept_view "
					  +"WHERE empno=?";
			// View => SELECT의 모든 기능을 사용 할 수 있다 (GROUP BY,WHERE,ORDER BY)
			// View도 일반 테이블처럼 사용이 된다 
			ps=conn.prepareStatement(sql);
			// ?에 값을 채운다 
			ps.setInt(1, empno);
			// 3. 실행요청
			ResultSet rs=ps.executeQuery();
			// 커서위치 변경 => 데이터가 있는 위치로 지정 => next()
			rs.next();
			// 4. 결과값 출력 => 브라우저 (VO에 값을 채운다)
			System.out.println(rs.getInt(1)+" "
	    			+rs.getString(2)+" "
	    			+rs.getString(3)+" "
	    			+rs.getDate(4)+" "
	    			+rs.getInt(5)+" "
	    			+rs.getString(6)+" "
	    			+rs.getString(7)+" "
	    			+rs.getInt(8));
			rs.close();
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			disConnection();
		}
	}
	// 페이지 나누기 => movie 
	public void movieListData(int page) // 사용자가 보내준 페이지를 받아서 해당 페이비만 전송 
	{
		try
		{
			// 1. 연결
			getConnection();
			String sql="SELECT mno,title,num "
					  +"FROM (SELECT mno,title,rownum as num "
					  +"FROM (SELECT mno,title "
					  +"FROM movie ORDER BY mno ASC)) "
					  +"WHERE num BETWEEN ? AND ?";
			ps=conn.prepareStatement(sql);
			// ?에 값을 채운다 
			int rowSize=20;
			int start=(page*rowSize)-(rowSize-1);
			int end=rowSize*page;
			
			/*
			 *   1page : 1~20
			 *   2page : 21~40  ==> rownum은 1번 시작 
			 *   ..
			 *   ..
			 */
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			// 실행 요청
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+". "+rs.getString(2));
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
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ViewDAO dao=new ViewDAO();
        //dao.empAllData();
        //dao.empDetailData(7788);
        Scanner scan=new Scanner(System.in);
        System.out.print("페이지 설정:");
        int page=scan.nextInt();
        dao.movieListData(page);
	}

}










