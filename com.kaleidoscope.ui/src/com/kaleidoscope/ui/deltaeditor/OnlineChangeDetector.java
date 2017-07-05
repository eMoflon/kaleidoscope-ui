package com.kaleidoscope.ui.deltaeditor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import com.kaleidoscope.ui.delta.JavaBasedEdge;
import com.kaleidoscope.ui.delta.operational.OperationalJavaBasedDelta;

import Deltameta.DeltametaFactory;
import Deltameta.Edge;

/**
 * Maps EMF notifications to delta structure for the synchronization algorithm.
 * 
 * @author anjorin
 *
 */
public class OnlineChangeDetector implements Adapter
{
	
   OperationalJavaBasedDelta delta = null;
   //OperationalDelta delta = null;

   Collection<EObject> allKnownElements = null;

   /**
    * Instances of this class listen to changes at {@code root} and record these changes in {@code delta}.
    * @param delta the 'storage' for change events
    * @param root the object (hierarchy) to be observed
    */
   public OnlineChangeDetector(OperationalJavaBasedDelta delta, EObject root)
   {
      this.delta = delta;
      this.allKnownElements = new HashSet<>();

      root.eAllContents().forEachRemaining(allKnownElements::add);
      allKnownElements.add(root);

      allKnownElements.forEach(elt -> elt.eAdapters().add(this));
   }

   @SuppressWarnings("unchecked")
   @Override
   public void notifyChanged(Notification notification)
   {
      EObject notifier = (EObject) notification.getNotifier();
      Object newValue = notification.getNewValue();
      Object oldValue = notification.getOldValue();

      switch (notification.getEventType())
      {
      case Notification.ADD:
         handleAddition(notifier, (EObject) newValue, (EReference) notification.getFeature());
         break;
      case Notification.ADD_MANY:
         ((List<Object>) newValue).stream().forEach(addedObj -> handleAddition(notifier, (EObject) addedObj, (EReference) notification.getFeature()));
         break;
      case Notification.SET:
         handleChange(notifier, oldValue, newValue, notification);
       
         break;
      case Notification.UNSET:
         handleDeletion(notifier, (EObject) oldValue, (EReference) notification.getFeature());
         break;
      case Notification.REMOVE:
         handleDeletion(notifier, (EObject) oldValue, (EReference) notification.getFeature());
         break;
      case Notification.REMOVE_MANY:
         ((List<Object>) oldValue).stream().forEach(addedObj -> handleDeletion(notifier, (EObject) addedObj, (EReference) notification.getFeature()));
         break;
      case Notification.MOVE:
    	  handleMove(notifier, newValue);
      default:
         // Notification not relevant, do nothing
      }

   }
   private void handleMove(EObject notifier, Object newValue){
	  // delta.moveNodeOp(notifier, newValue);
   }
   private void handleChange(EObject notifier, Object oldValue, Object newValue, Notification notification)
   {
      if (notification.getFeature() instanceof EAttribute)
      {
    	 delta.changeAttributeOp((EAttribute) notification.getFeature(), newValue, (EObject)notification.getNotifier());         
      }

      if (notification.getFeature() instanceof EReference)
      {
          handleAddition(notifier, (EObject) newValue, (EReference) notification.getFeature());
          handleDeletion(notifier, (EObject) oldValue, (EReference) notification.getFeature());
      }

   }

   private void handleDeletion(EObject notifier, EObject oldValue, EReference reference)
   {
      if (oldValue == null)
         return;

      JavaBasedEdge edge = new JavaBasedEdge(notifier, oldValue, reference);    
      delta.deleteEdgeOp(edge);      

      if (!isConnectedToModel(notifier)){
    	  delta.deleteNodeOp(notifier);    	 
      }         
      if (!isConnectedToModel(oldValue)){    	  
    	  delta.deleteNodeOp(oldValue);
      }      
   }
   
   @SuppressWarnings("unchecked")
   private void handleAddition(EObject notifier, EObject addedEObject, EReference reference){
	   
	   if (addedEObject == null)
	         return;

	   	  JavaBasedEdge edge = new JavaBasedEdge(notifier, addedEObject, reference);	  
	      delta.addEdgeOp(edge);
	  
	      if (!allKnownElements.contains(addedEObject))
	      {
	         allKnownElements.add(addedEObject);
	         delta.addNodeOp(addedEObject);	         	         

	         for (EReference referenceOfAddedNode : addedEObject.eClass().getEAllReferences())
	         {
	            if (referenceOfAddedNode.getUpperBound() != 1)
	               for (EObject containedObject : (EList<EObject>) addedEObject.eGet(referenceOfAddedNode, true))
	               {
	                  edge = new JavaBasedEdge(addedEObject, containedObject, referenceOfAddedNode);
	                  delta.addEdgeOp(edge);        	      	            	   
	               }
	            else
	            {
	               Object value = (EObject) addedEObject.eGet(referenceOfAddedNode, true);
	               if (value != null)
	               {
	            	   edge = new JavaBasedEdge(addedEObject, (EObject)value, referenceOfAddedNode);	            	 
	            	   delta.addEdgeOp(edge);		        	      
	               }

	            }
	         }

	         addedEObject.eAdapters().add(this);
	      }
	      
	      /*if (isConnectedToModel(addedEObject) && delta.getDeletedNodes().contains(addedEObject))
	          delta.getDeletedNodes().remove(addedEObject);

	       if (isConnectedToModel(notifier) && delta.getDeletedNodes().contains(notifier))
	          delta.getDeletedNodes().remove(notifier);*/
   }
   

   @Override
   public Notifier getTarget()
   {
      return null;
   }

   @Override
   public void setTarget(Notifier newTarget)
   {

   }

   @Override
   public boolean isAdapterForType(Object type)
   {
      if (type instanceof Class)
         return EObject.class.isAssignableFrom((Class<?>) type);
      else
         return false;
   }

   private boolean isConnectedToModel(EObject node)
   {
      return node.eContainer() != null || node.eResource() != null;
   }
   
   public static void removeDeltaListeners(final EObject root)
   {
      removeListenerFromNode(root);
      root.eAllContents().forEachRemaining(OnlineChangeDetector::removeListenerFromNode);
   }

   public static void removeListenerFromNode(final EObject element)
   {
      List<Adapter> toBeRemoved = new ArrayList<>();
      element.eAdapters().forEach(adapter -> {
         if (adapter instanceof OnlineChangeDetector)
            toBeRemoved.add(adapter);
      });

      if (toBeRemoved != null)
         element.eAdapters().removeAll(toBeRemoved);
   }
}
