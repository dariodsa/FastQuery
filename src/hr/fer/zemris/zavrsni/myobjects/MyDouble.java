package hr.fer.zemris.zavrsni.myobjects;

public class MyDouble implements _Comparable<MyDouble>{
	private double value;
	public MyDouble(double value)
	{
		this.value = value;
	}
	
	@Override
	public int compareTo(MyDouble A)
	{
		double raz = value - A.value;
		
		if(raz>1e-5) return 1;
		else if(raz<-1e-5) return -1;
		else return 0;
	}
}
