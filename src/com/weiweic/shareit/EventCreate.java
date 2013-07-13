package com.weiweic.shareit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EventCreate
 */
@WebServlet("/EventCreate")
public class EventCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EventCreate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		String start = request.getParameter("starttime");
		String end = request.getParameter("endtime");
		int number  = Integer.parseInt(request.getParameter("number"));
		start = "2007-10-10 12:12:12";
		end = "2007-10-10 13:13:13";
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		// java.sql.Date sqltDate= new java.sql.Date(parsedUtilDate.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

		long starttime = 0;
		long endtime = 0;
		try {
			starttime = formater.parse(start).getTime();  
			endtime = formater.parse(end).getTime();
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String location = request.getParameter("location");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude =  Double.parseDouble(request.getParameter("longitude"));
		response.setContentType("application/json");

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
			return;
		}

		System.out.println("PostgreSQL JDBC Driver Registered!");
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/shareit", "postgres",
					"root");
			String sql = "INSERT INTO event (name,description,starttime,endtime,location,latitude, longitude, price,number) "
					+ "VALUES (?,?,?,?,?,?,?,?,?)";

			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, name);
			stmt.setString(2, description);
			stmt.setDouble(3, starttime);
			
			stmt.setDouble(4, endtime);
			stmt.setString(5, location);
			stmt.setDouble(6, latitude);
			stmt.setDouble(7, longitude);
			stmt.setDouble(8, price);
			stmt.setInt(9, number);
			stmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
