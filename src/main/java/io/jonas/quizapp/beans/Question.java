package io.jonas.quizapp.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Question {

	private int id;
	@NonNull
	private String question;
	@NonNull
	private String option1;
	@NonNull
	private String option2;
	@NonNull
	private String option3;
	@NonNull
	private String option4;
	@NonNull
	private String answer;

}
