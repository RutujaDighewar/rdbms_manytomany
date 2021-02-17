package com.app.model;

import java.util.List;

public class Product {

	private int id;
	private String name;
	private int price;
	private List<Color> colors;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<Color> getColors() {
		return colors;
	}
	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
	
	
}
