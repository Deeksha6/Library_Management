package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Models.SearchFields;
import Connection.ConnectionClass;

@WebServlet(name = "SearchForBook", urlPatterns = { "/SearchForBook" })

public class SearchForBook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SearchForBook() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sc = request.getParameter("search_book");
		ConnectionClass obj = new ConnectionClass();
		Connection con = null;
		try {
			con = obj.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		SearchFields searchOb = new SearchFields();
		ArrayList<String> name = new ArrayList<String>();
		ArrayList<String> isbn1 = new ArrayList<String>();
		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> available = new ArrayList<String>();
		Map<String, String> bookAuthors = new HashMap<>();

		try {
			PreparedStatement ps = con.prepareStatement(
					"select b.isbn, b.title, b.Available, a.name from book b join book_authors ba on b.isbn=ba.isbn join authors a on ba.author_id= a.author_id where (b.isbn LIKE '%"
							+ sc + "%' OR b.title LIKE '%" + sc + "%' OR a.name LIKE '%" + sc + "%') ");
			ResultSet rs = ps.executeQuery();
			//System.out.println(rs);
			while (rs.next()) {
				title.add(rs.getString(2));
				available.add(rs.getString(3));
				String isbn = (String) rs.getString(1);
				String author = (String) rs.getString(4);
				if (author.contains("&")) {
					author = author.replaceAll("&amp;", ", ");
				}
				if (!bookAuthors.containsKey(isbn)) {
					bookAuthors.put(isbn, author);
					isbn1.add(isbn);
					name.add(author);
				} else {
					bookAuthors.put(isbn, bookAuthors.get(isbn) + ", " + author);
					isbn1.add(isbn);
					name.add(bookAuthors.get(isbn));
				}
			}
			if (isbn1 != null) {
				searchOb.setIsbn(isbn1);
				searchOb.setTitle(title);
				searchOb.setName(name);
				searchOb.setAvailable(available);
			}
			if (searchOb!=null && !isbn1.isEmpty()) {
				session.setAttribute("searchvalues", searchOb);
				session.setAttribute("searchval", "hasvalue");
			} else {
				session.setAttribute("searchval", "novalue");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("home.jsp");
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
