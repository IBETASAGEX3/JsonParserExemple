package sn.soulesidibe.example.json;

public class Personne {

    private String nom;
    private String age;
    private String adress;

    public Personne(String nom, String prenom, String age, String adress) {
	super();
	this.nom = nom;
	this.age = age;
	this.adress = adress;
    }

    public Personne() {
	super();
    }

    public String getNom() {
	return nom;
    }

    public void setNom(String nom) {
	this.nom = nom;
    }

    public String getAge() {
	return age;
    }

    public void setAge(String age) {
	this.age = age;
    }

    public String getAdress() {
	return adress;
    }

    public void setAdress(String adress) {
	this.adress = adress;
    }

    @Override
    public String toString() {
	return "Personne [nom=" + nom + ", age=" + age + ", adress=" + adress
		+ "]";
    }

}
