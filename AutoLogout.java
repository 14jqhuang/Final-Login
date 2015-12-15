package login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;


	//自动切换Logout部分类
public class AutoLogout extends TimerTask
{	
	ResultSet res1;
	Second dbc = new Second();
	Flow flow;
	public AutoLogout(Flow flow)
	{
		this.flow = flow;
	}
		public void run()
		{			
					//Logout
					new Logout();
					res1=dbc.executeQuery("select * from stuacc where account='"+flow.l5.getText()+"'");
					try 
					{
						while (res1.next())
						{
							flow.list.remove(res1.getString(3));
						}
						flow.la4.setText("You hava logged out");
						flow.la5.setText("");
						flow.l5.setText("");
						flow.l6.setText("");
						flow.l7.setText("");
					}
					catch (SQLException e2) 
					{
						e2.printStackTrace();
					}
		}
}
