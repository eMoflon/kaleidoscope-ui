package com.kaleidoscope.ui.delta.javabased.operational;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;

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
		/*EStructuralFeature feature = edge.getType();
		if(!feature.isDerived()){
			if (feature.isMany()) {
				((EList) edge.getSrc().eGet(feature)).remove(edge.getTrg());
			} else
				edge.getSrc().eUnset(feature);
		}*/
		
	}
}
