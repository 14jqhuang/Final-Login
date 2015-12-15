package login;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//实时监控流量类
public class Update extends TimerTask
{
	Flow flow;
	//判断是否断网（24:00）
	String str = null;
	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	ResultSet res;
	Second dbc = new Second();
	String accou,mused,mused1;
	public Update(Flow flow)
	{
		this.flow=flow;
	}
	public void run() 
	{
		try { 
			//格式化时间
			Date time = new Date(System.currentTimeMillis());
			str = format.format(time);

			DecimalFormat df = new DecimalFormat("#0.00");
			URL url = new URL("http://192.168.31.4:8080/");
			//打开URL 
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			//得到输入流，即获得了网页的内容 
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection
					.getInputStream()));
			String line;
			StringBuffer sb=new StringBuffer();
			while((line = reader.readLine()) != null)
			{
				sb.append(line);//asp页面打印的内容，注意是整个页面内容，包括HTML标签
			}

			if (sb.toString().contains("Used bytes"))
			{
				String acc=sb.substring(2896,2922);//Used Bytes
				String stacc=sb.substring(2550,2580);//Account
				String total = sb.substring(3248,3280);//Total Flow

				int numm=stacc.indexOf(">");
				int num=stacc.indexOf("<");
				accou=stacc.substring(numm+1,num);
				/*
				 * 正则表达式提取已使用流量数据和总流量数据
				 */
				String regEx="[^0-9]";   
				Pattern p = Pattern.compile(regEx);   
				Matcher m1 = p.matcher(acc);
				Matcher m2 = p.matcher(total);
				String mrp1=m1.replaceAll("").trim();
				String mrp2=m2.replaceAll("").trim();
				double usedflow1=Double.parseDouble(mrp1);
				double usedflow2=Double.parseDouble(mrp2);
				mused=df.format(usedflow1/(1024*1024));
				mused1=df.format(usedflow2/(1024*1024));
				flow.l5.setText(accou);flow.l6.setText(mused);flow.l7.setText(mused1);
				flow.la4.setText(accou+"------Connected------");
				flow.la9.setText("");//Clear the warning 
			}
		
			else 
			{
				flow.la4.setText("You have logged out");
				flow.l5.setText("");//set username to null
				flow.l6.setText("");//set used flow to 
				flow.l7.setText("");//set total flow to null
				flow.la5.setText("");//set tips to null
			}

			//判断流量是否超过指定额度流量	
			if (Double.parseDouble(flow.l6.getText())>=Double.parseDouble(flow.t4.getText()))
			{
				flow.la5.setForeground(Color.red);
				flow.la5.setText("Red Warning :"+accou+"的账号已经超过额定流量,您还可使用 ："+df.format(Double.parseDouble(mused1)-Double.parseDouble(mused))+"M");
			}
			/*
			 * 没有超过额定值且显示不为空才显示提示
			 */
			else if (!flow.l5.getText().equals("")&&Double.parseDouble(flow.l6.getText())<Double.parseDouble(flow.t4.getText()))
			{
				flow.la5.setForeground(Color.black);
				flow.la5.setText(accou+"账号流量在指定范围内，请放心使用");
			}
		}	
		catch (Exception e)
		{
			System.out.println("Empty");
			flow.la9.setText("亲，您的网络异常！请检查诸如:您的网线松动、无WIFI信号等网络异常.");
			flow.la4.setText("You have logged out");
			flow.l5.setText("");//set username to null
			flow.l6.setText("");//set used flow to 
			flow.l7.setText("");//set total flow to null
			flow.la5.setText("");//set tips to null
		}
	}
}
