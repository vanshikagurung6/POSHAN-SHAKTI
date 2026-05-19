import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.sql.*;
/**
 * Poshan Shakti - Login Servlet
 * Handles NGO user authentication via POST from login.html
 AT]/lib/servlet-api.jar WEB-INF/classes/LoginServlet.java
 */
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Get form fields — these now match login.html name attributes
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Basic validation
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            response.sendRedirect("login.html?error=empty");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                response.sendRedirect("login.html?error=db");
                return;
            }

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM ngo_users WHERE username = ? AND password = ?");
            ps.setString(1, username.trim());
            ps.setString(2, password.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Login successful — create session
                HttpSession session = request.getSession();
                session.setAttribute("loggedIn", true);
                session.setAttribute("username", username);
                response.sendRedirect("index.html");
            } else {
                // Login failed
                response.sendRedirect("login.html?error=invalid");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Database Error: " + e.getMessage() + "</h3>");
            out.println("<a href='login.html'>Go Back</a>");
        }
    }

    // Handle GET request (someone visiting /login directly)
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.html");
    }
}
