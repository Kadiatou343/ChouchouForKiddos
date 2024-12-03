package com.chouchouforkiddos.servlet;

import com.chouchouforkiddos.bean.Parent;
import com.chouchouforkiddos.dao.ParentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe servlet pour les opérations effectuées sur les parents indépendamment des enfants.
 * Urls : parents, deleteParent
 * parents :
 * Elle charge tous les parents du système et les envoie vers la page d'affichage des parents
 * deleteParent :
 * Elle réçoit l'identifiant du parent à supprimer et supprime ce dernier.
 */

@WebServlet({"/parents", "/deleteParent"})
public class ParentServlet extends HttpServlet {
    ParentDAO parentDAO = new ParentDAO();
    ArrayList<Parent> parents = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();

        if (url.equals("/parents")) {
            parents = parentDAO.getAllParents();
            req.setAttribute("parents", parents);
            this.getServletContext().getRequestDispatcher("/parent.jsp").forward(req, resp);
        } else if (url.equals("/deleteParent")) {
            Parent par = parentDAO.getParentById(Integer.parseInt(req.getParameter("numPar")));
            parentDAO.delete(par);
            resp.sendRedirect("parents");
        }
    }
}
