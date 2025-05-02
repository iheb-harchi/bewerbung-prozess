package org.mycompany.bewerbungssystem.domain.job;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.mycompany.bewerbungssystem.domain.bewerbung.BewerbungEntity;

import com.mycompany.job.JobStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "JOBS")
public class JobEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Titel darf nicht null sein.")
	@Size(min = 3, max = 255, message = "Der Titel muss zwischen 3 und 255 Zeichen lang sein.")
	private String titel;

	@NotNull(message = "Beschreibung darf nicht null sein.")
	@Size(min = 10, max = 500, message = "Die Beschreibung muss zwischen 10 und 500 Zeichen lang sein.")
	private String beschreibung;

	@NotNull(message = "Status darf nicht null sein.")
	@Enumerated(EnumType.STRING)
	private JobStatus status;

	@NotNull(message = "Gültig ab darf nicht null sein.")
	@Column(name = "GUELTIG_AB")
	private LocalDate gueltigAb;

	@Column(name = "GUELTIG_BIS")
	@NotNull(message = "Gültig bis darf nicht null sein.")
	private LocalDate gueltigBis;

	@OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<BewerbungEntity> bewerbungen = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}

	public LocalDate getGueltigAb() {
		return gueltigAb;
	}

	public void setGueltigAb(LocalDate gueltigAb) {
		this.gueltigAb = gueltigAb;
	}

	public LocalDate getGueltigBis() {
		return gueltigBis;
	}

	public void setGueltigBis(LocalDate gueltigBis) {
		this.gueltigBis = gueltigBis;
	}

}
