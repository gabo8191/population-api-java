package co.edu.uptc.presenter;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import co.edu.uptc.persistence.Persistence;
import co.edu.uptc.view.MyFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter implements ActionListener {
	private Persistence persistence;
	private MyFrame view;
	private static final String BASE_URL = "https://api.worldbank.org/v2/countries/";
	private static final String FILTER = "/indicators/SP.POP.TOTL?per_page=5000&format=json&date=2010:2022";
	private Map<String, String> countries;
	private List<String> years;
	private List<String> quantities;
	private List<String> percentages;
	private String data;
	private String country;
	private Gson gson;
	private String key;

	public Presenter() {
		persistence = new Persistence();
		years = new ArrayList<String>();
		quantities = new ArrayList<String>();
		percentages = new ArrayList<String>();
		countries = new HashMap<String, String>();
		country = "CHN";
		data = persistence.WebServicePlainReader(BASE_URL, country, FILTER);
		gson = new Gson();
		setDataTable();
		key = countries.entrySet().stream().filter(entry -> country.equals(entry.getValue())).findFirst()
				.get().getKey();

		view = new MyFrame(years, quantities, percentages, countries, this, key);
		view.setActionListener(this);
	}

	public void loadCountries() {
		countries.put("China", "CHN");
		countries.put("Colombia", "COL");
		countries.put("EEUU", "USA");
		countries.put("India", "IND");
		countries.put("Rusia", "RUS");
	}

	public void setDataTable() {
		loadCountries();
		setYears();
		setQuantities();
		calculatePercentages();
	}

	private void setYears() {
		JsonElement element = gson.fromJson(data, JsonElement.class);
		JsonArray jsonArray = element.getAsJsonArray().get(1).getAsJsonArray();
		int numElements = jsonArray.size();
		for (int i = numElements - 1; i >= 0; i--) {
			years.add(jsonArray.get(i).getAsJsonObject().get("date").toString().replace("\"", ""));
		}
	}

	private void setQuantities() {
		JsonElement element = gson.fromJson(data, JsonElement.class);
		JsonArray jsonArray = element.getAsJsonArray().get(1).getAsJsonArray();
		int numElements = jsonArray.size();
		for (int i = numElements - 1; i >= 0; i--) {
			quantities.add(jsonArray.get(i).getAsJsonObject().get("value").toString());
		}
	}

	private void calculatePercentages() {
		long total = quantities.stream().mapToLong(Long::parseLong).sum();
		for (String value : quantities) {
			double percent = (Double.parseDouble(value) / total) * 100;
			this.percentages.add(String.valueOf(percent));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
			case "Cake":
				view.setCake();
				break;
			case "Bar":
				view.setBar();
				break;
			case "Show":
				handleShowData();
				break;
		}
	}

	public void handleShowData() {
		String value = view.getCountriesComboBox().getSelectedItem().toString();
		country = countries.get(value);
		years.clear();
		quantities.clear();
		percentages.clear();
		data = persistence.WebServicePlainReader(BASE_URL, country, FILTER);
		setYears();
		setQuantities();
		calculatePercentages();
		view.updateDataInChart();
		view.updateTableData(years, quantities);
		key = countries.entrySet().stream().filter(entry -> country.equals(entry.getValue())).findFirst()
				.get().getKey();
		view.updateTitle(key);
	}

	public static void main(String[] args) {
		new Presenter();
	}

}
