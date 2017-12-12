package lab3;
import java.io.*;
import java.awt.*;
import javax.swing.AbstractAction; import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
public class main {

	public static void main(String[] args) {
		System.out.println("hell");
		if(args.length==0)
		{
			System.out.println("Невозможно табулировать многочлен, для которого не зад");
			System.exit(-1);
		}
		Double[] coefficients = new Double[args.length];
		for(int i=0;i<args.length;i++)
		{
			try{
			coefficients[i]=Double.parseDouble(args[i]);
			}catch(NumberFormatException ex)
			{
				System.out.println("Ошибка преобразования строки '" + args[i] + "' в число типа Double");
				System.exit(-2);
			}
		}
		myFrame frame = new myFrame(coefficients);
		frame.show();

	}

}
class myFrame extends JFrame
{
	private JFileChooser fileChooser = null;
	private JFileChooser csvChooser = null;
	private Double[] coefficients;
	private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
	private myTable data;
	private JMenuItem saveToTextMenuItem;
	private JMenuItem saveToGraphicsMenuItem;
	private JMenuItem searchValueMenuItem;
	private JMenuItem saveCSV;
	private JMenuItem obAvtore;
	private JTextField textFieldFrom;
	private JTextField textFieldTo;
	private JTextField textFieldStep;
	private Box hBoxResult;
	myFrame(Double[] coef)
	{
		super("Табулирование многочлена на отрезке по схеме Горнера");
		this.coefficients = coef;
		setName("Date_Table");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,600);
		setResizable(false);
		JMenu file = new JMenu("Файл");
		JMenu table = new JMenu("Таблица");
		JMenu help = new JMenu("Справка");	
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);
		menu.add(file);
		menu.add(table);
		menu.add(help);
		Action obA = new AbstractAction("Об авторе")
				{
					public void actionPerformed(ActionEvent e) {
						JDialog dialog = createDialog("Об авторе", true);
		                dialog.setVisible(true);
					}
			
				};
		obAvtore=help.add(obA);
		obAvtore.setEnabled(true);
		Action saveToCSV = new AbstractAction("Сохранить в CSV")
				{
			
					public void actionPerformed(ActionEvent e) {
						if(csvChooser==null)
						{
							csvChooser = new JFileChooser();
							csvChooser.setCurrentDirectory(new File("."));
						}
						if(csvChooser.showSaveDialog(myFrame.this)==JFileChooser.APPROVE_OPTION)
						{
							saveToCSV(csvChooser.getSelectedFile());
						}
					}
			
				};
		saveCSV = file.add(saveToCSV);
		saveCSV.setEnabled(false);
		Action saveToTextFile = new AbstractAction("Сохранить в текстовый файл")
		{
			public void actionPerformed(ActionEvent e) {
				if(fileChooser==null)
				{
					fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File("."));
				}
				if(fileChooser.showSaveDialog(myFrame.this)==JFileChooser.APPROVE_OPTION)
				{
					saveToTextFile(fileChooser.getSelectedFile());
				}
			}
	
		};
		saveToTextMenuItem = file.add(saveToTextFile);
		saveToTextMenuItem.setEnabled(false);
		Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика")
				{
					public void actionPerformed(ActionEvent e) {
						if (fileChooser==null)
						{
							fileChooser = new JFileChooser();
							fileChooser.setCurrentDirectory(new File("."));
						}
						if (fileChooser.showSaveDialog(myFrame.this) == JFileChooser.APPROVE_OPTION)
							saveToGraphicsFile(
								      fileChooser.getSelectedFile());
					}
			
				};
		 saveToGraphicsMenuItem = file.add(saveToGraphicsAction);
		 saveToGraphicsMenuItem.setEnabled(false);
		 Action searchValueAction = new AbstractAction("Найти из диапазона")
				 {
					public void actionPerformed(ActionEvent e) {
						String value1 =JOptionPane.showInputDialog(myFrame.this, "Введите значение начала", "Поиск значений", JOptionPane.QUESTION_MESSAGE);
						String value2 =JOptionPane.showInputDialog(myFrame.this, "Введите значение конца", "Поиск значений", JOptionPane.QUESTION_MESSAGE);
						Double v1=Double.valueOf(value1);
						Double v2=Double.valueOf(value2);
						renderer.setNeedle(v1,v2);
						getContentPane().repaint();						
					}
				 };
		searchValueMenuItem = table.add(searchValueAction);
		searchValueMenuItem.setEnabled(false);
		JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
		textFieldFrom = new JTextField("0.0", 10);
		textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());
		JLabel labelForTo = new JLabel("до:");
		textFieldTo = new JTextField("1.0", 10);
		textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());
		JLabel labelForStep = new JLabel("с шагом:");
		textFieldStep = new JTextField("0.1", 10);
		textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());
		Box hboxRange = Box.createHorizontalBox();
		hboxRange.setBorder(BorderFactory.createBevelBorder(1));
	    hboxRange.add(Box.createHorizontalGlue());
		hboxRange.add(labelForFrom);
		hboxRange.add(Box.createHorizontalStrut(10));
		hboxRange.add(textFieldFrom);
		hboxRange.add(Box.createHorizontalStrut(20));
		hboxRange.add(labelForTo);
		hboxRange.add(Box.createHorizontalStrut(10));
		hboxRange.add(textFieldTo);
		hboxRange.add(Box.createHorizontalStrut(20));
		hboxRange.add(labelForStep);
		hboxRange.add(Box.createHorizontalStrut(10));
		hboxRange.add(textFieldStep);
		hboxRange.add(Box.createHorizontalGlue());
		hboxRange.setPreferredSize(new Dimension(new Double(hboxRange.getMaximumSize().getWidth()).intValue(),new Double(hboxRange.getMinimumSize().getHeight()).intValue()*2));
		getContentPane().add(hboxRange, BorderLayout.NORTH);
		JButton buttonCalc = new JButton("Вычислить");
		buttonCalc.addActionListener(new ActionListener()
				{
			     public void actionPerformed(ActionEvent ev)
			     {
			    	 try {
			    		 Double from = Double.parseDouble(textFieldFrom.getText());
			    		 Double to = Double.parseDouble(textFieldTo.getText());
			    		 Double step = Double.parseDouble(textFieldStep.getText());
			    		 data = new myTable(from, to, step,myFrame.this.coefficients);
			    		 JTable table = new JTable(data);
			    		 table.setDefaultRenderer(Double.class,renderer);
			    	      table.setRowHeight(30);
			    	      hBoxResult.removeAll();
			    	      hBoxResult.add(new JScrollPane(table));
			    	      getContentPane().validate();
			    	      getContentPane().repaint();
			    	      saveToTextMenuItem.setEnabled(true);
			    	      saveToGraphicsMenuItem.setEnabled(true);
			    	      searchValueMenuItem.setEnabled(true);
			    	      saveCSV.setEnabled(true);
			    	 }catch (NumberFormatException ex)
			    	 {
			    		 JOptionPane.showMessageDialog(myFrame.this,"Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",JOptionPane.WARNING_MESSAGE);	 
			    	 }
			     }
				});
		JButton buttonReset = new JButton("Очистить поля");
		buttonReset.addActionListener(new ActionListener()
				{
			public void actionPerformed(ActionEvent ev) {
				textFieldFrom.setText("0.0");
				textFieldTo.setText("1.0");
				textFieldStep.setText("0.1");
				hBoxResult.removeAll();
				hBoxResult.add(new JPanel());
				saveToTextMenuItem.setEnabled(false);
				saveToGraphicsMenuItem.setEnabled(false);
				searchValueMenuItem.setEnabled(false);
				getContentPane().validate();
			}
				});
		Box hboxButtons = Box.createHorizontalBox();
		hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
		hboxButtons.add(Box.createHorizontalGlue());
		hboxButtons.add(buttonCalc);
		hboxButtons.add(Box.createHorizontalStrut(30));
		hboxButtons.add(buttonReset);
		hboxButtons.add(Box.createHorizontalGlue());
		hboxButtons.setPreferredSize(new Dimension(new Double(hboxButtons.getMaximumSize().getWidth()).intValue(), new Double(hboxButtons.getMinimumSize().getHeight()).intValue()*2));
		getContentPane().add(hboxButtons, BorderLayout.SOUTH);
		hBoxResult = Box.createHorizontalBox();
		hBoxResult.add(new JPanel());
		getContentPane().add(hBoxResult, BorderLayout.CENTER);
		
	}
	protected void saveToTextFile(File selectedFile) {
		try {
			PrintStream out = new PrintStream(selectedFile);
			out.println("Результаты табулирования многочлена по схеме Горнера");
			out.print("Многочлен: ");
			for (int i=0; i<coefficients.length; i++) 
			{
                out.print(coefficients[i] + "*X^" +(coefficients.length-i-1));
             if (i!=coefficients.length-1) 
            	 out.print(" + ");
            }
            out.println("");
            out.println("Интервал от " + data.getFrom() + " до " +data.getTo() + " с шагом " + data.getStep());
            out.println("===================================================="); // Записать в поток вывода значения в точках
            for (int i = 0; i<data.getRowCount(); i++) 
            {
            out.println("Значение в точке " + data.getValueAt(i,0) + " равно " + data.getValueAt(i,1));
            }
            out.close();
		}catch (FileNotFoundException e) {
			
		}
	}
	protected void saveToCSV(File selectedFile) {
		try {
			PrintStream out = new PrintStream(selectedFile);
			out.println(data.getColumnName(0)+","+data.getColumnName(1)+","+data.getColumnName(2)+","+data.getColumnName(3));
			for(int i=0;i<data.getRowCount();i++)
			{
				out.println(data.getValueAt(i, 0)+","+data.getValueAt(i, 1)+","+data.getValueAt(i, 2)+","+data.getValueAt(i, 3));
			}
			out.close();
			
		}catch (FileNotFoundException e) {
			
		}
	}
   protected void saveToGraphicsFile(File selectedFile)
   {
	   try {
		   DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
		   for (int i = 0; i<data.getRowCount(); i++)
		   { 
			   out.writeDouble((Double)data.getValueAt(i,0)); 
			   out.writeDouble((Double)data.getValueAt(i,1));
		   }
		   out.close();
	   }catch (Exception e) {
		   
	   }
   }
   private JDialog createDialog(String title, boolean modal)
   {
       JDialog dialog = new JDialog(this, title, modal);
       dialog.setDefaultCloseOperation(HIDE_ON_CLOSE);
       dialog.setSize(400, 400);
       Image img1 = null;
       try{
   		img1=ImageIO.read(new File("/Users/mike/Documents/Java/lab3/bin/lab3/im1.png"));
   	    }catch(IOException exception){System.out.println("ne naideno");}
       JLabel name = new JLabel("Лабутин Михаил Дмитриевич,10 группа");
       JLabel photo = new JLabel(new ImageIcon(img1));
       Box im =Box.createHorizontalBox();
      // im.add(photo);
       Box na =Box.createHorizontalBox();
       na.add(name);
       dialog.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
       dialog.getContentPane().add(im,BorderLayout.NORTH);
       dialog.getContentPane().add(na,BorderLayout.SOUTH);
       return dialog;
   }
}