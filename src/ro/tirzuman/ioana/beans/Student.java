package ro.tirzuman.ioana.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "student")
@RequestScoped
public class Student {
	private String name;
	private List<String> skills;
	private String preferredProject;
	private String allocatedProject;

	@PostConstruct
	public void init() {
		skills = new ArrayList<String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public String getPreferredProject() {
		return preferredProject;
	}

	public void setPreferredProject(String preferredProject) {
		this.preferredProject = preferredProject;
	}

	public String getAllocatedProject() {
		return allocatedProject;
	}

	public void setAllocatedProject(String allocatedProject) {
		this.allocatedProject = allocatedProject;
	}
}
