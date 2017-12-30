package hr.fer.zemris.zavrsni.structures;

import java.util.*;

public class SimpleTrie implements Trie{

	private final int addOn = 3;
	
	private int numOfNodes = 0;
	private ArrayList<ArrayList<Node>> V = new ArrayList<>();
	public SimpleTrie() 
	{
		V.add(new ArrayList<>());
		++numOfNodes;
	}
	@Override
	public boolean find(String object) 
	{
		String objectNorm = normalize(object, addOn);
		int len = objectNorm.length();
		int pos = 0;
		for(int i=0;i<len;++i)
		{
			boolean find = false;
			for(int j=0;j<V.get(pos).size();++j)
			{
				Node N = V.get(pos).get(j);
				
				if(N.znak == objectNorm.charAt(i))
				{
					find = true;
					pos = N.id;
					if(i+1 == len && N.finish<=0)
						return false;
					if(i+1 == len && N.finish>=1)
						return true;
					break;
				}
			}
			if(!find)return false;
		}
		return false;
	}

	@Override
	public void add(String object) 
	{
		String objectNorm = normalize(object, addOn);
		int len = objectNorm.length();
		
		int pos = 0;
		
		for(int i=0; i<len; ++i)
		{
			boolean find = false;
			int newPos = 0;
			for(int j=0;j<V.get(pos).size();++j)
			{
				Node N = V.get(pos).get(j);
				if(N.znak == objectNorm.charAt(i))
				{
					find = true;
					newPos = N.id;
					N.subTreeSize++;
					if(i+1 == len)
						N.finish++;
					break;
				}
			}
			if(find)
			{
				pos = newPos;
			}
			else
			{
				for(int j=i;j<len;++j)
				{
					Node N = new Node(numOfNodes++,objectNorm.charAt(j),1);
					
					if(j==len-1)
						N.finish = 1;
					V.get(pos).add(N);
					V.add(new ArrayList<>());
					pos = numOfNodes-1;
				}
				return;
			}
		}
	}

	@Override
	public int update(String oldObject, String newObject) 
	{
		if(delete(normalize(oldObject,addOn)))
		{
			add(normalize(newObject, addOn));
		}
		return 0;
	}
	public boolean delete(String object)
	{
		String objectNorm = normalize(object, addOn);
		if(find(objectNorm))
		{
			int len = objectNorm.length();
			int pos = 0;
			for(int i=0;i<len;++i)
			{
				for(int j=0;j<V.get(pos).size();++j)
				{
					Node N = V.get(pos).get(j);
					if(N.znak == objectNorm.charAt(i))
					{
						pos = N.id;
						N.subTreeSize--;
						if(i+1 == len)
							N.finish--;
						break;
					}
				}	
			}
			return true;
		}
		return false;
	}
	@Override
	public List<String> getFromInterval(String min, String max) 
	{
		min = normalize(min, addOn);
		max = normalize(max, addOn);
		
		List<String> ans = new ArrayList<>();
		Queue<Node> Q = new LinkedList<>();
		Queue<String> QString = new LinkedList<>();
		Q.add(new Node(0,'\0',0));
		QString.add("");
		while(!Q.isEmpty())
		{
			Node pos = Q.peek();
			String tempString = QString.peek();
			//System.err.println("Pos "+pos.id);
			Q.poll();
			QString.poll();
			
			if(pos.finish>0)
			{
				for(int i=0;i<pos.finish;++i)
					ans.add(tempString);
			}
			for(int i=0, len = V.get(pos.id).size();i<len;++i)
			{
				Node N = V.get(pos.id).get(i);
				String temp = tempString + N.znak;
				//System.out.println("TempStr "+temp + "=> ("+min+","+max+") "+canIMoveOn(temp, min, max));
				if(canIMoveOn(temp, min, max))
				{
					QString.add(temp);
					Q.add(N);
				}
			}
		}
		return ans;
	}

	public boolean canIMoveOn(String S, String min, String max)
	{
		int len1 = min.length();
		int len2 = max.length();
		boolean firstCheck = true;
		boolean secondCheck = true;
		for(int i=0, len=S.length();i<len;++i)
		{
			if(firstCheck && secondCheck && i+1 >= len1 && i+1 >= len2)
				return true;
			else if(secondCheck && i+1 >= len1 && i+1 < len2)
			{
				if(S.charAt(i) > max.charAt(i))
					return false;
			}
			else if(firstCheck && i+1 < len1 && i+1 >= len2)
			{
				if(S.charAt(i) < min.charAt(i))
					return false;
			}
			else
			{
				if(min.charAt(i)<S.charAt(i))
					firstCheck = false;
				if(max.charAt(i)>S.charAt(i))
					secondCheck = false;
				if(!secondCheck && !firstCheck)
					return true;
				if(firstCheck && min.charAt(i)<=S.charAt(i))
					{}
				else if(secondCheck  && max.charAt(i)>=S.charAt(i))
				{}
				else
					return false;
			}
		}
		return true;
	}
	
	private String normalize(String input, int addOn)
	{
		int len = input.length();
		int numOfZeros = 3;
		for(int i=0; i<len; ++i)
		{
			if(input.charAt(i)=='.')
			{
				break;
			}
			else numOfZeros--;
		}
		String res = "";
		for(int i=0; i<numOfZeros; ++i)
		{
			res += "0";
		}
		for(int i=0; i<len; ++i)
		{
			res += input.charAt(i);
		}
		return res;
	}
	private class Node
	{
		private int id;
		private char znak;
		private int subTreeSize;
		int finish;
		public Node(int id, char znak, int subTreeSize)
		{
			this.id          = id;
			this.znak        = znak;
			this.subTreeSize = subTreeSize;
			this.finish = 0;
		}
	}
}
