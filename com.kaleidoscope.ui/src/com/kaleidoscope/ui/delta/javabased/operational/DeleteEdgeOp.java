package com.kaleidoscope.ui.delta.javabased.operational;

import com.kaleidoscope.ui.delta.javabased.JavaBasedEdge;

import Deltameta.DeleteEdgeOP;
import Deltameta.DeltametaFactory;

public class DeleteEdgeOp extends Operation{
	private JavaBasedEdge edge;
	
	public DeleteEdgeOp(JavaBasedEdge edge){
		this.edge = edge;
	}
	public DeleteEdgeOp(Deltameta.DeleteEdgeOP deleteEdgeOp){
		   this.edge = new JavaBasedEdge(deleteEdgeOp.getEdge());	   
	}
	
	
	
	public JavaBasedEdge getEdge(){
		return edge;
	}
	
	public Deltameta.Operation toOperationalEMF()
   {	      
	  DeleteEdgeOP deleteEdgeOp = DeltametaFactory.eINSTANCE.createDeleteEdgeOP();      
	  deleteEdgeOp.setEdge(edge.toEMF());
      
      return deleteEdgeOp;
   }
	@Override
	public void executeOperation() {
		edge.getSrc().eUnset(edge.getType());
		
	}
}
