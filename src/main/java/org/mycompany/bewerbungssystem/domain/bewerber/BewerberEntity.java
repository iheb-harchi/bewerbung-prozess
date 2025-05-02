package org.mycompany.bewerbungssystem.domain.bewerber;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "BEWERBER")
public class BewerberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

//	@NotNull(message = "Vorname darf nicht null sein.")
	@Size(min = 1, max = 255, message = "Der Vorname muss zwischen 1 und 255 Zeichen lang sein.")
	private String vorname;

//	@NotNull(message = "Nachname darf nicht null sein.")
	@Size(min = 1, max = 255, message = "Der Nachname muss zwischen 1 und 255 Zeichen lang sein.")
	private String nachname;

//	@NotNull(message = "Geburtsdatum darf nicht null sein.")
	private LocalDate geburtsdatum;

//	@NotNull(message = "E-Mail-Adresse darf nicht null sein.")
//	@Email(message = "Die E-Mail-Adresse muss gültig sein.")
	@Size(max = 255, message = "Die E-Mail-Adresse darf maximal 255 Zeichen lang sein.")
	private String email;

	@Size(max = 20, message = "Die Telefonnummer darf maximal 20 Zeichen lang sein.")
	private String telefon;

	@OneToMany(mappedBy = "bewerber", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<BewerbungEntity> bewerbungen = new ArrayList<>();


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public LocalDate getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(LocalDate geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
}
