package login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class Flow extends JPanel implements FocusListener,ItemListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String acc,pass,temp1,index;
	int id=0;
	int clicknum=0;
	int closePane=0;
	Second dbc=new Second();
	ResultSet res;
	ResultSetMetaData rsmd;
	JPanel npanel,mpanel,spanel;
	JButton button,button1,exit,trip,setDefault,clear,handset,handdel;
	JLabel la1,la2,la3,la4,la5,la6,la7,la8,la9;//la7——specialized for account,la8——specialized for warning
	JComboBox<String> user;
	JTextField t4;
	JPasswordField pw;
	URL url;
	JLabel l1,l2,l3,l4,l5,l6,l7;
	Random random=new Random();
	List list=new ArrayList();
	List addList=new ArrayList();
	int[] string=new int[4];
	//键值对(账号——密码)
	Map<String,String> map;
	ScheduledExecutorService ser4= Executors.newScheduledThreadPool(2);//流量监控
	//初始化数组
	String name[]=new String[10];
		//初始化字符串
	String nameset=" ";
	int temp2=0;
	
	public Flow()
	{	
		UIManager.put("RootPane.setupButtonVisible",false); //不显示设置按钮
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		npanel= new JPanel()
		{  
			private static final long serialVersionUID = 1L;  
			protected void paintComponent(Graphics g) {  
				Graphics2D g2 = (Graphics2D) g;  
				super.paintComponent(g);  
				// 绘制渐变  
				g2.setPaint(new GradientPaint(0, 0, new Color(255, 149, 100), getWidth(),  getHeight(), new Color(255, 212, 100)));  
				g2.fillRect(0, 0, getWidth(), getHeight());  
			}  
		}; 
		spanel= new JPanel()
		{  
			private static final long serialVersionUID = 1L;  

			protected void paintComponent(Graphics g) {  
				Graphics2D g2 = (Graphics2D) g;  
				super.paintComponent(g);  
				// 绘制渐变  
				g2.setPaint(new GradientPaint(0, 0, new Color(255, 149, 100), getWidth(),  getHeight(), new Color(255, 212, 100)));  
				g2.fillRect(0, 0, getWidth(), getHeight());  
			}  
		}; 
		mpanel= new JPanel()
		{  
			private static final long serialVersionUID = 1L;  

			protected void paintComponent(Graphics g) {  
				Graphics2D g2 = (Graphics2D) g;  
				super.paintComponent(g);  
				// 绘制渐变  
				
				g2.setPaint(new GradientPaint(0, 0, new Color(255, 149, 100), getWidth(),  getHeight(), new Color(255, 212, 100)));  
				g2.fillRect(0, 0, getWidth(), getHeight());  
			}  
		}; 
		button=new JButton("Summit");
		button.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		button1=new JButton("Logout");
		button1.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		trip=new JButton("Switch");
		trip.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue));
		exit=new JButton("Exit");
		exit.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		setDefault=new JButton("设置当前为默认账号");
		setDefault.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		clear=new JButton("清空输入框");
		clear.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		handset=new JButton("自由设置默认登陆账号");
		handset.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
		handdel=new JButton("删除某项输入");
		handdel.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));		
		la1=new JLabel("Username : ",JLabel.CENTER);
		la2=new JLabel("Password : ",JLabel.CENTER);
		la3=new JLabel("设置默认登陆账号，以便下次自动登陆轻松上网",JLabel.CENTER);
		la3.setFont(new Font("宋体",Font.BOLD,14));
		la4= new JLabel("",JLabel.CENTER);la4.setFont(new Font("宋体",Font.BOLD,20));
		la5= new JLabel("",JLabel.CENTER);la5.setFont(new Font("宋体",Font.BOLD,14));
		la6= new JLabel("",JLabel.CENTER);la6.setFont(new Font("宋体",Font.BOLD,19));
		la7= new JLabel("",JLabel.CENTER);la7.setFont(new Font("宋体",Font.BOLD,25));
		la8= new JLabel("",JLabel.LEFT);la8.setFont(new Font("宋体",Font.BOLD,15));
		la9= new JLabel("",JLabel.CENTER);la8.setFont(new Font("宋体",Font.BOLD,20));
		
		//账号密码对应
		map = new HashMap<String,String>();
		user=new JComboBox<String>();
		user.setEditable(true);
		user.addFocusListener(this);
		user.addItemListener(this);
		pw=new JPasswordField(10);
		pw.addFocusListener(this);
		//获取输入历史
		res=dbc.executeQuery("select * from lazer");
		try {
			while (res.next())
			{
				user.addItem(res.getString(1));
				map.put(res.getString(1),res.getString(2));
			}	pw.setText(map.get(user.getItemAt(0)));
		} catch (SQLException e) {e.printStackTrace();}
		l5=new JLabel("",JLabel.CENTER);
		l5.setBackground(Color.cyan);
		l6=new JLabel("",JLabel.CENTER);
		l7=new JLabel("",JLabel.CENTER);
		t4=new JTextField(10);
		//设置默认的流量额度
		t4.setText("800");
		
		l1=new JLabel("Username:",JLabel.RIGHT);
		l2=new JLabel("  Used(M)   :",JLabel.RIGHT);
		l3=new JLabel("  Total(M)  :",JLabel.RIGHT);
		l4=new JLabel("设置额定流量(M) :",JLabel.RIGHT);
		init();
	}
	public void init()
	{
//		UIManager.put("RootPane.setupButtonVisible",false); //不显示设置按钮
//		BeautyEyeLNFHelper.translucencyAtFrameInactive = true;
//		BeautyEyeLNFHelper.frameBorderStyle = FrameBorderStyle.translucencyAppleLike;
//		try {
//			BeautyEyeLNFHelper.launchBeautyEyeLNF();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		UIManager.put("TabbedPane.tabAreaInsets"
//			    , new javax.swing.plaf.InsetsUIResource(0, 0, 0, 0)); //tab 的左缩进
		npanel.add(la1);npanel.add(user);npanel.add(l1);npanel.add(l5);
		npanel.add(la2);npanel.add(pw);npanel.add(l2);npanel.add(l6);
		npanel.add(button);npanel.add(button1);npanel.add(l3);
		npanel.add(l7);npanel.add(exit);npanel.add(trip);npanel.add(l4);
		npanel.add(t4);
		npanel.setLayout(new GridLayout(4,4));
		mpanel.add(la4);mpanel.add(la5);mpanel.add(la9);
		mpanel.setLayout(new GridLayout(3,1));
		//spanel.setLayout(mgr);
		la3.setPreferredSize(new Dimension(500, 30));
		//la8单独一行显示
		la8.setPreferredSize(new Dimension(550, 20));
		//设置la8颜色为红色
		la8.setForeground(Color.RED);
		//la8单独一行显示
		//设置la9颜色为红色
		la9.setForeground(Color.RED);
		//自定义组件大小
		//spanel.setLayout(new FlowLayout());
		setDefault.setPreferredSize(new Dimension(155,30));
		handdel.setPreferredSize(new Dimension(120,30));
		clear.setPreferredSize(new Dimension(100,30));
		handset.setPreferredSize(new Dimension(165,30));
		spanel.add(la8);spanel.add(la3);spanel.add(setDefault);spanel.add(handset);spanel.add(clear);spanel.add(handdel);spanel.add(la6);spanel.add(la7);
		
		add(mpanel,BorderLayout.NORTH);add(npanel,BorderLayout.CENTER);add(spanel,BorderLayout.SOUTH);
		
		setLayout(new GridLayout(3,1));
		setVisible(true);
		setBounds(750,0,500,500);
		setBackground(Color.cyan);
		
		button.addActionListener(new OuterClass(this));
		button1.addActionListener(new OuterClass(this));
		exit.addActionListener(new OuterClass(this));
		trip.addActionListener(new OuterClass(this));
		setDefault.addActionListener(new OuterClass(this));
		clear.addActionListener(new OuterClass(this));
		handset.addActionListener(new OuterClass(this));
		handdel.addActionListener(new OuterClass(this));
		//设置有默认登陆账号后，下次会自动登陆
		new FirstLogin(this);
		//运行程序后同时启动定时器
		ser4.scheduleAtFixedRate(new Update(this),0,1000,TimeUnit.MILLISECONDS);//动态显示
	}
	
	//实现Tab切换文本框
	public void focusGained(FocusEvent e) {}
	public void focusLost(FocusEvent e) {}
	
	//选择账号并显示密码
	public void itemStateChanged(ItemEvent e) 
	{
		if (e.getStateChange()==ItemEvent.SELECTED)
		{
			String acc = (String) user.getSelectedItem();
			pw.setText(map.get(acc));
		}
	}
}