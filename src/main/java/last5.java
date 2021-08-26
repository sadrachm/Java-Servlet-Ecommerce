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


@WebServlet(urlPatterns = {"/lastt"})
public class last5 extends HttpServlet {

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
      final String DB_URL="jdbc:mysql://localhost:3306/testdb?useSSL=false";
      final String DB_URL2="jdbc:mysql://localhost:3306/products?useSSL=false";
        HttpSession session = request.getSession();

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
        out.println("<hr>");
         out.println("<h1 align=\"center\">Recent Orders</h1>\n");
         Connection conn = null;
         Statement stmt = null;
         Connection conn2 = null;
         Statement stmt2 = null;
      try{    
         // Register JDBC driver
         Class.forName(JDBC_DRIVER);
         // Open a connection
         conn = DriverManager.getConnection(DB_URL, DBCredentials.USER, DBCredentials.PASS);
         conn2 = DriverManager.getConnection(DB_URL2, DBCredentials.USER, DBCredentials.PASS);
         // Execute SQL query
         stmt = conn.createStatement();
         stmt2 = conn2.createStatement();
         String sql;
         String sql2;
         sql = "SELECT guitarIds FROM users WHERE IdSes = \""+ session.getId() + "\"";
         sql2 = "SELECT name FROM guitars";
         ResultSet rs = stmt.executeQuery(sql);
         ResultSet rs2 = stmt2.executeQuery(sql2);
         // Extract data from result set
         String[] guitars = new String[12]; 
         int count = 0;
         List<String> purchases = new ArrayList();
         while(rs2.next()){
            //Retrieve by column name
            String name = rs2.getString("name");

            //Display values
            guitars[count] = name;
            count++;
         }
         List<String> purID = new ArrayList();
         while(rs.next()){
            //Retrieve by column name
            String[] y = rs.getString("guitarIds").split("\\s+");
            for (String x : y) {
                if (x.isEmpty()){}
                else {
                    
                    purchases.add(guitars[Integer.parseInt(x)]);
                    purID.add(x);
                }
            }
         }       
         List<String> ans = new ArrayList();
         List<String> ansID = new ArrayList();
         for (String x : purchases) {
             if (ans.contains(x)){
                 ans.remove(x);
             }
             ans.add(x);
         }
         for (String x : purID) {
             if (ansID.contains(x)) {
                 ansID.remove(x);
             }
             ansID.add(x);
         }
         
         Collections.reverse(ans);
         Collections.reverse(ansID);
         String[] imgName = {"lesPaul", "tele", "fender", "acoustic"};
         
         count = 0;
         out.println("<form action=\"submitReview\" method=\"post\">");
         out.println("<div class=\"container\" >");
         int countID = 0;
         for (String x: ans) {
             out.println("<div class = \"first" + count +"\" >");
             out.println("<img class = \"smallImg\" src = \"img/"+ imgName[Integer.parseInt(ansID.get(count))/3] + ((Integer.parseInt(ansID.get(count))%3)+1)+".jpg\" style = \"max-width: 375px;\">");
           
             out.println("<h2 style = \"text-align: center;\">" + x + "</h2>");
             out.println("  <div style = \"text-align:center;\"><div class=\"radioLabel\">\n" +
"  <input type=\"hidden\" name=\"XX"+ count +"\" value=\""+ ansID.get(count) +"\">"+
"    <input type=\"radio\" name=\"X" + count+ "\" id=\"X" + countID + "\" value=\"1\">\n" +
"    <label for=\"X" + countID+ "\">1</label>\n" +
"  </div>\n");
              countID++;       
out.println("  <div class=\"radioLabel\">\n" +
"    <input type=\"radio\" name=\"X" + count+ "\" id=\"X" + countID+ "\" value=\"2\">\n" +
"    <label for=\"X" + countID+ "\">2</label>\n" +
"  </div>\n");
countID++;
out.println("  <div class=\"radioLabel\">\n" +
"    <input type=\"radio\" name=\"X" + count+ "\" id=\"X" + countID+ "\" value=\"3\">\n" +
"    <label for=\"X" + countID+ "\">3</label>\n" +
"  </div>\n");
countID++;
out.println("  <div class=\"radioLabel\">\n" +
"    <input type=\"radio\" name=\"X" + count+ "\" id=\"X" + countID+ "\" value=\"4\">\n" +
"    <label for=\"X" + countID+ "\">4</label>\n" +
"  </div>\n");
        countID++;
out.println("  <div class=\"radioLabel\">\n" +
"    <input type=\"radio\" name=\"X" + count+ "\" id=\"X" + countID+ "\" value=\"5\">\n" +
"    <label for=\"X" + countID+ "\">5</label>\n" +
"  </div></div>");
countID++;
             count++;
             
             out.println("</div>");
             if (count >= 5) {
                 break;
             }
         }
         out.println("</div>");
         out.println("<input type=\"hidden\" name=\"count\" value=\""+ count + "\">");
         out.println("<button type=\"submit\" name=\"button\">Submit Review</button>");
         out.println("</form>");
         
         out.println("</body></html>");
         // Clean-up environment
         //rs.close();
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


