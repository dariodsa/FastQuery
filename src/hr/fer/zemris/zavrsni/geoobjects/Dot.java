package hr.fer.zemris.zavrsni.geoobjects;
import java.util.*;
public class Dot {
	/**
	 * 
	 * @param dimension
	 */
	private double[] points;
	private int dimension;
	public Dot(int dimension)
	{
		points = new double[dimension];
		this.dimension = dimension;
	}
	public Dot(int dimension,List<Double> values)
	{
		this(dimension);
		int cnt=0;
		for(double object : values)
			points[cnt++] = object;
	}
	public void setValues(List<Double> values)
	{
		int cnt=0;
		for(double value : values)
		{
			this.points[cnt++] = value;
		}
	}
}
