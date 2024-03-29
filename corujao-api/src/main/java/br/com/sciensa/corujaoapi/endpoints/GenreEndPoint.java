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

import br.com.sciensa.corujaoapi.model.Genre;
import br.com.sciensa.corujaoapi.repository.GenreRepository;

@RestController
@RequestMapping("v1")
public class GenreEndPoint {

	@Autowired
	GenreRepository repository;
	
    @PostMapping(value = "/genres")
    public ResponseEntity<Genre> addGenre(@Valid @RequestBody Genre genre){
    	genre = addCreatedAndUpdated(genre);
    	return new ResponseEntity<Genre>(repository.save(genre), HttpStatus.CREATED);
    };

	@GetMapping(value = "/genres/{genreId}")
    public ResponseEntity<Optional<Genre>> getGenre(@PathVariable("genreId") Long genreId){
    	return new ResponseEntity<Optional<Genre>>(repository.findById(genreId), HttpStatus.OK);
    };

    @GetMapping(value = "/genres")
    public ResponseEntity<Page<Genre>> listGenres(@Valid @RequestParam(value = "page", required = false, defaultValue="1") Integer page, @Valid @RequestParam(value = "size", required = false, defaultValue="10") Integer size, @Valid @RequestParam(value = "search", required = false) String search, Pageable pageable){
    	if(search != null) { return searchDescription(search,  pageable); }
    	return new ResponseEntity<Page<Genre>>(repository.findAll(pageable), HttpStatus.OK);
    };

    @PutMapping(value = "/genres/{genreId}")
    public ResponseEntity<?> updateGenre(@PathVariable("genreId") Long genreId, @Valid @RequestBody Genre genre){
    	return new ResponseEntity<Genre>(update(genreId, genre), HttpStatus.CREATED);
    };
    
    private ResponseEntity<Page<Genre>> searchDescription(String search, Pageable pageable) {
    	Iterable<Genre> genres = repository.findAll();
		List<Genre> genresFiltrados = new ArrayList<Genre>();

		genres.forEach(genre -> {
			if (removeAcento(genre.getDescription().toLowerCase()).contains(removeAcento(search.toLowerCase())))
				genresFiltrados.add(genre);
		});

		// convertendo List para page
		Page<Genre> page = new PageImpl<>(genresFiltrados, pageable, genresFiltrados.size());
		return new ResponseEntity<Page<Genre>>(page, HttpStatus.OK);
	}
    
    private static String removeAcento(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}
    
    private @Valid Genre addCreatedAndUpdated(@Valid Genre genre) {
    	genre.setCreatedAt(OffsetDateTime.now());
		genre.setUpdatedAt(OffsetDateTime.now());
		return genre;
	}

    private Genre update(Long genreId, Genre genre) {
		Optional<Genre> genreWillUpdate = repository.findById(genreId);
		genreWillUpdate.ifPresent(genreOpt -> {
			genreOpt.setDescription(genre.getDescription());
			genreOpt.setUpdatedAt(OffsetDateTime.now());
		});
		Genre genreEntity = genreWillUpdate.get();
		genreEntity = repository.save(genreEntity);

		return genreEntity;
	}
}
