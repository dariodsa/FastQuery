package hr.fer.zemris.zavrsni;

import hr.fer.zemris.zavrsni.geoobjects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {
		
		// text file with location
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
