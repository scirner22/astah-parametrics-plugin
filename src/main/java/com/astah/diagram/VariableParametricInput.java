package com.astah.diagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IBlockDefinitionDiagram;
import com.change_vision.jude.api.inf.model.IValueAttribute;
import com.change_vision.jude.api.inf.presentation.IPresentation;

public class VariableParametricInput {
	private IBlockDefinitionDiagram diagram;
	
	public VariableParametricInput(IBlockDefinitionDiagram diagram) {
		this.diagram = diagram;
	}
	
	public List<IBlock> getVariableInputBlocks() throws InvalidUsingException {
		List<IBlock> concreteBlocks = new ArrayList<IBlock>();
		
		for(IPresentation presentation : diagram.getPresentations()) {
			if("Block".equals(presentation.getType()) && !((IBlock) presentation.getModel()).isAbstract()) {
				concreteBlocks.add((IBlock) presentation.getModel());
			}
		}
		
		return concreteBlocks;
	}
	
	public List<IValueAttribute> getValuesForBlock(String blockName) throws InvalidUsingException {
		if(blockName == null) {
			return new ArrayList<IValueAttribute>();
		}
		
		for(IBlock block : this.getVariableInputBlocks()) {
			if(blockName.equals(block.getName())) {
				return new ArrayList<IValueAttribute>(Arrays.asList(block.getValueAttributes()));
			}
		}
		
		return new ArrayList<IValueAttribute>();
	}
}
