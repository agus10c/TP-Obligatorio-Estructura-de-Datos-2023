package Clases;

import java.util.Objects;

public class IDCliente {

    private String tipoDoc;
    private int numDoc;

    public IDCliente(String td, int nd) {
        this.tipoDoc = td.toUpperCase();
        this.numDoc = nd;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public int getNumDoc() {
        return numDoc;
    }

    public boolean equals(Object obj) {
        IDCliente ic = (IDCliente) obj;
        return this.tipoDoc.equalsIgnoreCase(ic.getTipoDoc()) && (this.numDoc == ic.numDoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoDoc, numDoc);
    }

    public String toString() {
        return "Tipo de documento: " + this.tipoDoc + ", Numero de documento: " + this.numDoc;
    }

}
