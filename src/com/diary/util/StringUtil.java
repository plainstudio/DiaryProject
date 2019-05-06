/*
 * 스트링 처리 관련하여 기능을 모아놓은 클래스
 * */
package com.diary.util;

public class StringUtil {
//확장자 추출하기!!
   public static String getExt(String path) {
      //가장 마지막 점의 index를 구한다
      int last=path.lastIndexOf(".");
      String ext=path.substring(last+1,path.length());
      return ext;
   }
   //전화번호 맨 뒷 4자리 추출하는 메서드
   public static String getId(String member_phone) {
      //가장 마지막 점의 index를 구한다
      int last=member_phone.lastIndexOf("-");
      String phoneName=member_phone.substring(last+1,member_phone.length());
      return phoneName;
   }
   //0000-00-00 00:00:00 형식에서 날짜부분만 추출하는 메서드
   public static String getDate(String origin_date) {
	   String date=origin_date.substring(0,11);
	   return date;
   }
 //+000000000 00:00:00.000000 형식에서 시,분만 추출하는 메서드
   public static String getHHMM(String origin_date) {
	   String hhmm=origin_date.substring(11,16);
	   return hhmm;
   }
 //00:00 형식을 분으로 변환시켜주는 메서드
   public static int getMM(String origin_date) {
	   String hh=origin_date.substring(0,2);
	   String mm=origin_date.substring(3,5);
	   
	   int hour=(Integer.parseInt(hh));
	   int min=(Integer.parseInt(mm));
	  int totalMin=(hour*60)+min;
	   
	   return totalMin;
   }
   //00:00 - 00:00 형식을 분으로 변환시켜주는 메서드
   public static String getTime(String finishTime,String startTime) {
	   String hour;
	   String min;
	   
	   String fhh=finishTime.substring(0,2);
	   String fmm=finishTime.substring(3,5);
	   String shh=startTime.substring(0,2);
	   String smm=startTime.substring(3,5);
	   int fhour=(Integer.parseInt(fhh));
	   int fmin=(Integer.parseInt(fmm));
	   int shour=(Integer.parseInt(shh));
	   int smin=(Integer.parseInt(smm));
	   
	   int thour=fhour-shour;
	   int tmin=fmin-smin;
	   
	   if(thour>9) {
		    hour=Integer.toString(thour)+":";
	   }else {
		    hour="0"+Integer.toString(thour)+":";
	   }
	   if(tmin>9) {
		    min=Integer.toString(tmin);
	   }else {
		    min="0"+Integer.toString(tmin);
	   }
	   
	  String totalTime=hour+min;
	   return totalTime;
   }

   /////////////////////////////////////////////////////////////////////////////
   //public static void main(String[] args) {
      //System.out.println(getExt("eeeeeeeeeee.jpg"));
   //}
}