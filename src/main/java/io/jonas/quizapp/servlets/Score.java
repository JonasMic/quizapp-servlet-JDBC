package io.jonas.quizapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jonas.quizapp.beans.Question;
import io.jonas.quizapp.dao.QuestionDao;

@WebServlet("/score")
public class Score extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QuestionDao questionDao = new QuestionDao();
		List<Question> questions = questionDao.findQuestions();
		List<String> userAnswers = new ArrayList<>();
		int score = 0;
//		Enumeration<String> en = req.getParameterNames();
//		System.out.println(en);
//		while(en.hasMoreElements()) {
//			System.out.println("the vlues of en are: "+en.nextElement());
//			String questionId =en.nextElement();
//			String value=req.getParameter(questionId);
//			System.out.println("given answers "+value);
//			
//		}

		questions.stream().forEach(q -> {
			String answer = req.getParameter(Integer.toString(q.getId()));
			userAnswers.add(answer);
		});

		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getAnswer().equalsIgnoreCase(userAnswers.get(i))) {
				score++;
			}
		}
		req.setAttribute("score", score);
		req.setAttribute("questions", questions);

		req.getRequestDispatcher("./views/resultPage.jsp").forward(req, resp);

	}

}
