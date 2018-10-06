/**
 * 
 */
package ma.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author TOBORI
 *
 */
@Embeddable
public class Adresse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String rue;
	private String numero;
	private String ville;
	private String boitePostal;
	@ManyToOne
	private Personne personne;

	public Adresse() {
		super();
	}

	/**
	 * @param id
	 * @param rue
	 * @param numero
	 * @param ville
	 * @param boitePostal
	 */
	public Adresse(Long id, String rue, String numero, String ville, String boitePostal) {
		super();
		this.id = id;
		this.rue = rue;
		this.numero = numero;
		this.ville = ville;
		this.boitePostal = boitePostal;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rue
	 */
	public String getRue() {
		return rue;
	}

	/**
	 * @param rue
	 *            the rue to set
	 */
	public void setRue(String rue) {
		this.rue = rue;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the ville
	 */
	public String getVille() {
		return ville;
	}

	/**
	 * @param ville
	 *            the ville to set
	 */
	public void setVille(String ville) {
		this.ville = ville;
	}

	/**
	 * @return the boitePostal
	 */
	public String getBoitePostal() {
		return boitePostal;
	}

	/**
	 * @param boitePostal
	 *            the boitePostal to set
	 */
	public void setBoitePostal(String boitePostal) {
		this.boitePostal = boitePostal;
	}

	/**
	 * @return the personne
	 */
	public Personne getPersonne() {
		return personne;
	}

	/**
	 * @param personne
	 *            the personne to set
	 */
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

}
