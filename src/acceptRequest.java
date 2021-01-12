

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/acceptRequest")
public class acceptRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession h=request.getSession();
		String acceptor=(String)h.getAttribute("emailFrom");
		String donor=request.getParameter("donor");
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodbank","root","234567");
				Statement st=cn.createStatement();
				st.executeUpdate("insert into bloodrequest(donor,status,acceptor) values('"+donor+"','accepted','"+acceptor+"';");
		}catch(Exception ex){
			
		}
	}
}
