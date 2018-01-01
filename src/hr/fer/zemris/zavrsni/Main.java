package hr.fer.zemris.zavrsni;

import hr.fer.zemris.threads.Radilica;
import hr.fer.zemris.zavrsni.geoobjects.*;
import hr.fer.zemris.zavrsni.structures.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.nio.file.Paths;

public class Main {

	static Radilica[] rad = new Radilica[14];
	//static SimpleTrie T = new SimpleTrie();
	static ParallelTrie P = new ParallelTrie();
	static ParallelTrie P2 = new ParallelTrie();
	static TreeMap<Double,Boolean> L = new TreeMap<>();
	public static void main(String[] args) throws InterruptedException {
		
		// text file with location
		Double[] S = new Double[10000000];
		Node[] N = new Node[10000000];
		Random R = new Random();
		for(int i=0;i<1000000;++i)
		{
			Double rand = R.nextDouble()*360;
			
			S[i]= rand;
		}
		long t1 = System.currentTimeMillis();
		for(int i=1000000-1;i>=0;--i)
		{
			N[i] = P.add(String.format ("%.4f",S[i]));
			//System.out.println(S[i] + " je dodan.");
		}
		long t2 = System.currentTimeMillis();
		/*for(int i=0;i<5000000;++i)
		{
			Double rand2 = S[i] + R.nextDouble() / 1000.0;
			P2.fastAdd(String.format ("%.4f",S[i]),String.format("%.4f", rand2));
		}*/
		long t3 = System.currentTimeMillis();
		System.out.println((t2-t1)+ " "+ (t3-t2));
		/*for(int i=0;i<10000000;++i)
		{
			Double r = S[i] + R.nextDouble() *360;
			P.update(String.format ("%.4f",S[i]), String.format ("%.4f",r),N[i]); 
		}*/
		long t3a = System.currentTimeMillis();
		System.out.println("moj update "+(t3a-t3));
		
		for(int i=0;i<100000;++i)
		{
			//SortedMap<Double,Boolean> D = L.subMap(S[i], S[i] + R.nextDouble() * 100);
			Double pi = S[i]+0.1;
			List<Double> p = P.getFromInterval(S[i], pi);
			if(i%10000==0)
			{	
				System.out.println("sz "+p.size() +" "+S[i] + p.get(p.size()-1));
				/*for(Double d1 : p)
				{
					System.out.printf("%.4f ",d1);
				}
				System.out.println();*/
			}
		}
		
		long t4 = System.currentTimeMillis();
		System.out.println("moj request "+(t4-t3a));
		for(int i=0;i<1000000;++i)
		{
			L.put(S[i],true);
		}
		long t5 = System.currentTimeMillis();
		System.out.println(t5-t4);
		/*for(int i=0;i<10000000;++i)
		{
			L.remove(S[i]);
			L.put(S[i]+ R.nextDouble() *360,true);
		}*/
		long t6 = System.currentTimeMillis();
		System.out.println(t6-t5);
		for(int i=0;i<100000;++i)
		{
			SortedMap<Double,Boolean> p = L.subMap(S[i], S[i] + 0.1);
			if(i%10000==0)
			{	
				//Set<Double> D = p.keySet();
				System.out.println("sz "+p.size()+" "+S[i] +" "+p.lastKey());
				/*for(Double d1 : D)
				{
					System.out.printf("%.4f ",d1);
				}*/
			}
		}
		long t7 = System.currentTimeMillis();
		System.out.println("njihov  " +(t7-t6));
		/*for(int i=0;i<10000000;++i)
		{
			Double rand = R.nextDouble()/10000.0;
			Double old = S[i];
			S[i] += rand; 
			S[i] = Math.round(S[i] * 100000.0)/100000.0;
			T.update(old.toString(), S[i].toString());
		}*/
		System.exit(0);
		
		
		
		String filePath = args[0];
		try
		{
			List<String> lines = Files.readAllLines(Paths.get(filePath, ""));
			List<Dot> dots = parse(lines);
			int numOfQuerys = Integer.parseInt(args[1]);
			int numMovesBetweenQuerys = Integer.parseInt(args[2]);
			int numOfThreads = Integer.parseInt(args[3]);
			int treeType = Integer.parseInt(args[4]);
			/*
			 * 0 -> simpleTrie
			 * 1 -> CompressTrie
			 * 2 -> BBT -> TBD later
			 */
			Program.run(dots, numOfQuerys, numMovesBetweenQuerys, numOfThreads, treeType);
		}
		catch(IOException e)
		{
			errorCatch(e);
		}
	}
	private static List<Dot> parse(List<String> lines)
	{
		List<Dot> sol = new ArrayList<>();
		for(String line : lines)
		{
			String[] numbers = line.split(",");
			List<Double> dotValues = new ArrayList<>();
			for(String number : numbers)
			{
				dotValues.add(Double.parseDouble(number));
			}
			Dot D = new Dot(dotValues.size(),dotValues);
			sol.add(D);
		}
		return sol;
	}
	public static void dretveRun()
	{
		/*System.out.println("Dodano u bazu.");
		for(int i=0;i<14;++i)
		{
			rad[i] = new Radilica(T);
		}
		for(int i=0;i<14;++i)
		{
			rad[i].start();
		}
		for(int i=0;i<14;++i)
		{
			try {
				rad[i].dretva.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("gotovi 14000");*/
	}
	public static void errorCatch(Exception ex)
	{
		System.err.println(ex.getMessage());
		ex.printStackTrace();
	}
}
