package ro.tirzuman.ioana.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="projects")
@ApplicationScoped
public class ProjectRepo {
	private List<Project> projects;
	
	@PostConstruct
	public void init() {
		projects = new ArrayList<Project>();
		Project p = new Project();
		p.setName("project x");
		projects.add(p);
		p = new Project();
		p.setName("tesla");
		projects.add(p);
		p = new Project();
		p.setName("farm");
		projects.add(p);
	}

	public List<Project> getList() {
		return projects;
	}

	public void addProject(Project project) {
		projects.add(project);
	}
}
