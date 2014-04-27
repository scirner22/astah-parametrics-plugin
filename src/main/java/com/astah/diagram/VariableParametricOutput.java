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
import com.change_vision.jude.api.inf.presentation.ILinkPresentation;
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
		abstractBlock.setAbstract(true);
		
		INodePresentation abstractBlockPresentation = bdde.createNodePresentation(abstractBlock, new Point2D.Double(20.0, 20.0));
		abstractBlockPresentation.setProperty("stereotype_visibility", "true");
		
		double xPosition = 20.0;
		
		for(OutputBlock block : blocks) {
			IBlock iBlock = block.createBlock(sme, pakkage);
			IAssociation association = sme.createAssociation(abstractBlock, iBlock, "", "", "");
			
			INodePresentation iBlockPresentation = bdde.createNodePresentation(iBlock, new Point2D.Double(xPosition, 120.0));
			iBlockPresentation.setProperty("stereotype_visibility", "true");
			iBlockPresentation.setProperty("value_compartment_visibility", "true");
			ILinkPresentation link = bdde.createLinkPresentation(association, abstractBlockPresentation, iBlockPresentation);
			link.setProperty("line.shape", "line");
			
			xPosition += 40.0 + iBlockPresentation.getWidth();
		}
		
		abstractBlockPresentation.setLocation(new Point2D.Double((xPosition - 40.0) / 2 - (abstractBlockPresentation.getWidth() / 2), 20.0));
		
		return bdd;
	}
}
