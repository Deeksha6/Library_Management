package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

import Connection.ConnectionClass;
import Models.Fines;

@WebServlet(name="RefreshFines",urlPatterns={"/RefreshFines"})

public class RefreshFines extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RefreshFines() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
		ConnectionClass obj = new  ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			PreparedStatement ps=con.prepareStatement("select * from book_loans  where (Due_date < NOW() AND Date_in is NULL) OR (Due_date < Date_in AND Date_in IS NOT NULL) ;");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
            {
				try
				{
				Date d1 = new SimpleDateFormat("yyyy-M-dd").parse((String) rs.getString(5));
				String date;
				Date d2;
				if(rs.getString(6)==null) {
				 date = new SimpleDateFormat("yyyy-M-dd").format(new Date());
				 d2 = new SimpleDateFormat("yyyy-M-dd").parse(date);

				}else {
				d2=new SimpleDateFormat("yyyy-M-dd").parse((String) rs.getString(6));
				}
				long diff = Math.abs(d1.getTime() - d2.getTime());
				long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				float fine= (float) (diffDays*(0.25));
				PreparedStatement ps2=con.prepareStatement("select * from fines where Loan_id='"+rs.getString(1)+"';");
				ResultSet rs2=ps2.executeQuery();
				if(rs2.next()) {
					if(rs2.getBoolean(3)==false) {
				PreparedStatement ps3=con.prepareStatement("update fines set Fine_amt='"+fine+"' where Loan_id='"+rs.getString(1)+"';");
				ps3.executeUpdate();
					}
				}else {
				String query = " insert into fines (Loan_id, fine_amt,paid)"
						        + " values (?,?,?)";
			    PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString (1, rs.getString(1));
			    preparedStmt.setFloat(2, fine);
			    preparedStmt.setBoolean(3,false);
			    preparedStmt.execute();
				}
				session.setAttribute("refresh","hasvalue");
				}
				catch (SQLException e) {
					session.setAttribute("refresh","novalue");
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} 
            }
			response.sendRedirect("home.jsp");
		}
			catch (SQLException e) {
				e.printStackTrace();
			} 
			

		Fines fine = new Fines();
		ArrayList<Float> amt = new ArrayList<Float>();
		ArrayList<String> first_name = new ArrayList<String>();
		ArrayList<String> last_name = new ArrayList<String>();
		ArrayList<String> card_id = new ArrayList<String>();
		try
		{
		PreparedStatement ps=con.prepareStatement("select b.Card_id, b.first_name,b.last_name,SUM(f.fine_amt) from fines f join book_loans bl on f.Loan_id=bl.loan_id join borrower b on bl.card_id=b.card_id where f.paid=false group by b.Card_id ");
		ResultSet rs=ps.executeQuery();
		while(rs.next())
        {
			card_id.add(rs.getString(1));
			first_name.add(rs.getString(2));
			last_name.add(rs.getString(3));
			amt.add(rs.getFloat(4));
        }
		if(amt!=null) {
			fine.setFine_amt(amt);
			fine.setFirst_name(first_name);
			fine.setLast_name(last_name);
			fine.setCard_id(card_id);
		}
		if(fine!=null && amt!=null)
		{
			session.setAttribute("fineresultval", fine);
			session.setAttribute("fineresult", "hasvalue");
		}
		else
		{
			session.setAttribute("fineresult", "novalue");
		}
        }
		catch(SQLException e) {
			
			e.printStackTrace();
		} 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
