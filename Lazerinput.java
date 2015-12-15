package login;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Lazerinput
{
	Second dbc= new Second();
	ResultSet res;
	public Lazerinput(String user, String pass)
	{
		res=dbc.executeQuery("select * from lazer where account='"+user+"'");
		try {
			if (!res.next())
			{
				dbc.executeUpdate("insert into lazer values('"+user+"','"+pass+"')");
			}
			else 
			{
				//同时更新lazer表和stuacc表的数据
				dbc.executeUpdate("update lazer set password='"+pass+"' where account='"+user+"'");
				dbc.executeUpdate("update stuacc set password='"+pass+"' where account='"+user+"'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
