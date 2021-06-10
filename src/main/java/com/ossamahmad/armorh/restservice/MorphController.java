package com.ossamahmad.armorh.restservice;

import AlKhalil.api.Morpho;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MorphController {

	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/analyse")
	public Morph analyse(@RequestParam(value = "text", defaultValue = "World") String text) {
		//List res = ["abc", "def"];
		Map map=Morpho.Analyse(text);
		return new Morph(counter.incrementAndGet(), map);
	}
	@GetMapping("/stems")
	public Morph stems(@RequestParam(value = "text", defaultValue = "World") String text) {
		//List res = ["abc", "def"];
		Map res = Morpho.Stems(text);
		return new Morph(counter.incrementAndGet(), res);
	}

	@GetMapping("/roots")
	public Morph roots(@RequestParam(value = "text", defaultValue = "World") String text) {
		//List res = ["abc", "def"];
		Map res = Morpho.Roots(text);
		return new Morph(counter.incrementAndGet(), res);
	}
}