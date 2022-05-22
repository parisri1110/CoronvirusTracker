package com.insanecoder.coronavirustracker.service;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.insanecoder.coronavirustracker.model.LocationStats;

@Service
public class CoronaVirusDataService {

	private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

	private List<LocationStats> stats=new ArrayList<LocationStats>();
	
	public List<LocationStats> getStats() {
		return stats;
	}

	public void setStats(List<LocationStats> stats) {
		this.stats = stats;
	}

	@PostConstruct
	@Scheduled(cron="* * 1 * * *")
	public void fetchVirusData() throws IOException, InterruptedException {
		 List<LocationStats> newStats=new ArrayList<LocationStats>();	
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(VIRUS_DATA_URL)).build();

		HttpResponse<String> httpresponse = client.send(request, HttpResponse.BodyHandlers.ofString());

	    //System.out.println(httpresponse.body());
		
		// we can convert our string to Reader object
		
		StringReader csvReader=new StringReader(httpresponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvReader);
		for (CSVRecord record : records) {
			LocationStats locationStat=new LocationStats();
			locationStat.setState(record.get("Province/State"));
			locationStat.setCountry(record.get("Country/Region"));
			int latestCases=Integer.parseInt(record.get(record.size()-1));
			int prevdiffCases=Integer.parseInt(record.get(record.size()-2));
			locationStat.setLatestTotalCases(latestCases);
			locationStat.setDifferenceTotalCases(latestCases-prevdiffCases	);
			System.out.println(locationStat);
			newStats.add(locationStat);
			/*
			 * String state = record.get("Province/State"); System.out.println(state);
			 * String country = record.get("Country/Region"); System.out.println(country)
			 */;
		}
		this.stats=newStats;
	}

}
