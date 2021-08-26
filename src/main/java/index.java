/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
 
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;


@WebServlet(urlPatterns = {""})
public class index extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
      // JDBC driver name and database URL
      //final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
      //final String DB_URL="jdbc:mysql://localhost:3306/testdb";
      final String DB_URL="jdbc:mysql://localhost:3306/products?useSSL=false";

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "The Best Company in the World";
      String docType =
        "<!doctype html public \"-//w3c//dtd html 4.0 " +
         "transitional//en\">\n";
         out.println(docType +
         "<html>\n" +
         "<head>  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>" + title + "</title><link rel=\"stylesheet\" href=\"styles.css\"></head>\n");
         
        out.println("<body> <div class=\"nav\">"
                +   "<a href=\"goCart\" class=\"nav-right\">Cart</a>\n" 
                +   "</div><hr>"
                +   "<div class = \"header\"><h1>" + title + "</h1> </div>");
                 
         
         Connection conn = null;
         Statement stmt = null;
      try{    
         // Register JDBC driver
         Class.forName(JDBC_DRIVER);
         // Open a connection
         conn = DriverManager.getConnection(DB_URL, DBCredentials.USER, DBCredentials.PASS);
         // Execute SQL query
         stmt = conn.createStatement();
         String sql;
         //sql = "INSERT INTO users(firstName, lastName, email) VALUES('hey', 'hola', 'bye')";
         //PreparedStatement pst = conn.prepareStatement(sql);
         //pst.executeUpdate();
         sql = "SELECT name, price, shortDescription, rating, reviews FROM guitars";
         ResultSet rs = stmt.executeQuery(sql);
         // Extract data from result set
         int count = 0;
         String[] x = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};     
         String[] imgName = {"lesPaul", "tele", "fender", "acoustic"};
         out.println("<hr><br>");
         out.println("<div class = \"row\">");
         int rating;
         while(rs.next()){
            String name = rs.getString("name");
            String price = rs.getString("price");
            String shortD = rs.getString("shortDescription");
          
            out.println("<div class=\"" + x[count] + "\">");
            out.println("<form action = \"template\" method = \"post\">");
            out.println("<input type = \"image\" src = \"img/"+ imgName[count/3] + ((count%3)+1)+".jpg\" name = \"submit\" value = \"hey\" style = \"display: block;width: 75%;margin-left: auto;margin-right: auto;\">");
            out.println("<h2 style=\"text-align: center; color: red\"> $" +price+ "<br><a href= \"template\" style=\"text-decoration: none;\">" + name + "</a><br><span style = \"color:black; font-size:0.8em;\">Rating: </span>");
            rating = rs.getInt("rating")/rs.getInt("reviews");
            for (int a =0; a < rating; a++) {
                out.println("&#11088;");
                
            } 
            out.println("</h2>");
            out.println("<p style=\"text-align: center;\">"+ shortD +"</p>");
            out.println("<input type=\"hidden\" name=\"ID\" value=\""+ count +"\">");
            out.println("</form>");
            out.println("</div>");
            count++;
         }
         out.println("</div>");
         out.println("<form action = \"cart\" method = \"post\">");
         out.println("<button>Cart</button>");
         out.println("</form>");
         out.println("</body></html>");
         
        RequestDispatcher rd=request.getRequestDispatcher("/last5");  
        rd.include(request, response);  
         // Clean-up environment
         rs.close();
         stmt.close();
         conn.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         out.println(se);
      }catch(Exception e){
         //Handle errors for Class.forName
         out.println(e);
      }finally{
         //finally block used to close resources
         try{
            if(stmt!=null)
               stmt.close();
         }catch(SQLException se2){
         }// nothing we can do
         try{
            if(conn!=null)
            conn.close();
         }catch(SQLException se){
            se.printStackTrace();
         }//end finally try
      } //end try
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


*/