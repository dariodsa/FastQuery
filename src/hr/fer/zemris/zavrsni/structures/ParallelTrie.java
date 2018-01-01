package hr.fer.zemris.zavrsni.structures;
import java.util.*;

public class ParallelTrie {

	public Node poc = new Node((char)0,null);
	private final int addOn = 3;
	
	public ParallelTrie()
	{
		//poc.setUp();
	}
	
	public boolean find(String object) 
	{
		object = normalize(object);
		
		int len = 7;//object.length();
		Node rod = poc;
		for(int i=0;i<len;++i)
		{
			Node newRod = rod.kids[(int)(object.charAt(i)-'0')];
			if(newRod==null)return false;
			rod = newRod;
		}
		if(rod.finish<=0)
			return false;
		return true;
	}
	public Node add(String object, Node poc, int pos)
	{
		
		object = normalize(object);
		int len = 7;//object.length();
		
		Node rod = poc;
		for(int i=pos;i<len;++i)
		{
			rod.subTreeSize++;
			Node newRod = null;
			int br = (int)object.charAt(i)-'0';
			if(rod.isSetUp(br))
				newRod = rod.kids[br];
			else
			{
				rod.kids[br] = new Node((char)('0'+br),rod);
				newRod =  rod.kids[br];
			}
			
			rod = newRod;
		}
		rod.subTreeSize++;
		rod.finish++;
		return rod;
	}
	
	public Node add(String object)
	{
		return add(object,this.poc,0);
	}
	public void fastAdd(String object1, String object2)
	{
		object1 = normalize(object1);
		object2 = normalize(object2);
		int len = 7;//object1.length();
		Node rod = poc;
		for(int i=0;i<len;++i)
		{
			if(object1.charAt(i)!=object2.charAt(i))
			{
				Node rod2 = rod;
				Node rod3 = rod;
				add(object1,rod3,i);
				add(object2,rod3,i);
				return;
			}
			Node newRod = null;
			int br = (int)object1.charAt(i)-'0';
			if(rod.isSetUp(br))
				newRod = rod.kids[br];
			else
			{
				rod.kids[br] = new Node((char)('0'+br),rod);
				newRod =  rod.kids[br];
			}
			
			rod = newRod;
		}
		rod.finish+=2;
	}
	
	public Node update(String oldObject, String newObject, Node N) 
	{
		oldObject = normalize(oldObject);
		newObject = normalize(newObject);
		/*if(find(oldObject)==false)
		{
			System.out.println("SRANJE "+oldObject +" nema u bazi.");
		}*/
		int len =7;// oldObject.length();
		int jednaki = 0;
		for(int i=0;i<len;++i)
		{
			if(oldObject.charAt(i)!=newObject.charAt(i))
			{
				jednaki = i;
				break;
			}
		}
		int br = len - jednaki;
		N.finish--;
		while(br-->0)
		{
			N.subTreeSize--;
			N = N.parent;
		}
		
		return add(newObject,N,jednaki);
	}

	
	public boolean delete(String object) 
	{
		return false;
	}

	
	public List<Double> getFromInterval(Double min1, Double max1) {
		List<Double> ans = new ArrayList<>();
		
		String min = normalize(String.format ("%.4f",min1));
		String max = normalize(String.format ("%.4f",max1));
		
		//System.out.println("Request: " + min +" <==> " + max);
		int p = 0;
		Queue<Node> Q = new LinkedList<>();
		Queue<Boolean> firstCheckQ = new LinkedList<>();
		Queue<Boolean> secondCheckQ = new LinkedList<>();
		Queue<Integer> dubinaQ = new LinkedList<>();
		Queue<Double> brojQ = new LinkedList<>();
		Queue<Double> mnoQ = new LinkedList<>();
		
		Q.add(poc);
		firstCheckQ.add(true);
		secondCheckQ.add(true);
		dubinaQ.add(0);
		brojQ.add(0.0);
		mnoQ.add(100.0);
		
		while(!Q.isEmpty())
		{
			++p;
			Node pos = Q.peek();
			boolean firstCheck = firstCheckQ.peek();
			boolean secondCheck = secondCheckQ.peek();
			int dubina = dubinaQ.peek();
			double broj = brojQ.peek();
			double mno = mnoQ.peek();
			
			Q.poll();
			firstCheckQ.poll();
			secondCheckQ.poll();
			dubinaQ.poll();
			brojQ.poll();
			mnoQ.poll();
			if(pos.finish>0)
				for(int i=0;i<pos.finish;++i)
					ans.add(broj);
			if(dubina == 7)continue;
			int mini = firstCheck ? min.charAt(dubina) - '0' : 0;
			int maxi = secondCheck ? max.charAt(dubina) - '0' +1: 10;
			for(int i=mini;i<maxi;++i)
			{
				if(pos.kids[i]==null)continue;
				if(pos.kids[i].subTreeSize==0)continue;
				
				boolean t1 = firstCheck;
				boolean t2 = secondCheck;
				
				if(!t1 && !t2)
				{
					Q.add(pos.kids[i]);
					firstCheckQ.add(false);
					secondCheckQ.add(false);
					dubinaQ.add(dubina+1);
					brojQ.add(broj + mno*((double)(pos.kids[i].znak-'0')));
					
					mnoQ.add(mno/10.0);
				}
				else if(t1 && !t2)
				{
					if(pos.kids[i].znak>=min.charAt(dubina))
					{
						if(pos.kids[i].znak > min.charAt(dubina))
							t1 = false;
						Q.add(pos.kids[i]);
						firstCheckQ.add(t1);
						secondCheckQ.add(t2);
						dubinaQ.add(dubina+1);
						brojQ.add(broj + mno*((double)(pos.kids[i].znak-'0')));
						mnoQ.add(mno/10.0);
					}
				}
				else if(!t1 && t2)
				{
					if(pos.kids[i].znak<=max.charAt(dubina))
					{
						if(pos.kids[i].znak < max.charAt(dubina))
							t2 = false;
						Q.add(pos.kids[i]);
						firstCheckQ.add(t1);
						secondCheckQ.add(t2);
						dubinaQ.add(dubina+1);
						brojQ.add(broj + mno*((double)(pos.kids[i].znak-'0')));
						mnoQ.add(mno/10.0);
					}
				}
				else if(t1 && t2)
				{
					if(pos.kids[i].znak<=max.charAt(dubina) &&
					   pos.kids[i].znak>=min.charAt(dubina))
					{ 
						if(pos.kids[i].znak > min.charAt(dubina))
							t1 = false;
						if(pos.kids[i].znak < max.charAt(dubina))
							t2 = false;
						//System.out.println("dubina " +dubina+" znak " + pos.kids[i].znak +" "+firstCheck+","+secondCheck);
						Q.add(pos.kids[i]);
						firstCheckQ.add(t1);
						secondCheckQ.add(t2);
						dubinaQ.add(dubina+1);
						brojQ.add(broj + mno*((double)(pos.kids[i].znak-'0')));
						mnoQ.add(mno/10.0);
					}
				}
			}
		}
		//System.out.println(p+" --"+ans.size());
		return ans;
	}
	private String normalize(String input)
	{
		int len = input.length();
		int numOfZeros = 3;
		for(int i=0; i<len; ++i)
		{
			if(input.charAt(i)==',' || input.charAt(i)=='.')
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
			if(input.charAt(i)!=',' && input.charAt(i)!='.')
				res += input.charAt(i);
		}
		return res;
	}
}
