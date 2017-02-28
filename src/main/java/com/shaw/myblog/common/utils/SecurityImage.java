package com.shaw.myblog.common.utils;


import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 工具类，生成验证码图片
 * @version 1.0 2012/08/21
 * @author dongliyang
 */
@SuppressWarnings("restriction")
public class SecurityImage {
    
    /**
     * 生成验证码图片
     * @param securityCode   验证码字符
     * @return  BufferedImage  图片
     */
    public static BufferedImage createImage(String securityCode){
        
        //验证码长度
        int codeLength=securityCode.length();
        //字体大小
        int fSize = 24;
        int fWidth = fSize + 1;
        //图片宽度
        int width = codeLength * fWidth + 6 ;
        //图片高度
        int height = fSize * 2 + 1;
        
        //图片
        BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g=image.createGraphics();
        
        //设置背景色
        g.setColor(Color.WHITE);
        //填充背景
        g.fillRect(0, 0, width, height);
        
        //设置边框颜色
        g.setColor(Color.LIGHT_GRAY);
        //边框字体样式
        g.setFont(new Font("Arial", Font.BOLD, height - 2));
        //绘制边框
        g.drawRect(0, 0, width - 1, height -1);
        
        //绘制噪点
        Random rand = new Random();
        //设置噪点颜色
        g.setColor(Color.LIGHT_GRAY);
        for(int i = 0;i < codeLength * 6;i++){
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            //绘制1*1大小的矩形
            g.drawRect(x, y, 1, 1);
        }
        
        //设置干扰线
        for (int i = 0; i < 100; i++) {
            int x = rand.nextInt(width - 1);
            int y = rand.nextInt(height - 1);
            int xl = rand.nextInt(6) + 1;
            int yl = rand.nextInt(12) + 1;
            g.drawLine(x, y, x + xl, y + yl);
        }
        //设置干扰线
        for (int i = 0; i < 70; i++) {
            int x = rand.nextInt(width - 1);
            int y = rand.nextInt(height - 1);
            int xl = rand.nextInt(12) + 1;
            int yl = rand.nextInt(6) + 1;
            g.drawLine(x, y, x - xl, y - yl);
        }
        
        //绘制验证码
        int codeY = height - 20;  
        //设置字体颜色和样式
        g.setFont(new Font("Times New Roman", Font.BOLD, fSize));
        for(int i = 0; i < codeLength;i++){
	        g.setColor(getRandColor(30, 150));//add 
            RotateString(String.valueOf(securityCode.charAt(i)), i * 20 + 15, codeY, g,  rand.nextInt(45)+350);
        }
        //关闭资源
        g.dispose();
        return image;
    }
    
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    
    
    
    /** 
     * 旋转并且画出指定字符串 
     * @param s 需要旋转的字符串 
     * @param x 字符串的x坐标 
     * @param y 字符串的Y坐标 
     * @param g 画笔g 
     * @param degree 旋转的角度 
     */  
    private static void RotateString(String s,int x,int y,Graphics g,int degree)  
    {  
        Graphics2D g2d = (Graphics2D) g.create();                                    
        //   平移原点到图形环境的中心  ,这个方法的作用实际上就是将字符串移动到某一个位置  
        g2d.translate(x-1, y+3);               
        //   旋转文本    
         g2d.rotate(degree* Math.PI / 180);  
         //特别需要注意的是,这里的画笔已经具有了上次指定的一个位置,所以这里指定的其实是一个相对位置  
         g2d.drawString(s,0 , 0);  
    }  
    
    
    
    /**
     * 返回验证码图片的流格式
     * @param securityCode  验证码
     * @return ByteArrayInputStream 图片流
     */
    public static ByteArrayInputStream getImageAsInputStream(String securityCode){
        
        BufferedImage image = createImage(securityCode);
        return convertImageToStream(image);
    }
    
    /**
     * 将BufferedImage转换成ByteArrayInputStream
     * @param image  图片
     * @return ByteArrayInputStream 流
     */
    private static ByteArrayInputStream convertImageToStream(BufferedImage image){
        
        ByteArrayInputStream inputStream = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(bos);
        try {
            jpeg.encode(image);
            byte[] bts = bos.toByteArray();
            inputStream = new ByteArrayInputStream(bts);
        } catch (ImageFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}