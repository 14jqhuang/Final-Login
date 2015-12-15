package login;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLogin 
{
	/*
	 * 判断是否有默认的登陆账号,并且字段名不能为某些特殊字符，如default等
	 */

	Second dbc = new Second();
	ResultSet res4;
	String temp1,username,password;
	Flow flow;
	public FirstLogin(Flow flow)
	{
		this.flow=flow;
		res4=dbc.executeQuery("select * from stuacc where defaultacc='yes'");
		try {
			while (res4.next())
			{
				username=res4.getString(1);
				password=res4.getString(2);
				//显示默认的登陆账号
				flow.la7.setForeground(Color.magenta);
				flow.la6.setText("当前的默认的自动登陆账号为 ： ");
				flow.la7.setText(username);
				new Login(username,password);
			}

			if (username==null)
			{
				flow.la8.setText("亲，当前无默认登陆账号，请设置!!!");
			}
			else{
				URL url =new URL("http://192.168.31.4:8080/");

				HttpURLConnection hurl = (HttpURLConnection) url.openConnection();

				BufferedReader br;
				br = new BufferedReader(new InputStreamReader(hurl.getInputStream()));
				String temp;

				StringBuffer sb= new StringBuffer();
				while ((temp=br.readLine())!=null)
				{
					sb.append(temp);
				}
				temp1 = sb.toString();
				if (temp1.contains("Password"))
				{
					flow.la8.setText(""+username+"(默认登陆账号) 已经修改密码了，请更正!!!");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
