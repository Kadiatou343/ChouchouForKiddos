package com.chouchouforkiddos.servlet;

import com.chouchouforkiddos.bean.Child;
import com.chouchouforkiddos.bean.Employee;
import com.chouchouforkiddos.bean.Inscription;
import com.chouchouforkiddos.dao.InscriptionDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe servlet pour les opéraions effectuées sur les inscription.
 * Urls : makeInscri, deleteInscri, voirInscri
 * voirInscri :
 * Elle charge toutes les inscriptions du système et les envoie vers la page d'affichage des inscriptions
 * makeInscri :
 * Elle gère la reponse au formulaire d'inscription
 * deleteInscri :
 * Elle reçoit l'identifiant de l'inscription à supprimer et supprime cette inscription du système
 */

@WebServlet({"/makeInscri", "/deleteInscri", "/voirInscri"})
public class InscriptionServlet extends HttpServlet {
    InscriptionDAO insDAO = new InscriptionDAO();
    ArrayList<Inscription> inscriptions = new ArrayList<>();
    Inscription insc;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        switch (url) {
            case "/voirInscri":
                inscriptions = insDAO.getAllInscriptions();
                req.setAttribute("inscriptions", inscriptions);
                this.getServletContext().getRequestDispatcher("/inscription.jsp").forward(req, resp);
                break;
            case "/deleteInscri":
                insc = insDAO.getInscriptionByIds(Integer.parseInt(req.getParameter("numEmp")),
                        Integer.parseInt(req.getParameter("numChild")));
                insDAO.deleteInscription(insc);
                resp.sendRedirect("voirInscri");
                break;
            default:
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = "";
        if (req.getServletPath().equals("/makeInscri")) {
            Employee emp = insDAO.getEmployeeDAO().getById(
                    Integer.parseInt(req.getParameter("numEmp")));
            Child child = insDAO.getChildDAO().getChildById(
                    Integer.parseInt(req.getParameter("numChild")));
            try {
                insc = new Inscription(child, emp);
                insDAO.createInscription(insc);
            } catch (NullPointerException e) {
                System.err.println("Erreur : L'employé ou l'enfant est introuvable : " + e.getMessage());
                message = "L'Id de l'employé ou de l'enfant est introuvable dans le système!";
            }
            req.setAttribute("message", message);
            this.getServletContext().getRequestDispatcher("/inscription.jsp").forward(req, resp);
        }
    }
}
