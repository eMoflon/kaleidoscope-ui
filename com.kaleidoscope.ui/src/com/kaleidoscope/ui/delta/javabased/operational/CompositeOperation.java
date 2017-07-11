package com.kaleidoscope.ui.delta.javabased.operational;

import java.util.ArrayList;
import java.util.List;

import Deltameta.DeltametaFactory;

public class CompositeOperation extends Operation{

	List<Operation>operations = new ArrayList<Operation>();
	@Override
	public Deltameta.Operation toOperationalEMF() {
		 Deltameta.CompositeOperation compositeOperation = DeltametaFactory.eINSTANCE.createCompositeOperation();      
		 operations.forEach(o -> compositeOperation.getOperations().add(o.toOperationalEMF()));
		       
	     return compositeOperation;
	}

	@Override
	public void executeOperation() {
		operations.forEach(o -> o.executeOperation());		
	}

}
