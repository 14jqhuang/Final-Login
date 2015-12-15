package login;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;

public class NetworkState {
	String temp=null;
	
	public NetworkState()
	{
		init();
	}
	public String init()
	{
		try {
			URL url = new URL("http://192.168.31.4:8080/");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setDoOutput(true);//使用 URL 连接进行输出
			http.setDoInput(true);// 使用 URL 连接进行输入
			http.setUseCaches(false);// 忽略缓存 
			BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line=br.readLine())!=null)
			{
				sb.append(line);
			}
//			String temp= sb.substring(553,570);
//			String temp= sb.substring(553,570);
//			int num = temp.indexOf("<");
//			String temp1 = temp.substring(0,num);
//			String regEx="[^0-9]";   
//			Pattern p = Pattern.compile(regEx);   
//			Matcher m = p.matcher(temp1);   
//			String mrp=m.replaceAll("").trim();
//			
//			double d = Double.parseDouble(mrp)/(1024*1024);
//			String temp2 = df.format(d);
//			System.out.println(temp2);
//			System.out.println(sb.indexOf("Total bytes:"));
//			System.out.println(sb.substring(3250,3280));
			System.out.println(sb);
			temp = sb.toString();
//			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		}
		catch (SocketException se)
		{System.out.println("Empty");}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public static void main(String[] args) {
		new NetworkState();
	}
}
