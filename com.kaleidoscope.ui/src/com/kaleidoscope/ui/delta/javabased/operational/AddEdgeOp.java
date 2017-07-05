package com.kaleidoscope.ui.delta.javabased.operational;

import com.kaleidoscope.ui.delta.javabased.JavaBasedEdge;

import Deltameta.AddEdgeOP;
import Deltameta.DeltametaFactory;

public class AddEdgeOp extends Operation{
	private JavaBasedEdge edge;
	
	public AddEdgeOp(JavaBasedEdge edge){
		this.edge = edge;
	}
	public AddEdgeOp(Deltameta.AddEdgeOP addEdgeOp){
		   this.edge = new JavaBasedEdge(addEdgeOp.getEdge());	   
	}
	
	public JavaBasedEdge getEdge(){
		return edge;
	}
	
   public Deltameta.Operation toOperationalEMF()
   {	      
	  AddEdgeOP addEdgeOp = DeltametaFactory.eINSTANCE.createAddEdgeOP();      
	  addEdgeOp.setEdge(edge.toEMF());      
      return addEdgeOp;
   }
   
	
   public void executeOperation(){	   
	   edge.getSrc().eSet(edge.getType(), edge.getTrg());
   }
   
   
}
