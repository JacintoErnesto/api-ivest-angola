package ucan.edu.api_sig_invest_angola.dtos.response;

import lombok.Getter;

@Getter
public class PaginatorDTO {
	private Integer pageNumber;
	private long totalElements;
	private Integer totalPages;

	public PaginatorDTO() {
	}
	
	public PaginatorDTO(Integer pageNumber, long totalElements, Integer totalPages) {
		this.pageNumber = pageNumber;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}

    public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

    public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
}
