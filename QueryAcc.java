package login;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class QueryAcc extends JPanel implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int clicktimes=0;
	int row = 0;
	int accorder = 0;
	int increase = 0;
	//键值对
	HashSet<String> set;//数字集合
	Second dbc=new Second();
	ResultSet res,res1,res2,res3,res4,res5;
	DefaultTableModel mo1=new DefaultTableModel(new String[]{"账号"},0);
	DefaultTableModel mo2=new DefaultTableModel(new String[]{"顺序","账号"},0);
	JLabel Qlabel,Qlabel1,Qlabel2;
	JButton order,update;
	JTable Qt2;
	JScrollPane jsp2;
	//设置内容居中显示
	DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
	//显示已选的账号排序

	public QueryAcc()
	{
		UIManager.put("RootPane.setupButtonVisible",false); //不显示设置按钮
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		tcr.setHorizontalAlignment(JLabel.CENTER);//数据在表格居中
		Qlabel=new JLabel("亲 ! 请在表格中的顺序列修改(自动切换)账号顺序 :",JLabel.CENTER);
		Qlabel1=new JLabel("",JLabel.CENTER);
		Qlabel2=new JLabel("",JLabel.CENTER);
		Qlabel.setForeground(Color.MAGENTA);
		Qlabel.setFont(new Font("宋体",Font.BOLD, 20));
		Qlabel1.setFont(new Font("宋体", Font.BOLD, 15));
		Qlabel2.setFont(new Font("宋体", Font.BOLD, 15));
		Qlabel1.setForeground(Color.RED);
		Qlabel2.setForeground(Color.magenta);
		order = new JButton("排序");
		update = new JButton("更新数据");
		Qt2=new JTable(mo2);
		Qt2.setDefaultRenderer(Object.class,tcr);
		jsp2=new JScrollPane(Qt2);
		//设置组件大小
		Qlabel.setPreferredSize(new Dimension(500, 30));
		jsp2.setPreferredSize(new Dimension(250, 200));
		Qlabel1.setPreferredSize(new Dimension(500, 20));
		Qlabel2.setPreferredSize(new Dimension(400, 20));
		order.setPreferredSize(new Dimension(100, 30));
		//注册监听器
		order.addActionListener(this);
		update.addActionListener(this);
		add(Qlabel);add(jsp2);add(order);add(update);add(Qlabel1);add(Qlabel2);
		setLayout(new FlowLayout());
		setVisible(true);
		//Table可编辑
		Qt2.setEnabled(true);

		//显示已有切换账号顺序
		try 
		{
			res1=dbc.executeQuery("select * from stuacc order by id");
			while (res1.next())
			{
				increase++;
				Vector currow=new Vector();
				//显示一行数据
				currow.addElement(increase);
				currow.addElement(res1.getString(1));
				mo2.addRow(currow);
			}
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==order)
		{
			//初始化set,用于判断是否有重复顺序
			set = new HashSet<String>();
			int row = Qt2.getRowCount();//获取行数
			//添加元素
			for (int i=0;i<row;i++)
			{
				set.add(Qt2.getValueAt(i, 0).toString());
			}
			//判断是否有新元素加入
			res3 = dbc.executeQuery("select * from stuacc order by id");
			int count = 0;
			try {
				while (res3.next())
				{
					count++;
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			//判断元素个数是否等于行数(无空白，无重复，数据库的账号数等于行数)
			if (set.size()==row&&!set.contains("")&&set.size()==count)
			{
				try {
					//用修改后的数据更新数据库
					for (int i=0;i<row;i++)
					{
						//数据类型转换（bug所在）
						Object temp = Qt2.getValueAt(i,0);//序号
						String name = (String)Qt2.getValueAt(i,1);//账号
//						System.out.println(temp+name);
						dbc.executeUpdate("update stuacc set id ="+temp+" where account='"+name+"'");
					}
					//将排序后的账号更新到表格
					mo2.setRowCount(0);
					res5 = dbc.executeQuery("select * from stuacc order by id");
					while (res5.next())
					{
						Vector currow=new Vector();
						currow.addElement(res5.getString(3));
						currow.addElement(res5.getString(1));
						mo2.addRow(currow);
					}
					Qlabel2.setText("^_^!!!排序成功!!!^_^");
					Qlabel1.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			//顺序列不允许有空白
			else if (set.contains(""))
			{
				Qlabel1.setText("^_^亲，输入序号不能有空，请检查一下吧^_^");Qlabel2.setText("");
			}
			//不允许序号重复
			else if (set.size()<row){Qlabel1.setText("^_^亲，您输入的序号有重复哦，请检查修改一下吧^_^");Qlabel2.setText("");}		

			else{Qlabel2.setText("亲^_^发现您有添加或删除账号哦^_^请先更新数据再来排序吧^_^");};
		}


		if (e.getSource()==update)
		{
			try 
			{
				increase=0;//置零，以便更新数据时由1递增
				res4=dbc.executeQuery("select * from stuacc order by id");
				mo2.setRowCount(0);
				while (res4.next())
				{
					increase++;
					Vector currow=new Vector();
					//显示一行数据
					currow.addElement(increase);
					currow.addElement(res4.getString(1));
					mo2.addRow(currow);
				}
				//清空提醒
				Qlabel1.setText("");
				Qlabel2.setText("");
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
	}
}