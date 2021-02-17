/**
 * 
 */
package com.app.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.app.model.Color;
import com.app.model.Product;

/**
 * @author Rutuja
 *
 */
public class Product_color {
	
	Scanner sc=new Scanner(System.in);
	private boolean flag=Boolean.FALSE;
	  List<Product> product=new ArrayList<>();
	  private List cart=null;
	  
	public void addColor() throws SQLException {
		
		Connection con=JdbcUtility.getConnection();
		
		System.out.println("How many color want to add?");
		int noOfCol=sc.nextInt();
		for(int i=0;i<noOfCol;i++) {
			Color col=new Color();
			System.out.println("Enter color name");
			col.setCname(sc.next());
			PreparedStatement ps=con.prepareStatement("insert into color(cname) value(?)");
			ps.setString(1, col.getCname());
			ps.executeUpdate();
			flag=Boolean.TRUE;
		}
		if(flag==Boolean.TRUE) {
			System.out.println("Inserted successfully");
		}else {
			System.out.println("Failed");
		}
	}
	
	
	public void displayColor() throws SQLException {
		List<Color> colorlist=new ArrayList<>();
		Connection con=JdbcUtility.getConnection();
		
		PreparedStatement ps=con.prepareStatement("select * from color");
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			Color color=new Color();
			color.setId(rs.getInt(1));
			color.setCname(rs.getString(2));
			colorlist.add(color);
		}
		for (Color color : colorlist) {
		System.out.println(color.getId()+"\t"+color.getCname());	
		}
	}
	
	
	public void addProduct() throws SQLException {
		
		List<Product> products=new ArrayList<>();
		Connection con=JdbcUtility.getConnection();
		System.out.println();
		System.out.println("How many product you want to add?");
		int noOfPro=sc.nextInt();
		for(int i=0;i<noOfPro;i++) {
			Product pro=new Product();
			System.out.println("Enter product name");
			pro.setName(sc.next());
			System.out.println("Enter price");
			pro.setPrice(sc.nextInt());
			System.out.println("------------------------------------------------");
			displayColor();
			System.out.println("------------------------------------------------");
			System.out.println("How many color you want to allocate?");
			int num=sc.nextInt();
			List<Color> allocatedColorList=new ArrayList<>();
			for(int j=0; j<num;j++) {
				Color col=new Color();
				System.out.println("Enter color id");
				col.setId(sc.nextInt());
				allocatedColorList.add(col);
			}
			pro.setColors(allocatedColorList);
			products.add(pro);
		}
		
		PreparedStatement ps=con.prepareStatement("insert into product (name,price) value(?,?)");
		for (Product product : products) {
			ps.setString(1, product.getName());
			ps.setInt(2, product.getPrice());
			int result =ps.executeUpdate();
			if(result>0) {
				PreparedStatement ps1=con.prepareStatement("select max(id) from product");
				ResultSet rs=ps1.executeQuery();
				if(rs.next()) {
					product.setId(rs.getInt(1));
					PreparedStatement ps2=con.prepareStatement("insert into product_color(pid,cid) values(?,?)");
					for(Color color : product.getColors()) {
						ps2.setInt(1, product.getId());
						ps2.setInt(2, color.getId());
						ps2.executeUpdate();
						
						flag=Boolean.TRUE;
					}
					if(flag==Boolean.TRUE) {
						System.out.println("Success");
					}else {
						System.out.println("Failed");
					}
					
				}
						
			}
		}
	}
	
	public void select() throws SQLException {
		Connection con=JdbcUtility.getConnection();
	 
	   PreparedStatement ps=con.prepareStatement("select * from product");
	    ResultSet rs=ps.executeQuery();
	    while(rs.next()) {
	    	Product pro=new Product();
	    	pro.setId(rs.getInt(1));
	    	pro.setName(rs.getString(2));
	    	pro.setPrice(rs.getInt(3));
	    	product.add(pro);
	    }
	    
	    for (Product product2 : product) {
	    	List<Color> colors=new ArrayList<>();
			PreparedStatement ps1=con.prepareStatement("select c.id, c.cname from product p inner join color c inner join product_color pc on p.id=pc.pid and c.id=pc.cid where pc.pid="+product2.getId());
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()) {
				Color col=new Color();
				col.setId(rs1.getInt(1));
				col.setCname(rs1.getString(2));
				colors.add(col);
			}
			product2.setColors(colors);
			
		}
	    System.out.println();
	    for(Product pro : product) {
	    	System.out.println(pro.getId()+"\t"+pro.getName()+"\t"+pro.getPrice());
	    	for(Color c : pro.getColors()) {
	    		System.out.println("\t\t\t"+c.getId()+"\t"+c.getCname());
	    	}
	    }
	}
	
	public void addToCart() {
		
		cart=new ArrayList<>();
		System.out.println();
		System.out.println("How many products want to buy");
		int num=sc.nextInt();
		for(int i=0;i<num;i++) {
			System.out.println("Enter product id to buy");
			int id=sc.nextInt();
			for(Product p : product) {
				if(id==p.getId()) {
					cart.add(p);
				}
			}
		}
	}
	
	
	public void createBill() {
		int findGrandTotal=0;
		System.out.println("-------------------------------------------");
		for (Object obj: cart) {
			Product p = (Product) obj;
			System.out.println(p.getId()+"\t"+p.getName()+"\t"+p.getPrice());
			findGrandTotal +=p.getPrice();
		}
		System.out.println("--------------------------------------------");
		System.out.println("Your total is = "+findGrandTotal);
	}
	

}
