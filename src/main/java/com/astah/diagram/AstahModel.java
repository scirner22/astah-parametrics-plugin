package com.astah.diagram;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IBlockDefinitionDiagram;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IParametricDiagram;

public class AstahModel {
	
	private static void getBlockDefinitionDiagramsHelper(IPackage iPackage, List<IDiagram> listOfDiagrams) {
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
	
	private static void getParametricDiagramsHelper(IPackage iPackage, List<IDiagram> listOfDiagrams) {
		for(INamedElement element : iPackage.getOwnedElements()) {
			if(element instanceof IBlock) {
				IDiagram[] diagrams = element.getDiagrams();
				for(IDiagram diagram : diagrams) {
					if(diagram instanceof IParametricDiagram) {
						listOfDiagrams.add((IParametricDiagram) diagram);
					}
				}
			}
			else if(element instanceof IPackage) {
				getParametricDiagramsHelper((IPackage) element, listOfDiagrams);
			}
		}
	}
	
	public static List<IDiagram> getBlockDefinitionDiagrams(IModel model) {
		List<IDiagram> diagrams = new ArrayList<IDiagram>();
		getBlockDefinitionDiagramsHelper(model, diagrams);
		
		return diagrams;
	}
	
	public static List<IDiagram> getParametricDiagrams(IModel model) {
		List<IDiagram> diagrams = new ArrayList<IDiagram>();
		getParametricDiagramsHelper(model, diagrams);
		
		return diagrams;
	}
}
