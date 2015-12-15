package login;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
//自动登陆模块
public class SetDefaultAcc implements Runnable
{
	//单独的线程
	Second dbc=new Second();
	ResultSet res,res1;
	String pass;
	boolean corr,yes;
	Flow flow;
	public SetDefaultAcc(String temp,Flow flow)
	{
		this.flow=flow;
		this.pass=temp;
	}
	public void run()
	{
		//查找yes与账号(看是否存在)
		res=dbc.executeQuery("select * from stuacc where defaultacc='yes'");
		res1=dbc.executeQuery("select * from stuacc where account='"+pass+"'");
		try {
			yes=res.next();
			corr=res1.next();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try
		{
			if (!corr){JOptionPane.showMessageDialog(null, "Sorry!!!您的添加列表中没有此账号，请先添加再来吧^_^");}
			if (yes&&corr)//yes存在切账号存在
			{
				String acc=res.getString(1);
				dbc.executeUpdate("update stuacc set defaultacc='' where account='"+acc+"'");
				dbc.executeUpdate("update stuacc set defaultacc='yes' where account='"+pass+"'");
				flow.la7.setForeground(Color.magenta);
				flow.la6.setText("当前的默认的自动登陆账号为 ： ");
				flow.la7.setText(pass);
				flow.la8.setText("");//清空label
			}
			if (corr&&!yes)//无yes关键字，但账号存在
			{
				dbc.executeUpdate("update stuacc set defaultacc='yes' where account='"+pass+"'");
				flow.la7.setForeground(Color.magenta);
				flow.la6.setText("当前的默认的自动登陆账号为 ： ");
				flow.la7.setText(pass);
				flow.la8.setText("");//清空label
			}
		}
		catch(Exception e){}
	}
}