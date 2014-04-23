package com.astah.diagram;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.change_vision.jude.api.inf.editor.BlockDefinitionDiagramEditor;
import com.change_vision.jude.api.inf.editor.ModelEditorFactory;
import com.change_vision.jude.api.inf.editor.SysmlModelEditor;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IAssociation;
import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.presentation.INodePresentation;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

public class VariableParametricOutput {
	private String name;
	private IPackage pakkage;
	private List<OutputBlock> blocks;
	
	public VariableParametricOutput(String name, IPackage pakkage) {
		this.name = name;
		this.pakkage = pakkage;
		blocks = new ArrayList<OutputBlock>();
	}
	
	public void addVariableBlock(OutputBlock block) {
		blocks.add(block);
	}
	
	public IDiagram createDiagram(ProjectAccessor prjAccessor) throws InvalidEditingException, 
																  ClassNotFoundException, 
																  InvalidUsingException  {
		SysmlModelEditor sme = ModelEditorFactory.getSysmlModelEditor();
		BlockDefinitionDiagramEditor bdde = prjAccessor.getDiagramEditorFactory().getBlockDefinitionDiagramEditor();
		
		IDiagram bdd = bdde.createBlockDefinitionDiagram(pakkage, name);
		bdde.setDiagram(bdd);
		
		IBlock abstractBlock = sme.createBlock(pakkage, name);
		INodePresentation abstractBlockPresentation = bdde.createNodePresentation(abstractBlock, new Point2D.Double(10.0, 10.0));
		
		for(OutputBlock block : blocks) {
			IBlock iBlock = block.createBlock(sme, pakkage);
			
			// TODO link abstract block to this block
			IAssociation association = sme.createAssociation(abstractBlock, iBlock, "", "", "");
			
			INodePresentation iBlockPresentation = bdde.createNodePresentation(iBlock, new Point2D.Double(10.0, 10.0));
			bdde.createLinkPresentation(association, abstractBlockPresentation, iBlockPresentation);
		}
		
		return bdd;
	}
}
