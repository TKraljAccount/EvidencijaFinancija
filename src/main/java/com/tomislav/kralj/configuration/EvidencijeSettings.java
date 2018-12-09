package com.tomislav.kralj.configuration;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@Component
@ConfigurationProperties(prefix="evidencije")
public class EvidencijeSettings {

	@NotNull
	private int page_size;
	@NotNull
	private String date_format;
	private EvidencijeEntities sortField = new EvidencijeEntities();
	private EvidencijeEntities sortDirection = new EvidencijeEntities();
	
	
	@Data
	public class EvidencijeEntities {
		private String person;
		private String subTransaction;
		private String transaction;
		private String transactionType;
	}
	
}
