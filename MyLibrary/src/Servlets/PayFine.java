package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import Connection.ConnectionClass;

@WebServlet(name="PayFine",urlPatterns={"/PayFine"})

public class PayFine extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PayFine() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Card id: "+ request.getParameter("cardid"));
		System.out.println("Payment: "+ request.getParameter("amount"));
		String card_id=request.getParameter("cardid");
		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			PreparedStatement ps=con.prepareStatement("select b.Card_id,f.loan_id,f.fine_amt from fines f join book_loans bl on f.Loan_id=bl.loan_id join borrower b on bl.card_id=b.card_id where b.card_id='"+card_id+"' and paid=0");
			System.out.println("the query is :"+ps);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
            {	
				PreparedStatement ps3=con.prepareStatement("update fines set Paid=true where Loan_id="+rs.getString(2));
				ps3.executeUpdate();
				response.setContentType("text/html;charset=UTF-8");
				 response.getWriter().write("Payment Successfull!");
            }	 
		}catch(Exception e) {
			 response.setContentType("text/html;charset=UTF-8");
			 response.getWriter().write("Payment failed due to some error!");
			e.printStackTrace();
		}
        response.sendRedirect("home.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
