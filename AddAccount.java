package login;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class AddAccount extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel1,panel2;
	JLabel label1,label2,label3;
	JTextField addtext1;
	JPasswordField addtext2;
	JButton addbutton;
	int clicknumber=0;
	Second dbc=new Second();
	ResultSet res,res1,res2;
	int id=0;//自增
	
	public AddAccount()
	{
		UIManager.put("RootPane.setupButtonVisible",false); //不显示设置按钮
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel1.setPreferredSize(new Dimension(500,200));
		panel2.setPreferredSize(new Dimension(400,100));
		label1=new JLabel("账号",JLabel.CENTER);
		label2=new JLabel("密码",JLabel.CENTER);
		label3=new JLabel("亲！！！请在下面添加您要添加的账号密码",JLabel.CENTER);
		label3.setFont(new Font("宋体",Font.BOLD,25));
		addtext1=new JTextField(10);
		addtext2=new JPasswordField(10);
		addbutton=new JButton("添加");
		
		panel1.add(label3);
		panel2.setPreferredSize(new Dimension(400, 100));
		panel2.setLayout(new GridLayout(3,2));
	
		panel2.add(label1);panel2.add(label2);panel2.add(addtext1);
		panel2.add(addtext2);panel2.add(addbutton);
		
		add(panel1,BorderLayout.NORTH);
		add(panel2,BorderLayout.CENTER);
		setVisible(true);
		setBounds(200,200,400,400);
		addbutton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand()=="添加")
		{
			//首次使用，设置id为10001，确保自由选择账号
			res2 = dbc.executeQuery("select * from stuacc");
			try {
				if (res2.next()==false)
				{
					id=0;
				}
			} catch (SQLException e3) {
				e3.printStackTrace();
			}
			//输入不能为空，注意==和equals()的区别：equals比较的是对象的值，==用来普安短是否为同一对象
			if (addtext1.getText().equals("")||addtext2.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "!!!输入不能为空!!!");
			}
			else
			{
				System.out.println(addtext1.getText()+"xxx");
				res1=dbc.executeQuery("select * from stuacc where account='"+addtext1.getText()+"'");
				try 
				{
					//Check whether there exists acounnt
					if (res1.next())
					{
						JOptionPane.showMessageDialog(null, addtext1.getText()+"账号已经存在 ");
					}

					else 
					{
						res=dbc.executeQuery("select * from stuacc order by id");
						try 
						{
							while (res.next())
							{
								//遍历到最后
								id=Integer.parseInt(res.getString(3));
							}
							dbc.executeUpdate("insert into stuacc(account,password,id,defaultacc) values('"+addtext1.getText()+"',"
									+ "'"+addtext2.getText()+"',"+(id+1)+",' ')");//bug所在（''表示字符，SQL语句中""无实际意义）
							System.out.println(id+1);
							JOptionPane.showMessageDialog(null,"添加成功！","已保存",JOptionPane.OK_OPTION);
						} 
						catch (SQLException e1)
						{e1.printStackTrace();}
					}
				} 
				catch (HeadlessException e2) 
				{e2.printStackTrace();}

				catch (SQLException e2)
				{e2.printStackTrace();}

			}
			//添加后清空
			addtext1.setText("");addtext2.setText("");
		}
	}
}