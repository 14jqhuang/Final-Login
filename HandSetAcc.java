package login;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class HandSetAcc extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField jtf;
	JButton jb;
	JLabel label1;
	Second dbc=new Second();
	ResultSet res;
	Flow flow;
	public HandSetAcc(Flow flow)
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
		
	}
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==jb)
		{
			SetDefaultAcc sDA=new SetDefaultAcc(jtf.getText(),flow);
			Thread t = new Thread(sDA);
			t.start();
			setVisible(false);
		}
	}
}
