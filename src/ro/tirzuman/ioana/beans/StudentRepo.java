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

@ManagedBean(name = "students")
@ApplicationScoped
public class StudentRepo {

	@PostConstruct
	public void init() {
	}

	public List<Student> getList() {
		return getStudentsFromDB();
	}

	public void addStudent(Student student) {
		addStudentToDB(student);
	}

	private void addStudentToDB(Student student) {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = getConnection();

			stmt = con.prepareStatement("insert into students(name, preferredproject) values(?, ?);", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, student.getName());
			stmt.setString(2, student.getPreferredProject());
			int affectedRows = stmt.executeUpdate();

			long id = -1;
			
			if (affectedRows == 0) {
				System.out.println("Creating student failed, no rows affected.");
			} else {
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						id = generatedKeys.getLong(1);
					} else {
						System.out.println("Creating student failed, no ID obtained.");
					}
				}
			}
			
			if (id != -1) {
				List<String> skills = student.getSkills(); 
				if (skills != null && !skills.isEmpty()) {
					for (String skill : skills) {
						stmt = con.prepareStatement("insert into student_skills(student_id, skill_name) values(?, ?);");
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

	private List<Student> getStudentsFromDB() {
		ResultSet rs = null;
		PreparedStatement stmt = null;
		Connection con = null;
		List<Student> students = new ArrayList<Student>();

		try {
			con = getConnection();
			stmt = con.prepareStatement("select * from students");
			stmt.execute();
			rs = stmt.getResultSet();

			while (rs != null && rs.next()) {
				Student student = new Student();
				student.setName(rs.getString("name"));
				student.setPreferredProject(rs.getString("preferredproject"));
				student.setAllocatedProject(rs.getString("allocatedproject"));
				students.add(student);
			}
			stmt.close();
			if (!students.isEmpty()) {
				stmt = con.prepareStatement("select * from student_skills ss, students s where s.id = ss.student_id;");
				stmt.execute();
				rs = stmt.getResultSet();
				
				while (rs != null && rs.next()) {
					String studentName = rs.getString("name");
					String skill = rs.getString("skill_name");
					addSkillToStudent(students, studentName, skill);
				}
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	private void addSkillToStudent(List<Student> students, String studentName, String skill) {
		if (students != null && !students.isEmpty()) {
			for (Student s : students) {
				if (s != null) {
					if (s.getName().equalsIgnoreCase(studentName)) {
						s.getSkills().add(skill);
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
