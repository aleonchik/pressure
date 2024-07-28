package ru.leonchik.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PatientDao;
import ru.leonchik.dao.PatientDaoImpl;
import ru.leonchik.dao.PressureDao;
import ru.leonchik.dao.PressureDaoImpl;
import ru.leonchik.entiti.Patient;
import ru.leonchik.entiti.Pressure;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

@WebServlet(urlPatterns = {"/show"})
public class Show extends HttpServlet {

    protected PatientDao patientDao = new PatientDaoImpl();
    protected PressureDao pressureDao = new PressureDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Получим араметры
        int userId = Integer.parseInt(req.getParameter("usr"));
        String period = req.getParameter("period");

        Patient patient;
        List<Pressure> pressureList;

        // Получим имя
        patient = patientDao.single(userId);
        // Получим данные давления определенного пациента
        pressureList = pressureDao.all(userId, 14);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");

        out.println("<h3>Данные для " + patient.getName() + "</h3>");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("EEEE d MMMM u HH:MM");

        if (!pressureList.isEmpty()) {
            out.println("<table>");
            for (Pressure p : pressureList) {
                LocalDateTime dtm = p.getDtm();

                out.println("<tr>");
                out.println("<td>" + p.getDia() + "</td>");
                out.println("<td>" + p.getSys() + "</td>");
                out.println("<td>" + p.getPulse() + "</td>");
                out.println("<td>" + dtm.format(fmt) + "</td>");
//                out.println("<td>" + p.getDtm() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } else {
            out.println("<h3>Данные отсутствуют</h3>");
        }

        out.println("<a href='./view'>Вернуться</a>");

        out.println("</body>");
        out.println("</html>");
    }
}
