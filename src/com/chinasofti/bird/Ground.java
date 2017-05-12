package com.chinasofti.bird;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * �·���������ground.png��
 * @author ӳ
 *
 */
public class Ground {
	private BufferedImage ground;
	private int x;//������
	private int y;//�����꣬��ʾ��ground.png�ĸ߶ȣ�����Ϊ400
	private int width;//ground.pngͼƬ���
	private int height;//ground.pngͼƬ�߶�
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Ground(int y) throws IOException{
		//���¹�����ͼƬ�ĳ�ʼλ��
		this.y = y;
		x = 0;
		ground = ImageIO.read(this.getClass().getResource("ground.png"));
		width = ground.getWidth();//��ÿ�� 497
		height = ground.getHeight();//��ø߶� 80	
	}
	
	public void step(){
		x--;
		//step2.1:������ͼƬһֱ���ҹ���
		if(x <= -(width - 360)){//360Ϊ����320��һ����
			x = 0;
		}
	}
	
	//���ƹ�����
	public void paint(Graphics g) {
		g.drawImage(ground, x, y, null);//0,400		
	}
	
	public static void main(String[] args) throws IOException {
		new Ground(400);
	}
}
