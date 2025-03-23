package com.fiap.framesnap.crosscutting.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class ValidationErrorResponse {

	private List<Violation> violations = new ArrayList<>();

	public void add(Violation violation) {
		this.violations.add(violation);
	}
	
	public void addAll(Violation[] violations) {
		this.violations.addAll(Arrays.asList(violations));
	}

}
