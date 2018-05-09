package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Connection.ConnectionClass;

@WebServlet(name = "AddBorrower", urlPatterns = { "/AddBorrower" })
public class AddBorrower extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public AddBorrower() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String query = "insert into Borrower (card_id,Ssn, First_Name, Last_Name,Address,Phone,City,State)"
				+ " values (?,?, ?, ?, ?,?,?,?)";
		PreparedStatement preparedStmt;
		PreparedStatement preparedStmt2;
		try {
			preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(2, request.getParameter("ssn"));
			preparedStmt.setString(3, request.getParameter("fname"));
			preparedStmt.setString(4, request.getParameter("lname"));
			preparedStmt.setString(5, request.getParameter("ad"));
			preparedStmt.setString(6, request.getParameter("phone"));
			preparedStmt.setString(7, request.getParameter("city"));
			preparedStmt.setString(8, request.getParameter("state"));
			String query2 = "Select count(*) from Borrower";
			preparedStmt2 = con.prepareStatement(query2);
			ResultSet rs = preparedStmt2.executeQuery();
			int count = 0;
			String cardnum = "";
			if (rs.next()) {
				count = rs.getInt(1);
				count = count + 1;
				cardnum = String.format("ID%06d", count);
			}
			preparedStmt.setString(1, cardnum);
			preparedStmt.execute();
			session.setAttribute("addval", "hasvalue");
			session.setAttribute("card_id", cardnum);
		} catch (SQLException e) {
			session.setAttribute("addval", "novalue");
			e.printStackTrace();
		}
		response.sendRedirect("home.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
