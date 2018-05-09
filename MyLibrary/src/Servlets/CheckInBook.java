package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.BookCheckIn;
import Connection.ConnectionClass;

@WebServlet(name="CheckInBook",urlPatterns={"/CheckInBook"})

public class CheckInBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CheckInBook() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();  
		String sc= request.getParameter("searchcheckin");
		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		BookCheckIn bookcheckin = new BookCheckIn();
		ArrayList<String> isbn = new ArrayList<String>();
		ArrayList<String> first_name = new ArrayList<String>();
		ArrayList<String> last_name = new ArrayList<String>();
		ArrayList<String> card_num = new ArrayList<String>();

        try {
			PreparedStatement ps=con.prepareStatement("select bl.isbn, bl.card_id, b.first_name,b.last_name from book_loans bl join borrower b on ( bl.Card_id=b.Card_id AND (bl.isbn LIKE '%"+sc+"%' OR bl.card_id LIKE '%"+sc+"%' OR concat(b.first_name,' ',b.last_name) LIKE '%"+sc+"%')) AND Date_in IS NULL");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
            {
				isbn.add(rs.getString(1));
				card_num.add(rs.getString(2));
				first_name.add(rs.getString(3));
				last_name.add(rs.getString(4));
            }
			if(isbn!=null && card_num!=null && first_name!=null && last_name!=null) {
				bookcheckin.setIsbn(isbn);
				bookcheckin.setCardNum(card_num);
				bookcheckin.setFirstName(first_name);
				bookcheckin.setLastName(last_name);
				
			}
			
		if(null!=bookcheckin && !isbn.isEmpty()) {
		session.setAttribute("searchcheckinvalues", bookcheckin);
		session.setAttribute("searchvalcheckin", "hasvalue");
		}
		else{
		session.setAttribute("searchvalcheckin", "novalue");
		}
		
		
        } catch (SQLException e) {
			e.printStackTrace();
		}
        response.sendRedirect("home.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
