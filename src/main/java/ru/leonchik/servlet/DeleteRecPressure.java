package ru.leonchik.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PressureDao;
import ru.leonchik.dao.PressureDaoImpl;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/delrec"})
public class DeleteRecPressure extends HttpServlet {

    protected PressureDao pressureDao = new PressureDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        int recId = Integer.parseInt(req.getParameter("id"));

        int userId = 0;
        Cookie cookieUsserId = null;
        Cookie[] cookies = null;

        // Read cookieUserId
        cookies = req.getCookies();

        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookieUsserId = cookies[i];
                if (cookieUsserId.getName().equals("userId")) {
                    userId = Integer.parseInt(cookieUsserId.getValue());
                }
            }
        }

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");

        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"styles.css\">");
        out.println("<head>");

        out.println("<body>");

        pressureDao.delete(recId);

        out.println("<h3>" + recId + "</h3>");

//        out.println("<p><a href=\"/pressure/show?usr=" + userId + "&period=14\">Вернуться</a></p>");
        out.println("<p><a href=\"/pressure/show?usr=" + userId + "&period=14\">Вернуться</a></p>");

        out.println("</body>");
    }

}
