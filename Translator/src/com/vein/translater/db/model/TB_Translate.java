package com.vein.translater.db.model;

public class TB_Translate {
	private String input;
	private String tresult;

	public void setInput(String input) {
		this.input = input;
	}

	public void setTresult(String tresult) {
		this.tresult = tresult;
	}

	public String getInput() {
		return input;
	}

	public String getTresult() {
		return tresult;
	}

	public TB_Translate() {
		super();
	}

	public TB_Translate(String input, String tresult) {
		super();
		this.input = input;
		this.tresult = tresult;

	}

}
