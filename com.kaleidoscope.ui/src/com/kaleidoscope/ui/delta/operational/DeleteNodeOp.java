package com.kaleidoscope.ui.delta.operational;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import Deltameta.DeleteNodeOP;
import Deltameta.DeltametaFactory;

public class DeleteNodeOp extends Operation{
	private EObject node;
	
	public DeleteNodeOp(EObject node){
		this.node = node;
	}
	public DeleteNodeOp(Deltameta.DeleteNodeOP deleteNodeOP){
		this.node = deleteNodeOP.getNode();
	}
	
	
	public EObject getNode(){
		return node;
	}
	public void setNode(EObject node){
		this.node = node;
	}
	
	public Deltameta.Operation toOperationalEMF()
   {	      
	  DeleteNodeOP deleteNodeOp = DeltametaFactory.eINSTANCE.createDeleteNodeOP();      
	  deleteNodeOp.setNode(node);
      
      return deleteNodeOp;
   }
	@Override
	public void executeOperation() {
		EcoreUtil.delete(node);
		
	}
}