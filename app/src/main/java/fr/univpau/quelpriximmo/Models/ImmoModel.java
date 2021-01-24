package fr.univpau.quelpriximmo.Models;

public class ImmoModel {
    private int id ;
    private double prix;
    private String adresse;
    private int nb_pieces;
    private double[] coords;
    private double distance;
    private String type_bien;
    //une image ??


    public void setType_bien(String type_bien) {
        this.type_bien = type_bien;
    }

    public String getType_bien() {
        return type_bien;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setAdress(String adresse) {
        this.adresse = adresse;
    }

    public void setNb_pieces(int nb_pieces) {
        this.nb_pieces = nb_pieces;
    }

    public void setCoords(double longitude, double latitude) {
        this.coords = new double[2];
        this.coords[0] = longitude;
        this.coords[1] = latitude;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setID(int id){this.id = id;}

    public double getPrix() {
        return prix;
    }

    public String getAdress() {
        return adresse;
    }

    public int getNb_pieces() {
        return nb_pieces;
    }

    public double[] getCoords() {
        return coords;
    }

    public double getDistance() {
        return distance;
    }

    public int getId(){return id;}

    public double getLongitude(){
        return this.coords[0];
    }
    public double getLatitude(){
        return this.coords[1];
    }
}
