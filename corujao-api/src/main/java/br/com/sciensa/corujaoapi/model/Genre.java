package br.com.sciensa.corujaoapi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.threeten.bp.OffsetDateTime;

@Entity
@Table(name="genre_tb")
public class Genre implements Serializable {

	private static final long serialVersionUID = -3644154780671051785L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@NotEmpty
	private String description;
	
	@Column
	private OffsetDateTime createdAt;
	
	@Column
	private OffsetDateTime updatedAt;
	
	@ManyToMany(mappedBy = "genres")
	private List<Movie> movies;
	
	// construtores para os testes
	public Genre() {}
	
	public Genre(String description, 
			OffsetDateTime createdAt, 
			OffsetDateTime updatedAt) {
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Genre(Long id, 
			String description, 
			OffsetDateTime createdAt, 
			OffsetDateTime updatedAt) {
		this.id = id;
		this.description = description;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(OffsetDateTime createAt) {
		this.createdAt = createAt;
	}
	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(OffsetDateTime updateAt) {
		this.updatedAt = updateAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((movies == null) ? 0 : movies.hashCode());
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (movies == null) {
			if (other.movies != null)
				return false;
		} else if (!movies.equals(other.movies))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}
	
}
