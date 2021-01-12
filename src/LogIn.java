import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/LogIn")
public class LogIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String e=request.getParameter("email");
		String p=request.getParameter("password");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodbank","root","234567");
			java.sql.Statement st=cn.createStatement();
			//System.out.println("QUERY :: insert into b3(name,age,email,pincode,password,contact,address,city,state,country) values('"+n+"','"+age+"','"+e+"','"+pin+"','"+p+"','"+c+"','"+a+"','"+ci+"','"+s+"','"+co+"')");
			ResultSet rs=st.executeQuery("select*from b3 where email='"+e+"'");
			rs.next();
			String n=rs.getString("name");
			int age=rs.getInt("age");
			int pin=rs.getInt("pincode");
			String c=rs.getString("mobile");
			String a=rs.getString("address");
			//String g=request.getParameter("gen");
			String ci=rs.getString("city");
			String s=rs.getString("state");
			String co=rs.getString("country");
			request.setAttribute("name",n);
			request.setAttribute("age",age);
			request.setAttribute("email",e);
			request.setAttribute("pin",pin);
			request.setAttribute("mobile",c);
			request.setAttribute("address",a);
			request.setAttribute("city",ci);
			request.setAttribute("state",s);
			request.setAttribute("country",co);
			//request.setAttribute("about",about);
			String id=rs.getString("id");
			HttpSession h=request.getSession();
			h.setAttribute("email",e);
			h.setAttribute("id",id);
			st.close();
			cn.close();
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			 response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			 response.setHeader("Expires", "0"); // Proxies.
			RequestDispatcher rd=request.getRequestDispatcher("Profile.jsp");
			rd.forward(request,response);
		}
		catch(Exception ex){		
		}
	}
}
