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


@WebServlet(urlPatterns = {"/getPhone"})
public class getPhone extends HttpServlet {

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
        HttpSession session = request.getSession();
      // JDBC driver name and database URL
      //final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
      //final String DB_URL="jdbc:mysql://localhost:3306/testdb";
      final String DB_URL="jdbc:mysql://localhost:3306/testdb?useSSL=false";

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
                 
         
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
         String phone = request.getParameter("number"); 
         //sql = "INSERT INTO users(firstName, lastName, email) VALUES('hey', 'hola', 'bye')";
         //PreparedStatement pst = conn.prepareStatement(sql);
         //pst.executeUpdate();
         sql = "SELECT firstName, lastName, shippingAddress, cardNumber, expirationDate, securityCode, billingAddress, zip  FROM users WHERE phone = \""+ phone + "\"";
         ResultSet rs = stmt.executeQuery(sql);
         // Extract data from result set
         String fname ="", lname="", shipA="", cardN="", expD="", secC="", billA="", zip="";
         while(rs.next()){
            fname = rs.getString("firstname");
            lname = rs.getString("lastName");
            shipA = rs.getString("shippingAddress");
            cardN = rs.getString("cardNumber");
            expD = rs.getString("expirationDate");
            secC = rs.getString("securityCode");
            billA = rs.getString("billingAddress");
            zip = rs.getString("zip");
         }
         String[] arr = {fname, lname, shipA, cardN, expD, secC, billA, zip};
         for (String x : arr) {
             if (x.equals(null)) {
                 x = "";
             }
         }
         out.println(fname + ","+ lname +","+ shipA +","+ cardN +","+ expD +","+ secC +","+ billA + "," + zip);
       
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


