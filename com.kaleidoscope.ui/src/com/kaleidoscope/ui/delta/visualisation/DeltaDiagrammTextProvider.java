package com.kaleidoscope.ui.delta.visualisation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import com.kaleidoscope.ui.delta.visualisation.DeltaPlantUMLGenerator;

import Deltameta.Operation;
//import net.sourceforge.plantuml.eclipse.utils.AbstractDiagramTextProvider;
import net.sourceforge.plantuml.eclipse.utils.DiagramTextProvider;


public class DeltaDiagrammTextProvider implements DiagramTextProvider  {
	private EcoreEditor currentEditor;
	
	@Override
	public String getDiagramText(IEditorPart editorPart, ISelection selected) {

		EObject selectedElement = getSelectedObject(editorPart);
		
		if (selectedElement != null && isElementValidInput(selectedElement)) {
			
			DeltaPlantUMLGenerator gen = new DeltaPlantUMLGenerator();
			// Extract input object
			return gen.wrapInTags(
					gen.handleOperation((Operation)selectedElement));
		}

		return "";

	}

	@Override
	public boolean supportsSelection(ISelection selectedElement) {
		return selectedElement instanceof Operation;
	}
	
	   
	protected EObject getInput(EObject selectedElement){
		   return selectedElement;
	}
	
	public boolean isElementValidInput(Object selectedElement) {
		return selectedElement instanceof Operation;
	}


	 private EObject getSelectedObject(IEditorPart editorPart){
		   ISelection selection = editorPart.getSite().getSelectionProvider().getSelection();

			if (selection != null && !selection.isEmpty() && selection instanceof TreeSelection) {
				StructuredSelection structuredSelection = (StructuredSelection) selection;
				if (structuredSelection.getFirstElement() instanceof EObject) {
					return (EObject) structuredSelection.getFirstElement();
				}
			}
			
			return null;
	   }
	 
	  @Override
	   public boolean supportsEditor(IEditorPart editorPart)
	   {
		  EObject selectedElement = getSelectedObject(editorPart); 
		   
		  if(selectedElement == null || !isElementValidInput(selectedElement))
			 return false;
		  
		  if (editorPart.equals(currentEditor))
	         return true;

	      if (editorPart instanceof EcoreEditor)
	      {
	         currentEditor = (EcoreEditor) editorPart;	         
	         return true;
	      }

	      return false;
	   }

	
	  
}
