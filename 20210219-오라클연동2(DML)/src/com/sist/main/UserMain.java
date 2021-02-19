package com.sist.main;
import java.util.*;
import com.sist.dao.*; // �ٸ� ��Ű���� Ŭ������ �����ϸ� �ݵ�� import�� �̿��ؼ� ������ �´� 
public class UserMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println("=========== ��� ��ü ��� ==============");
        // ����Ŭ�κ��� ������ �б� 
        SawonDAO dao=new SawonDAO();
        ArrayList<SawonVO> list=dao.selectList();
        // �о�� �����͸� ����Ѵ� 
        for(SawonVO vo:list)
        {
        	System.out.println(vo.getSabun()+" "
        			+vo.getName()+" "
        			+vo.getSex()+" "
        			+vo.getDept()+" "
        			+vo.getJob()+" "
        			+vo.getHiredate());
        }
        System.out.println("======================================");
        Scanner scan=new Scanner(System.in);
        System.out.print("�� �� ����� �Է�:");
        int sabun=scan.nextInt();
        SawonVO vo=dao.selectOne(sabun);
        System.out.println("���:"+vo.getSabun());
        System.out.println("�̸�:"+vo.getName());
        System.out.println("����:"+vo.getSex());
        System.out.println("�μ�:"+vo.getDept());
        System.out.println("����:"+vo.getJob());
        System.out.println("�Ի���:"+vo.getHiredate());
        System.out.println("����:"+vo.getPay());
	} 

}







