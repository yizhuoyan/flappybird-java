package com.yizhuoyan.bird;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Column {
	//ͼƬ����
	private BufferedImage column;
	//�������������ӵ��м���Ϊԭ�㶨λ
	private int x;
	private int y;
	private int width;
	private int height;
	
	//step3.4
	private Random random;
	
	//step4.4��������Ŀռ�߶�
	private int gap = 109;
	
	public int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}

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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @param x ȷ������ֵĺ�����
	 * @throws IOException
	 */
	public Column(int x) throws IOException{
		column = ImageIO.read(getClass().getResource("column.png"));
		width = column.getWidth();
		height = column.getHeight();
		random = new Random();
		this.x = x;
		
		//step3.4
		//y = 240-350;//��Ϸ�߶ȵ�һ�������ͼƬ���ȵ�һ�룬�����������м�Ŀճ��������м�
		y = 140 + random.nextInt(140);
	}
	
	/**
	 * step3.1:���������ƶ�
	 */
	public void step(){
		x--;
		if(x <= -width){//-58,����ͼƬ�Ŀ��
			x = 320;//step3.3
			//step3.4
			y = 140 + random.nextInt(140);
		}
	}
	
	/**
	 * ��ͼ�����������ӳ����ڽ�����
	 * @param g
	 */
	public void paint(Graphics g){
		//step3.4
		g.drawImage(column, x-width/2, y-height/2, null);
	}
}
