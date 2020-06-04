package com.example.webdepruebasback.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webdepruebasback.header.GeneralHeader;
import com.example.webdepruebasback.service.WebDePruebasService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/demo")
public class WebDePruebasController {
	
	@Autowired
	private WebDePruebasService webDePruebasService;
	
	@PostMapping(value="/send", produces = "application/json", consumes = "application/json")
	public String sendQuery(@RequestHeader Map<String, String> headers, @RequestBody JsonNode jsonNode) throws Exception {
		GeneralHeader cabecera = new GeneralHeader();
		
		headers.forEach((key, value)  -> {
			if(key.equalsIgnoreCase("app-code")) {
				cabecera.setApplicationCode(value);
				System.out.println("app-code "+cabecera.getApplicationCode());
			}if(key.equalsIgnoreCase("app-name")){
				cabecera.setApplicationName(value);
				System.out.println("app-name "+cabecera.getApplicationName());
			}
			
			//System.out.println(String.format("Header '%s' = %s", key, value));
	    });
		
		//cabecera.setApplicationCode("RO");
		//cabecera.setApplicationName("apix");
		
		return webDePruebasService.consultarApi(cabecera, jsonNode);
		
	}

}
