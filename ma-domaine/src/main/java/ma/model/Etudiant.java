/**
 * 
 */
package ma.model;

import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author TOBORI
 *
 */
@Entity
@Table(name = "etudiant")
public class Etudiant extends Personne {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nomClasse;
	private Set<String> listeMatiere;
	@ManyToOne
	private Professeur professeur;
	private Map<String, String> notes;

	/**
	 * 
	 */
	public Etudiant() {
		super();
	}

	/**
	 * @param nomClasse
	 * @param listeMatiere
	 * @param professeur
	 * @param notes
	 */
	public Etudiant(String nomClasse, Set<String> listeMatiere, Professeur professeur, Map<String, String> notes) {
		super();
		this.nomClasse = nomClasse;
		this.listeMatiere = listeMatiere;
		this.professeur = professeur;
		this.notes = notes;
	}

	/**
	 * @return the nomClasse
	 */
	public String getNomClasse() {
		return nomClasse;
	}

	/**
	 * @param nomClasse
	 *            the nomClasse to set
	 */
	public void setNomClasse(String nomClasse) {
		this.nomClasse = nomClasse;
	}

	/**
	 * @return the listeMatiere
	 */
	public Set<String> getListeMatiere() {
		return listeMatiere;
	}

	/**
	 * @param listeMatiere
	 *            the listeMatiere to set
	 */
	public void setListeMatiere(Set<String> listeMatiere) {
		this.listeMatiere = listeMatiere;
	}

	/**
	 * @return the professeur
	 */
	public Professeur getProfesseur() {
		return professeur;
	}

	/**
	 * @param professeur
	 *            the professeur to set
	 */
	public void setProfesseur(Professeur professeur) {
		this.professeur = professeur;
	}

	/**
	 * @return the notes
	 */
	public Map<String, String> getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(Map<String, String> notes) {
		this.notes = notes;
	}

}
