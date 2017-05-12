package com.yizhuoyan.bird;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird {
	private int x;
	private int y;
	private BufferedImage bird;
	
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

	//step4.1
	private BufferedImage[] birds;//��Ŷ���С��ͼƬ
	private int index;//ͼƬ��ż���
	
	//step4.2
	private int g;//�������ٶ�
	private double t;//������ʱ�䣨�룩
	private double v0;//��ʼ�ٶȣ�����/�룩
	private double vt;//��ǰʱ���ٶ�
	private double s;//�˶�����
	
	//step4.3
	private double angle;//��ķ��нǶ�
	
	//step4.4
	private int size = 26;
	
	public Bird(int x , int y) throws IOException{
		this.x = x;
		this.y = y;
		birds = new BufferedImage[3];
		for(int i = 0;i < 3; i++){
			birds[i] = ImageIO.read(getClass().getResource(i + ".png"));
		}
		bird = birds[0];
		
		//step4.2
		this.g = 4; //�������ٶ�
		this.t = 0.25; //ÿ�μ���ļ��ʱ��
		this.v0 = 20; //��ʼ�����ٶ�
		BufferedImage img=null;
		
	}
	
	/**
	 * ����С��
	 * @param g
	 */
	public synchronized void paint(Graphics g){
		//g.drawImage(bird, x, y, null);
		
		//step4.3:��ת����ϵ
		Graphics2D g2d = (Graphics2D)g;
		g2d.rotate(angle, this.x, this.y);
		//��x,y Ϊ���Ļ���ͼƬ
		int x = this.x-bird.getWidth()/2;
		int y = this.y-bird.getHeight()/2;
		g.drawImage(bird, x, y, null);
		//��ת���� 
		g2d.rotate(-angle, this.x, this.y);
		
	}
	
	/**
	 * step4.1:�ı�С����̬
	 * 
	 * step4.2
	 * ��ֱ����λ�Ƽ���
	 * (1) �����ٶȼ��� Vt=Vo-gt  
	 * (2) ���׾������ S=Vot-1/2gt^2
	 */
	public void step(){
		//step4.2
		//Vt1 �Ǳ���
		double vt1 = vt;
		//���㴹ֱ�����˶�, ����ʱ��t�Ժ���ٶ�, 
		double v = vt1 - g*t;
		//��Ϊ�´μ���ĳ�ʼ�ٶ�
		vt = v;
		//���㴹ֱ�����˶������о���
		s = vt1*t - 0.5 * g * t * t;
		//������ľ��� ����Ϊ y����ı仯
		y = y - (int)s;
		
		//step4.3
		//��������
		angle = -Math.atan(s/15);//�������ƶ��ٶ�60����/�룬t=0.25�룬60*0.25=15
		
		//����С��ͼƬ
		index ++;
		bird = birds[index/8%3];//00000000,11111111,22222222,00000000...
		
		
	}
	
	/**
	 * step4.2
	 */
	public void flappy(){
		AudioPlayWave audioPlayWave = new AudioPlayWave("fei.wav");
		audioPlayWave.start();
		//ÿ��С��������Ծ, ���ǽ�С���ڵ�ǰ�������Գ�ʼ�ٶ� V0 ������
		System.out.println();
		vt = v0;
	}
	
	//step4.4��ײ���
	/** �ж����Ƿ������Ӻ͵ط�����ײ */
	public boolean hit(Column column1, Column column2, Ground ground) {
		//��������
		if(y-size/2 >= ground.getY() || y-size/2 <= 0){
			return true;
		}
		//��������
		return hit(column1) || hit(column2);
	}
	/** ��鵱ǰ���Ƿ��������� */
	public boolean hit(Column col){
		//�������������: ������ĵ�x������ ���ӿ�� + ���һ��
		if( x >col.getX()-col.getWidth()/2-size/2 && x<col.getX()+col.getWidth()/2+size/2){
			if(y>col.getY()-col.getGap()/2+size/2  && y<col.getY()+col.getGap()/2-size/2 ){
			
				return false;
				
			}
			return true;
		}
		return false;
	}
	
	//step5
	/** �ж����Ƿ�ͨ������ */
	public boolean pass(Column col1, Column col2) {
	
		return col1.getX()==x || col2.getX()==x;
		
	}
}
