package org.mycompany.bewerbungssystem.application.validation;

import java.util.Collections;
import java.util.List;

public final class ValidationResult {
	private final boolean valid;
	private final List<String> errors;

	// Private constructor - use factory methods
	private ValidationResult(boolean valid, List<String> errors) {
		this.valid = valid;
		this.errors = Collections.unmodifiableList(errors);
	}

	// Factory method for successful validation
	public static ValidationResult success() {
		return new ValidationResult(true, List.of());
	}

	// Factory method for failed validation
	public static ValidationResult failed(List<String> errors) {
		return new ValidationResult(false, errors);
	}

	// Factory method for single error case
	public static ValidationResult error(String errorMessage) {
		return new ValidationResult(false, List.of(errorMessage));
	}

	public boolean isValid() {
		return valid;
	}

	public List<String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "ValidationResult{" + "valid=" + valid + ", errors=" + errors + '}';
	}
}