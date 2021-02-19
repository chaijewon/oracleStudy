package com.sist.main;
import java.util.*;
import com.sist.dao.*; // 다른 패키지에 클래스가 존재하면 반드시 import를 이용해서 가지고 온다 
public class UserMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println("=========== 사원 전체 목록 ==============");
        // 오라클로부터 데이터 읽기 
        SawonDAO dao=new SawonDAO();
        ArrayList<SawonVO> list=dao.selectList();
        // 읽어온 데이터를 출력한다 
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
        System.out.print("상세 볼 사번을 입력:");
        int sabun=scan.nextInt();
        SawonVO vo=dao.selectOne(sabun);
        System.out.println("사번:"+vo.getSabun());
        System.out.println("이름:"+vo.getName());
        System.out.println("성별:"+vo.getSex());
        System.out.println("부서:"+vo.getDept());
        System.out.println("직위:"+vo.getJob());
        System.out.println("입사일:"+vo.getHiredate());
        System.out.println("연봉:"+vo.getPay());
	} 

}







