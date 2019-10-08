package br.com.sciensa.corujaoapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.sciensa.corujaoapi.model.Movie;

public interface MovieRepository  extends PagingAndSortingRepository<Movie, Long>{}
