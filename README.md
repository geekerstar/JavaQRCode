# JavaQRCode
Java使用ZXing、QRCode及JQuery-qrcode生成二维码教程


## 简介
二维码在现实生活中无处不在，应用越来越广泛。那么大家想不想知道如何生成二维码，以及如何去解析二维码呢？本文将为大家介绍Java中三种二维码的实现方式，分为使用ZXing、QRCode以及jquery-qrcode。


> 本项目GitHub地址,如果您觉得不错，欢迎给个star：https://github.com/geekerstar/JavaQRCode

### 需要了解的小知识
二维码的分类：线性堆叠式二维码、矩阵式二维码、邮政码。

目前流行的三大国际标准
PDF417：不支持中文
DM：专利未公开，需支付专利费用
QR code：专利公开，支持中文


QR code比其他二维码相比，具有识读速度快，数据密度大，占用空间小的优势。
QR Code是有日本Demso公司于1994年研制的一种矩阵二维码符号码

纠错能力：
* L级：约可纠错7%的数据码字
* M级：约可纠错15%的数据码字
* Q级：约可纠错25%的数据码字
* H级：约可纠错30%的数据码字

纠错能力越高，存储的数据就越少

## 使用ZXing生成二维码
在eclipse里创建一个java project，src下建一个包，创建文件`CreateQRCode.java`，然后导入下载的jar包，导入`Zxing3.2.1.jar`，编写如下代码：
```java
package com.geekerstar.zxing;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class CreateQRCode {
	public static void main(String[] args) {
		int width=300; 											//设置二维码的宽
		int height=300;											//设置二维码的长
		String format="png";									//设置二维码图片的格式
		String contents="www.geekerstar.com";			//设置的连接地址

		//二维码参数
		HashMap hints=new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET,"utf-8");		//设置支持中文编码
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);		//纠错等级。
		hints.put(EncodeHintType.MARGIN, 2);					//边框
		try{
			BitMatrix bitmatrix=new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height);
			File files=new File("E:/zxing.png");						//文件
			Path file=files.toPath();								//文件路径
			MatrixToImageWriter.writeToPath(bitmatrix, format, file);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

```

代码很简单，应该能看懂，运行后将在E盘根目录下生成一个QRCode.png的二维码图片，如下图所示：

![二维码图](http://www.geekerstar.com/usr/uploads/2018/04/3665328909.png)


拿手机扫一下将访问设置的地址……

## 使用ZXing解析二维码

同样创建一个文件，命名为`ReadQRCode.java`，并编写如下代码：

```java
package com.geekerstar.zxing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ReadQRCode {
	public static void main(String[] args) {

		try{
			MultiFormatReader formatReader =new MultiFormatReader();
			File file=new File("E:/zxing.png");
			BufferedImage image=ImageIO.read(file);
			BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));

			HashMap hints=new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET,"utf-8");		//设置支持中文编码
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);		//纠错等级。
			hints.put(EncodeHintType.MARGIN, 2);
			Result result=formatReader.decode(binaryBitmap,hints);

			System.out.println("解析结果"+result.toString());
			System.out.println("二维码格式"+result.getBarcodeFormat());
			System.out.println("二维码格式"+result.getText());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}






```

执行后就可以在控制台里看到输出结果

![解析二维码](http://www.geekerstar.com/usr/uploads/2018/04/3838462923.jpg)

## 使用QRcode方式生成二维码

新建一个package，创建文件`CreateQRCode.java`，编写如下代码：

```java
package com.geekerstar.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class CreateQRCode {
		public static void main(String[] args) throws Exception {
			Qrcode x=new Qrcode();
			x.setQrcodeErrorCorrect('M');//纠错等级
			x.setQrcodeEncodeMode('B'); //N代表数字；A代表a—Z；B代表其他字符
			x.setQrcodeVersion(7);		//版本号（1-40）
			String qrData="www.geekerstar.com";
			int width= 67 + 12 * (7-1);
			int height= 67 + 12 * (7-1);
			
			
			
			//依托javaGUI的画图工具实现的
			BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			Graphics2D gs=bufferedImage.createGraphics();
			
			gs.setBackground(Color.WHITE);
			gs.setColor(Color.BLACK);
			gs.clearRect(0, 0, width, height);
			
			int pixoff=2;//偏移量
			
			byte[] d=qrData.getBytes("gb2312");
			if(d.length>0 && d.length<120){
				boolean[][] s=x.calQrcode(d);
				for(int i=0;i<s.length;i++){
					for(int j=0;j<s.length;j++){
						if(s[i][j]){
							gs.fillRect(i*3+pixoff,j*3+pixoff,3,3);
						}
					}
				}
			}
			
			gs.dispose();
			bufferedImage.flush();
			ImageIO.write(bufferedImage, "png", new File("E:/qrcode.png"));
		}
}

```

同样运行即可生成二维码图片。

## 使用QRcode解析二维码

创建文件`ReadQRCode.java`，编写如下代码：
```java
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
```

创建一个类实现QRCodeImage接口，命名为`MyQRCodeImage.java`，编写代码如下：

```java
package com.geekerstar.qrcode;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

public class MyQRCodeImage implements  QRCodeImage {
	BufferedImage bufferedImage;
	
	public MyQRCodeImage(BufferedImage bufferedImage){
		this.bufferedImage=bufferedImage;
	}
	
	
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return bufferedImage.getHeight();
	}

	@Override
	public int getPixel(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return bufferedImage.getRGB(arg0,arg1);
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return bufferedImage.getWidth();
	}

}

```

运行即可在控制台打印出二维码所表示的网址。

## 使用JQuery-qrcode生成二维码

GitHub开源地址：https://github.com/jeromeetienne/jquery-qrcode


------------
[scode type="green"]如果您发现了文章有任何错误欢迎指正，有任何意见或建议，或者有疑问需要我提供帮助，也欢迎在下面留言，只需输入`昵称`+`邮箱`即可，`网站或博客`可选填。对于所有留言内容我会及时回复，非常期待与大家的交流！[/scode]


> 版权声明：本文（除特殊标注外）为原创文章，版权归 [Geekerstar](http://www.geekerstar.com) 所有。

> 本文链接：http://www.geekerstar.com/backend/624.html

> 除了有特殊标注文章外欢迎转载，但请务必标明出处，格式如上，谢谢合作。

> 本作品采用 [知识共享署名-非商业性使用-相同方式共享 3.0 中国大陆许可协议](https://creativecommons.org/licenses/by-nc-sa/3.0/cn/) 进行许可。







