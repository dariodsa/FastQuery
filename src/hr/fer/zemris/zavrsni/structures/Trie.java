package hr.fer.zemris.zavrsni.structures;

import hr.fer.zemris.zavrsni.myobjects._Comparable;

public interface Trie<String> {
	
	public boolean find(String object);
	public void add(String object);
	public int update(String oldObject, String newObject);
	public String[] getFromInterval(String object, String object2);
	public int compareTo(String object);
	
}
