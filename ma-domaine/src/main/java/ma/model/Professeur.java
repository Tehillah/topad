/**
 * 
 */
package ma.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author TOBORI
 *
 */
@Entity
@Table(name = "professeur")
public class Professeur extends Personne {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Set<String> listeNomClasse;
	@OneToMany(mappedBy = "professeur")
	private Set<Etudiant> etudians;
	private String nomMatier;

	/**
	 * 
	 */
	public Professeur() {
		super();
	}

	/**
	 * @param listeNomClasse
	 * @param etudians
	 * @param nomMatier
	 */
	public Professeur(Set<String> listeNomClasse, Set<Etudiant> etudians, String nomMatier) {
		super();
		this.listeNomClasse = listeNomClasse;
		this.etudians = etudians;
		this.nomMatier = nomMatier;
	}

	/**
	 * @return the listeNomClasse
	 */
	public Set<String> getListeNomClasse() {
		return listeNomClasse;
	}

	/**
	 * @param listeNomClasse
	 *            the listeNomClasse to set
	 */
	public void setListeNomClasse(Set<String> listeNomClasse) {
		this.listeNomClasse = listeNomClasse;
	}

	/**
	 * @return the etudians
	 */
	public Set<Etudiant> getEtudians() {
		return etudians;
	}

	/**
	 * @param etudians
	 *            the etudians to set
	 */
	public void setEtudians(Set<Etudiant> etudians) {
		this.etudians = etudians;
	}

	/**
	 * @return the nomMatier
	 */
	public String getNomMatier() {
		return nomMatier;
	}

	/**
	 * @param nomMatier
	 *            the nomMatier to set
	 */
	public void setNomMatier(String nomMatier) {
		this.nomMatier = nomMatier;
	}

}
