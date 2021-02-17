/**
 * 
 */
package com.app.client;

import java.sql.SQLException;

import com.app.impl.Product_color;

/**
 * @author Rutuja
 *
 */
public class PcTest {

	public static void main(String[] args) throws SQLException {

		 Product_color pc=new  Product_color();
		 pc.addColor();
		pc.addProduct();
		 pc.select();
		 pc.addToCart();
		 pc.createBill();
	}
	

}
