package com.grant.showtime.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.grant.showtime.mvc.models.LoginUser;
import com.grant.showtime.mvc.models.Show;
import com.grant.showtime.mvc.models.User;
import com.grant.showtime.mvc.services.ShowService;
import com.grant.showtime.mvc.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {

	@Autowired
	UserService userservice;

	@Autowired
	ShowService showservice;

	@GetMapping("/")
	public String registration(Model model) {
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoginUser());
		return "Login.jsp";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
			HttpSession session) {
		userservice.register(newUser, result);
		if (result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "Login.jsp";
		}
		session.setAttribute("userId", newUser.getId());
		return "redirect:/shows";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result, Model model,
			HttpSession session) {
		User user = userservice.login(newLogin, result);
		if (result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "Login.jsp";
		}
		session.setAttribute("userId", user.getId());
		session.setAttribute("user", user);
		return "redirect:/shows";
	}

	@GetMapping("/shows")
	public String shows(Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		} else {
			User user = userservice.findUser(userId);
			List<Show> shows = showservice.allShows();
			model.addAttribute("user", user);
			model.addAttribute("shows", shows);
			return "Dashboard.jsp";
		}
	}

	@GetMapping("/shows/new")
	public String newshow(Model model, HttpSession session, @ModelAttribute("Show") Show show) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		} else {
			return "NewShow.jsp";
		}
	}

	@PostMapping("/shows/new")
	public String saveshow(@Valid @ModelAttribute("Show") Show show, BindingResult result, Model model,
			HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (result.hasErrors()) {
			return "NewShow.jsp";
		} else {
			User user = (User) userservice.findUser(userId);
			show.setAdminuser(user);
			showservice.createShow(show);
			return "redirect:/shows";
		}
	}

	@GetMapping("/shows/edit/{sid}")
	public String editshow(Model model, HttpSession session, @ModelAttribute("Show") Show show,
			@PathVariable("sid") Long sid) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		} else {
			Long currUserId = userservice.findUser(userId).getId();
			Long showUserid = showservice.findShow(sid).getAdminuser().getId();
			if (showUserid == null) {
				return "redirect:/shows";
			} else if (showUserid != currUserId) {
				return "redirect:/shows";
			} else {
				Show s = showservice.findShow(sid);
				model.addAttribute("showedits", s);
				model.addAttribute("show", s);
				return "EditShow.jsp";
			}
		}
	}

	@PutMapping("/shows/edit/{sid}")
	public String update(@Valid @ModelAttribute("Show") Show show, BindingResult result, Model model,
			@PathVariable("sid") Long sid, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		} else {
			if (result.hasErrors()) {
				Show b = showservice.findShow(sid);
				model.addAttribute("show", b);
				model.addAttribute("showedits", show);
				model.addAttribute("sid", sid);
				return "EditShow.jsp";
			} else {
				User user = (User) userservice.findUser(userId);
				show.setAdminuser(user);
				showservice.updateShow(show, sid);
				return "redirect:/shows";
			}
		}
	}

	@GetMapping("/shows/delete/{sid}")
	public String delete(@PathVariable("sid") Long sid, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		} else {
			Long currUserid = userservice.findUser(userId).getId();
			Long showuserid = showservice.findShow(sid).getAdminuser().getId();
			if (currUserid != showuserid) {
				return "redirect:/shows";
			} else {
				showservice.deleteShow(sid);
				return "redirect:/shows";
			}
		}

	}

	@GetMapping("/shows/{sid}")
	public String details(Model model, @PathVariable("sid") Long sid, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		} else {
			User user = userservice.findUser(userId);
			Show show = showservice.findShow(sid);
			Boolean rsvp = false;
			Boolean confirmed = false;
			if (show == null) {
				return "redirect:/shows";
			} else {
				List<User> gl = show.getGuestlist();
				List<User> glf = show.getFinalguestlist();
				if (glf.contains(user)) {
					rsvp = true;
					confirmed = true;
					model.addAttribute("show", show);
					model.addAttribute("user", user);
					model.addAttribute("rsvp", rsvp);
					model.addAttribute("confirmed", confirmed);
					return "ShowDetails.jsp";
				} else if (gl.contains(user)) {
					rsvp = true;
					model.addAttribute("show", show);
					model.addAttribute("user", user);
					model.addAttribute("rsvp", rsvp);
					model.addAttribute("confirmed", confirmed);
					return "ShowDetails.jsp";

				} else {

					model.addAttribute("show", show);
					model.addAttribute("user", user);
					model.addAttribute("rsvp", rsvp);
					model.addAttribute("confirmed", confirmed);
					return "ShowDetails.jsp";

				}
			}
		}
	}

	@GetMapping("/shows/guestlist/{sid}")
	public String guestlist(Model model, @PathVariable("sid") Long sid, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) {
			return "redirect:/";
		} else {
			User user = userservice.findUser(userId);
			Show show = showservice.findShow(sid);
			if (show == null) {
				return "redirect:/shows";
			} else {
				Long showuserid = show.getAdminuser().getId();
				if (showuserid != user.getId()) {
					return "redirect:/shows";
				} else {
					List<User> guestlist = show.getGuestlist();
					List<User> finalguestlist = show.getFinalguestlist();
					model.addAttribute("show", show);
					model.addAttribute("user", user);
					model.addAttribute("guestlist", guestlist);
					model.addAttribute("finalguestlist", finalguestlist);
					return "GuestList.jsp";
				}
			}
		}
	}

	@GetMapping("/shows/confirm/{sid}/{uid}")
	public String confrim(@PathVariable("uid") Long uid, @PathVariable("sid") Long sid, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		Long currUserId = userservice.findUser(userId).getId();
		Show thisShow = showservice.findShow(sid);
		User thisUser = userservice.findUser(uid);
		Long showadminid = thisShow.getAdminuser().getId();
		if (userId == null) {
			return "redirect:/";
		} else if (thisUser.getId() == showadminid) {
			return "redirect:/shows";
		} else if (currUserId != showadminid) {
			return "redirect:/shows";
		} else if (thisShow == null || thisUser == null) {
			return "redirect:/shows";
		} else {
			List<User> gl = thisShow.getFinalguestlist();
			if (gl.contains(thisUser)) {
				return "redirect:/shows/guestlist/{sid}";
			} else {
				thisShow.getFinalguestlist().add(thisUser);
				thisShow.getGuestlist().remove(thisUser);
				showservice.updateShow(thisShow, sid);
				return "redirect:/shows/guestlist/{sid}";
			}
		}
	}

	@GetMapping("/shows/rsvp/{sid}/{uid}")
	public String rsvp(@PathVariable("uid") Long uid, @PathVariable("sid") Long sid, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		Long currUserid = userservice.findUser(userId).getId();
		Show thisShow = showservice.findShow(sid);
		User thisUser = userservice.findUser(uid);
		if (userId == null) {
			return "redirect:/";
		} else if (thisShow == null || thisUser == null) {
			return "redirect:/shows";
		} else if (currUserid != thisUser.getId()) {
			return "redirect:/shows";
		} else if (currUserid == thisShow.getAdminuser().getId()) {
			return "redirect:/shows";
		} else {
			List<User> gl = thisShow.getGuestlist();
			List<User> fgl = thisShow.getFinalguestlist();
			if (gl.contains(thisUser) || fgl.contains(thisUser)) {
				return "redirect:/shows/{sid}";
			} else {
				thisShow.getGuestlist().add(thisUser);
				showservice.updateShow(thisShow, sid);
				return "redirect:/shows";
			}
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}