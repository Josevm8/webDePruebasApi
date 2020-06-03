package com.example.webdepruebasback.service;


import com.example.webdepruebasback.header.GeneralHeader;
import com.fasterxml.jackson.databind.JsonNode;

public interface WebDePruebasService {
	
	String consultarApi(GeneralHeader generalHeader, JsonNode jsonNode) throws Exception;

}
