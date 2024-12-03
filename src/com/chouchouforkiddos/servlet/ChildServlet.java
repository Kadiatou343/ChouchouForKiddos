package com.chouchouforkiddos.servlet;

import com.chouchouforkiddos.bean.Child;
import com.chouchouforkiddos.dao.ChildDAO;
import com.chouchouforkiddos.dao.ParentDAO;
import com.chouchouforkiddos.bean.Parent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe servlet pour les opérations effectuées sur les enfants.
 * Urls : children, addChild, editChild, updateChild, deleteChild .
 * children :
 * Elle effectue le chargement de tous les enfants du système et les envoyer vers la page d'affichage des enfants
 * editChild :
 * Elle reçoit l'identifiant de l'enfant à modifier et envoie cet enfant à la page de modification
 * deleteChild :
 * Elle reçoit l'identifiant de l'enfant à supprimer et le supprime et redirige vers la page d'accueil
 * addChild :
 * Elle gère la reponse au formulaire d'ajout d'un enfant via post
 * updateChild :
 * Elle gère la reponse au formulaire de modification d'un parent via post
 */

@WebServlet({"/children", "/addChild", "/editChild", "/updateChild", "/deleteChild"})
public class ChildServlet extends HttpServlet {
    ChildDAO childDAO = new ChildDAO();
    ParentDAO parentDAO = new ParentDAO();
    ArrayList<Child> children = new ArrayList<>();
    HashMap<Child, String> childrenSection = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        Child child;
        switch (url) {
            case "/children":
                children = childDAO.getAllChildren();
                for (Child chd : children) {
                    childrenSection.put(chd, chd.assignSection(chd.getChildAge()));
                }
                req.setAttribute("sections", childrenSection);
                req.setAttribute("children", children);
                this.getServletContext().getRequestDispatcher("/child.jsp").forward(req, resp);
                break;
            case "/editChild":
                child = childDAO.getChildById(Integer.parseInt(req.getParameter("numChild")));
                req.setAttribute("child", child);
                this.getServletContext().getRequestDispatcher("/addChild.jsp").forward(req, resp);
                break;
            case "/deleteChild":
                child = childDAO.getChildById(Integer.parseInt(req.getParameter("numChild")));
                childDAO.delete(child);
                resp.sendRedirect("home");
                break;
            default:
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getServletPath();
        Parent par;
        Child child;
        switch (url) {
            case "/addChild":
                String exist = req.getParameter("exist");

                if (exist != null && exist.equals("vrai")) {
                    par = parentDAO.getParentById(Integer.parseInt(req.getParameter("numPar")));
                } else {
                    par = new Parent(Integer.parseInt(req.getParameter("numPar")), req.getParameter("lastNameP"),
                            req.getParameter("firstNameP"), req.getParameter("contact"));
                    parentDAO.create(par);
                }

                child = new Child(req.getParameter("lastNameE"), req.getParameter("firstNameE"),
                        Integer.parseInt(req.getParameter("age")), par);
                try {
                    childDAO.createChild(child);
                } catch (NullPointerException e) {
                    req.setAttribute("message", "L'id du parent est introuvable dans le système.");
                }

                this.getServletContext().getRequestDispatcher("/addChild.jsp").forward(req, resp);

                break;
            case "/updateChild":
                par = new Parent(Integer.parseInt(req.getParameter("numParHidden")),
                        req.getParameter("lastNameP"), req.getParameter("firstNameP"),
                        req.getParameter("contact"));
                parentDAO.update(par);
                child = new Child(Integer.parseInt(req.getParameter("numChild")),
                        req.getParameter("lastNameE"), req.getParameter("firstNameE"),
                        Integer.parseInt(req.getParameter("age")), par);
                childDAO.update(child);

                resp.sendRedirect("children");
            default:
        }
    }
}
