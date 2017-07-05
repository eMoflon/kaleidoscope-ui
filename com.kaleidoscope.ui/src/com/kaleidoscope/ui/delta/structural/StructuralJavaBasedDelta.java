package com.kaleidoscope.ui.delta.structural;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import com.kaleidoscope.ui.delta.JavaBasedDelta;

import Deltameta.Edge;



public class StructuralJavaBasedDelta extends JavaBasedDelta {
	
	   private Collection<EObject> addedNodes = new HashSet<>();

	   private Collection<EObject> deletedNodes = new HashSet<>();

	   private Collection<Edge> addedEdges = new HashSet<>();

	   private Collection<Edge> deletedEdges = new HashSet<>();
	   
	   private Collection<AttributeJavaBasedDelta> attributeChanges = new HashSet<>();
	   	   	 
	   
	   
	   public void addNode(EObject node)
	   {
	      addedNodes.add(node);
	   }  

	   public void addEdge(Edge edge)
	   {
	      addedEdges.add(edge);
	   }

	   public void changeAttribute(EAttribute affectedAttribute, Object newValue, EObject obj){
		  attributeChanges.add(new AttributeJavaBasedDelta(affectedAttribute, newValue, obj)); 
	   }
	   
	   public void deleteNode(EObject node)
	   {
	      deletedNodes.add(node);
	   }

	   public void deleteEdge(Edge edge)
	   {
	      deletedEdges.add(edge);
	   }

	   public Collection<EObject> getAddedNodes()
	   {
	      return addedNodes;
	   }

	   public Collection<EObject> getDeletedNodes()
	   {
	      return deletedNodes;
	   }

	   public Collection<Edge> getAddedEdges()
	   {
	      return addedEdges;
	   }
	   

	   public Collection<Edge> getDeletedEdges()
	   {
	      return deletedEdges;
	   }
	   
	   public Collection<AttributeJavaBasedDelta> getAttributeChanges(){
		   return attributeChanges;
	   }

	   public Collection<EObject> getAllAddedElements()
	   {
	      return Stream.concat(addedNodes.stream(), addedEdges.stream()).collect(Collectors.toSet());
	   }

	   public Collection<EObject> getAllDeletedElements()
	   {
	      return Stream.concat(deletedNodes.stream(), deletedEdges.stream()).collect(Collectors.toSet());
	   }

	   @Override
	   public String toString()
	   {
		   return "";
	    /*  return "Added Nodes       (" + addedNodes.size()       + "):" + Graph.displayNodes(addedNodes)   + "\n" + 
	             "Added Edges       (" + addedEdges.size()       + "):" + Graph.displayEdges(addedEdges)   + "\n" + 
	             "Deleted Nodes     (" + deletedNodes.size()     + "):" + Graph.displayNodes(deletedNodes) + "\n" + 
	             "Deleted Edges     (" + deletedEdges.size()     + "):" + Graph.displayEdges(deletedEdges) + "\n" + 
	             "Attribute Changes (" + attributeChanges.size() + "):" + displayAttrChanges(attributeChanges);*/
	   }

	   private String displayAttrChanges(Collection<AttributeJavaBasedDelta> changes)
	   {
	      return changes.stream().map(chg -> chg.getAffectedAttribute().getEContainingClass().getName() + "." + chg.getAffectedAttribute().getName() + 
	                  ": " + "[ OLD VALUE ]" + " -> " + "[" + chg.getNewValue() + "]").collect(Collectors.joining(", "));
	   }

	   public boolean isChangeDetected() {
		   return !(addedNodes.isEmpty() && addedEdges.isEmpty() && deletedNodes.isEmpty() && deletedEdges.isEmpty() && attributeChanges.isEmpty());
	   }

	   public void clear()
	   {
	      addedEdges.clear();
	      addedNodes.clear();
	      attributeChanges.clear();
	      deletedEdges.clear();
	      deletedNodes.clear();
	   }
}
