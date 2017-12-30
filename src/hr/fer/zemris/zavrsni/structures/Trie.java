package hr.fer.zemris.zavrsni.structures;

import java.util.*;

public interface Trie {
	
	public boolean find(String object);
	public void add(String object);
	public int update(String oldObject, String newObject);
	public boolean delete(String object);
	public List<String> getFromInterval(String object, String object2);
	
}
