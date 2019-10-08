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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.OffsetDateTime;

import br.com.sciensa.corujaoapi.model.Movie;
import br.com.sciensa.corujaoapi.repository.MovieRepository;

@RestController
@RequestMapping("v1")
public class MovieEndPoint {

	@Autowired
	MovieRepository repository;

	@Autowired
	MovieRepository movieRepository;
	
    @PostMapping(value = "/movies")
    public ResponseEntity<Movie> addMovie(@Valid @RequestBody Movie movie){
    	movie = addCreatedAndUpdated(movie);
    	return new ResponseEntity<Movie>(repository.save(movie), HttpStatus.CREATED);
    };

	@GetMapping(value = "/movies/{movieId}")
    public ResponseEntity<Optional<Movie>> getMovie(@PathVariable("movieId") Long movieId){
    	return new ResponseEntity<Optional<Movie>>(repository.findById(movieId), HttpStatus.OK);
    };

    @GetMapping(value = "/movies")
    public ResponseEntity<Page<Movie>> listMovies(@Valid @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @Valid @RequestParam(value = "size", required = false, defaultValue="10") Integer size, @Valid @RequestParam(value = "search", required = false) String search, Pageable pageable){
    	if(search != null) { return searchTitle(search,  pageable); }
    	return new ResponseEntity<Page<Movie>>(repository.findAll(pageable), HttpStatus.OK);
    };

    @PutMapping(value = "/movies/{movieId}")
    public ResponseEntity<?> updateMovie(@PathVariable("movieId") Long movieId, @Valid @RequestBody Movie movie){
    	return new ResponseEntity<Movie>(update(movieId, movie), HttpStatus.CREATED);
    };
    
    @DeleteMapping(value = "/movies/{movieId}")
    public ResponseEntity<Movie> removeMovie(@PathVariable("movieId") Long movieId) {
		movieRepository.deleteById(movieId);
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		header.add("Mensagem", "Filme removido com sucesso");
		return new ResponseEntity<Movie>(header, HttpStatus.NO_CONTENT);
	}
    
    private ResponseEntity<Page<Movie>> searchTitle(String search, Pageable pageable) {
    	Iterable<Movie> movies = repository.findAll();
		List<Movie> moviesFiltrados = new ArrayList<Movie>();

		movies.forEach(movie -> {
			if (removeAcento(movie.getTitle().toLowerCase()).contains(removeAcento(search.toLowerCase())))
				moviesFiltrados.add(movie);
		});

		// convertendo List para page
		Page<Movie> page = new PageImpl<>(moviesFiltrados, pageable, moviesFiltrados.size());
		return new ResponseEntity<Page<Movie>>(page, HttpStatus.OK);
	}

    private static String removeAcento(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}
    
    private @Valid Movie addCreatedAndUpdated(@Valid Movie movie) {
    	movie.setCreatedAt(OffsetDateTime.now());
		movie.setUpdatedAt(OffsetDateTime.now());
		return movie;
	}

    private Movie update(Long movieId, Movie movie) {
		Optional<Movie> movieWillUpdate = repository.findById(movieId);
		movieWillUpdate.ifPresent(movieOpt -> {
			movieOpt.setTitle(movie.getTitle());
			movieOpt.setReleaseYear(movie.getReleaseYear());
			movieOpt.setGenres(movie.getGenres());
			movieOpt.setCast(movie.getCast());
			movieOpt.setDirector(movie.getDirector());
			movieOpt.setUpdatedAt(OffsetDateTime.now());
		});
		Movie movieEntity = movieWillUpdate.get();
		movieEntity = repository.save(movieEntity);

		return movieEntity;
	}
    
}
