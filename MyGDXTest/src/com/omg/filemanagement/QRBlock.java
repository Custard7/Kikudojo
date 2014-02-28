package com.omg.filemanagement;

import com.omg.filemanagement.QRSet.QROptions;
import com.omg.sfx.LucidSound;

public class QRBlock {
	
	String soundPath;
	LucidSound questionSound;
	
	String question;
	String A;
	String B;
	String C;
	
	QROptions correct;
	
	String hint;
	
	public void QRBlock() {}
	
	
	/**
	 * @return the questionSound
	 */
	public LucidSound getQuestionSound() {
		return questionSound;
	}


	/**
	 * @param questionSound the questionSound to set
	 */
	public void setQuestionSound(LucidSound questionSound) {
		this.questionSound = questionSound;
	}


	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}


	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}


	/**
	 * @return the a
	 */
	public String getA() {
		return A;
	}


	/**
	 * @param a the a to set
	 */
	public void setA(String a) {
		A = a;
	}


	/**
	 * @return the b
	 */
	public String getB() {
		return B;
	}


	/**
	 * @param b the b to set
	 */
	public void setB(String b) {
		B = b;
	}


	/**
	 * @return the c
	 */
	public String getC() {
		return C;
	}


	/**
	 * @param c the c to set
	 */
	public void setC(String c) {
		C = c;
	}


	/**
	 * @return the correct
	 */
	public QROptions getCorrect() {
		return correct;
	}


	/**
	 * @param correct the correct to set
	 */
	public void setCorrect(QROptions correct) {
		this.correct = correct;
	}


	/**
	 * @return the hint
	 */
	public String getHint() {
		return hint;
	}


	/**
	 * @param hint the hint to set
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}


	public void loadBlock(String soundPath, String question, String A, String B, String C, String correct, String hint){
		this.soundPath = soundPath;
		this.question = question;
		this.A = A;
		this.B = B;
		this.C = C;
		
		this.correct = Enum.valueOf(QROptions.class, correct);
		
		this.hint = hint;
		
		
	}
	
	public void loadSound(String setName) {
		
		questionSound = new LucidSound("questions/" + setName + "/" + soundPath); 
	}
	
	
	
	
	
}