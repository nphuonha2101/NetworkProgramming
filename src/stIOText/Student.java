package stIOText;

import java.util.List;

public class Student {
	private int id;
	private String name;
	private int year;
	private List<Double> grades;
	/**
	 * @param id
	 * @param name
	 * @param year
	 * @param grades
	 */
	public Student(int id, String name, int year, List<Double> grades) {
		super();
		this.id = id;
		this.name = name;
		this.year = year;
		this.grades = grades;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public List<Double> getGrades() {
		return grades;
	}
	public void setGrades(List<Double> grades) {
		this.grades = grades;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "stId: " + this.id + ", name: " + this.name + ", year: " + this.year + ", grades: " + this.grades.toString();
	}
	
}
