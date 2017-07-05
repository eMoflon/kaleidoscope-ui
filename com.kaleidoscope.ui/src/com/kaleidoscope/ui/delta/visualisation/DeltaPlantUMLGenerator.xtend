package com.kaleidoscope.ui.delta.visualisation

import Deltameta.AddEdgeOP
import Deltameta.AddNodeOP
import Deltameta.AttributeChangeOP
import Deltameta.DeleteEdgeOP
import Deltameta.DeleteNodeOP
import Deltameta.Edge
import Deltameta.Operation
import Deltameta.OperationalDelta
import com.kaleidoscope.ui.delta.util.DeltaUtil
import org.eclipse.emf.ecore.EObject

class DeltaPlantUMLGenerator {

	val nodesToBeDrawn= <EObject>newLinkedList()
	
	public def String wrapInTags(String body) {
		'''
			@startuml
			digraph root {
				fontname=Monospace
				fontsize=9
				label="";
			«body»
			}
			@enduml
		'''
	}

	public  def String emptyDiagram() {
		'''
			title Choose an element that can be visualised
		'''
	}
	public def handleOperation(Operation delta){
		if(delta instanceof AddNodeOP || delta instanceof DeleteNodeOP){
					return handleNodeOperations(delta)
				}else if(delta instanceof AddEdgeOP || delta instanceof DeleteEdgeOP){
					return handleEdgeOperations(delta)
				}else if(delta instanceof AttributeChangeOP){
					return handleAttributeChange(delta)
				}else{
					return ""
				}
	}
	public  def handleOperations(OperationalDelta delta){
		
		return 
			delta.operations.map[o |
				return handleOperation(o)	
			].join
	}
	
	private def handleNodeOperations(Operation op) {
		var String color = null
		var EObject node = null
		val fontname="Monospace" 
		val penwidth=1
		val fontsize=8 
		var shape="record"
		var fillcolor = "WHITE"
		val style = "filled"
		
		if(op instanceof AddNodeOP){
			 color = "GREEN"
			 node = (op as AddNodeOP).node
			 
		}else if(op instanceof DeleteNodeOP){
			 color = "RED"
			 node = (op as DeleteNodeOP).node
		}else{
			return ""
		}
						
		val nodeID = DeltaUtil.createGoodNameToIdentify(node)
		nodesToBeDrawn.remove(node)
		
		return 
		'''"«nodeID»" [fontsize=«fontsize», fontname=«fontname», penwidth=«penwidth», shape=«shape», color=«color», fillcolor=«fillcolor», label="{«nodeID» | }",style=«style»];
		'''
	}
	private def handleAttributeChange(AttributeChangeOP attrChange) {
		val fontname="Monospace" 
		val penwidth=1
		val color="BLACK"
		val fontsize=8 
		var shape="record"
		var fillcolor = "WHITE"
		val style = "filled"
	
		val newValue = attrChange.newValue
		val nodeID = DeltaUtil.createGoodNameToIdentify(attrChange.node)
		
		return 
		'''"«nodeID»" [fontsize=«fontsize», fontname=«fontname», penwidth=«penwidth», shape=«shape», color=«color», fillcolor=«fillcolor», label="{«nodeID» | value: OLD VALUE ==\> «newValue»}",style=«style»];
		'''
	}
	private  def handleEdgeOperations(Operation op) {
		var Edge edge = null
		var String color = null
		val fontname="Monospace" 
		val penwidth=1
		var shape="record"
		val style = "filled"
		var fillcolor = "WHITE"
		
		val fontsize=8 
		val constraint=true
		
		if(op instanceof AddEdgeOP){
			 color = "GREEN"
			 edge = (op as AddEdgeOP).edge
			 
		}else if(op instanceof DeleteEdgeOP){
			 color = "RED"
			 edge = (op as DeleteEdgeOP).edge
		}else{
			return ""
		}
		
		val EObject srcEObject = edge.src
		val EObject trgEObject = edge.trg
		val label = edge.type.name
		
		val srcNodeID = DeltaUtil.createGoodNameToIdentify(srcEObject)
		val trgNodeID = DeltaUtil.createGoodNameToIdentify(trgEObject)
		
		nodesToBeDrawn.add(srcEObject);
		nodesToBeDrawn.add(trgEObject);
		
		return 
		'''
		"«srcNodeID»" [fontsize=«fontsize», fontname=«fontname», penwidth=«penwidth», shape=«shape», color=BLACK, fillcolor=«fillcolor», label="{«srcNodeID» | }",style=«style»];
		"«trgNodeID»" [fontsize=«fontsize», fontname=«fontname», penwidth=«penwidth», shape=«shape», color=«color», fillcolor=«fillcolor», label="{«trgNodeID» | }",style=«style»];
		"«srcNodeID»" -> "«trgNodeID»" [fontname=«fontname», penwidth=«penwidth», color=«color», label="«label»", fontsize=«fontsize», constraint=«constraint»];
		'''
	}
}


