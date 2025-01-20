package ru.leonchik.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PatientDao;
import ru.leonchik.dao.PatientDaoImpl;
import ru.leonchik.entiti.Patient;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

@WebServlet(urlPatterns = {"/delpatient"})
public class DelPatient extends HttpServlet {

    protected PatientDao dao = new PatientDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        int userId = Integer.parseInt(req.getParameter("usr"));

        Patient pat = new Patient();

        pat = dao.single(userId);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");

        out.println("<h3 align=\"center\">Удалить пользователя " + pat.getName() + " вместе с его данными</h3>");

        out.println("<a href=\"/pressure/view\">Вернуться</a>");

        out.println("</body>");
        out.println("</html>");
    }
}
