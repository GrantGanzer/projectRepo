package com.grant.showtime.mvc.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.grant.showtime.mvc.models.Show;

@Repository
public interface ShowRepo extends CrudRepository<Show, Long> {
	List<Show> findAll();

	List<Show> findById(int id);

}
