package lab2;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.*;
public class main {

	public static void main(String[] args) {
		System.out.println("Hell");
		myFrame frame = new myFrame();
		frame.setSize(1000,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.show();
	}

}
class myFrame extends JFrame{
	public myFrame()
	{
		setResizable(false);
		Container content= getContentPane();
		myPanel panel = new myPanel();
		content.add(panel);
		pack();
	}	
}
class myPanel extends JPanel{
	public JButton consider = new JButton("Вычислить");
	public Image img1;
	public Image img2;
	{try{
		img1=ImageIO.read(new File("/Users/mike/Documents/Java/lab2/bin/lab2/im1.png"));
		img2 =ImageIO.read(new File("/Users/mike/Documents/Java/lab2/bin/lab2/im2.png"));
	}catch(IOException exception){}}
	public double mem1,mem2,mem3;
    double x=0;
	double y=0;
	double z=0;
	double result =0;
	public JTextField per1 = new JTextField(8);
	public JTextField per2 = new JTextField(8);
	public JTextField per3 = new JTextField(8);
	public JTextField memF1 = new JTextField(20);
	public JTextField memF2 = new JTextField(20);
	public JTextField memF3 = new JTextField(20);
	public JTextField result1 = new JTextField(20);
	public ButtonGroup group = new ButtonGroup();
	public JRadioButton form1 = new JRadioButton("Формула 1");
	public JRadioButton form2 = new JRadioButton("Формула 2");
	public ButtonGroup memGr = new ButtonGroup();
	public JRadioButton memM1 = new JRadioButton("mem1");
	public JRadioButton memM2 = new JRadioButton("mem2");
	public JRadioButton memM3 = new JRadioButton("mem3");
	ActionListener memb = new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					try{
						if(memM1.isSelected())							
							{
							mem1 = result;
							memF1.setText(Double.toString(mem1));
							}
						if(memM2.isSelected())
						{
						mem2 = result;
						memF2.setText(Double.toString(mem2));
						}
						if(memM3.isSelected())
						{mem3 = result;
						memF3.setText(Double.toString(mem3));
						}
						
					}catch(NumberFormatException expt){
						if(memM1.isSelected())
							mem1 = 0;
						if(memM2.isSelected())
							mem2 = 0;
						if(memM3.isSelected())
							mem3 = 0;
					}
				}
		
			};
	ActionListener cons = new ActionListener()
			{
		public void actionPerformed(ActionEvent evt)
		{
			  try {
				     x=Double.valueOf(per1.getText());
					  System.out.println(x);
					 y=Double.valueOf(per2.getText());
					  System.out.println(y);
					 z=Double.valueOf(per3.getText());
					  System.out.println(z);
			    } catch (NumberFormatException e) {
			        System.err.println("Неверный формат строки!");
			    }
			  if(form1.isSelected())
			  result=sin(log(y)+sin(Math.PI * y*y)*pow((x*x)+sin(z)+exp(cos(z)),1/4));
			  if(form2.isSelected())
			  result=pow(cos(exp(x))+pow(log(1+y),2)+sqrt(exp(cos(x))+pow(sin(Math.PI*z),2))+sqrt(1/x)+cos(y*y),sin(z));
			  result1.setText(Double.toString(result));
			  System.out.println(result);
			  
		}
			};
	ActionListener memCL = new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					if(memM1.isSelected())
					{
						mem1=0;
						memF1.setText(Double.toString(mem1));
					}
					if(memM2.isSelected())
					{
						mem2=0;
						memF2.setText(Double.toString(mem2));
					}
					if(memM3.isSelected())
					{
						mem3=0;
						memF3.setText(Double.toString(mem3));
					}
				}
		
			};
	ActionListener memPL = new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
					try{
						if(memM1.isSelected())
						{
							result +=mem1;
							memF1.setText(Double.toString(result));
						}
						if(memM2.isSelected())
						{
							result +=mem2;
							memF2.setText(Double.toString(result));
						}
						if(memM3.isSelected())
						{
							result +=mem3;
							memF3.setText(Double.toString(result));
						}
						
					}catch(NumberFormatException expt){
						if(memM1.isSelected())
							mem1 = 0;
						if(memM2.isSelected())
							mem2 = 0;
						if(memM3.isSelected())
							mem3 = 0;
					}
				}
			};
    ActionListener vichel = new ActionListener()
		{
				public void actionPerformed(ActionEvent e) {
					String s =per1.getText();
					System.out.println(s);
				}
		};
	public myPanel()
	{
		consider.addActionListener(cons);
		memM1.addActionListener(memb);
		memM2.addActionListener(memb);
		memM3.addActionListener(memb);
		JButton membCl = new JButton("MC");
		membCl.addActionListener(memCL);
		JButton membPl = new JButton("M+");
		membPl.addActionListener(memPL);
		setLayout(new BorderLayout());
		Box verh = Box.createHorizontalBox();
		Box ser = Box.createHorizontalBox();
		Box niz = Box.createHorizontalBox();
		Box east = Box.createVerticalBox();
		final Box west = Box.createHorizontalBox();
		JButton vich = new JButton("Vicheslit");
		vich.addActionListener(cons);
		vich.addActionListener(vichel);
		ActionListener radio = new ActionListener()
				{
			public JLabel imm1;
			public JLabel imm2;
			Container m = new Container();
			public void actionPerformed(ActionEvent e) {
				//validate();
				if(form1.isSelected())
				{
					if(imm1 != null)
					imm1.hide();
					imm1=new JLabel(new ImageIcon(img1.getScaledInstance(400, 50, 0)));
					imm1.show();
					if(imm2 != null)
					imm2.hide();
				}
				if(form2.isSelected())
				{
					if(imm2 != null)
						imm2.hide();
					imm2=new JLabel(new ImageIcon(img2.getScaledInstance(400, 50, 0)));
					imm2.show();
					if(imm1 != null)
					imm1.hide();
				}
				if(imm1 != null)
				west.add(imm1);
				if(imm2 != null)
				west.add(imm2);
				validate();
			}
	
		};
		form1.addActionListener(radio);
		form2.addActionListener(radio);
		group.add(form1);
		group.add(form2);
		memGr.add(memM1);
		memGr.add(memM2);
		memGr.add(memM3);
		verh.add(form1);
		verh.add(form2);
		verh.add(memM1);
		verh.add(memM2);
		verh.add(memM3);
		verh.add(membCl);
		verh.add(membPl);
		JLabel perX = new JLabel("X:");
		JLabel perY = new JLabel("Y:");
		JLabel perZ = new JLabel("Z:");	
		JLabel memm1 = new JLabel("mem1:");
		JLabel memm2 = new JLabel("mem2:");
		JLabel memm3 = new JLabel("mem3:");
		ser.add(perX);
		per1.setMaximumSize(new Dimension(Integer.MAX_VALUE,per1.getMinimumSize().height));
		ser.add(per1);
		ser.add(perY);
		per2.setMaximumSize(new Dimension(Integer.MAX_VALUE,per2.getMinimumSize().height));
		ser.add(per2);
		ser.add(perZ);
		per3.setMaximumSize(new Dimension(Integer.MAX_VALUE,per3.getMinimumSize().height));
		ser.add(per3);
		JLabel res = new JLabel("Результат:");
		niz.add(consider);
		niz.add(res);
		niz.add(result1);
		east.add(memm1);
		memF1.setMaximumSize(new Dimension(Integer.MAX_VALUE,memF1.getMinimumSize().height));
		east.add(memF1);
		east.add(memm2);
		memF2.setMaximumSize(new Dimension(Integer.MAX_VALUE,memF2.getMinimumSize().height));
		east.add(memF2);
		east.add(memm3);
		memF3.setMaximumSize(new Dimension(Integer.MAX_VALUE,memF3.getMinimumSize().height));
		east.add(memF3);
		east.add(Box.createGlue());
		add(verh,BorderLayout.NORTH);
		add(ser,BorderLayout.CENTER);
		add(niz,BorderLayout.SOUTH);
		add(west,BorderLayout.WEST);
		add(east,BorderLayout.EAST);
	}	
}
