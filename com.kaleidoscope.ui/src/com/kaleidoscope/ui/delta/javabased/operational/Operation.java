package com.kaleidoscope.ui.delta.javabased.operational;

import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;

public abstract class Operation{
	
	public abstract Deltameta.Operation toOperationalEMF();
	public abstract void executeOperation();
	
	public Consumer<EObject> toJavaConsumer(){
		   Consumer<EObject> edit = (input) -> {
			   this.executeOperation();		
			};
			return edit;
	}
}
