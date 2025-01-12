package ru.leonchik.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PressureDao;
import ru.leonchik.dao.PressureDaoImpl;
import ru.leonchik.entiti.Pressure;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@WebServlet(urlPatterns = {"/addpressure"})
public class AddPressure extends HttpServlet {

    protected PressureDao pressureDao = new PressureDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        int userId = Integer.parseInt(req.getParameter("userid"));

        Pressure p = new Pressure();

        p.setPatientId(Long.parseLong(req.getParameter("userid")));
        p.setSys(Integer.parseInt(req.getParameter("sys")));
        p.setDia(Integer.parseInt(req.getParameter("dia")));
        p.setPulse(Integer.parseInt(req.getParameter("pulse")));
        // 2024-08-03T20:30
        DateTimeFormatter formatter = ISO_LOCAL_DATE_TIME;
        p.setDtm(LocalDateTime.parse(req.getParameter("dtm"),formatter));

        pressureDao.insert(userId, p);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");

        out.println("<head>");
//        out.println("<link rel=\"stylesheet\" href=\"styles.css\">");
//        out.println("<meta http-equiv=\"refresh\" content=\"2;URL=http://localhost:8080/pressure/show">");

        out.println("<head>");

        out.println("<body>");
        out.println("userid: " + userId);
        out.println("<br>dtm: " + req.getParameter("dtm") + "<br><br>");
//        out.println(p.toString());
        out.println("<p><a href=\"/pressure/show?usr=" + userId + "&period=14\">Вернуться</a></p>");
        out.println("</body>");

        out.println("</html>");
    }
}
