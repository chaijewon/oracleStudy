package com.sist.dao;
/*
 * 1. ����Ŭ ���� 
 *     Connection 
 *    ����Ŭ�� SQL�� ����  
 *     PreparedStatement : SQL���� ,���������� �޴´� 
 *    ����Ŭ���� ���� ������� ���� 
 *     ResultSet
 *    # ����Ŭ�� ���� => SQL => �ڹٿ��� SQL������ ���� ����
 *    # �������� ���� => HTML => �ڹٿ��� HTML���� �귯������ ���� 
 *    
 *           ����� ��û        SQL������ ����
 *    ������   ======   �ڹ�  ======= ����Ŭ
 *           HTML�� ����     ���� ����� �����ش�   ==> JSP
 *           ==== ���(�������� �����ϴ� ���)
 * 2. ����Ŭ �������� ����̹��� ��ġ Class.forName("����Ŭ ����̹�")
 *    �ڹ� <=> ����Ŭ (X) (oci=> ����)
 *    �ڹ� <=> ����̹� <=> ����Ŭ (thin)
 * 3. ������� �޴� ��� 
 *    ===== ������ Record (�÷��� �������� ��� �´�) => while�� �ѹ� ������
 *          ��ü �����͸� �޾Ƽ� ���� (VO) => VO������ ��Ƽ� ����(�÷���)
 *    # ���̺귯�� => ǥ��ȭ(��� �����ڰ� �����ϰ� �ڵ�) => ���� SQL������ �� �������...
 */
import java.sql.*;
import java.util.*;
public class SawonDAO {
   // ����Ŭ ���ῡ �ʿ��� Ŭ������ ������ �´� 
   // 1. ����
   private Connection conn; // Socket�̿� => TCP
   // 2. ���� (SQL������ ����) 
   private PreparedStatement ps; // OutputStrem (����), BufferedReader(����)
   // 3. ����Ŭ �ּ� => jdbc:��ü��:IP:PORT:�����ͺ��̽�(���̺� ����Ǿ� �ִ� �����ͺ��̽�(����)) => XE
   private final String URL="jdbc:oracle:thin:@localhost:1521:XE";
   
   // 1. ����̹� ��� => �ѹ��� ������ ���� (�ѹ��� ȣ���� ���� �޼ҵ� => ������)
   public SawonDAO()
   {
	   // JDBC (Network) => CheckException => �ݵ�� ����ó���� �ؾߵȴ�
	   try
	   {
		   Class.forName("oracle.jdbc.driver.OracleDriver"); // ����Ŭ���� ������ �����ϰ� ���� Ŭ����
		   // ��ҹ��� ���� ==> ��� = ��Ű��.Ŭ������ => Ŭ������ ������ �о �޸� �Ҵ� 
		   // ��� : ��Ű��.Ŭ���� => �޸� �Ҵ��� ��û 
		   // 1. new , 2. Class.forName() => ������(����� ���� Ŭ���� ������)
		   // new�� ����ϸ� => ���ռ��� ���� ���α׷� 
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
   }
   // 2. ����,������ �ݺ� => �ѻ���� 1���� Connection�� ����� �����ϰ� �����  (XE:������=>50���̻�)
   public void getConnection()
   {
	   try
	   {
		   conn=DriverManager.getConnection(URL,"hr","happy");
		   // conn hr/happy
	   }catch(Exception ex) {}
   }
   // 3. ����
   public void disConnection()
   {
	   try
	   {
		   if(ps!=null) ps.close();
		   if(conn!=null) conn.close();
	   }catch(Exception ex) {}
   }
   // �̱��� => Web�� ����ϱ����� ���������� 
   // 4. �� ���̺��� ��� (SELECT , INSERT , UPDATE ,DELETE) => CURD
   // ��� 1 => ��ü ����� ���  VO�� ������ ���� ArrayList
   public ArrayList<SawonVO> selectList()
   {
	   ArrayList<SawonVO> list=new ArrayList<SawonVO>();
	   try
	   {
		   // ��� => ������ ������ (�ζ��κ�)
		   getConnection();//����Ŭ ���� 
		   // SQL������ ���� ����Ŭ ������ �غ� 
		   String sql="SELECT sabun,name,sex,dept,job,hiredate "
				     +"FROM sawon "
				     +"ORDER BY 1"; // sql������ ������ ���ڿ��� �����
		   ps=conn.prepareStatement(sql);
		   // ����Ŭ�� ��û => SQL������ ������ �Ŀ� ������� �޶� 
		   ResultSet rs=ps.executeQuery();
		   // ó������ ������ => �����͸� �о���� ���� => next()
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
		   disConnection();//����Ŭ ���� ����
	   }
	   return list;
   }
   // ��� 2 => ������� �󼼺��� VO�Ѱ��� ���� ä��� 
   public SawonVO selectOne(int sabun) // �ݵ�� �ߺ��� ���� ������(Primary Key)
   {
	   SawonVO vo=new SawonVO();
	   try
	   {
		   // ����
		   getConnection();
		   // SQL���� ����
		   String sql="SELECT * "
				     +"FROM sawon "
				     +"WHERE sabun=?";
		   ps=conn.prepareStatement(sql);
		   // ?�� ���� ä��� 
		   ps.setInt(1, sabun); // ���ڿ� => setString() , ��¥ => setDate()
		   //      ��ȣ => ?�� ����  => ����Ŭ(��ȣ�� 1������ �����Ѵ�)
		   //  ?,?,? => 1,2,3
		   // �����û
		   ResultSet rs=ps.executeQuery();
		   rs.next();
		   // ����� �ޱ�
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
		   // ����ó�� 
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   // ����Ŭ ���� 
		   disConnection();
	   }
	   return vo;
   }
   // ��� 3 => ���Ի�� �߰� => �Ű����� 
   // ��� 4 => ����,�޿����� => �Ű�����
   // ��� 5 => ���        => �Ű����� 
   // ��� 6 => ã��        => ArrsyList
   
}








