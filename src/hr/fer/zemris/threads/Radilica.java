package hr.fer.zemris.threads;

import java.util.*;
import hr.fer.zemris.zavrsni.structures.*;

public class Radilica implements Runnable 
{
	private static Random R = new Random();
	private Trie T;
	public Thread dretva;
	public Radilica(Trie T)
	{
		this.T = T;
		dretva = new Thread(this);
	}
	public void start()
	{
		dretva.start();
	}
	public void run()
	{
		for(int i=0;i<1000;++i)
		{
			double x = R.nextDouble()*10;
			double y = R.nextDouble()*10;
			Double min = Math.min(x,y);
			Double max = Math.max(x,y);
			List<String> L = T.getFromInterval(min.toString(), max.toString());
			//System.out.println(L.size());
			if(i%1000 == 0)
				System.out.println(i);
		}
	}
}
