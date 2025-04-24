package org.mycompany.bewerbungssystem.domain.bewerbung;

import java.time.LocalDateTime;

import org.mycompany.bewerbungssystem.domain.bewerber.BewerberEntity;
import org.mycompany.bewerbungssystem.domain.job.JobEntity;

import com.mycompany.bewerbung.BewerbungStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "BEWERBUNG")
public class BewerbungEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@NotNull(message = "Anschreiben darf nicht null sein.")
	@Size(min = 50, max = 1000, message = "Das Anschreiben muss zwischen 50 und 1000 Zeichen lang sein.")
	private String anschreiben;

	@Column(name = "EINGEREICHT_AM")
//	@NotNull(message = "Eingereicht am darf nicht null sein.")
	private LocalDateTime eingereichtAm;

//	@NotNull(message = "Status darf nicht null sein.")
	@Enumerated(EnumType.STRING)
	private BewerbungStatus status;

//	@NotNull(message = "Bewerber ID darf nicht null sein.")
	@ManyToOne
	@JoinColumn(name = "BEWERBER_ID")
	private BewerberEntity bewerber;

//	@NotNull(message = "Job ID darf nicht null sein.")
	@ManyToOne
	@JoinColumn(name = "JOB_ID")
	private JobEntity job;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnschreiben() {
		return anschreiben;
	}

	public void setAnschreiben(String anschreiben) {
		this.anschreiben = anschreiben;
	}

	public LocalDateTime getEingereichtAm() {
		return eingereichtAm;
	}

	public void setEingereichtAm(LocalDateTime eingereichtAm) {
		this.eingereichtAm = eingereichtAm;
	}

	public BewerbungStatus getStatus() {
		return status;
	}

	public void setStatus(BewerbungStatus status) {
		this.status = status;
	}

}
