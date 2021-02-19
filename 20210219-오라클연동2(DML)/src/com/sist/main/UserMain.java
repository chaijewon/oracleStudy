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
        /*System.out.print("상세 볼 사번을 입력:");
        int sabun=scan.nextInt();
        SawonVO vo=dao.selectOne(sabun);
        System.out.println("사번:"+vo.getSabun());
        System.out.println("이름:"+vo.getName());
        System.out.println("성별:"+vo.getSex());
        System.out.println("부서:"+vo.getDept());
        System.out.println("직위:"+vo.getJob());
        System.out.println("입사일:"+vo.getHiredate());
        System.out.println("연봉:"+vo.getPay());*/
        //System.out.print("퇴사할 사원의 사번 입력:");
        //int sabun=scan.nextInt();
        //dao.delete(sabun);
        /*// 데이터 추가 
        // 1.이름 입력
        System.out.print("이름 입력:");
        String name=scan.next();
        // 2.성별 입력
        System.out.print("성별을 선택하세요(남자,여자):");
        String sex=scan.next();
        // 3.부서 선택 
        ArrayList<String> dList=dao.sawonGetDept();
        String temp="";
        for(String s:dList)
        {
        	temp+=s+",";
        }
        temp=temp.substring(0,temp.lastIndexOf(","));
        System.out.println("부서를 선택하세요("+temp+"):");
        String dept=scan.next();
        
        // 4. 직위 선택 
        ArrayList<String> jList=dao.sawonGetJob();
        temp="";
        for(String s:jList)
        {
        	temp+=s+",";
        }
        temp=temp.substring(0,temp.lastIndexOf(","));
        System.out.println("직위를 선택하세요("+temp+"):");
        String job=scan.next();
        
        // 5. 급여 입력
        System.out.print("급여 입력:");
        int pay=scan.nextInt();
        
        // VO로 모아서 => DAO로 전송 => 오라클연결 (INSERT)
        SawonVO vo=new SawonVO();
        vo.setName(name);
        vo.setSex(sex);
        vo.setDept(dept);
        vo.setJob(job);
        vo.setPay(pay);
        
        dao.insert(vo);
        System.out.println("사원 추가 완료");*/
        // 수정 
        // 1. 수정할 사번 선택 
        System.out.print("수정할 사원의 사번 입력:");
        int sabun=scan.nextInt();
        
        // 2.부서 선택 
        ArrayList<String> dList=dao.sawonGetDept();
        String temp="";
        for(String s:dList)
        {
        	temp+=s+",";
        }
        temp=temp.substring(0,temp.lastIndexOf(","));
        System.out.println("부서를 선택하세요("+temp+"):");
        String dept=scan.next();
        
        // 3. 직위 선택 
        ArrayList<String> jList=dao.sawonGetJob();
        temp="";
        for(String s:jList)
        {
        	temp+=s+",";
        }
        temp=temp.substring(0,temp.lastIndexOf(","));
        System.out.println("직위를 선택하세요("+temp+"):");
        String job=scan.next();
        
        // 4. 급여 입력
        System.out.print("급여 입력:");
        int pay=scan.nextInt();
        
        
        // 유저로부터 받은 값을 DAO로 전송 => 오라클로 SQL제작후에 전송 
        dao.update(sabun, dept, job, pay); // UPDATE ==> COMMIT
        System.out.println("사원 수정 완료!!");
	} 

}







