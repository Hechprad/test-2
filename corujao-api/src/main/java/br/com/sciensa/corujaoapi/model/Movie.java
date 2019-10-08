package br.com.sciensa.corujaoapi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.threeten.bp.OffsetDateTime;

@Entity
@Table(name = "movie_tb")
public class Movie implements Serializable {

	private static final long serialVersionUID = 9006144777923865184L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id = null;

	@NotEmpty(message = "Título é obrigatório")
	@Column(nullable = false)
	private String title;

	@Column
	private Integer releaseYear = null;

	@ManyToMany
	@JoinTable(	name="movie_has_genre", 
				joinColumns={@JoinColumn(name="movie_id")}, 
				inverseJoinColumns={@JoinColumn(name="genre_id")})
	private List<Genre> genres;

	@ManyToMany
	@JoinTable(	name="movie_has_artist", 
				joinColumns={@JoinColumn(name="movie_id")}, 
				inverseJoinColumns={@JoinColumn(name="artist_id")})
	private List<Artist> cast;

	@ManyToOne
	@JoinTable(	name="movie_has_director", 
				joinColumns={@JoinColumn(name="movie_id")}, 
				inverseJoinColumns={@JoinColumn(name="director_id")})
	private Artist director;

	// construtores para os testes
	public Movie() {}
	
	public Movie(String title, Integer releaseYear, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
		this.title = title;
		this.releaseYear = releaseYear;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Movie(Long id, String title, Integer releaseYear, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
		this.id = id;
		this.title = title;
		this.releaseYear = releaseYear;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	@Column
	private OffsetDateTime createdAt = null;

	@Column
	private OffsetDateTime updatedAt = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public Artist getDirector() {
		return director;
	}

	public void setDirector(Artist director) {
		this.director = director;
	}

	public List<Artist> getCast() {
		return cast;
	}

	public void setCast(List<Artist> cast) {
		this.cast = cast;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public OffsetDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(OffsetDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}