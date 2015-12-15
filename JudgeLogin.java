package login;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JudgeLogin 
{
	String temp1;
	Flow flow;
	public JudgeLogin(Flow flow)
	{
		this.flow = flow;
	}
	
	public int judge(String acc,String pass)
	{
		//检查登陆状态
		if(flow.l5.getText().contains("1"))
		{return -1;}
		else
		{
			new Login(acc,pass);
			try {
				URL url =new URL("http://192.168.31.4:8080/");

				HttpURLConnection hurl = (HttpURLConnection) url.openConnection();

				BufferedReader br = new BufferedReader(new InputStreamReader(hurl.getInputStream()));

				String temp;

				StringBuffer sb= new StringBuffer();
				while ((temp=br.readLine())!=null)
				{
					sb.append(temp);
				}
				temp1 = sb.toString();

			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//检验账号密码
			if (temp1.contains("Password"))
			{
				//JOptionPane.showMessageDialog(null,"Invalid username&&||password","用户名或密码错误",JOptionPane.ERROR_MESSAGE);
				flow.la8.setText("Invalid username&&||password");
				return 0;
			}
			else return 1;
		}
  }
}
