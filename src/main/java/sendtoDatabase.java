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


@WebServlet(urlPatterns = {"/Sending"})
public class sendtoDatabase extends HttpServlet {

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
        HttpSession session = request.getSession();

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType =
        "<!doctype html public \"-//w3c//dtd html 4.0 " +
         "transitional//en\">\n";
         out.println(docType +
         "<html>\n" +
         "<head><title>" + title + "</title></head>\n" +
         "<body bgcolor=\"#f0f0f0\">\n" +
         "<h1 align=\"center\">" + title + "</h1>\n");
         Connection conn = null;
         Statement stmt = null;
      try{    
          
          List<String> names = Collections.list(session.getAttributeNames());
          if (names.isEmpty()) {
              
         RequestDispatcher requestDispatcher = request.getRequestDispatcher("/invalid");
        requestDispatcher.forward(request, response);
          }
         // Register JDBC driver
         Class.forName(JDBC_DRIVER);
         // Open a connection
         conn = DriverManager.getConnection(DB_URL, DBCredentials.USER, DBCredentials.PASS);
         // Execute SQL query
         stmt = conn.createStatement();
         String sql;
         String id = request.getParameter("ID");
         String fn = request.getParameter("First_Name");
         String ln = request.getParameter("Last_Name");
        String ph = request.getParameter("Phone_Number");
         String sa = request.getParameter("Shipping_Address");
         String sm = request.getParameter("Shipping_Method");
         String cn = request.getParameter("Card_Number");
         String ce = request.getParameter("Card_Expiration");
         String csc = request.getParameter("Card_Security_Code");
         String ba = request.getParameter("Billing_Address");
         String qu = request.getParameter("quantity");
         String zip = request.getParameter("zip");
         out.println("<h1>"+sm+"</h1>");
         String ses = session.getId();
         sql = "INSERT INTO users(idSes, firstName, lastName, phone, shippingAddress, shippingMethod, cardNumber,expirationDate, securityCode,"
                 + " billingAddress, quantity, guitarIDs, zip) VALUES(\""//'hey', 'hey', 'hey', 'hey', 'hey', 'hey', 'hey', 'hey', 'hey', 'hey', 'hey ok')";
                 + ses + "\", \"" + fn +"\", \""+ ln+"\", \"" + ph+"\", \"" + sa+"\", \"" + sm+"\", \"" + cn+"\", \"" + ce+"\", \"" + csc+"\", \"" + ba+"\", \"" + qu+"\", \"" + id +  "\", \""+zip+ "\")";
         //sql = "INSERT INTO users(firstName, lastName, phone) VALUES('hey', 'hola', " + ans+ ")";
         PreparedStatement pst = conn.prepareStatement(sql);
         pst.execute();
         
         RequestDispatcher requestDispatcher = request.getRequestDispatcher("/orderDetails");
        requestDispatcher.forward(request, response);
         
         
//         ResultSet rs = stmt.executeQuery(sql);
//         // Extract data from result set
//         while(rs.next()){
//            //Retrieve by column name
//            String firstName = rs.getString("firstName");
//            String lastName = rs.getString("lastName");
//            String email = rs.getString("email");
//
//            //Display values
//            out.println("Name: " + firstName + " " + lastName + ", Email: " + email + "<br>");
//         }
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


