package login;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
//import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class LoginMethod extends WindowAdapter
{
	JTabbedPane jtp=new JTabbedPane();
	ResultSet res;
	Second dbc = new Second();
	JFrame frame;
	public LoginMethod()
	{
		frame = new JFrame("^_^Welcome to use^_^");
		jtp.add("Main Interface",new Flow());
		jtp.add("Add new User",new AddAccount());
		jtp.add("Delete User",new DeleteAcc());
		jtp.add("Correct User",new CorrectAcc());
		jtp.add("Query User",new QueryAcc());
		frame.add(jtp);
		frame.setVisible(true);
		frame.setBounds(800,0,600,500);
		frame.setIconImage(new ImageIcon("E:/images/penguin.png").getImage());
		//关闭窗口事件（适配器）
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
//				//从11开始重置(强制)
//				res = dbc.executeQuery("select * from stuacc order by id");
//				try {
//					int num = 0;
//					if (res.next())
//					{
//						num++;
//						dbc.executeUpdate("update stuacc set id="+num+" where account='"+res.getString(1)+"'");
//					}
//					else{res.close();}
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) 
	{
		new LoginMethod();
	}

}
