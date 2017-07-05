package com.kaleidoscope.ui.delta.javabased.operational;

import org.eclipse.emf.ecore.EObject;
import Deltameta.DeltametaFactory;
import Deltameta.MoveNodeOP;

public class MoveNodeOp extends Operation{
	private EObject node;
	private int newIndex;
	
	public MoveNodeOp(EObject node, int newIndex){
		this.node = node;
		this.newIndex = newIndex;
	}
	public MoveNodeOp(MoveNodeOP moveNodeOP){
		this.node = moveNodeOP.getNode();
		this.newIndex = moveNodeOP.getNewIndex();
	}
	
	public EObject getNode(){
		return node;
	}
	public int getNewIndex(){
		return newIndex;
	}
	
	public Deltameta.Operation toOperationalEMF()
    {	      
	  MoveNodeOP moveNodeOp = DeltametaFactory.eINSTANCE.createMoveNodeOP();
	  moveNodeOp.setNewIndex(newIndex);
	  moveNodeOp.setNode(node);
      
      return moveNodeOp;
   }
	@Override
	public void executeOperation() {
		// TODO Auto-generated method stub
		
	}
}