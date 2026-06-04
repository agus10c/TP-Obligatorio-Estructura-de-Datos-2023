package Clases;

public class Ciudad {

    private int codigoPostal;
    private String nomCiudad;
    private String nomProvincia;
   
    public Ciudad(int cp, String nc, String np) {
        this.codigoPostal = cp;
        this.nomCiudad = nc;
        this.nomProvincia = np;
    }

    public int getCodigoPostal() {
        return this.codigoPostal;
    }

    public String getNomProvincia() {
        return this.nomProvincia;
    }

    public String getNomCiudad() {
        return this.nomCiudad;
    }

    public void setNomProvincia(String nombre) {
        this.nomProvincia = nombre;
    }

    public void setNomCiudad(String nombre) {
        this.nomCiudad = nombre;
    }

    public String toString() {
        return this.nomCiudad + "/ " + this.nomProvincia + " /Codigo Postal: " + this.codigoPostal;
    }
}
