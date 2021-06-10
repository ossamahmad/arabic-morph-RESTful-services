package com.example.restservice;

import java.util.*;
/*
{
    "id": 1,
    "content": "Hello, World!"
}
*/
public class Morph {

	private final long id;
	private final List<String> result;
	private Map map;  

	public Morph(long id,List<String> res) {
		this.id = id;
		this.result = res;
		this.map = null;
	}

	public Morph(long id, Map mp) {
		this.id = id;
		this.map = mp;
		this.result = null;
	}


	public long getId() {
		return id;
	}

	public List getResult() {
		return result;
	}

	public Map getMap() {
		return map;
	}
}