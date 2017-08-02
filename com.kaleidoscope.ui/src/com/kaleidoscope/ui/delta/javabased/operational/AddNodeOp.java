package com.kaleidoscope.ui.delta.javabased.operational;

import org.eclipse.emf.ecore.EObject;


import Deltameta.AddNodeOP;
import Deltameta.DeltametaFactory;
import GridLanguage.Block;


public class AddNodeOp extends Operation{
	private EObject node;
	
	public AddNodeOp(EObject node){
		this.node = node;
	}
	
	public AddNodeOp(Deltameta.AddNodeOP addNodeOP){
		this.node = addNodeOP.getNode();
	}
	
	public EObject getNode(){
		return node;
	}
	
	public Deltameta.Operation toOperationalEMF()
   {	      
	  AddNodeOP addNodeOp = DeltametaFactory.eINSTANCE.createAddNodeOP(); 
	  addNodeOp.setNode(node);
      return addNodeOp;
   }
	@Override
	public void executeOperation(EObject model) {
		// TODO Auto-generated method stub
		
	}
}