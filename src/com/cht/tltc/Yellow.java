package com.cht.tltc;

public class Yellow extends White {

	String color="yellow";
	public Yellow() {
		System.out.println(this.getClass()+": "+this.color);
		//super.showColor();
		//this.showColor();
	}
	void showColor() {
		
		System.out.println(this.getClass()+": "+this.color);
	}
}
