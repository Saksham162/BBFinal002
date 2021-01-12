import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SAV_DIR="D:/uploadFiles";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			System.out.println("start");
		HttpSession h=request.getSession(false);
		Object idOb=h.getAttribute("id");
		if(idOb!=null){
		String about=request.getParameter("about");
		int id=(int)idOb;
		System.out.println(id);
		System.out.println("shubhi profile");
		String savPath=SAV_DIR;
		System.out.println("profile  "+savPath);
		File fileSaveDir=new File(savPath);
		if(!fileSaveDir.exists())
			fileSaveDir.mkdir();
		String finalSavePath=savPath+File.separator+id;
		fileSaveDir=new File(finalSavePath);
		System.out.println("between11");
		if(!fileSaveDir.exists())
			fileSaveDir.mkdir();
		for(Part part:request.getParts()){
			System.out.println("loop");
			String FileName= extractFileName(part);
			System.out.println("fileName :: "+FileName);
			if(FileName!=null && !FileName.trim().isEmpty())
				part.write(finalSavePath+File.separator+FileName);
		}
		System.out.println("start jdbc");
		Class.forName("com.mysql.jdbc.Driver");
		java.sql.Connection cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodbank","root","234567");
			PreparedStatement pst=cn.prepareStatement("update b3 set aboutme= ? where id= ?");
			System.out.println(pst);
			pst.setString(1,about);
			pst.setInt(2,id);
			Statement st=cn.createStatement();
		//	System.out.println("update b3 set aboutme='"+about+"' where id="+id);
		//	st.executeUpdate(" update b3 set aboutme='"+about+"' where id="+id);
			String query = "select * from b3 where id=?"; 
			PreparedStatement prSt = cn.prepareStatement(query); 
			System.out.println(prSt);
			prSt.setInt(1,id);
			ResultSet rs = prSt.executeQuery(); 
			//ResultSet rs=pst.executeQuery("select * from b3 where id="+id);
			rs.next();
			String n=rs.getString("name");
			int age=rs.getInt("age");
			String bg=rs.getString("bgroup");
			String e=rs.getString("email");
			int pin=rs.getInt("pincode");
			String c=rs.getString("mobile");
			String a=rs.getString("address");
			//String g=request.getParameter("gen");
			String ci=rs.getString("city");
			String s=rs.getString("state");
			String co=rs.getString("country");
			request.setAttribute("name",n);
			request.setAttribute("age",age);
			request.setAttribute("pin",pin);
			request.setAttribute("mobile",c);
			request.setAttribute("address",a);
			request.setAttribute("city",ci);
			request.setAttribute("state",s);
			request.setAttribute("country",co);
			request.setAttribute("about",about);
			request.setAttribute("bgroup",bg);
			System.out.println("profilr "+bg);
			ResultSet rs1=st.executeQuery("select * from bloodrequest where donor='"+e+"' and status='requestSent';");
			if(rs1.next()){
			String acceptor=rs1.getString("acceptor");
			request.setAttribute("acceptor",acceptor);
			}
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			 response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			 response.setHeader("Expires", "0"); // Proxies.
			RequestDispatcher rd=request.getRequestDispatcher("Profile.jsp");
			rd.forward(request,response);
		} 
		else{
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			 response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			 response.setHeader("Expires", "0"); // Proxies.
			 response.sendRedirect("index.html");
		}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private String extractFileName(Part part) {
		System.out.println("extractFile");
        String contentDisp = part.getHeader("Content-Disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
