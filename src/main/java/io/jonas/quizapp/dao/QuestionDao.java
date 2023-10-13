package io.jonas.quizapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.jonas.quizapp.beans.Question;

public class QuestionDao {

	private Connection connect = null;
	private Statement stm = null;
	private PreparedStatement pstm = null;
	private int result;
	private ResultSet rs;

	public void createQuestionTable() {
		String sql = "create table `question_tbl`( `id` int(20) not null AUTO_INCREMENT,"
				+ " `question` varchar(200) default null," + "`option1` varchar(200) default null,"
				+ "`option2` varchar(200) default null," + "`option3` varchar(200) default null,"
				+ "`option4` varchar(200) default null," + "`answer` varchar(200) default null,"
				+ " Primary key(`id`)) engine= InnoDB default char set=utf8mb4 collate=utf8mb4_0900_as_ci;";
		try {
			connect = JDBCUtil.getDbConnection();
			Statement stm = connect.createStatement();
			stm.execute(sql);
			System.out.println("table created");
		} catch (SQLSyntaxErrorException e) {
			// throw new SQLSyntaxErrorException("Table 'question_tbl' already exists");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JDBCUtil.closeResources(stm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// CRUD- 1->create question
	public int createQuestion(Question question) {

		String sql = "INSERT INTO `question_tbl` (question,option1,option2,option3,option4,answer)  VALUES "
				+ "(?,?,?,?,?,?)";

		try {
			connect = JDBCUtil.getDbConnection();

			pstm = connect.prepareStatement(sql);
			pstm.setString(1, question.getQuestion());
			pstm.setString(2, question.getOption1());
			pstm.setString(3, question.getOption2());
			pstm.setString(4, question.getOption3());
			pstm.setString(5, question.getOption4());
			pstm.setString(6, question.getAnswer());
			result = pstm.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JDBCUtil.closeResources(pstm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;

	}
	// CRUD- 1->retrieve all questions

	public List<Map<String, ?>> getQuestions() {
		String sql = "SELECT question,option1,option2,option3,option4  FROM `question_tbl`";
		List<Map<String, ?>> questionList = new ArrayList<>();
		try {
			connect = JDBCUtil.getDbConnection();
			pstm = connect.prepareStatement(sql);
			rs = pstm.executeQuery();

			ResultSetMetaData metaData = rs.getMetaData();
			int columns = metaData.getColumnCount();

			while (rs.next()) {
				// linked hashmap keeps order of insertion
				Map<String, Object> row = new LinkedHashMap<>();
				for (int i = 1; i <= columns; i++) {
					row.put(metaData.getColumnLabel(i).toUpperCase(), rs.getObject(i));
				}
				questionList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JDBCUtil.closeResources(rs, pstm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return questionList;
	}

	public List<Question> findQuestions() {
		Question question = null;
		List<Question> questions = new ArrayList<>();

		try {
			String sql = "SELECT * FROM question_tbl";
			connect = JDBCUtil.getDbConnection();
			stm = connect.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				question = new Question();
				question.setId(rs.getInt("id"));
				question.setQuestion(rs.getString("question"));
				question.setOption1(rs.getString("option1"));
				question.setOption2(rs.getString("option2"));
				question.setOption3(rs.getString("option3"));
				question.setOption4(rs.getString("option4"));
				question.setAnswer(rs.getString("answer"));
				questions.add(question);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JDBCUtil.closeResources(rs, pstm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return questions;
	}

	// get question by Id
	public Question getQuestion(int id) {
		Question question = new Question();
		String sql = "SELECT id,question,option1,option2,option3,option4  FROM `question_tbl` where id=?";
		try {
			connect = JDBCUtil.getDbConnection();
			pstm = connect.prepareStatement(sql);
			if (pstm != null) {
				pstm.setInt(1, id);
				rs = pstm.executeQuery();

			}
			if (rs != null) {
				if (rs.next()) {
					System.out.println("result not null");
					question.setId(rs.getInt("id"));
					question.setQuestion(rs.getString(2));
					question.setOption1(rs.getString(3));
					question.setOption2(rs.getString(4));
					question.setOption3(rs.getString(5));
					question.setAnswer(rs.getString(6));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JDBCUtil.closeResources(rs, pstm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return question;
	}

	// update question
	public String updateQuestion(Question question) {
		String modStatus = null;
		String sql = "Update question_tbl set question=? ,option1=? ,option2=? ,option3=? ,option4=? ,answer=? where id=?";

		try {
			connect = JDBCUtil.getDbConnection();
			pstm = connect.prepareStatement(sql);
			pstm.setString(1, question.getQuestion());
			pstm.setString(2, question.getOption1());
			pstm.setString(3, question.getOption2());
			pstm.setString(4, question.getOption3());
			pstm.setString(5, question.getOption4());
			pstm.setString(6, question.getAnswer());
			pstm.setInt(7, question.getId());
			boolean status = pstm.execute();
			if (status == true) {
				rs = pstm.getResultSet();
				System.out.println(rs);
				modStatus = "sucess";
			} else {
				int rowsAffected = pstm.getUpdateCount();
				if (rowsAffected > 0) {
					modStatus = "sucess";
				} else {
					modStatus = "failed";
					;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JDBCUtil.closeResources(rs, pstm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return modStatus;
	}

	// delete question by id
	public String deleteById(int id) {
		String deleteStatus = null;

		String sql = "DELETE FROM `question_tbl` where id=?";
		try {
			connect = JDBCUtil.getDbConnection();
			pstm = connect.prepareStatement(sql);
			pstm.setInt(1, id);
			int row = pstm.executeUpdate();
			if (row == 1) {
				deleteStatus = "sucess";
			} else {
				deleteStatus = "failed";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JDBCUtil.closeResources(rs, pstm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return deleteStatus;

	}

}
