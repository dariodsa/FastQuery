package hr.fer.zemris.zavrsni.structures;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class Union {
	public static double[] intersection(double[] array1, double[] array2){
		List<Double> solution = new ArrayList<>();
		HashSet<Double> map = new HashSet<Double>();
		for(double object : array1)
			map.add(object);
		int cnt = 0;
		double[] sol = new double[Math.max(array1.length, array2.length)];
		
		for(double object : array2)
		{ 
			if(map.contains(object))
			{
				sol[cnt++]=object;
				++cnt;
			}
		}
		
		return sol;
	}
}
