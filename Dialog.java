package login;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dialog extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton di1,di2;
	JPanel panel;
	JLabel label = new JLabel("Are you sure you want to delete input records? The operation can't be revoked!!!");
	Second dbc = new Second();
	Flow flow;
	public Dialog(Flow flow)
	{
		this.flow = flow;
		di1 =new JButton("Ok");
		di2 =new JButton("Cancal");
		di1.addActionListener(this);
		di2.addActionListener(this);
		panel = new JPanel();
		panel.add(di1);
		panel.add(di2);
		add(label,BorderLayout.NORTH);
		add(panel);
		setBounds(500,200,550,150);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==di1)
		{
			dbc.executeUpdate("delete from lazer");
			flow.user.removeAllItems();
			flow.pw.setText("");
			setVisible(false);
		}
		
		else if (e.getSource()==di2)
		{
			setVisible(false);
		}
	}
}
