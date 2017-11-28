package com.kaleidoscope.ui.delta.visualisation

import KaleidoscopeDelta.AddEdgeOP
import KaleidoscopeDelta.AddNodeOP
import KaleidoscopeDelta.AttributeChangeOP
import KaleidoscopeDelta.DeleteEdgeOP
import KaleidoscopeDelta.DeleteNodeOP
import KaleidoscopeDelta.Edge
import KaleidoscopeDelta.StructuralDelta
import com.kaleidoscope.ui.delta.util.DeltaUtil
import java.util.Collection
import org.eclipse.emf.ecore.EObject

class DeltaPlantUMLGenerator {
	
	def String wrapInTags(String body) {
		'''
		@startuml
			hide empty members
			hide circle
			hide stereotype
			
			skinparam shadowing false
			
			skinparam class {
				BackgroundColor White
				BorderColor<<GREEN>> SpringGreen
				BorderColor<<GREY>> Gray
				BorderColor<<RED>> Red
			}			
		«body»
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
		class "«DeltaUtil.idFor(node)»" <<«colour»>>
		'''
	}
	
	def String handleAttributeChange(AttributeChangeOP op) {
		'''class "«DeltaUtil.idFor(op.node)»" : value: OLD VALUE ==\> "«op.newValue»";
		'''
	}
	
	def dispatch handleOperation(AddEdgeOP op){
		renderNode("GREY", op.edge.src)
		renderNode("GREY", op.edge.trg)
		renderEdge("springgreen", op.edge)
	}
	
	def dispatch handleOperation(DeleteEdgeOP op){
		renderNode("GREY", op.edge.src)
		renderNode("GREY", op.edge.trg)
		renderEdge("red", op.edge)
	}
	
	def String renderEdge(String colour, Edge edge){
		'''
		"«DeltaUtil.idFor(edge.src)»" --[#«colour»]> "«DeltaUtil.idFor(edge.trg)»" : "«edge.type.name»"
		'''
	}
	
	def String handleSDelta(StructuralDelta delta){
		'''
		«FOR added : delta.addedNodes» «renderNode("GREEN", added)» «ENDFOR»
		«FOR deleted : delta.deletedNodes» «renderNode("RED", deleted)» «ENDFOR»
		«FOR attributeChange : delta.changedAttributes» «handleAttributeChange(attributeChange)» «ENDFOR»
		«renderEdgeAndContext(delta, "red", delta.deletedEdges)»
		«renderEdgeAndContext(delta, "springgreen", delta.addedEdges)»
		'''
	}
	
	def String renderEdgeAndContext(StructuralDelta delta, String colour, Collection<Edge> edges)
		'''«FOR edge : edges»
			«IF(!delta.addedNodes.contains(edge.src) && !delta.deletedNodes.contains(edge.src))»
				«renderNode("GREY", edge.src)»
			«ENDIF»
			«IF(!delta.addedNodes.contains(edge.trg) && !delta.deletedNodes.contains(edge.trg))»
				«renderNode("GREY", edge.trg)»
			«ENDIF»
			«renderEdge(colour, edge)»
		«ENDFOR»'''
	
}
