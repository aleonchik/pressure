package ru.leonchik.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.leonchik.dao.PatientDao;
import ru.leonchik.dao.PatientDaoImpl;
import ru.leonchik.entiti.Patient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {"/view"})
public class First extends HttpServlet {

    protected PatientDao dao = new PatientDaoImpl();

    public First() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        int SELECTED_USER_ID = 2;
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
//                    SELECTED_USER_ID = userId;
                }
            }
        }



        /*Logger LOG = Logger.getLogger(First.class.getName());
        LOG.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        LOG.addHandler(ch);
        SimpleFormatter sf = new SimpleFormatter();
        ch.setFormatter(sf);*/

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");

        out.println("<h3>Все пользователи</h3>");

        out.println("<form method=\"POST\" action=\"show\">");
        out.println("<p>");
//        out.println("<label for=\"select-user\">Выберите пользователя:</label>");
        out.println("<select name=\"usr\" id=\"select-user\">");
        out.println("<option value=''>-- Выберите пользователя --</option>");




        List<Patient> pat;
        pat = dao.all();

        for (Patient pt : pat) {
            int uId = pt.getId();

            if (uId == userId)
                out.println("<option value='" + uId + "' selected>" + pt.getName() + "</option>");
            else
                out.println("<option value='" + uId + "'>" + pt.getName() + "</option>");
        }

        out.println("</select>");

//        out.println(" <b>|</b> <a href=\"\">Показать данные</a> ");
        out.println("<a href=\"\">Редактировать</a> ");
        out.println("<b>|</b> <a href=\"add_patient_form.html\">Добавить</a>");
        out.println("</p>");

        out.println("<p>");
        out.println("<fieldset>");

        out.println("<input type=\"radio\" name=\"period\" id=\"last_7\" value=\"7\">");
        out.println("<label for=\"last_7\">7 дней</label><br>");

        out.println("<input type=\"radio\" name=\"period\" id=\"last_14\" value=\"14\" checked>");
        out.println("<label for=\"last_14\">14 дней</label><br>");

        out.println("<input type=\"radio\" name=\"period\" id=\"last_30\" value=\"30\">");
        out.println("<label for=\"last_30\">30 дней</label>");

        out.println("</fieldset>");
        out.println("</p>");

        out.print("<input type=\"submit\" value=\"Показать\">");
        out.println("<form>");

        out.println("</body>");
        out.println("</html>");

//        LOG.info("Отработал сервлет");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        doGet(req, resp);
    }
}
