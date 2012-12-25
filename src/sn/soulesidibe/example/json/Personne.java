package sn.soulesidibe.example.json;

public class Personne {

    private int id;
    private String nom;
    private String age;
    private String adress;

    public Personne(int id, String nom, String prenom, String age, String adress) {
	this.id = id;
	this.nom = nom;
	this.age = age;
	this.adress = adress;
    }

    public Personne() {
	super();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
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
	return "Personne [id=" + id + ", nom=" + nom + ", age=" + age
		+ ", adress=" + adress + "]";
    }

}
