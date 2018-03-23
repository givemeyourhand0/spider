package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;

public class OutputFile
{
	public static void outputToFile(LinkedList<Calculate> result) throws IOException
	{
		FileOutputStream fs = new FileOutputStream(new File("text.txt"));
		PrintStream ps = new PrintStream(fs);
		
		for(Calculate element : result)
		{
			ps.write((element + "\n").toString().getBytes());
			
		}
		ps.close();
	}
}