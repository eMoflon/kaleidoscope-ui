package com.kaleidoscope.ui.delta.visualisation;

import KaleidoscopeDelta.AddEdgeOP;
import KaleidoscopeDelta.AddNodeOP;
import KaleidoscopeDelta.AttributeChangeOP;
import KaleidoscopeDelta.DeleteEdgeOP;
import KaleidoscopeDelta.DeleteNodeOP;
import KaleidoscopeDelta.Edge;
import KaleidoscopeDelta.Operation;
import com.kaleidoscope.ui.delta.util.DeltaUtil;
import java.util.Arrays;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class DeltaPlantUMLGenerator {
  public String wrapInTags(final String body) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("@startuml");
    _builder.newLine();
    _builder.append("digraph root {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fontname=Monospace");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("fontsize=9");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("label=\"\";");
    _builder.newLine();
    _builder.append(body);
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    _builder.append("@enduml");
    _builder.newLine();
    return _builder.toString();
  }
  
  protected String _handleOperation(final AddNodeOP op) {
    return this.renderNode("GREEN", op.getNode());
  }
  
  protected String _handleOperation(final DeleteNodeOP op) {
    return this.renderNode("RED", op.getNode());
  }
  
  protected String _handleOperation(final AttributeChangeOP op) {
    return this.handleAttributeChange(op);
  }
  
  public String renderNode(final String colour, final EObject node) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"");
    String _idFor = DeltaUtil.idFor(node);
    _builder.append(_idFor);
    _builder.append("\" [");
    CharSequence _defaultsForRecord = this.defaultsForRecord();
    _builder.append(_defaultsForRecord);
    _builder.append(", color=");
    _builder.append(colour);
    _builder.append(", label=\"{");
    String _idFor_1 = DeltaUtil.idFor(node);
    _builder.append(_idFor_1);
    _builder.append(" | }\"];");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  public CharSequence defaultsForRecord() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("fontsize=8, fontname=\"Monospace\", penwidth=1, shape=\"record\", style=\"filled\", fillcolor=\"WHITE\"");
    return _builder;
  }
  
  public String handleAttributeChange(final AttributeChangeOP op) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\"");
    String _idFor = DeltaUtil.idFor(op.getNode());
    _builder.append(_idFor);
    _builder.append("\" [");
    CharSequence _defaultsForRecord = this.defaultsForRecord();
    _builder.append(_defaultsForRecord);
    _builder.append(", color=\"BLACK\", label=\"{");
    String _idFor_1 = DeltaUtil.idFor(op.getNode());
    _builder.append(_idFor_1);
    _builder.append(" | value: OLD VALUE ==\\> ");
    Object _newValue = op.getNewValue();
    _builder.append(_newValue);
    _builder.append("}\"];");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  protected String _handleOperation(final AddEdgeOP op) {
    return this.defaultsForEdge("GREEN", op.getEdge());
  }
  
  protected String _handleOperation(final DeleteEdgeOP op) {
    return this.defaultsForEdge("RED", op.getEdge());
  }
  
  public String defaultsForEdge(final String colour, final Edge edge) {
    StringConcatenation _builder = new StringConcatenation();
    String _renderNode = this.renderNode("GREY", edge.getSrc());
    _builder.append(_renderNode);
    _builder.newLineIfNotEmpty();
    String _renderNode_1 = this.renderNode("GREY", edge.getTrg());
    _builder.append(_renderNode_1);
    _builder.newLineIfNotEmpty();
    _builder.append("\"");
    String _idFor = DeltaUtil.idFor(edge.getSrc());
    _builder.append(_idFor);
    _builder.append("\" -> \"");
    String _idFor_1 = DeltaUtil.idFor(edge.getTrg());
    _builder.append(_idFor_1);
    _builder.append("\" [fontname=\"Monospace\", penwidth=1, color=\"");
    _builder.append(colour);
    _builder.append("\", label=\"");
    String _name = edge.getType().getName();
    _builder.append(_name);
    _builder.append("\", fontsize=8, constraint=true];");
    _builder.newLineIfNotEmpty();
    return _builder.toString();
  }
  
  public String handleOperation(final Operation op) {
    if (op instanceof AddEdgeOP) {
      return _handleOperation((AddEdgeOP)op);
    } else if (op instanceof AddNodeOP) {
      return _handleOperation((AddNodeOP)op);
    } else if (op instanceof AttributeChangeOP) {
      return _handleOperation((AttributeChangeOP)op);
    } else if (op instanceof DeleteEdgeOP) {
      return _handleOperation((DeleteEdgeOP)op);
    } else if (op instanceof DeleteNodeOP) {
      return _handleOperation((DeleteNodeOP)op);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(op).toString());
    }
  }
}
