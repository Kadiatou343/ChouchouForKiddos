package com.chouchouforkiddos.servlet;

import com.chouchouforkiddos.bean.Employee;
import com.chouchouforkiddos.dao.EmployeeDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe servlet pour les opérations effectués sur les employés.
 * Url : employees .
 * Elle charge tous les employés du système et les envoie vers la page d'affichage des employés.
 */

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    EmployeeDAO empDAO = new EmployeeDAO();
    ArrayList<Employee> employees = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        employees = empDAO.getAll();
        req.setAttribute("employees", employees);
        this.getServletContext().getRequestDispatcher("/employee.jsp").forward(req, resp);
    }
}
