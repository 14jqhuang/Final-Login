package login;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//定时器
public class TaskSelection extends TimerTask
{
	Flow flow;
	ScheduledExecutorService ser3= Executors.newScheduledThreadPool(1);//AutoLogout
	ScheduledExecutorService ser5= Executors.newScheduledThreadPool(1);//AutoLogin
	public TaskSelection(Flow flow)
	{
		this.flow = flow;
	}
		public void run()
		{
			 try 
			 {
				 /*
				  * 防止多次弹窗
				  */
				 if (flow.list.size()==0&&flow.closePane==0)
				 {
					 try 
					 {
						 flow.la4.setText("");flow.la5.setText("流量群已全爆完，等着挨揍吧，少年");
						 flow.l5.setText("");flow.l6.setText("");flow.l7.setText("");
						 flow.closePane+=1;
						 //账号有错才输出
						 if (flow.temp2>=1)
						 {
							 for (int i=1;i<=flow.temp2;i++)
							 {
								 flow.nameset+=flow.name[i]+" ";
							 }
							 flow.la8.setText(""+flow.nameset+" 已经更改密码啦！！！或者账号无效");
						 }
					 }
					 catch (Exception e) 
					 {
						e.printStackTrace();
					 }
				 }
				 else if ((Double.parseDouble(flow.l6.getText())>=Double.parseDouble(flow.t4.getText()))&&flow.list.size()!=0)
				{
					ser5.schedule(new AutoLogout(flow),0,TimeUnit.MILLISECONDS);
					Thread.sleep(4000);
					ser3.schedule(new AutoLogin(flow),0,TimeUnit.MILLISECONDS);//缓冲Logout
				}
			 }
			 catch (Exception e)  { }
		}
}
