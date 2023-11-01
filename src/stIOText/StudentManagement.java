package stIOText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class StudentManagement {
	private List<Student> stList;

	/**
	 * @param stList
	 */
	public StudentManagement() {
		super();
		this.stList = new ArrayList<Student>();
	}

	/**
	 * @param stList
	 */
	public StudentManagement(List<Student> stList) {
		super();
		this.stList = stList;
	}
	
	public void loadStudent(String stFilePath, String gradeFilePath) throws IOException {

		List<String> stRawDatas = IO.loadData(stFilePath);
		List<String> gradeRawDatas = IO.loadData(gradeFilePath);

		
		for (String stRaw : stRawDatas) {
			String[] stDataArr = stRaw.split("\t");
			List<Double> grades = new ArrayList<Double>();
			Student st = new Student(Integer.parseInt(stDataArr[0]), stDataArr[1], Integer.parseInt(stDataArr[2]), grades);
			for (String gradeRaw : gradeRawDatas) {
				String[] gradeDataArr = gradeRaw.split("\t");
				if (Integer.parseInt(gradeDataArr[0]) == st.getId()) {
					for (int i = 1; i < gradeDataArr.length; i++) {
						double grade = Double.parseDouble(gradeDataArr[i]);
	
						grades.add(grade);
					}
				}
			}
			this.stList.add(st);
		}
	}
	
	public void writeStudentData(String filePath) throws IOException {
		IO.writeData(filePath, stList);
	}
	
	
	public double getGPA(int stID) {
		double result = 0;
		for (Student student : stList) {
			if (student.getId() == stID) {
				for (double grade : student.getGrades()) {
					result += grade;
				}
				result /= student.getGrades().size();
			}
			
		}
		
		return result;
	}
	
	
	public List<Student> getStList() {
		return stList;
	}

	public void setStList(List<Student> stList) {
		this.stList = stList;
	}
	

	public static void main(String[] args) throws IOException {
		StudentManagement stManagement = new StudentManagement();
		stManagement.loadStudent("C:\\Users\\Admin\\Desktop\\st.txt", "C:\\Users\\Admin\\Desktop\\grade.txt");
		System.out.println(stManagement.getStList());
		
		stManagement.writeStudentData("C:\\Users\\Admin\\Desktop\\stExport.txt");
		
		System.out.println(stManagement.getGPA(1));
	}
	
	
}
