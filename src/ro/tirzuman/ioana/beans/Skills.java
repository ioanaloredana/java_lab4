package ro.tirzuman.ioana.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "skills")
public class Skills {
	private List<String> skillsList;

	@PostConstruct
	public void init() {
		skillsList = new ArrayList<String>();
		skillsList.add("java");
		skillsList.add("html");
		skillsList.add("jsp");
		skillsList.add("jsf");
		skillsList.add("jstl");
		skillsList.add("js");
	}

	public List<String> getSkillsList() {
		return skillsList;
	}
}
