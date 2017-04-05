


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.BSONObject;
import org.json.JSONArray;

import com.ibm.json.java.JSONObject;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

/**
 * Servlet implementation class Register
 */
//@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MongoException, DuplicateKeyException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String uname = request.getParameter("username");
        String password = request.getParameter("password");
        MongoClientURI uri = new MongoClientURI("mongodb://Manasa:ravinder10@ds033103.mlab.com:33103/asedb");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("demoase");
		BasicDBObject query = new BasicDBObject("username", uname).append("password", password);
		DBCursor docs = users.find(query);
		DBCursor docs1 = users.find();
		JSONArray jsonarray = new JSONArray();

			if(docs.hasNext())
			{	
			HttpSession session=request.getSession(); 
				session.setAttribute("username",uname); 
				out.println("<html>");
				out.println("<head><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">");
				out.println(" <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
				out.println("</head>");
				out.println("<body>");
				out.println("<div class='login-body'><div class='login-block'>");
				out.println("<h2>Welcome</h2><form>");
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
			else{
				response.sendRedirect("loginFailure.html");
			}

		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MongoException, DuplicateKeyException, UnknownHostException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
         StringBuilder buffer = new StringBuilder();
         BufferedReader reader = request.getReader();
         String uname = request.getParameter("username");
         String password = request.getParameter("password");
         String email = request.getParameter("email");
         System.out.println(uname+" "+password+" "+email);
         /*String line;
         while((line=reader.readLine())!=null){
        	 buffer.append(line);
         }
         String data = buffer.toString();
         System.out.println(data);
         data = "{\"p\":\"N\",\"c\":\"W\"}";*/
         //System.out.println(data);
         //JSONObject params = (JSONObject)JSON.parse(data);
         JSONObject params = new JSONObject();
         params.put("username", uname);
         params.put("password", password);
         BasicDBObject user1 = new BasicDBObject(params);
         for(Object key:params.keySet().toArray()){
         user1.put(key.toString(), params.get(key));}
        // System.out.println(user1.toJson());
         MongoClientURI uri = new MongoClientURI("mongodb://Manasa:ravinder10@ds033103.mlab.com:33103/asedb");
 		MongoClient client = new MongoClient(uri);
 		DB db = client.getDB(uri.getDatabase());
 		DBCollection users = db.getCollection("demoase");
 		users.insert(user1);
 		response.setHeader("Access-Control-Allow-Origin", "*");
 		response.setHeader("Access-Control-Allow-Methods", "POST");
 		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
 		response.setHeader("Access-Control-Max-Age", "86400");
         
         
		}

}

