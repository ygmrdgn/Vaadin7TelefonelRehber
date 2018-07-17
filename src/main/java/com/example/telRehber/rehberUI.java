package com.example.telRehber;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.aspectj.weaver.ast.Var;
import org.dom4j.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.EmitUtils;

import com.example.telRehber.Entitiy;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.GridRowDragger;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@SpringComponent
//S@Theme("telefonrehberi")

public class rehberUI extends UI {

	private HorizontalLayout horizontal,forGrid;
	private VerticalLayout root, vertical;
	private TextField textAd, textSoyad, textTel;
	private Button btnKaydet, btnSil;
	private Label label;
	ErrorMessage hata;
	List<Entitiy> liste;
	List<Entitiy> liste2;
	private Entitiy nesnem;
	Grid<Entitiy> gridSol ;
	Grid<Entitiy> gridSag ;
	Var dataItem;

	@Autowired
	RehberLayout rehberLayout;

	@Override
	protected void init(VaadinRequest request) {
		setupLayout();
		addHeader();
		addForm();
		addFormGrid();
	
		

	}

	private void setupLayout() {
		root = new VerticalLayout();
		root.setStyleName("black");
		root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		setContent(root);
		

	}

	private void addHeader() {
		label = new Label("Telefon Rehberi");
		label.addStyleName(ValoTheme.LABEL_H1);
		root.addComponent(label);

	}

	private void addForm() {
		horizontal = new HorizontalLayout();

		vertical = new VerticalLayout();
		textAd = new TextField(" isim ");
		textSoyad = new TextField(" soyisim ");
		textTel = new TextField(" telefon ");
		btnKaydet = new Button(" KAYDET ");
		btnKaydet.setIcon(VaadinIcons.PLUS);
		btnKaydet.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		btnSil = new Button(" SİL ");
		btnSil.setIcon(VaadinIcons.MINUS);
		btnSil.setStyleName(ValoTheme.BUTTON_DANGER);
		btnKaydet.setSizeFull();
		btnSil.setSizeFull();
		
		horizontal.addComponents(textAd, textSoyad, textTel);
		horizontal.addComponents(btnKaydet, btnSil);

		btnSil.addClickListener(click -> {
			if (nesnem != null) {
				rehberLayout.Delete(nesnem);
				textSoyad.clear();
				textAd.clear();
				textTel.clear();

			}
			
			updatePage();

		});

		btnKaydet.addClickListener(click -> {
			if(textTel.getValue().length()!=11) {
				Notification.show("tel no 11 haneli olmkalıdır..",Notification.Type.ERROR_MESSAGE);
			}
			else
			{
			if (nesnem == null) {
				nesnem = new Entitiy();
				nesnem.setSoyisim(textSoyad.getValue());
				nesnem.setIsim(textAd.getValue());
				nesnem.setTelefon(textTel.getValue());
				rehberLayout.Ekle(nesnem);

				textSoyad.clear();
				textAd.clear();
				textTel.clear();
				nesnem=null;
			} else if (nesnem.getId() != 0) {

				nesnem.setSoyisim(textSoyad.getValue());
				nesnem.setIsim(textAd.getValue());
				nesnem.setTelefon(textTel.getValue());
				rehberLayout.Ekle(nesnem);

				textSoyad.clear();
				textAd.clear();
				textTel.clear();
				nesnem=null;
			}
			
			updatePage();
	}
			
		});
		
	
		root.addComponents(horizontal);

	}

	private void addFormGrid() {
		forGrid=new HorizontalLayout();
		liste = new ArrayList<>();
		liste = rehberLayout.listem();
		liste2=new ArrayList<>();
		gridSol = new Grid<>(Entitiy.class);
		gridSag=new Grid<>(Entitiy.class);
		gridSol.setItems(liste);
		//grid.setWidth("70%");
		gridSag.setItems(liste2);
		forGrid.addComponents(gridSol,gridSag);
		
		//gridSol.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		gridSol.addItemClickListener(click->{
			
			for (Entitiy a1 : gridSol.getSelectedItems()) {
				nesnem = a1;
				textAd.setValue(a1.getIsim());
				textSoyad.setValue(a1.getSoyisim());
				textTel.setValue(a1.getTelefon());
				
			}
		});
		
		


		GridRowDragger<Entitiy> leftToRight = new GridRowDragger<>(gridSol, gridSag);
		GridRowDragger<Entitiy> rightToLeft = new GridRowDragger<>(gridSag, gridSol);

		// Don't show the drop indicator for drags over the same grid where the drag started
		leftToRight.getGridDragSource()
		        .addDragStartListener(event -> {rightToLeft.getGridDropTarget()
		                        .setDropEffect(DropEffect.NONE);
		     //   for(Entitiy a:liste2)
		       //rehberLayout.Delete(a);
	
		        }); 
		leftToRight.getGridDragSource().addDragEndListener(
		        event ->{ rightToLeft.getGridDropTarget().setDropEffect(null);
		       for(Entitiy a:liste2)
				       rehberLayout.Delete(a);
				      
		        	});

		rightToLeft.getGridDragSource()
		        .addDragStartListener(event -> leftToRight.getGridDropTarget()
		                        .setDropEffect(DropEffect.NONE));
		rightToLeft.getGridDragSource().addDragEndListener(
		        event -> leftToRight.getGridDropTarget().setDropEffect(null));
		
		
		root.addComponents(forGrid);

	}
	
	private void updatePage() {
		
		this.gridSol.setItems(rehberLayout.listem());

	}


}
