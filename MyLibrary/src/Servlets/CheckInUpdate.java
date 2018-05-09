package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Connection.ConnectionClass;

@WebServlet(name="CheckInUpdate",urlPatterns={"/CheckInUpdate"})

public class CheckInUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CheckInUpdate() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("The value of isbn: "+ request.getParameter("isbn"));
		String isbn=request.getParameter("isbn");
		String card_id=request.getParameter("card_id");
		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
		String query = "update book_loans set Date_in='"+sqlDate+"' where isbn='"+isbn+"' and card_id='"+card_id+"'";
		String query2="update book set available='YES' where isbn='"+isbn+"'";
	     try {
	    	 PreparedStatement	preparedStmt = con.prepareStatement(query);
	    	 preparedStmt.executeUpdate();
	    	 response.setContentType("text/html;charset=UTF-8");
	    	 response.getWriter().write("CHECK IN SUCCESSFUL!!!!!!!!!");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	     try {
	    	 PreparedStatement	preparedStmt2 = con.prepareStatement(query2);
		     preparedStmt2.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
