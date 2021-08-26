/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
 
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;


@WebServlet(urlPatterns = {"/JDBCDemoServlet"})
public class submitReview extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
         
        out.println("<body> <div class=\"nav\">" +"<a href=\"goHome\" class=\"nav-left\">Home</a>"
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
         sql = "INSERT INTO guitars(firstName, lastName, email) VALUES('hey', 'hola', 'bye')";
         PreparedStatement pst = conn.prepareStatement(sql);
//         pst.executeUpdate();
         sql = "SELECT name FROM guitars";
         ResultSet rs = stmt.executeQuery(sql);

          int count = Integer.parseInt(request.getParameter("count"));
          String[] IDs = new String[count];
          String x = "XX";
          for (int a = 0; a < count; a++) {
              IDs[a] = request.getParameter(x+a);
          }
          String[] reviews = new String[count];
          x = "X";
          for (int a = 0; a < count; a++) {
              reviews[a] = request.getParameter(x+a);
          }
          int rating;
          int numReviews;
          int id;
          for (int a = 0; a < count; a++) {
              if (reviews[a] == null) {}
              else {
                  id = Integer.parseInt(IDs[a])+1;
                  sql = "SELECT reviews, rating FROM guitars WHERE idguitars =\"" + id +"\"";
                  rs = stmt.executeQuery(sql);
                  rs.next();
                  rating = rs.getInt("rating");
                  numReviews = rs.getInt("reviews");
                  rating = (rating + Integer.parseInt(reviews[a]));
                  
                  sql = "UPDATE `products`.`guitars` SET `rating` = '"+ rating +"' WHERE (`idguitars` = '"+ id +"');";
                  pst = conn.prepareStatement(sql);
                  pst.executeUpdate();    
                  sql = "UPDATE `products`.`guitars` SET `reviews` = '"+ (numReviews+1) +"' WHERE (`idguitars` = '"+ id +"');";
                  pst = conn.prepareStatement(sql);
                  pst.executeUpdate();         
              }
          }
          out.println("<h2 style = \"text-align:center;\"> Thank You For the Review!</h2>");
         out.println("</body></html>");
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
     */
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
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


