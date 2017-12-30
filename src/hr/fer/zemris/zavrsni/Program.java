package hr.fer.zemris.zavrsni;
import hr.fer.zemris.threads.Radilica;
import hr.fer.zemris.zavrsni.geoobjects.*;
import hr.fer.zemris.zavrsni.myobjects.MyDouble;
import hr.fer.zemris.zavrsni.structures.*;

import java.util.*;

import com.oracle.xmlns.internal.webservices.jaxws_databinding.ExistingAnnotationsType;


public class Program {
	
	static Trie<MyDouble> trie;  
	protected static void run(List<Dot> dots, int numOfQuerys, int numMovesBetweenQuerys, int numOfThreads,int treeType)
	{
		try
		{
			chooseTreeType(treeType);
		}
		catch(Exception e)
		{
			Main.errorCatch(e);
		}
		for(int j=0;j<numOfThreads;++j)
		{
			Radilica R = new Radilica();
			Thread dretva = new Thread(R);
			dretva.run();
			
		}
		
		for(int i=0;i<numOfQuerys;++i)
		{
			
		}
	}
	private static void chooseTreeType(int treeType) throws Exception
	{
		switch (treeType) {
		case 0:
			trie = new SimpleTrie<MyDouble>();
			break;
		case 1:
			trie = new CompressTrie<MyDouble>();
			break;
		default:
			throw new Exception("Wrong tree type");
		}
	}
}
