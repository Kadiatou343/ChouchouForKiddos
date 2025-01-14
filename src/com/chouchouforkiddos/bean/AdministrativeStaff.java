package com.chouchouforkiddos.bean;

/**
 * Classe représentant un personnel administratif qui est un employé
 */
public class AdministrativeStaff extends Employee {
    /**
     * Le numéro de téléphone du personnel administratif
     */
    private String phoneNumber;
    /**
     * L'email professionnel du personnel administratif
     */
    private String emailProf;

    /**
     * Constructeur orienté coté code
     *
     * @param empLastName  Le nom de famille du personnel administratif
     * @param empFirstName Le prénom du personnel administratif
     * @param passwordHash Le hash du mot de passe du personnel administratif
     * @param phoneNumber  Le numéro de téléphone du personnel administratif
     * @param emailProf    L'email professionnel du personnel administratif
     */
    public AdministrativeStaff(String empLastName, String empFirstName, String passwordHash, String phoneNumber, String emailProf) {
        super(empLastName, empFirstName, passwordHash);
        this.phoneNumber = phoneNumber;
        this.emailProf = emailProf;
    }

    /**
     * Constrcteur orienté coté base de données
     *
     * @param numEmp       L'identifiant du personnel administratif
     * @param empLastName  Le nom de famille du personnel administratif
     * @param passwordHash Le hash du mot de passe du personnel administratif
     * @param empFirstName Le prénom du personnel administratif
     * @param phoneNumber  Le numéro de téléphone du personnel administratif
     * @param emailProf    L'email professionnel du personnel administratif
     */
    public AdministrativeStaff(int numEmp, String empLastName, String empFirstName, String passwordHash, String phoneNumber, String emailProf) {
        super(numEmp, empLastName, empFirstName, passwordHash);
        this.phoneNumber = phoneNumber;
        this.emailProf = emailProf;
    }

    //region Getters et Setters

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailProf() {
        return emailProf;
    }

    public void setEmailProf(String emailProf) {
        this.emailProf = emailProf;
    }

    //endregion

    /**
     * Override de la méthode toString pour afficher les information du personnel administratif
     *
     * @return String
     */
    @Override
    public String toString() {
        return super.toString() +
                "\tTéléphone : " + this.phoneNumber +
                "\tEmail Prof : " + this.emailProf;
    }
}
