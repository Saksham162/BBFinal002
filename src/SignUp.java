import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String n=request.getParameter("name");
		String bd=request.getParameter("bday");
		String ae=request.getParameter("age");
		String ad=request.getParameter("address");
		String ci=request.getParameter("city");
		String sta=request.getParameter("state");
		String con=request.getParameter("country");
		String pi=request.getParameter("pin");
		String em=request.getParameter("email");
		String mb=request.getParameter("mobile");
		String pwd=request.getParameter("password");
		String blg=request.getParameter("optradio");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodbank","root","234567");
			java.sql.Statement st=cn.createStatement();
			System.out.println("QUERY :: insert into b3(name,dob,age,address,city,state,country,pincode,email,mobile,password,bgroup) values('"+n+"','"+bd+"','"+ae+"','"+ad+"','"+ci+"','"+sta+"','"+con+"','"+pi+"','"+em+"','"+mb+"','"+pwd+"','"+blg+"')");
			st.executeUpdate("insert into b3(name,dob,age,address,city,state,country,pincode,email,mobile,password,bgroup)  values('"+n+"','"+bd+"','"+ae+"','"+ad+"','"+ci+"','"+sta+"','"+con+"','"+pi+"','"+em+"','"+mb+"','"+pwd+"','"+blg+"')");
			ResultSet rs=st.executeQuery("select id from b3 where email='"+em+"'");
			rs.next();
			int id=Integer.parseInt(rs.getString("id"));
			st.close();
			cn.close();
			HttpSession h=request.getSession();
			h.setAttribute("email",em);
			h.setAttribute("id",id);
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			 response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			 response.setHeader("Expires", "0"); // Proxies.
			RequestDispatcher rd=request.getRequestDispatcher("ImageUpdate.jsp");
			rd.forward(request,response);
		//	response.("ImageUpdate.jsp");
		//	response.("ImageUpdate.jsp");
		}catch(Exception ex){
			System.out.println(ex);
		}
	}
}