import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
public class SearchServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String number = request.getParameter("number").toUpperCase();
		try	{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","abc123");
			ps = con.prepareStatement("select * from vehicle where vehicleno ='"+number+"'");
			request.getRequestDispatcher("Search.html").include(request,response);
			pw.print("<table a align=center bgcolor = lightblue width = 50% border = 1>");
			pw.print("<caption>Result:</caption>");
			rs = ps.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			int total =rsmd.getColumnCount();
			pw.print("<tr>");
			for(int i=1;i<=total;i++) {
				pw.print("<th>"+rsmd.getColumnName(i)+"</th>");
			}
			pw.print("</tr>");
			pw.print("<tr>");
 
		while(rs.next())	{
			pw.print("<td>"+rs.getString(1)+"</td><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td></tr>");
		}
		pw.print("</table>");
		}
		catch(Exception e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
				pw.close();
				con.close();
					ps.close();
			}
			catch(Exception e1)
			{
			}
		}
	}
}