package io.jonas.quizapp;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.function.Predicate;

import io.jonas.quizapp.beans.Question;
import io.jonas.quizapp.dao.QuestionDao;


public class App {
	public static void main(String[] args) {
		
		QuestionDao questionDao = new QuestionDao();
		try {
			//questionDao.createQuestionTable();
			Question  question1 = new Question("Which interface restricts duplicate elements?","Set","List","Map","all","Set");
			Question  question2 = new Question("A HashMap allows the existence of:?","null values","one null value","none of thse","all","one null value");
			Question  question3 = new Question("Which is faster and uses less memory?","ListEnumeration","Enumeration","Iterator","ListIterator","Enumeration");
			Question  question4 = new Question("The default capacity of a Vector is:","8","10","16","12","10");
			Question  question5 = new Question("how many methods are there in functional interface in Java 8?","0","1","2","3","1");
			// create question->1 ->C
			//questionDao.createQuestion(question1);
//			questionDao.createQuestion(question2);
//			questionDao.createQuestion(question3);
//			questionDao.createQuestion(question4);
//			questionDao.createQuestion(question5);
			
			// retrieve all question list :Map<String,Object> -R
			System.out.println(questionDao.getQuestions());
			// get question by id-R
			System.out.println(questionDao.getQuestion(2));
			// update question by id ->U
			Question upradtedQuestion = new Question(5,"How many methods are there in marker interface ? ","0","1","2","3","0");
			System.out.println(questionDao.updateQuestion( upradtedQuestion));
			// delete question by id
			System.out.println(questionDao.deleteById(5));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
