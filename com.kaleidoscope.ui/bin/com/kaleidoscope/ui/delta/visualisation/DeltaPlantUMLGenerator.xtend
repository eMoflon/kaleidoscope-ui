package com.kaleidoscope.ui.delta.visualisation

import KaleidoscopeDelta.AddEdgeOP
import KaleidoscopeDelta.AddNodeOP
import KaleidoscopeDelta.AttributeChangeOP
import KaleidoscopeDelta.DeleteNodeOP
import com.kaleidoscope.ui.delta.util.DeltaUtil
import org.eclipse.emf.ecore.EObject
import KaleidoscopeDelta.DeleteEdgeOP
import KaleidoscopeDelta.Edge

class DeltaPlantUMLGenerator {
	
	def String wrapInTags(String body) {
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

	def dispatch handleOperation(AddNodeOP op){
		return renderNode("GREEN", op.node)
	}
	
	def dispatch handleOperation(DeleteNodeOP op){
		return renderNode("RED", op.node)
	}
	
	def dispatch handleOperation(AttributeChangeOP op){
		return handleAttributeChange(op)
	}
	
	def String renderNode(String colour, EObject node){
		'''
		"«DeltaUtil.idFor(node)»" [«defaultsForRecord», color=«colour», label="{«DeltaUtil.idFor(node)» | }"];
		'''
	}
	
	def defaultsForRecord(){
		'''fontsize=8, fontname="Monospace", penwidth=1, shape="record", style="filled", fillcolor="WHITE"'''
	}
	
	def String handleAttributeChange(AttributeChangeOP op) {
		'''"«DeltaUtil.idFor(op.node)»" [«defaultsForRecord», color="BLACK", label="{«DeltaUtil.idFor(op.node)» | value: OLD VALUE ==\> «op.newValue»}"];
		'''
	}
	
	def dispatch handleOperation(AddEdgeOP op){
		defaultsForEdge("GREEN", op.edge)
	}
	
	def dispatch handleOperation(DeleteEdgeOP op){
		defaultsForEdge("RED", op.edge)
	}
	
	def String defaultsForEdge(String colour, Edge edge){
		'''
		«renderNode("GREY", edge.src)»
		«renderNode("GREY", edge.trg)»
		"«DeltaUtil.idFor(edge.src)»" -> "«DeltaUtil.idFor(edge.trg)»" [fontname="Monospace", penwidth=1, color="«colour»", label="«edge.type.name»", fontsize=8, constraint=true];
		'''
	}
}
