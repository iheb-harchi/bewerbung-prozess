package org.mycompany.bewerbungssystem.domain.bewerbung.history;

import java.time.LocalDateTime;

import org.mycompany.common.BewerbungStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "BEWERBUNG_H")
public class BewerbungHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull
	@Column(name = "BEWERBUNG_ID", nullable = false)
    private Long bewerbungId;

	@NotNull
    @Enumerated(EnumType.STRING)
	@Column(name = "ALTER_STATUS", nullable = false)
	private BewerbungStatus alterStatus;

	@NotNull
    @Enumerated(EnumType.STRING)
	@Column(name = "NEUER_STATUS", nullable = false)
	private BewerbungStatus neuerStatus;

	@NotNull
	@Column(name = "GEAENDERT_AM", nullable = false)
    private LocalDateTime geaendertAm;

	@Column(name = "GEAENDERT_VON")
	private String geaendertVon;

	@Column(name = "BEWERBERT_BENACHRICHTIGT")
	private boolean bewerberBenachrichtigt;

    public BewerbungHistoryEntity() {}

	public BewerbungHistoryEntity(Long bewerbungId, BewerbungStatus alterStatus, BewerbungStatus neuerStatus,
			LocalDateTime geaendertAm,
			String geaendertVon, boolean bewerberBenachrichtigt) {
        this.bewerbungId = bewerbungId;
        this.alterStatus = alterStatus;
        this.neuerStatus = neuerStatus;
        this.geaendertAm = geaendertAm;
        this.geaendertVon = geaendertVon;
		this.bewerberBenachrichtigt = bewerberBenachrichtigt;
    }

	// Getter & Setter

	public Long getId() {
		return id;
	}

	public Long getBewerbungId() {
		return bewerbungId;
	}

	public void setBewerbungId(Long bewerbungId) {
		this.bewerbungId = bewerbungId;
	}

	public BewerbungStatus getAlterStatus() {
		return alterStatus;
	}

	public void setAlterStatus(BewerbungStatus alterStatus) {
		this.alterStatus = alterStatus;
	}

	public BewerbungStatus getNeuerStatus() {
		return neuerStatus;
	}

	public void setNeuerStatus(BewerbungStatus neuerStatus) {
		this.neuerStatus = neuerStatus;
	}

	public LocalDateTime getGeaendertAm() {
		return geaendertAm;
	}

	public void setGeaendertAm(LocalDateTime geaendertAm) {
		this.geaendertAm = geaendertAm;
	}

	public String getGeaendertVon() {
		return geaendertVon;
	}

	public void setGeaendertVon(String geaendertVon) {
		this.geaendertVon = geaendertVon;
	}

	public boolean isBewerberBenachrichtigt() {
		return bewerberBenachrichtigt;
	}

	public void setBewerberBenachrichtigt(boolean bewerberBenachrichtigt) {
		this.bewerberBenachrichtigt = bewerberBenachrichtigt;
	}
}
