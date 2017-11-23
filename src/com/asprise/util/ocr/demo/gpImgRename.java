/*
 * ���ļ�����OCRʶ�𣬲������ļ���Ϊ��Ʊ����+ʱ�����У�ͬʱ��������һ��Ŀ¼�ȴ���һ����ʶ��Ͳ�����
 * ���յĲ�����D:\20170928 1525  58  71  21  D:\20170930 20171120
 * param1  Ϊ��Ҫ������ļ�Ŀ¼
 * param2��param3��param4��param5  Ϊʶ������
 * param6  ʶ����ļ��Ĵ��Ŀ¼
 * param7  ��������ڣ����ڶ��ļ�����������Ϊϵͳ���ڲ���ȷ�����Ը�һ����ǰ�����ڣ�
 */
package com.asprise.util.ocr.demo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.asprise.util.ocr.OCR;

public class gpImgRename {
	
	static String[] imagePosition = {
			"166",
			"209",
			"252",
			"295",
			"338",
			"381",
			"424",
			"467",
			"510",
			"553",
			"596",
			"639",
			"682",
			"725",
			"768",
			"811",
			"854",
			"897",
			"940",
			"983",
			"1026",
			"1069",
			"1112",
			"1155",
			"1198",
			"1241",
			"1284",
			"1327",
			"1370",
			"1413",
			"1456"				
	};
	
	
	 
	

	public static void main(String[] args) throws IOException {

		if (args.length <= 4) {
			System.out.println("------------------��������-----------------------------------------");
			for (int i = 1; i < args.length; i++)
				System.out.println(args[i]+"\r\n");
			System.out.println("---------------- -------------------------------------------\n");
		 } else {			 
			 getFileName( args);
			 getGuaDanData(args);
		 }
		
	}	

	/*
	 * ȡ�ùҵ����ݣ��������txt�ļ��С�
	 * */
	public static void getGuaDanData(String[] args ){
		
	   int gpdmCount=0;
//	  for (int i=0;i <imagePosition.length;i++)
//	  {
//		  System.out.print("\n i="+i+" pos="+imagePosition[i]);
//	  }
		String path = args[5] ; // ·�� "D:/20170928"
		File f = new File(path);
		if (!f.exists()) {
			System.out.println("ʶ��ҵ�ʱ���� "+path + " not exists");
			return;
		}		
		
	//	Pattern pattern = Pattern.compile("[a-z]*");	
		
		File outFile = new File(args[0] + "/" + args[6]+ "all.txt");

		if (outFile.exists()) {
		//	outFile.delete();
			System.out.println(" all.txt ���ļ�����");
		} else {
			
			try {
				outFile.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Writer ow = null;
		BufferedWriter bw = null; 

		try {		
			 ow = new FileWriter(outFile);
			 bw = new BufferedWriter(ow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			String fileName =(String)fs.getName().substring(0,2);
		
						
			if (fs.isDirectory()) {
				// System.out.println(fs.getName() + " [Ŀ¼]");
			} else if ( !fileName.equals("sc")  &&fs.canRead() && fs.getTotalSpace()>500   ) {	
				gpdmCount++;					 
				
				System.out.println("OCRʶ���ļ�: " + (String)fs.getName());
				
				BufferedImage image;
				BufferedImage image1;
				String OcrResult="";
				try {
					image = ImageIO.read(fs);
					
					for (int j=0;j <imagePosition.length;j++)
					{
					 image1 = image.getSubimage( Integer.valueOf( imagePosition[j]).intValue(), 
							Integer.valueOf("974").intValue(), 
							Integer.valueOf( "43").intValue(), 
							Integer.valueOf( "15").intValue());
					String s = new OCR().recognizeCharacters(image1);
					String ss = s.replace("l", "1").replace("\r\n", "").replace("O", "0").replace("T", "7").replace(" ", "");
					System.out.println("\n���е� "+j+" ��ʶ�� ���=" + ss);
					
					if ( !ss.isEmpty() && isNumeric(ss) && Integer.valueOf(ss).intValue()>=200) 
						OcrResult=OcrResult+ss+",";
					}
					
					for (int k=0;k <19;k++)
					{
					 image1 = image.getSubimage( Integer.valueOf( imagePosition[k]).intValue(), 
							Integer.valueOf("995").intValue(), 
							Integer.valueOf( "43").intValue(), 
							Integer.valueOf( "15").intValue());
					String s = new OCR().recognizeCharacters(image1);
					String ss = s.replace("l", "1").replace("\r\n", "").replace("O", "0").replace("T", "7").replace(" ", "");
					System.out.println("\n���е� "+k+" ��ʶ�� ���=" + ss);
					if (   !ss.isEmpty() && isNumeric(ss) &&   Integer.valueOf(ss).intValue()>=200) 
						OcrResult=OcrResult+ss+",";
					}
					
				} catch (IOException e) {					
					e.printStackTrace();
				}  
				String gpdm =(String)fs.getName().substring(0,6);
				String update_date =(String)fs.getName().substring(7,15);
				String time =(String)fs.getName().substring(16,20);
				OcrResult=gpdm+"," +update_date+ time+","+  OcrResult;
				if (OcrResult.length()>=36) {
				System.out.println("\n" +  OcrResult );
				  try {
					bw.append(OcrResult);
					bw.newLine();
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			}
		}
		System.out.print("����ɨ���ļ�����="+gpdmCount);
		        try {
					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}	
				
	}
	
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
		//   System.out.println(str.charAt(i));
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		}
		return true;
		}
	
	/*
	 * ʶ��gpdm��Ȼ��������������������Ӧ��Ŀ¼
	 * 
	 * */
	public static void getFileName(String[] args ) {
		String path = args[0] ; // ·�� "D:/20170928"
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return;
		}		

		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			String fileName =(String)fs.getName().substring(0,2);
		
						
			if (fs.isDirectory()) {
				//System.out.println(fs.getName() + " [Ŀ¼]");
			} else if ( fileName.equals("sc")   ) {			
				 int j=fs.getName().indexOf("_");
				 String reName = (String)fs.getName().substring( j+9 ); 				 
				
				System.out.println("����OCRʶ��: " + fs.getAbsolutePath());
				
				BufferedImage image;
				try {
					image = ImageIO.read(fs);
					BufferedImage image1 = image.getSubimage( Integer.valueOf( args[1]).intValue(), 
							Integer.valueOf( args[2]).intValue(), 
							Integer.valueOf( args[3]).intValue(), 
							Integer.valueOf( args[4]).intValue());
					String s = new OCR().recognizeCharacters(image1);
					String ss = s.replace("\r\n", "").replace("O", "0").replace("T", "7");
					String reNameFile = ss+"_"+ args[6]+reName;
					System.out.println("\n���е� "+i+" ��ʶ�� ���=" + ss+"\n ��������Ϊ"+reNameFile +" \n");
					File newfile=null;
					if (ss.equals("*") ) {
					 newfile=new File(args[5]+"/sc/"+ fs.getName());
						
					}else{
					 newfile=new File(args[5]+"/"+ reNameFile);
					
					}
					fs.renameTo(newfile);
					
				} catch (IOException e) {					
					e.printStackTrace();
				}  
				

			}
		}
	
		
	}

}
