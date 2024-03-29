package br.com.sciensa.corujaoapi.endpoints;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.OffsetDateTime;

import br.com.sciensa.corujaoapi.model.Artist;
import br.com.sciensa.corujaoapi.model.Movie;
import br.com.sciensa.corujaoapi.repository.ArtistRepository;
import br.com.sciensa.corujaoapi.repository.MovieRepository;

@RestController
@RequestMapping("v1")
public class ArtistEndPoint {

	@Autowired
	ArtistRepository repository;

	@Autowired
	MovieRepository movieRepository;
	
    @PostMapping(value = "/artists")
    public ResponseEntity<Artist> addartist(@Valid @RequestBody Artist artist){
    	artist = addCreatedAndUpdated(artist);
    	return new ResponseEntity<Artist>(repository.save(artist), HttpStatus.CREATED);
    };

	@GetMapping(value = "/artists/{artistId}")
    public ResponseEntity<Optional<Artist>> getartist(@PathVariable("artistId") Long artistId){
    	return new ResponseEntity<Optional<Artist>>(repository.findById(artistId), HttpStatus.OK);
    };

    @GetMapping(value = "/artists")
    public ResponseEntity<Page<Artist>> listartists(@Valid @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @Valid @RequestParam(value = "size", required = false, defaultValue="10") Integer size, @Valid @RequestParam(value = "search", required = false) String search, Pageable pageable){
    	if(search != null) { return searchName(search,  pageable); }
    	return new ResponseEntity<Page<Artist>>(repository.findAll(pageable), HttpStatus.OK);
    };

    
    
    @GetMapping(value = "/artists/{artistId}/filmography")
    public ResponseEntity<List<Movie>> getFilmography(Long artistId) {
		return new ResponseEntity<List<Movie>>(buscaFilmesDoArtista(artistId), HttpStatus.OK);
	}
    
    @PutMapping(value = "/artists/{artistId}")
    public ResponseEntity<?> updateArtist(@PathVariable("artistId") Long artistId, @Valid @RequestBody Artist artist){
    	return new ResponseEntity<Artist>(update(artistId, artist), HttpStatus.CREATED);
    };
    
    
    private ResponseEntity<Page<Artist>> searchName(String search, Pageable pageable) {
    	Iterable<Artist> artists = repository.findAll();

		List<Artist> artistsFiltrados = new ArrayList<Artist>();
		artists.forEach(artist -> {
			if (removeAcento(artist.getFirstName() + " " + artist.getLastName()).toLowerCase()
					.contains(removeAcento(search.toLowerCase())))
				artistsFiltrados.add(artist);
		});

		// convertendo List para page
		Page<Artist> page = new PageImpl<>(artistsFiltrados, pageable, artistsFiltrados.size());
		return new ResponseEntity<Page<Artist>>(page, HttpStatus.OK);
	}

    private static String removeAcento(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}
    
    private @Valid Artist addCreatedAndUpdated(@Valid Artist artist) {
    	artist.setCreatedAt(OffsetDateTime.now());
		artist.setUpdatedAt(OffsetDateTime.now());
		return artist;
	}

    private Artist update(Long artistId, Artist artist) {
		Optional<Artist> artistWillUpdate = repository.findById(artistId);
		artistWillUpdate.ifPresent(artistOpt -> {
			artistOpt.setFirstName(artist.getFirstName());
			artistOpt.setLastName(artist.getLastName());
			artistOpt.setDateOfBirth(artist.getDateOfBirth());
			artistOpt.setUpdatedAt(OffsetDateTime.now());
		});
		Artist artistEntity = artistWillUpdate.get();
		artistEntity = repository.save(artistEntity);

		return artistEntity;
	}
    
    private List<Movie> buscaFilmesDoArtista(Long artistId) {
		List<Movie> movies = getAllMovies();
		List<Movie> filmography = new ArrayList<Movie>();

		movies.forEach(movie -> movie.getCast().forEach(artist -> {
			if (artist.getId() == artistId)
				filmography.add(movie);
		}));
		return filmography;
	}
    
    private List<Movie> getAllMovies() {
		Iterable<Movie> movieEntity = movieRepository.findAll();
		List<Movie> movies = (List<Movie>) movieEntity;

		return movies;
	}
    
}
