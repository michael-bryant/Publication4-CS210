
public class Entry implements Comparable<Entry> {		//Extends comparable, overwrite compare method

	
	Pair [] fields;
	int count;
	int longestLineLength = 0;
	int fieldCount = 0;
	int authorCount = 0;
	String longestLine;
	String name = "";
	String order;
	String nameRevised;
	String title;
	int year;
	
	
	public Entry()
	{
		fields = new Pair[22];
	}

	public Entry(String order) {
		this.order = order.substring(0,order.length()-1).substring(order.indexOf('{')+1);
	}
	public void add(Pair p)
	{
		if(p == null)
		{
			return;
		}
		fields[count] = p;
		count++;
	}

	public Pair returnPair() 
	{
		Pair s = null;

		for(int i = 0; i < fields.length; i++)
		{
			s = fields[i];
		}
		return s;
	}

	public int getFieldCount()
	{
		return this.fieldCount;
	}

	@Override
	public String toString()
	{
		String str = "";
		for (int i = 1; i < count; i++) {
			str = str + "	" + fields[i].type + " = " + fields[i].value + "\n";
		}
		return str;
	}

	public String getValues()
	{
		String str = "{Fields: " + (this.fieldCount) + ", Authors: " + authorCount + ", Longest Field: " + longestLine + ", Length of Longest Field: " + longestLineLength + "}\n}\n";
		return str;
	}
	
	@Override
	public int compareTo(Entry entry) {
		// TODO Auto-generated method stub
		return this.nameRevised.toLowerCase().compareTo(entry.nameRevised.toLowerCase());
		
	}
	
	
	
	
	
	
	
	
	
	
	
}