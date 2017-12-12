package lab3;
import javax.swing.table.*;
public class myTable extends AbstractTableModel {
	private Double from;
	private Double to;
	private Double step;
	private Double[] coeff;
	private double x;
	
	public myTable(Double from,Double to,Double step,Double[] coeff)
	{
		this.from=from;
		this.to=to;
		this.step = step; 
		this.coeff = coeff;

	}
	public int getRowCount() {
		return new Double(Math.ceil((to-from)/step)).intValue()+1;
	}

	public int getColumnCount() {
		return 4;
	}
	public Object getValueAt(int row, int col) {
		x=from+step*row;
		Double result1=gorner(coeff,x);
		Double result2=gorner2(coeff,x);
		Double razn=result1-result2;
		switch(col)
		{
		case 0:
			return x;
		case 1:
			return result1;
		case 2:
			return result2;
		case 3:
			return razn;
		default:
			return 0.0;
		}
	}
	public Double getFrom()
	{
		return from;
	}
	public Double getTo()
	{
		return to;
	}
	public Double getStep()
	{
		return step;
	}
	public String getColumnName(int col) 
	{
		switch(col)
		{
		case 0:
			return "Значение Х";
		case 2:
			return "Значение многочлена в обратном";
		case 3:
			return "Разница";
	    default:
	    	return "Значение многочлена";
		}
	}
	public Class<?> getColumnClass(int col) 
	{ 
		return Double.class;
	}
	public Double gorner(Double[] a,Double x)
	{
		Double result=a[a.length-1];
		for(int i=a.length-2;i>=0;i--)
		{
			result =x*result+a[i];
		}
		return result;
	}
	public Double gorner2(Double[] a,Double x)
	{
		Double result=a[0];
		for(int i=1;i<a.length;i++)
		{
			result =x*result+a[i];
		}
		return result;
	}
	public double getXx()
	{
		return x;
	}
}
