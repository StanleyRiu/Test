package com.cht.tltc;

public class Red extends Yellow {

	String color="red";
	public Red() {
		System.out.println(this.getClass()+": "+this.color);
		super.showColor();
		//this.showColor();
	}
	void showColor() {
		
		System.out.println(this.getClass()+": "+this.color);
	}
}
