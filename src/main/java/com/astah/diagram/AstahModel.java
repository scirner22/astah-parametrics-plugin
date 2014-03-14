package com.astah.diagram;

import java.util.LinkedList;
import java.util.List;

import com.change_vision.jude.api.inf.model.IBlockDefinitionDiagram;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IParametricDiagram;

public class AstahModel {
	
	private static void getBlockDefinitionDiagramsHelper(IPackage iPackage, List<IBlockDefinitionDiagram> listOfDiagrams) {
		for(INamedElement element : iPackage.getOwnedElements()) {
			if(element instanceof IPackage) {
				IDiagram[] diagrams = element.getDiagrams();
				for(IDiagram diagram : diagrams) {
					if(diagram instanceof IBlockDefinitionDiagram) {
						listOfDiagrams.add((IBlockDefinitionDiagram) diagram);
					}
				}
				
				getBlockDefinitionDiagramsHelper((IPackage) element, listOfDiagrams);
			}
		}
	}
	
	private static void getParametricDiagramsHelper(IPackage iPackage, List<IParametricDiagram> listOfDiagrams) {
		
	}
	
	public static List<IBlockDefinitionDiagram> getBlockDefinitionDiagrams(IModel model) {
		List<IBlockDefinitionDiagram> diagrams = new LinkedList<IBlockDefinitionDiagram>();
		getBlockDefinitionDiagramsHelper(model, diagrams);
		
		return diagrams;
	}
	
	public static List<IParametricDiagram> getParametricDiagrams(IModel model) {
		List<IParametricDiagram> diagrams = new LinkedList<IParametricDiagram>();
		getParametricDiagramsHelper(model, diagrams);
		
		return diagrams;
	}
}
