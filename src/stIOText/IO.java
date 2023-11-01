package stIOText;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class IO {

	public static List<String> loadData(String filePath) throws IOException {

		FileReader reader = new FileReader(filePath);
		BufferedReader buffReader = new BufferedReader(reader);

		List<String> result = new ArrayList<>();
		
		String data;
		while ((data = buffReader.readLine()) != null) {

			result.add(data);
		}
		
		buffReader.close();
		return result;
	}
	
	public static void writeData(String filePath, List<Student> stList) throws IOException {
		FileWriter writer = new FileWriter(filePath);
		PrintWriter pWriter = new PrintWriter(writer);
		
		for (Student st: stList) {
			List<Double> grades = st.getGrades();
			for (Double grade : grades) {
				pWriter.println(st.getId() + "\t" + st.getName() + "\t" + st.getYear() + "\t" + grade);
			}
			
		}
		
		pWriter.close();
		
	}
 }