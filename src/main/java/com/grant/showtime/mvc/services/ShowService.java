package com.grant.showtime.mvc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grant.showtime.mvc.models.Show;
import com.grant.showtime.mvc.repos.ShowRepo;

@Service
public class ShowService {

	private final ShowRepo showRepo;

	public ShowService(ShowRepo showRepo) {
		this.showRepo = showRepo;
	}

	public List<Show> allShows() {
		return showRepo.findAll();
	}

	public Show findShow(Long id) {
		Optional<Show> optionalshow = showRepo.findById(id);
		if (optionalshow.isPresent()) {
			return optionalshow.get();
		} else {
			return null;
		}
	}

	public void deleteShow(Long id) {

		showRepo.deleteById(id);
	}

	public Show createShow(Show t) {
		return showRepo.save(t);
	}

	public Show updateShow(Show t, Long showId) {
		Optional<Show> optionalShow = showRepo.findById(showId);
		if (optionalShow.isPresent()) {
			Show show = optionalShow.get();
			show.setName(t.getName());
			show.setVenue(t.getVenue());
			show.setAddress(t.getAddress());
			show.setPrice(t.getPrice());
			show.setDescription(t.getDescription());
			show.setShowdate(t.getShowdate());
			show.setAdminuser(t.getAdminuser());
			show.setGuestlist(t.getGuestlist());
			show.setFinalguestlist(t.getFinalguestlist());
			show.setPayurl(t.getPayurl());
			return showRepo.save(show);
		} else {
			return null;
		}
	}
}
