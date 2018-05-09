package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Connection.ConnectionClass;

@WebServlet(name = "CheckOutBook", urlPatterns = { "/CheckOutBook" })

public class CheckOutBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CheckOutBook() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String card = request.getParameter("cardid");
		String query = "select count(*) from book_loans where date_in IS NULL AND card_id='"+ card +"'";
		PreparedStatement preparedStmtcheck;
		try {
			preparedStmtcheck = con.prepareStatement(query);
			ResultSet rs = preparedStmtcheck.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
				System.out.println("count of active book loans: " + count);
			}
			if (count > 3) {
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("Checkout Failed. Borrower has reached the maximum limit");
			} else {
				String query1 = " insert into Book_Loans (isbn, card_id, date_out, due_date) values (?, ?, ?, ?)";
				PreparedStatement prepStmt1;
				PreparedStatement prepStmt2;
				try {
					prepStmt1 = con.prepareStatement(query1);
					prepStmt1.setString(1, request.getParameter("isbn"));
					prepStmt1.setString(2, request.getParameter("cardid"));
					java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
					prepStmt1.setDate(3, sqlDate);
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 14);
					java.sql.Date javaSqlDate = new java.sql.Date(cal.getTime().getTime());
					prepStmt1.setDate(4, javaSqlDate);
					prepStmt1.execute();
					String isbn_num = request.getParameter("isbn");
					System.out.println(isbn_num);
					String query2 = "update book set Available='NO' where isbn='" + isbn_num + "'";
					prepStmt2 = con.prepareStatement(query2);
					prepStmt2.executeUpdate();
					response.setContentType("text/html;charset=UTF-8");
					response.getWriter().write("CHECKOUT SUCCESSFUL!!!!!!!!!!!!!!!!!!!!!");
				} catch (SQLException e1) {
					response.setContentType("text/html;charset=UTF-8");
					response.getWriter().write("Checkout Failed!");
					e1.printStackTrace();
				}
			}
		} catch (SQLException e) {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("Checkout Failed!");
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
