package com.kaleidoscope.ui.deltavisualisation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import Deltameta.Operation;
import net.sourceforge.plantuml.eclipse.utils.AbstractDiagramTextProvider;

public class DeltaDiagrammTextProvider extends AbstractDiagramTextProvider {
	private EcoreEditor currentEditor;
	
	@Override
	protected String getDiagramText(IEditorPart editorPart, IEditorInput editorInput) {

		EObject selectedElement = getSelectedObject(editorPart);
		if (selectedElement != null && isElementValidInput(selectedElement)) {
			
			DeltaPlantUMLGenerator gen = new DeltaPlantUMLGenerator();
			// Extract input object
			return gen.wrapInTags(
					gen.handleOperation((Operation)selectedElement));
		}

		return "";
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
