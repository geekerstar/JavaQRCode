package com.geekerstar.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;

public class ReadQRCode {
	public static void main(String[] args) throws Exception  {
		File file=new File("E:/qrcode.png");
		BufferedImage bufferedImage=ImageIO.read(file);
		QRCodeDecoder coderDecoder=new QRCodeDecoder();
		
		String result=new String (coderDecoder.decode(new MyQRCodeImage(bufferedImage)),"utf-8");
		System.out.println(result);
	}
}


