package com.sist.music;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 *     Oracle 연결 (오라클 : 데이터베이스 (데이터를 저장하는 장소 => 데이터 공유)
 *     1. 데이터형 
 *        숫자형 
 *         NUMBER(10) , NUMBER(7,2)
 *         ==========   ===========
 *           int           double
 *        문자형
 *         CHAR , VARCHAR2 , CLOB => String 
 *        날짜형 
 *         DATE => Date
 *     ===> int , double , String , Date
 *     2. 데이터를 모아서 전송 
 *        ArrayList => 제네릭스 (데이터형 통일)
 *     3. Connection , Statement , ResultSet
 *     4. 예외처리 
 *    ========================================
 */
// 웹 크롤링 
public class GenieMusic {
    public void getGenieMusic()
    {
    	try
    	{
    		MusicDAO dao=new MusicDAO();
    		int k=201;
            for(int i=1;i<=2;i++)
            {
            	//Document doc=Jsoup.connect("https://www.genie.co.kr/chart/top200?ditc=D&ymd=20210210&hh=14&rtm=Y&pg="+i).get();
            	Document doc=Jsoup.connect("https://www.genie.co.kr/chart/genre?ditc=D&ymd=20210210&genrecode=M0100&pg="+i).get();
            	// title , singer , album , poster , state , idcrement, rank
            	Elements title=doc.select("tr.list a.title");// <a class="title">
            	Elements singer=doc.select("tr.list a.artist");
            	Elements album=doc.select("tr.list a.albumtitle");
            	Elements poster=doc.select("tr.list a.cover img");
            	Elements etc=doc.select("tr.list span.rank");
            	for(int j=0;j<title.size();j++)
            	{
            		System.out.println("순위:"+k);
            		System.out.println("제목:"+title.get(j).text());
            		System.out.println("가수:"+singer.get(j).text());
            		System.out.println("앨범:"+album.get(j).text());
            		System.out.println("포스터:"+poster.get(j).attr("src"));
            		String str=etc.get(j).text();
            		
            		// 유지
            		// 2하강
            		// 1상승
            		// new
            		String id="";
            		
            		String state=str.replaceAll("[^가-힣|^a-z]", "");// 한글을 제외하고 나머지는 공백 
            		
            		if(state.equals("유지"))
            		{
            			id="0";
            		}
            		else if(str.equals("new"))
            		{
            			id="0";
            		}
            		else
            		{
            			id=str.replaceAll("[^0-9]", ""); //1
            		}
            		
            		System.out.println("상태:"+state);
            		System.out.println("등폭:"+id);
            		//System.out.println("상태:"+etc.get(j).text());
            		System.out.println("=============================================");
            		// 데이터모아서 => MusicDAO로 전송 => 오라클에 Insert
            		GenieMusicVO vo=new GenieMusicVO();
            		vo.setNo(k);
            		vo.setCno(2);
            		vo.setTitle(title.get(j).text());
            		vo.setSinger(singer.get(j).text());
            		vo.setAlbum(album.get(j).text());
            		vo.setPoster(poster.get(j).attr("src"));
            		vo.setState(state);
            		vo.setIdcrement(Integer.parseInt(id));
            		
            		dao.genieMusicInsert(vo);
            		
            		Thread.sleep(100);
            		k++;
            	}
            }
    	}catch(Exception ex){ex.printStackTrace();}
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        GenieMusic gm=new GenieMusic();
        gm.getGenieMusic();
	}

}







