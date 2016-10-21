package com.ecom.safecom.controllers;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;

import com.ecom.safecom.entity.ItemMenuLateral;
import com.ecom.safecom.entity.PaginaMenuLateral;
import com.ecom.safecom.services.impl.ConfigPaginaMenuLateral;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PaginaMenuLateralController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	
	//@Wire
	//Grid fnList;
	@Wire
	Tabbox fnList;
	
	@WireVariable
	ConfigPaginaMenuLateral configPaginaMenuLateral;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception{
		super.doAfterCompose(comp);
		
		Tabs tabs = fnList.getTabs();
		Tabpanels panels = fnList.getTabpanels();
		
		for(PaginaMenuLateral pagina:configPaginaMenuLateral.getPages()){
			//Row row = construyeFila(pagina.getName(), pagina.getLabel(), pagina.getIconUri(),pagina.getUri());
			//filas.appendChild(row);
			
			Tab tab = new Tab(pagina.getLabel(), pagina.getIconUri());
			Tabpanel panel = new Tabpanel();
			panel.setStyle("overflow:auto;");
			tabs.appendChild(tab);
			panels.appendChild(panel);
			Grid menuitems = new Grid();
			panel.appendChild(menuitems);
			Columns columnas = new Columns();
			Column col1 = new Column();
			Column col2 = new Column();
			columnas.appendChild(col1);
			columnas.appendChild(col2);
			col1.setWidth("38px");
			//col1.setAttribute("hflex", 1);
			//col2.setAttribute("hflex", 2);
			menuitems.appendChild(columnas);
			Rows filas = new Rows();
			for(ItemMenuLateral item : pagina.getItems()){
				filas.appendChild(construyeFila(item));
			}
			menuitems.appendChild(filas);
		}
	}
	
	private Row construyeFila(final ItemMenuLateral item){
		Row fila = new Row();
		Label lab = new Label(item.getLabel());
		Image img = new Image(item.getIconUri());
		
		fila.appendChild(img);
		fila.appendChild(lab);
		fila.setClass("funcion-menu");
		
		EventListener<Event> onActionListener = new SerializableEventListener<Event>() {

			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				
				if(item.getUri().startsWith("http://")){
					Executions.getCurrent().sendRedirect(item.getUri());
				}else{
					Include include = (Include)Selectors.iterable(fnList.getPage(), "#maininclude").iterator().next();
					include.setSrc(item.getUri());
					
					//avanza el control de marcadores con prefijo
					if(item.getName() != null){
						getPage().getDesktop().setBookmark("p_"+item.getName());
					}
				}
			}
			
		};
		fila.addEventListener(Events.ON_CLICK, onActionListener);
		
		return fila;
		
	}
}
