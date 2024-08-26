package com.grant.showtime.mvc.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "shows")

public class Show {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 255, message = "A show name is required.")
	private String name;

	@NotNull
	@Size(min = 1, max = 255, message = "A venue is required.")
	private String venue;

	@NotNull
	@Size(min = 1, max = 255, message = "An address is required.")
	private String address;

	@Min(1)
	@NotNull(message = "A ticket price is required.")
	private Integer price;

	@NotNull
	@Size(min = 1, max = 255, message = "A payment link is required.")
	private String payurl;

	@NotNull(message = "A description is required.")
	@Size(min = 1, max = 5000, message = "A description is required.")
	private String description;

	@NotNull
	@Size(min = 1, max = 10, message = "A show date is required.")
	private String showdate;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created_at;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updated_at;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User adminuser;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "guestlist", joinColumns = @JoinColumn(name = "show_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> guestlist;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "guestlistfinal", joinColumns = @JoinColumn(name = "show_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> finalguestlist;

	public Show() {

	}

	public Show(String name, Integer price, String payurl, String description, String venue, String address,
			String showdate, Date created_at, Date updated_at, User adminuser, List<User> guestlist,
			List<User> finalguestlist) {
		this.name = name;
		this.showdate = showdate;
		this.price = price;
		this.payurl = payurl;
		this.description = description;
		this.venue = venue;
		this.address = address;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.adminuser = adminuser;
		this.guestlist = guestlist;
		this.finalguestlist = finalguestlist;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getPayurl() {
		return payurl;
	}

	public void setPayurl(String payurl) {
		this.payurl = payurl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShowdate() {
		return showdate;
	}

	public String getShowdateformatted() {
		String sDate = showdate;
		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat date_format2 = new SimpleDateFormat("MMM dd, yyyy");
		try {
			Date ndate = date_format.parse(sDate);
			String datef = date_format2.format(ndate);
			sDate = datef;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sDate;
	}

	public void setShowdate(String showdate) {
		this.showdate = showdate;
	}

	public User getAdminuser() {
		return adminuser;
	}

	public void setAdminuser(User adminuser) {
		this.adminuser = adminuser;
	}

	public List<User> getGuestlist() {
		return guestlist;
	}

	public void setGuestlist(List<User> guestlist) {
		this.guestlist = guestlist;
	}

	public List<User> getFinalguestlist() {
		return finalguestlist;
	}

	public void setFinalguestlist(List<User> finalguestlist) {
		this.finalguestlist = finalguestlist;
	}

	@PrePersist
	protected void onCreate() {
		this.created_at = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updated_at = new Date();
	}

}