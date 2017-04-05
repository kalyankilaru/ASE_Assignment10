


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.json.java.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
/**
 * Servlet implementation class Delete
 */
//@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession(); 
		String uname = (String)session.getAttribute("username"); 
		MongoClientURI uri = new MongoClientURI("mongodb://Manasa:ravinder10@ds033103.mlab.com:33103/asedb");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("demoase");
		BasicDBObject document = new BasicDBObject();
		document.put("username", uname);
		users.remove(document);
		DBCursor docs1 = users.find();
		out.println("<html>");
		out.println("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
		out.println(" <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class='login-body'><div class='login-block'>");
		out.println("<h2>Account Deleted Successfully</h2><form>");
		out.println("<button type='button' class='btn btn-default button turquoise' onclick=window.location='Update.html'>Update Details</button>");
        out.println("</form>");   
        
        out.println("<form action='delete' method='get'>");    
		out.println("<button type='submit' class='btn btn-default button turquoise'>Remove Account</button>");
        out.println("</form></div></div><h4 style='color:white'>Users List</h4><center><div class='table-responsive' style='width:50%'><table class='table' style='color:white;border:1px solid white'>");
        out.println("<tr><td>Username</td><td>Email</td><td>Password</td></tr>");
        while(docs1.hasNext()){
			 BasicDBObject obj = (BasicDBObject) docs1.next();
			 JSONObject jsonobj = new JSONObject();
			    BasicDBList name = (BasicDBList) obj.get("usernmae");
			   
			    out.println("<tr><td>"+obj.getString("username")+"</td>");
			    out.println("<td>"+ obj.getString("email")+"</td>");
			    out.println("<td>"+ obj.getString("password")+"</td></tr>");
    	}	
        out.println("</table></center></div></div>");
        out.println("</body>");
        out.println("</html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
