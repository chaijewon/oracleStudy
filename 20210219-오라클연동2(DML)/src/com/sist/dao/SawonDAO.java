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
   // ��� 3 => ���Ի�� �߰� => �Ű�����  INSERT => void (����Ŭ ��ü���� ó��)
   public void insert(SawonVO vo) // ��ü ������ ������ �ִ� Ŭ������ �Ű������� ��� (�Ű������� 3���̻��̸� => Ŭ����)
   {
	   try
	   {
		   // ���� ����� ó�� 
		   // 1. ���� 
		   getConnection();
		   // 2. SQL���� ���� 
		   String sql="INSERT INTO sawon VALUES("
				     +"(SELECT NVL(MAX(sabun)+1,1) FROM sawon)," // �ڵ� ���� (Primary Key�� �ذ�) = SEQUENCE
				     +"?,?,?,?,SYSDATE,?)";
		   // ���� (����Ŭ)
		   ps=conn.prepareStatement(sql);
		   // �������� ?�� ���� ä��� 
		   ps.setString(1, vo.getName());
		   ps.setString(2, vo.getSex());
		   ps.setString(3, vo.getDept());
		   ps.setString(4, vo.getJob());
		   ps.setInt(5, vo.getPay());
		   
		   // ���� ���
		   ps.executeUpdate();
		   
	   }catch(Exception ex)
	   {
		   // ���� Ȯ�� 
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   // try , catch�� ���þ��� ������ �����ϴ� ���� (�����ͺ��̽�,��Ʈ��ũ)
		   // ����Ŭ �ݱ�
		   disConnection();
	   }
   }
   // ��Ÿ 
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
   // ��� 4 => ����,�޿����� => �Ű�����  UPDATE => void (����Ŭ ��ü ó��)
   public void update(int sabun,String dept,String job,int pay)
   {
	   try
	   {
		   getConnection();
		   // SQL���� ���� 
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
		   // ?�� ���� ä��� 
		   ps.setString(1, dept);
		   ps.setString(2, job);
		   ps.setInt(3, pay);
		   ps.setInt(4, sabun);
		   
		   // ���� ��� 
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
   // ��� 5 => ���        => �Ű�����  DELEET => void (����Ŭ ��ü ó��)
   public void delete(int sabun)
   {
	   try
	   {
		   // ����
		   getConnection();
		   // SQL���� ����
		   String sql="DELETE FROM sawon "
				     +"WHERE sabun=?"; // COMMIT�� ���� 
		   // ����Ŭ ���� 
		   ps=conn.prepareStatement(sql);
		   // �������� ?�� ������ �ݵ�� ���� ä���ش� (�ε������� ������ IN �Ǵ� OUT �Ű�����:: 1)
		   ps.setInt(1, sabun);
		   // ����
		   ps.executeUpdate(); 
		   /*
		    *   executeUpdate() => COMMIT ==> INSERT,UPDATE , DELETE(����Ŭ �����Ͱ� �����)
		    *   executeQuery() => COMMIT�� ���� => SELECT
		    */
	   }catch(Exception ex)
	   {
		   System.out.println(ex.getMessage());
	   }
	   finally
	   {
		   // ����
		   disConnection();
	   }
   }
   // ��� 6 => ã��        => ArrsyList SELECT => ������ (���:ArrayList,�Ѱ� : VO)
   
   
}








