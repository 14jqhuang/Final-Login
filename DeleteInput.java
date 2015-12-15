package login;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DeleteInput extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField jtf;
	JButton jb;
	JLabel label1;
	Second dbc=new Second();
	ResultSet res,res1;
	Flow flow;
	public DeleteInput(Flow flow)
	{
		this.flow = flow;
		jtf= new JTextField(10);
		jb = new JButton("Ok");
		label1 = new JLabel("账号");
		setLayout(new FlowLayout());
		add(label1);
		add(jtf);
		add(jb);
		jb.addActionListener(this);
		setVisible(true);
		setBounds(500,200,200,150);
		setOpacity(1);
	}
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==jb)
		{
			res = dbc.executeQuery("select * from lazer where account='"+jtf.getText()+"'");
			
			try 
			{
				if (!res.next())
				{
					JOptionPane.showMessageDialog(null,"输入错误，输入框没有此项("+jtf.getText()+")，请校验后再次输入");
				}
				else 
				{
					dbc.executeUpdate("delete from lazer where account='"+jtf.getText()+"'");
					JOptionPane.showMessageDialog(null, "删除成功");
					//更新输入框列表
					flow.user.removeAllItems();
					res1=dbc.executeQuery("select * from lazer");
					//清空键值对
					flow.map.clear();
					try {
						while (res1.next())
						{
							flow.user.addItem(res1.getString(1));
							flow.map.put(res1.getString(1),res1.getString(2));
						}	flow.pw.setText(flow.map.get(flow.user.getItemAt(0)));
					} catch (SQLException e1) {e1.printStackTrace();}
					setVisible(false);//隐藏
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	}
}
