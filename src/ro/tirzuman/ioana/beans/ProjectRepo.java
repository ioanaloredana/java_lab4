package ro.tirzuman.ioana.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "projects")
@ApplicationScoped
public class ProjectRepo {

	@PostConstruct
	public void init() {
	}

	public List<Project> getList() {
		return getProjectsFromDB();
	}

	public void addProject(Project project) {
		addProjectToDB(project);
	}

	private void addProjectToDB(Project project) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getConnection();

			stmt = con.prepareStatement("insert into projects(name, quota) values(?, ?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, project.getName());
			stmt.setInt(2, project.getQuota());
			int affectedRows = stmt.executeUpdate();

			long id = -1;

			if (affectedRows == 0) {
				System.out.println("Creating project failed, no rows affected.");
			} else {
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						id = generatedKeys.getLong(1);
					} else {
						System.out.println("Creating project failed, no ID obtained.");
					}
				}
			}

			if (id != -1) {
				List<String> skills = project.getSkills();
				if (skills != null && !skills.isEmpty()) {
					for (String skill : skills) {
						stmt = con.prepareStatement("insert into project_skills(project_id, skill_name) values(?, ?);");
						stmt.setLong(1, id);
						stmt.setString(2, skill);
						stmt.executeUpdate();
					}
				}
			}

			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	private List<Project> getProjectsFromDB() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		List<Project> projects = new ArrayList<Project>();

		try {
			con = getConnection();
			stmt = con.prepareStatement("select * from projects");
			stmt.execute();
			rs = stmt.getResultSet();

			while (rs != null && rs.next()) {
				Project project = new Project();
				project.setName(rs.getString("name"));
				project.setQuota(rs.getInt("quota"));
				projects.add(project);
			}
			stmt.close();
			if (!projects.isEmpty()) {
				stmt = con.prepareStatement("select * from project_skills ps, projects p where p.id = ps.project_id;");
				stmt.execute();
				rs = stmt.getResultSet();

				while (rs != null && rs.next()) {
					String projectName = rs.getString("name");
					String skill = rs.getString("skill_name");
					addSkillToProject(projects, projectName, skill);
				}
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}

	private void addSkillToProject(List<Project> projects, String projectName, String skill) {
		if (projects != null && !projects.isEmpty()) {
			for (Project p : projects) {
				if (p != null) {
					if (p.getName().equalsIgnoreCase(projectName)) {
						p.getSkills().add(skill);
					}
				}
			}
		}
	}

	private Connection getConnection() throws SQLException {
		Connection con = null;
		String url = "jdbc:postgresql://localhost/postgres";
		String user = "postgres";
		String password = "postgres";

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		if(con == null) {
			throw new SQLException("Driver now found");
		}
		return con;
	}
}
