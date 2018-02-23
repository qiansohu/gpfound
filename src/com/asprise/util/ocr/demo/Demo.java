/*
 * 废弃不用 eagle 20180223
 * 
 */
package com.asprise.util.ocr.demo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.ImageIO;

import com.asprise.util.ocr.OCR;

public class Demo {

	public static void main(String[] args) throws IOException {
//		if (("1.4").compareTo(System.getProperty("java.vm.version")) > 0) {
//			System.err.println("Warining: \n\nYou need Java version 1.4 or above for ImageIO to run this demo.");
//			System.err.println("Your current Java version is: " + System.getProperty("java.vm.version"));
//			System.err.println("\nSolutions: \n");
//			System.err.println("(1) Download JRE/JDK version 1.4 or above; OR \n");
//			System.err.println("(2) Run DemoUI, which can run on your current Java virtual machine.");
//			System.err.println("    Double click the 'runDemoUI' to invoke it.\n");
//			return;
//		}
//
//		System.out.println("Welcome to Asprise OCR v4.0 Demo!\n");
//		if (args.length < 1) {
//			System.err.println("Usage: java Demo PATH_TO_IMAGE [Description]");
//			return;
//		}
//
		if (args.length <= 4) {
			System.out.println("------------------参数不够-----------------------------------------");
			for (int i = 1; i < args.length; i++)
				System.out.println(args[i]+"\r\n");
			System.out.println("---------------- -------------------------------------------\n");
		 } else {			 
			 getFileName( args);
		 }
//
//		File file = new File(args[0]);
//
//		System.out.println("Trying to perform OCR on image: " + file.getAbsolutePath());
//
//		// OCR.setLibraryPath("E:/Twain/OCR/OCR+i/Release/AspriseOCR.dll");
//
//		BufferedImage image = ImageIO.read(file);
//
//		String s = new OCR().recognizeEverything(image);
//		System.out.println("\n---- RESULTS: ------- \n" + s);		
		
	}
	
	public static void contentToTxt(String filePath, String content) {  
        String str = new String(); //原有txt内容  
        String s1 = new String();//内容更新  
        try {  
            File f = new File(filePath);  
            if (f.exists()) {  
                System.out.print("文件存在");  
            } else {  
                System.out.print("文件不存在");  
                f.createNewFile();// 不存在则创建  
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
  
            while ((str = input.readLine()) != null) {  
                s1 += str + "\n";  
            }          
            input.close();  
            s1 += content;  
  
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    } 

	public static void getFileName(String[] args ) {
		String path = args[0] ; // 路径 "D:/20170928"
		File f = new File(path);
		if (!f.exists()) {
			System.out.println(path + " not exists");
			return;
		}
		HashMap gpdm = new HashMap();

		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			String fileName =(String)fs.getName().substring(0,2);
		
						
			if (fs.isDirectory()) {
				System.out.println(fs.getName() + " [目录]");
			} else if ( fileName.equals("sc")   ) {
				//File file = new File(args[0]);
				 int j=fs.getName().indexOf("_");
				 String reName = (String)fs.getName().substring( j+1 ); 
				 
				
				System.out.println("Trying to perform OCR on image: " + fs.getAbsolutePath());
				
				BufferedImage image;
				try {
					image = ImageIO.read(fs);
					BufferedImage image1 = image.getSubimage( Integer.valueOf( args[1]).intValue(), 
							Integer.valueOf( args[2]).intValue(), 
							Integer.valueOf( args[3]).intValue(), 
							Integer.valueOf( args[4]).intValue());
					String s = new OCR().recognizeCharacters(image1);
					String ss = s.replace("\r\n", "").replace("O", "0").replace("T", "7");
					String reNameFile = ss+"_"+reName;
					System.out.println("\n row"+i+" RESULTS:=" + s+"\n"+reNameFile +" \n");
					File newfile=new File(args[0]+"/"+reNameFile);
					fs.renameTo(newfile);
					gpdm.put(ss, "");
				} catch (IOException e) {
					
					e.printStackTrace();
				}

				
				//System.out.println("\n---- RESULTS: ------- \n" + s);
				
			 //	System.out.println(fs.getName());
			}
		}
		
		Collection cl = gpdm.keySet();
	      Iterator itr = cl.iterator();
	      String gpdmList= new String();
	      while (itr.hasNext()) {
	    	  gpdmList=gpdmList+"\r\n"+itr.next();  
	     }
	      contentToTxt(  path+"/gpdm.txt"  , gpdmList );
		
		
	}

}
