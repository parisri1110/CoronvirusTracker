package com.insanecoder.coronavirustracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.insanecoder.coronavirustracker.model.LocationStats;
import com.insanecoder.coronavirustracker.service.CoronaVirusDataService;

@Controller
public class HomeController {

	@Autowired
	CoronaVirusDataService coronaVirusDataService;
	
	@GetMapping("/")
	public String home(Model model)
	{
		List<LocationStats> status=coronaVirusDataService.getStats();
		
		int totalCases=status.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
		int newCases=status.stream().mapToInt(stat->stat.getDifferenceTotalCases()).sum();
		
		model.addAttribute("locationStats" ,status);
		model.addAttribute("totalReportedCases",totalCases);
		model.addAttribute("totalNewCases",newCases);
		return "home";
		
	}
	
}
