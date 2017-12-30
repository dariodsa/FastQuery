package hr.fer.zemris.zavrsni;

import hr.fer.zemris.zavrsni.geoobjects.*;

import hr.fer.zemris.zavrsni.structures.SimpleTrie;
import hr.fer.zemris.zavrsni.structures.Trie;

import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		
		// text file with location
		SimpleTrie T = new SimpleTrie();
		Random R = new Random();
		for(int i=0;i<1000000;++i)
		{
			Double rand = R.nextDouble()*10;
			rand = Math.round(rand * 10000.0)/10000.0;
			T.add(rand.toString());
		}
		for(int i=0;i<1000000;++i)
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
	public static void errorCatch(Exception ex)
	{
		System.err.println(ex.getMessage());
		ex.printStackTrace();
	}
}
