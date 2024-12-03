package com.chouchouforkiddos.servlet;

import com.chouchouforkiddos.bean.Employee;
import com.chouchouforkiddos.dao.ChildDAO;
import com.chouchouforkiddos.dao.EmployeeDAO;
import com.chouchouforkiddos.util.PasswordHasher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

/**
 * Classe servelt pour l'index du programme (pages principales).
 * Urls : index, home, login, logout .
 * index :
 * Elle gère le chargement de la page de login (page qui ouverte par defaut dans le contexte /).
 * home :
 * Elle recupère le nombre d'enfants et d'employés dans le système et les envoie vers la page d'accueil.
 * login :
 * Elle gère la reponse au formulaire de connexion au système, redirige vers la page d'accueil
 * au cas ou les informations sont validées, sinon envoie un message d'erreur vers la page de connexion.
 * logout :
 * Elle gère le processus de deconnexion en ramenant vers la page de connexion
 */

@WebServlet({"/index", "/home", "/login", "/logout"})
public class IndexServlet extends HttpServlet {
    ChildDAO childDAO = new ChildDAO();
    EmployeeDAO empDAO = new EmployeeDAO();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        switch (url) {
            case "/home":
                Integer count = childDAO.getChildrenCount();
                Integer empCount = empDAO.getEmployeesCount();
                req.setAttribute("count", count);
                req.setAttribute("empCount", empCount);
                this.getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
                break;
            case "/index":
                this.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                break;
            case "/logout":
                resp.sendRedirect("index");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/login")) {
            Employee emp = empDAO.getById(Integer.parseInt(req.getParameter("numEmp")));
            try {
                if (PasswordHasher.validatePassword(req.getParameter("password"), emp.getPasswordHash())) {
                    resp.sendRedirect("home?emp=" + URLEncoder.encode(emp.getEmpFirstName() + " " + emp.getEmpLastName(), "UTF-8"));
                } else {
                    req.setAttribute("message", "Le mot de passe est incorrect");
                    this.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            } catch (NullPointerException e) {
                req.setAttribute("message", "Le num employé n'est pas valide");
                this.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Erreur lors de la validation du mot de passe. " + e.getMessage());
            }
        }
    }
}
