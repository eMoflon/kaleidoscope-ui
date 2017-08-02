package com.kaleidoscope.ui.delta.javabased.operational;

import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;

public abstract class Operation{
	
	private EObject model;
	
	public abstract Deltameta.Operation toOperationalEMF();
	public abstract void executeOperation(EObject model);
	
	public Consumer<EObject> toJavaConsumer(){
		   Consumer<EObject> edit = (model) -> {
			   this.setModel(model);
			   this.executeOperation(model);		
			};
			return edit;
	}
	
	private void setModel(EObject model){
		this.model = model;
	}
	
	private EObject getModel(){
		return model;
	}
}
