package ro.tirzuman.ioana.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "assignments")
@RequestScoped
public class AssignmentLogic {
	@ManagedProperty("#{students}")
	private StudentRepo students;

	@ManagedProperty("#{projects}")
	private ProjectRepo projects;

	public List<Student> getList() {
		return computeAssignments();
	}

	public void setStudents(StudentRepo students) {
		this.students = students;
	}

	public void setProjects(ProjectRepo projects) {
		this.projects = projects;
	}

	private synchronized List<Student> computeAssignments() {
		resetAllocatedProjects();
		List<Student> studentsList = students.getList();
		List<Project> projectsList = projects.getList();
		for (Project p : projectsList) {
			for (int i = 0; i < p.getQuota(); i++) {
				for (Student s : studentsList) {
					if (s.getAllocatedProject() == null && isMatch(p, s)) {
						s.setAllocatedProject(p.getName());
					}
				}
			}
		}
		return studentsList;
	}

	private boolean isMatch(Project p, Student s) {
		if (p.getSkills() != null) {
			for (String skill : p.getSkills()) {
				if (s.getSkills().contains(skill)) {
					return true;
				}
			}
		}
		return false;
	}

	private synchronized void resetAllocatedProjects() {
		for (Student s : students.getList()) {
			s.setAllocatedProject(null);
		}
	}
}
