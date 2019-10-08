package br.com.sciensa.corujaoapi.model;

import org.threeten.bp.OffsetDateTime;

public class ResponseMeta {

	private String version = null;

	private OffsetDateTime timestamp = null;

	private String hostname = null;

	private Integer numberOfRecords = null;

	private Integer page = null;

	private Integer size = null;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}
