package br.com.sciensa.corujaoapi.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.sciensa.corujaoapi.model.Artist;

public interface ArtistRepository extends PagingAndSortingRepository<Artist, Long>{}
