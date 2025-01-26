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

@WebServlet(urlPatterns = {"/add"})
public class AddPatient extends HttpServlet {
    protected PatientDao dao = new PatientDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String pname = req.getParameter("pname");
        String dd = req.getParameter("dd");
        String mm = req.getParameter("mm");
        String yyyy = req.getParameter("yyyy");

        Patient patient = new Patient(pname, LocalDate.of(Integer.parseInt(yyyy), Integer.parseInt(mm), Integer.parseInt(dd)));
        dao.insert(patient);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");

        out.println("<h3 align=\"center\">Новый пользователь добавлен</h3>");

        out.println("Имя: <b>" + pname + "</b><br>" );
        out.println("Дата рождения: <b>" + dd + "." + mm + "." + yyyy + "</b>");

        out.println("<a href=\"./view\">Вернуться</a>");

        out.println("</body>");
        out.println("</html>");
    }
}
