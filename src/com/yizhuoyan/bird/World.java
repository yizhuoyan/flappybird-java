package com.yizhuoyan.bird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 * ��ʼ���磬��������ͼƬ����ʼͼƬ����Ϸ����ͼƬ
 * @author ӳ
 *
 */
public class World extends JPanel{
	private BufferedImage background;
	private BufferedImage startImg;
	
	//step2:������һ��ground
	private Ground ground;
	
	//step3:������������
	private Column column1;
	private Column column2;
	
	//step3:����start��־λ
	private boolean start;
	
	//step4:����bird
	private Bird bird;
	
	//step4.4:�����Ϸ������־λ
	private boolean gameover;
	private BufferedImage end;
	
	//step5:��ӵ÷ּ�¼
	int score;
	
	/**
	 * ���췽��
	 * @throws IOException 
	 */
	public World() throws IOException{
		//��������ͼƬ
		background = ImageIO.read(this.getClass().getResource("bg.png"));
		startImg = ImageIO.read(getClass().getResource("start.png"));
		//step2:��ʼ��ground
		init();
	}
	
	/**
	 * ��ʼ����Ա����
	 * @throws IOException 
	 */
	private void init() throws IOException{
		//step2:��ʼ��ground
		ground = new Ground(400);//���ù�����ͼƬ�ĳ�ʼ������Ϊ400
		//step3:��ʼ��column1��column2
		column1 = new Column(320 + 100);//step3.3
		column2 = new Column(320 + 100 + 180);//step3.3
		
		//step4:��ʼ��bird
		bird = new Bird(140,225);
		
		//step4.4
		start = false;
		gameover = false;
		
		//step5
		score = 0;
	} 
	
	/**
	 * ��ʼ����
	 * @throws InterruptedException 
	 */
	public void action() throws InterruptedException{
		
		//step3:��Ӽ����¼�
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				
				//step4.4:�����Ϸ���������³�ʼ��
				if(gameover){
					try {
						init();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return;
				}
				start = true;
				
				//step4.2:ÿ���һ����꣬С��ǰ�ٶ�vt���±�Ϊv0 20
				bird.flappy();
			}
			
		});
		
		//step2:ѭ���ػ�ͼ��
		while(true){
			//step3.1��start=true���ƶ�����
			if(start){
				column1.step();
				column2.step();
				//step4.1С�����ͼƬ
				bird.step();
				
				//step4.4:�����ײ���ı��־λ״̬
				if(bird.hit(column1,column2,ground)){
					AudioPlayWave audioPlayWave = new AudioPlayWave("de.wav");
					audioPlayWave.start();
					start = false;
					gameover = true;
				}
				
				//step5:�жϵ÷�
				if(bird.pass(column1, column2)){
					score++;
				}
			}
			ground.step();//ground�ĺ�����--
			repaint();
			Thread.sleep(1000/60);//ÿ��1/60���ػ�һ��
		}
	}
	
	/**
	 * ��д����paint�������ػ�ͼ��
	 */
	@Override
	public void paint(Graphics g) {
		//���Ʊ���ͼƬ
		g.drawImage(background, 0, 0, null);
		//���ƹ�����
		ground.paint(g);
		
		//step3:��������,ֻ��start=false������²Ż��ƿ�ʼ����
		column1.paint(g);
		column2.paint(g);
		
		//step4:����С��
		bird.paint(g);
		
		//step5:���Ʒ���
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 30);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("�÷֣�" + score , 30, 50);
		
		//step4.4:������������ƽ�������
		if(gameover){
			try {
				end = ImageIO.read(getClass().getResource("gameover.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(end,0,0,null);
			return;
		}
		
		if (!start) {
			//���ƿ�ʼͼƬ
			g.drawImage(startImg, 0, 0, null);
		}
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		JFrame frame = new JFrame("FlappyBird");
		World world = new World();
		frame.add(world);
		frame.setSize(320, 480);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);//����λ��
		frame.setVisible(true);
		world.action();
		
	}
}
