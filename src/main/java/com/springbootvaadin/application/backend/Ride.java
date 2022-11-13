package com.springbootvaadin.application.backend;

import com.springbootvaadin.application.data.Person;
import com.springbootvaadin.application.util.css.lumo.BadgeColor;
import com.springbootvaadin.application.views.woyo.Client;

import java.time.LocalDate;

public class Ride {

	private  int id;
	private  Status status;
//	private final Collection<Item> items;

	private  Client client;
	private  Person driver;
	private  LocalDate date;

	public Ride() {

	}

	public Client getClient() {
		return client;
	}

//	public Ride() {
//
//		status = null;
//		id = null;
//	}
//	private final Double value;

	public enum Status {
		ONGOING("Ongoing", "Order received, on the route.",
				BadgeColor.NORMAL.getThemeName()), COMPLETED("Completed",
				"Order received, ride completed.",
				BadgeColor.SUCCESS.getThemeName()), CANCELLED("Cancelled",
				"Ride cancelled by client.",
				BadgeColor.CONTRAST.getThemeName()), AUTO_CANCELLED(
				"Auto Cancelled", "Ride cancelled by driver",
				BadgeColor.ERROR.getThemeName());

		private String name;
		private String desc;
		private String theme;

		Status(String name, String desc, String theme) {
			this.name = name;
			this.desc = desc;
			this.theme = theme;
		}

		public String getName() {
			return name;
		}

		public String getDesc() {
			return desc;
		}

		public String getTheme() {
			return theme;
		}
	}



	public Ride(int id, Status status, Client client,
				Person driver, LocalDate date) {
		this.id = id;
		this.status = status;
		this.client = client;
//		this.items = items;
		this.driver = driver;
		this.date = date;
//		this.value = items.stream().mapToDouble(Item::getPrice).sum();
	}

	public int getId() {
		return id;
	}

	public Status getStatus() {
		return status;
	}

//	public Collection<Item> getItems() {
//		return items;
//	}

//	public int getItemCount() {
//		return items.size();
//	}

	public Person getDriver() {
		return driver;
	}

	public LocalDate getDate() {
		return date;
	}

//	public Double getValue() {
//		return this.value;
//	}
}
