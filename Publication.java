
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;



//@authors Ya Bois Garrett O'Dell and Michael Bryant

public class Publication {
	private static int symCount = 0;
	public static void main(String[] args) throws IOException {
		String fileToRead = "ProjectConformantBibFile (Updated).bib"; //file to be read
		String outputFile = "ProjectConformantBibFile (Finished).bib"; // 
		String alphaFile = "ProjectConformantBibFile (Sorted).bib";
		FileWriter writer2 = new FileWriter(alphaFile);
		FileFormatError.main(args);
		EntryManagement entryAVLTree = new EntryManagement(); // new AVLTree holding objects of Entry

		Scanner scan1 = new Scanner(System.in); //used for IDE
		System.out.println("----------------------------------" +
				"\nPlease enter one of the following: \n" +
				"\tGUI or (U) \n" +
				"\tTerminal interface or (NU)" +
				"----------------------------------");
		String input= scan1.nextLine();
		input.toLowerCase();
		switch(input) {
		case "u":
			//launch GUI
			break;
		case "nu":
			boolean test = true;
			while(test)
			{
				System.out.println("----------------------------------" +
						"\nPlease enter one of the following: \n" +
						"\tName or (N) \n" +
						"\tTitle or (T) \n" +
						"\tYear or (Y) \n" +
						"\tQuit or (X) \n" +   
						"----------------------------------");
				String input1 = scan1.nextLine();
				input1 = input1.toLowerCase();
				if(input1.contains("name") || input1.contains("n"))
				{
					entryAVLTree = new EntryManagement(new NameComparator());
					test = false;
				}
				else if(input1.contains("title") || input1.trim().contentEquals("t"))
				{
					entryAVLTree = new EntryManagement(new TitleComparator());
					test = false;
				}
				else if(input1.contains("year") || input1.trim().contentEquals("y"))
				{
					entryAVLTree = new EntryManagement(new YearComparator());
					test = false;
				}
				else if(input1.contains("quit") || input1.trim().contentEquals("x"))
				{
					System.exit(0);
				}
				else {
					System.out.println("Invalid input!");

				}
			}
			try {
				LineReader reader = new LineReader(fileToRead); // Initializes LineReader
				FileWriter writer = new FileWriter(outputFile); // Initializes FileWriter
				while(reader.hasNext())
				{
					String line = reader.next();
					line = reader.next();
					if(!line.isEmpty() && line.charAt(0) == '@')
					{
						Entry ex = new Entry();
						Pair pair = new Pair("name",line);
						ex.nameRevised = handleLine(line);
						//System.out.println(ex.nameRevised);
						ex.name = line;
						ex.add(pair);
						line = reader.next();
						while(!line.isEmpty() && line.charAt(0) != '}')
						{
							if(line.contains(" = "))
							{
								/*if(occurence(line, '{') != occurence(line, '}'))
								{
									line = getNextLine(reader, line);
								}*/
								String str[] = line.split(" = ");
								Pair pair2 = new Pair(str[0].trim(),str[1].trim());
								ex.add(pair2);
								ex.fieldCount ++;
								if(str[1].length() - 1 > ex.longestLineLength)
								{
									ex.longestLineLength = str[1].length();
									ex.longestLine = str[0].trim();
								}
								if(str[0].contains("author")) 
								{
									String authorLine[] = str[1].split(" and ");
									ex.authorCount = authorLine.length;
								}
								if(str[0].contains("title"))
								{
									ex.title = str[1];
								}
								if(str[0].contains("year"))
								{
									int year = Integer.parseInt(str[1].replace('{', ' ').replace('}', ' ').replace(',', ' ').trim());
									ex.year  = year;
								}

							}
							line = reader.next();
						}
						Pair p = new Pair("count", ex.getValues());
						ex.add(p);

						writer.write(ex.name + "\n" + ex.toString());
						entryAVLTree.add(ex);
					}
				}
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			writer2.write(entryAVLTree.toWrite().toString());

			/*for (int i = 0; i < entryAVLTree.count; i++) 
			{
				writer2.write(entryAVLTree.allEntries[i].name + "\n" + entryAVLTree.allEntries[i].toString());
			}*/
			writer2.close();
			boolean condition = true;
			//Scanner scan = new Scanner(System.console().reader());  //used for cmd
			Scanner scan = new Scanner(System.in); //used for IDE
			while(condition)
			{
				System.out.println("----------------------------------" +
						"\nPlease enter one of the following: \n" +
						"\tSearch or (S) \n" +
						"\tDelete or (D) \n" +
						"\tPrint or (W) \n" +
						"\tMerge or (M) \n" + 
						"\tPurge or (P) \n" +
						"\tQuit or  (E) \n" +  
						"----------------------------------");
				String input1 = scan.nextLine();
				input1 = input1.toLowerCase();
				if(input1.contains("search") || input1.contains("s"))
				{
					input1 = "s";
				}
				else if(input1.contains("delete") || input1.trim().contentEquals("d"))
				{
					input1 = "d";
				}
				else if(input1.contains("print") || input1.trim().contentEquals("w"))
				{
					input1 = "w";
				}
				else if(input1.contains("merge") || input1.trim().contentEquals("m"))
				{
					input1 = "m";
				}
				else if(input.contains("purge") || input.trim().contentEquals("p"))
				{
					input = "p";
				}
				else if(input.contains("quit") || input.contains("exit") || input.trim().contentEquals("e") || input.trim().contentEquals("q"))
				{
					input = "e";
				}
				switch (input)
				{
				case "s":
					System.out.println("Entry Name: ");
					String entryToSearch = scan.nextLine();
					if(entryAVLTree.search(entryToSearch))
					{
						System.out.println(entryToSearch + " found!");
					}
					else {
						System.out.println(entryToSearch + " not found");
					}
					break;

				case "d":
					System.out.println("Entry Name: ");
					String entryToDelete = scan.nextLine();
					if(entryAVLTree.search(entryToDelete)) {
						System.out.println("Are you sure you want to delete \""+entryToDelete+"\" (y/n)?");
						if(scan.nextLine().toLowerCase().contains("y"))
						{
							entryAVLTree.entryDeleter(entryToDelete);
							System.out.println("The entry \""+ entryToDelete + "\" deleted!");
						}
						else {
							System.out.println("The entry \""+ entryToDelete + "\" not deleted!");
						}
					}
					else {
						System.out.println(entryToDelete + " not found!");
					}

					break;

				case "w":
					System.out.println("Enter a file name: ");
					String fileName = scan.nextLine();
					if(fileName.contentEquals(""))
					{
						entryAVLTree.print();
						break;
					}

					if(fileName.length() > 30)
					{
						System.out.println("Please create a file name that is shorter.");
						break;
					}
					File file = new File(fileName);
					if(file.isFile())
					{
						System.out.println("File already exists. Would you like to overwrite?");
						if(scan.nextLine().toLowerCase().contains("y"))
						{
							FileWriter writer = new FileWriter(fileName);
							writer.write(entryAVLTree.toWrite().toString());
							writer.close();
						}
					}
					else
					{
						FileWriter writer = new FileWriter(fileName);

						writer.write(entryAVLTree.toWrite().toString());
						writer.close();

						writer.close();
					}
					java.awt.Desktop.getDesktop().open(file);
					System.out.println("Wrote to file named: \"" +fileName +"\"");
					break;

				case "e":
					System.out.println("Goodbye!");
					condition = false;
					break;

				case "p":
					LineReader reader1 = null;
					Scanner scanner1 = new Scanner (System.console().reader());
					System.out.println("Enter the file you wish to purge: ");
					String purgefile = scanner1.nextLine();
					File inputFile1 = null;
					inputFile1 = new File(purgefile);
					if (inputFile1.exists())
					{
						reader1 = new LineReader(purgefile);
						purgeFile(reader1, entryAVLTree);
						rewrite(entryAVLTree, fileToRead);
						System.out.println("file " + inputFile1.getName()+ " found and has been purged.");
						break;
					}
					else
					{
						System.out.println("File not found!");
					}
					break;

				case"m":

					LineReader reader = null;
					Scanner scanner = new Scanner (System.console().reader());
					System.out.println("Enter the file you wish to merge: ");
					String mergefile = scanner.nextLine();
					File inputFile = null;
					inputFile = new File(mergefile);
					if (inputFile.exists())
					{
						reader = new LineReader(mergefile);
						mergeFile(reader, entryAVLTree);
						rewrite(entryAVLTree, fileToRead);
						System.out.println("file " + inputFile.getName()+ " found and has been merged.");
						break;

					}
					else
					{
						System.out.println("File not found!");
					}
					break;

				default:
					System.out.println("------------------------\nPlease use a valid input");
					break;
				}

				break;

			}
			}
		}




		private static void purgeFile(LineReader reader1, EntryManagement entryAVLTree) {

			String line = reader1.next();
			while(reader1.hasNext()) {
				if(!line.isEmpty() && line.charAt(0) == '@')
				{
					entryAVLTree.entryDeleter(handleLine(line));
				}
				line = reader1.next();
			}

		}

		private static void mergeFile(LineReader reader, EntryManagement entryArray) {
			String line = reader.next();
			while(reader.hasNext())
			{
				line = reader.next();
				System.out.println(line);
				if(!line.isEmpty() && line.charAt(0) == '@')
				{
					Entry ex = new Entry();
					Pair pair = new Pair("name",line);
					ex.nameRevised = handleLine(line);
					//System.out.println(ex.nameRevised);
					ex.name = line;
					ex.add(pair);
					line = reader.next();
					while(!line.isEmpty() && line.charAt(0) != '}')
					{
						if(line.contains(" = "))
						{

							String str[] = line.split(" = ");
							Pair pair2 = new Pair(str[0].trim(),str[1].trim());
							ex.add(pair2);
							ex.fieldCount ++;
							if(str[1].length() - 1 > ex.longestLineLength)
							{
								ex.longestLineLength = str[1].length();
								ex.longestLine = str[0].trim();
							}
							if(str[0].contains("author")) 
							{
								String authorLine[] = str[1].split(" and ");
								ex.authorCount = authorLine.length;
							}
							if(str[0].contains("title"))
							{
								ex.title = str[1];
							}
							if(str[0].contains("year"))
							{
								int year = Integer.parseInt(str[1].replace("\\{", "").replace("\\}", ""));
								ex.year  = year;
							}
						}
						line = reader.next();
					}
					Pair p = new Pair("count", ex.getValues());
					ex.add(p);
					entryArray.add(ex);
				}

			}
		}

		private static String handleLine(String line) {
			String nameRevised = "";
			String str[] = line.split("\\{");
			nameRevised = str[1].replace(",","").trim();
			return nameRevised;
		}

		static class NameComparator implements Comparator<Entry>
		{

			@Override
			public int compare(Entry o1, Entry o2) {
				// TODO Auto-generated method stub
				return o1.nameRevised.compareTo(o2.nameRevised);
			}

		}

		static class TitleComparator implements Comparator<Entry>
		{


			@Override
			public int compare(Entry o1, Entry o2) {
				// TODO Auto-generated method stub
				return o1.title.compareTo(o2.title);
			}

		}

		static class YearComparator implements Comparator <Entry>
		{

			@Override
			public int compare(Entry o1, Entry o2) {
				// TODO Auto-generated method stub
				if(o1.year < o2.year) {
					return -1;
				}
				else if(o1.year > o2.year) {
					return 1;
				}

				return 0;
			}

		}

		public static String getNextLine(LineReader reader, String line)
		{
			line = line + reader.next();
			return line;
		}

		public static Object occurence(String line, char c)
		{
			for (int i = 0; i < line.length(); i++) {
				if(line.charAt(i) == c)
				{
					symCount++;
				}
			}
			return symCount;
		}

		public static void rewrite(EntryManagement entries, String fileToWrite) throws IOException {

			//File file = new File(fileToWrite);
			FileWriter writer = new FileWriter(fileToWrite);
			
			writer.close();

		}
	}