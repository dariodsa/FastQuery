package hr.fer.zemris.zavrsni.structures;

import hr.fer.zemris.zavrsni.structures.*;

public class Node
{
	Node parent;
	Node[] kids = new Node[10];
	char znak;
	int finish;
	int subTreeSize;
	public Node(char znak, Node parent)
	{
		this.parent = parent;
		this.znak = znak;
		finish = 0;
		subTreeSize = 0;
		for(int i=0;i<10;++i)kids[i]=null;
	}
	boolean isSetUp(int br)
	{
		return kids[br]!=null;
	}
}
