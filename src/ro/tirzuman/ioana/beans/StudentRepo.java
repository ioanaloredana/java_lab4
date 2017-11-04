package ro.tirzuman.ioana.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="students")
@ApplicationScoped
public class StudentRepo {
	private List<Student> students;

	@PostConstruct
	public void init() {
		students = new ArrayList<Student>();
	}
	
	public List<Student> getList() {
		return students;
	}

	public void addStudent(Student student) {
		students.add(student);
	}
}
