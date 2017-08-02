package com.kaleidoscope.ui.delta.javabased.operational;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import Deltameta.AddEdgeOP;
import Deltameta.AddNodeOP;
import Deltameta.AttributeChangeOP;
import Deltameta.CompositeOP;
import Deltameta.DeleteEdgeOP;
import Deltameta.DeleteNodeOP;
import Deltameta.DeltametaFactory;
import Deltameta.MoveNodeOP;

public class CompositeOp extends Operation{

	List<Operation>operations = new ArrayList<Operation>();
	
	public CompositeOp(){
		
	}
	public CompositeOp(CompositeOP compositeOP) {
		
		for (Deltameta.Operation operation : compositeOP.getOperations()) {
			 if(operation instanceof AddEdgeOP){
				   operations.add(new AddEdgeOp((AddEdgeOP)operation));
			   }
			   if(operation instanceof DeleteEdgeOP){
				   operations.add(new DeleteEdgeOp((DeleteEdgeOP)operation));
			   }
			   if(operation instanceof AddNodeOP){
				   operations.add(new AddNodeOp((AddNodeOP)operation));
			   }
			   if(operation instanceof AttributeChangeOP){
				   operations.add(new AttributeChangeOp((AttributeChangeOP)operation));
			   }
			   if(operation instanceof DeleteNodeOP){
				   operations.add(new DeleteNodeOp((DeleteNodeOP)operation));
			   }
			   if(operation instanceof MoveNodeOP){
				   operations.add(new MoveNodeOp((MoveNodeOP)operation));
			   }
			   if(operation instanceof CompositeOP){
				   operations.add(new CompositeOp((CompositeOP)operation));
			   }
		}
	}

	@Override
	public Deltameta.Operation toOperationalEMF() {
		 Deltameta.CompositeOP compositeOperation = DeltametaFactory.eINSTANCE.createCompositeOP();      
		 operations.forEach(o -> compositeOperation.getOperations().add(o.toOperationalEMF()));
		       
	     return compositeOperation;
	}

	@Override
	public void executeOperation(EObject model) {
		operations.forEach(o -> o.executeOperation(model));		
	}

	public void addOperation(Operation o){
		operations.add(o);
	}
	public List<Operation> getOperations(){
		return operations;
	}
}
