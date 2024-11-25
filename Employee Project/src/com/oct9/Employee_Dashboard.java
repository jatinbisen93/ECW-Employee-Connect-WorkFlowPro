package com.oct9;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.crypto.Data;
import com.mysql.cj.xdevapi.Statement;

public class Employee_Dashboard {

	private static Scanner s = new Scanner(System.in);
	private static String path = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3307/";
	private static String uname = "root";
	private static String pwd = "root";
	private static String dtname = "newDatabase";
	private static Connection conn = null;
	private static ResultSet rs = null;
	private static PreparedStatement ps = null;

	// Creating Database
	public static void createDatabase() {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter");
		String query = "CREATE SCHEMA " + dtname;
		try {
			Class.forName(path);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/", uname, pwd);
			conn.prepareStatement(query).execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Creating Table
	public static void createTable() {
		String tname = "Registration";
		String query = "CREATE table " + tname
				+ "(id INT PRIMARY KEY AUTO_INCREMENT, Fullname VARCHAR(40), Username VARCHAR(40), Password VARCHAR(40), Salary VARCHAR(40) CreatedAt DATETIME DEFAULT now())  ";
		try {
			Class.forName(path);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/newDatabase", uname, pwd);
			conn.prepareStatement(query).execute();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Showing all Employee Data
	public static List<Employee_Dashboard> ShowUsers() {
		List<Employee_Dashboard> l1 = new ArrayList<>();
		Scanner s = new Scanner(System.in);
		String tname = "Registration";

		String query = "SELECT id, Fullname, Salary, CreatedAt FROM newDatabase.Registration;";

		try {
			conn = DriverManager.getConnection(url, uname, pwd);
			ps = conn.prepareStatement(query);

			System.out.println("\n-----------------------------------------------------");
			System.out.println("Showing Information about Employee\n");
			rs = ps.executeQuery(query);

			while (rs.next()) {
				int id = rs.getInt("id");
				String fn = rs.getString("Fullname");
				int sal = rs.getInt("Salary");
				Timestamp ct = rs.getTimestamp("CreatedAt");

				Employee_Dashboard emp = new Employee_Dashboard();
				l1.add(emp);

				System.out.println(id + "  " + fn + "   " + sal + "           " + ct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l1;
	}

	// Inserting Data
	public static void SignUp() {
		String tname = "Registration";
		String query = "insert into Registration (Fullname, Username, Password, Salary ) values  (?,?,?,?);";

		try {
			Scanner s = new Scanner(System.in);
			System.out.println("\n-----------------------------------------------------");
			System.out.println("Fill the Details of Employee\n");
			System.out.print("Enter Fullname : ");
			String fn = s.nextLine();

			System.out.print("Enter Username : ");
			String un = s.nextLine();

			System.out.print("Enter Password : ");
			String pass = s.nextLine();

			System.out.print("Enter Salary   : ");
			String sal = s.nextLine();

			Class.forName(path);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/newDatabase", uname, pwd);
			ps = conn.prepareStatement(query);

			ps.setString(1, fn);
			ps.setString(2, un);
			ps.setString(3, pass);
			ps.setString(4, sal);

			int rs = ps.executeUpdate();
			if (rs > 0) {
				System.out.println("\nEmployee Registered Successfully");
			} else {
				System.out.println("Please Enter Valid Details...");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Login to Employee
	public static void Login() {
		String query = "SELECT * FROM Registration WHERE Username = ? AND Password = ?";
		System.out.println("-----------------------------------------------------");
		System.out.println("Employee Login ");
		System.out.print("Username: ");
		String un = s.next();

		System.out.print("Password: ");
		String pass = s.next();

		try {

			Class.forName(path);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/newDatabase", uname, pwd);
			ps = conn.prepareStatement(query);

			ps.setString(1, un);
			ps.setString(2, pass);

			rs = ps.executeQuery();

			if (rs.next()) {
				System.out.println("\nLogin Done Successfully");
			} else {
				System.out.println("");
				System.out.println("Fail to Login");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// main method
	public static void main(String[] args) {

		Employee_Dashboard emp = new Employee_Dashboard();
		Scanner s = new Scanner(System.in);

		while (true) {
			System.out.println("\n_____________________________________________________");
			System.out.println("\n************WELCOME TO EMPLOYEE DASHBOARD************");
			System.out.println("\n_____________________________________________________");
			System.out.println("1. Show Employee Details \n2. Employee Sign Up  \n3. Employee Login  \n4. MENU ");
			System.out.println("\n-----------------------------------------------------");

			System.out.print("Enter Your Valuable Choice : ");

			int choice = s.nextInt();
			switch (choice) {
			case 1:

				List<Employee_Dashboard> ShowUsers = ShowUsers();
				System.out.println(" Showing All Data");
				for (Employee_Dashboard ss : ShowUsers) {
					System.out.printf("%-20s%-7d \n", ss.getId(), ss.getName());

				}

				break;
			case 2:
				emp.SignUp();
				break;
			case 3:
				emp.Login();
				break;
			case 4:
				System.out.println("\n_____________________________________________________");
				System.out.println("\n**************EXIT OF EMPLOYEE DASHBOARD*************");
				System.out.println("\n_____________________________________________________");

				System.exit(choice);

				break;
			}
		}
	}

	private Object getName() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object getId() {
		// TODO Auto-generated method stub
		return null;
	}
}
