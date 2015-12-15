package login;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CorrectAcc extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel corr,toCorr,orilabel,oripassword;
	JTextField corrText,acc;
	JPasswordField jp;
	JButton sure1,sure2;
	JPanel npanel,spanel;
	Second dbc=new Second();
	ResultSet res,res1;
	String id="middle";//记录账号位置,初始化
	
	public CorrectAcc()
	{
		npanel=new JPanel();
		spanel=new JPanel();
		
		//实例化
		corr=new JLabel("请输入您要修改的账号 :",JLabel.CENTER);
		toCorr=new JLabel("亲^_^请在下面修改，然后点击确定即可——也可以直接输入修改^_^",JLabel.CENTER);
		orilabel=new JLabel("账号 :",JLabel.CENTER);oripassword=new JLabel("密码 :",JLabel.CENTER);
		corrText=new JTextField(10);acc=new JTextField(10);
		jp=new JPasswordField(10);
		sure1=new JButton("确定");sure2=new JButton("确定修改");
		
		//add to contailor
		npanel.add(corr);npanel.add(corrText);npanel.add(sure1);
		npanel.setPreferredSize(new Dimension(500, 25));
		toCorr.setPreferredSize(new Dimension(500, 150));
		add(npanel);
		add(toCorr);
		//spanel.setPreferredSize(new Dimension(300, 60));
		spanel.setPreferredSize(new Dimension(400, 100));
		spanel.setLayout(new GridLayout(3,2));
	
		spanel.add(orilabel);spanel.add(oripassword);spanel.add(acc);
		spanel.add(jp);spanel.add(sure2);
		//添加监听器
		sure1.addActionListener(this);sure2.addActionListener(this);
		sure2.setPreferredSize(new Dimension(50, 30));
		add(spanel);
		//add(sure2);
		
		setLayout(new FlowLayout());
	}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==sure1)
		{
			//输入不能为空，注意==和equals()的区别：equals比较的是对象的值，==用来普安短是否为同一对象
			if (corrText.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "!!!输入不能为空!!!");
			}
			else
			{
				res=dbc.executeQuery("select * from stuacc where account='"+corrText.getText()+"'");
				try {
					if (res.next())
					{
						acc.setText(res.getString(1));jp.setText(res.getString(2));
					}
					else 
					{
						JOptionPane.showMessageDialog(null,"Sorry!暂无该账号的信息，请确认好后重新输入","Wrong Usernumber",JOptionPane.ERROR_MESSAGE);
					}
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		}
		
		else if (e.getSource()==sure2)
		{
			res1=dbc.executeQuery("select * from stuacc where account='"+acc.getText()+"'");
			try {
				if (res1.next())
				{
					id = res1.getString(3);
					dbc.executeUpdate("update stuacc set account='"+acc.getText()+"',password='"+jp.getText()+"' where id="+id+"");
					JOptionPane.showMessageDialog(null,"修改成功！","已保存",JOptionPane.OK_OPTION);
					corrText.setText("");acc.setText("");jp.setText("");
				}
				else 
				{
					JOptionPane.showMessageDialog(null,"Sorry!暂无该账号的信息，请确认好后重新输入","Wrong Usernumber",JOptionPane.ERROR_MESSAGE);
				}
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
	}

	
	
}
