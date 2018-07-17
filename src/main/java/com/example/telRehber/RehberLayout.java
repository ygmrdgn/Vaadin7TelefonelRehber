package com.example.telRehber;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;


@SpringComponent
public class RehberLayout extends VerticalLayout {

	@Autowired
	RehberRepository rehberRepository;

	@PostConstruct
	void init() {

	}

	public List<Entitiy> listem() {

		return rehberRepository.findAll();
	}

	public void Ekle(Entitiy nesne) {
		rehberRepository.save(nesne);
	}

	public void Delete(Entitiy nesne) {
		rehberRepository.delete(nesne);
	}
}
