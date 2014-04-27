package com.astah.diagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IBlockDefinitionDiagram;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IValueAttribute;
import com.change_vision.jude.api.inf.presentation.IPresentation;

public class VariableParametricInput {
	private IBlockDefinitionDiagram diagram;
	private List<InputBlock> variableBlocks;
	
	public VariableParametricInput(IBlockDefinitionDiagram diagram) throws InvalidUsingException {
		this.diagram = diagram;
		variableBlocks = new ArrayList<InputBlock>();
		
		this.parseValues();
	}
	
	public IPackage getPackage() {
		return (IPackage) diagram.getOwner();
	}
	
	private String parseValueName(String name) {
		if(name.indexOf('{') == -1 || name.indexOf('}') == -1) {
			return name;
		}
		
		StringBuilder newName = new StringBuilder(name);
		int indexOfStart = name.indexOf('{');
		int indexOfEnd = name.indexOf('}') + 1;
		
		return newName.replace(indexOfStart, indexOfEnd, "").toString().trim();
	}
	
	private void parseValues() throws InvalidUsingException {
		for(IBlock block : this.getVariableInputBlocks()) {
			InputBlock variableBlock = new InputBlock(block);
			
			for(IValueAttribute attribute : this.getValuesForBlock(block.getName())) {
				variableBlock.put(parseValueName(attribute.getName()), attribute.getInitialValue());
			}
			
			variableBlocks.add(variableBlock);
		}
	}
	
	public IBlock getAbstractBlock() throws InvalidUsingException {
		IBlock abstractBlock = null;
		
		for(IPresentation presentation : diagram.getPresentations()) {
			if("Block".equals(presentation.getType()) && ((IBlock) presentation.getModel()).isAbstract()) {
				abstractBlock = (IBlock) presentation.getModel();
			}
		}
		
		return abstractBlock;
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
	
	public InputBlock getInputBlock(String blockName) {
		for(InputBlock block : variableBlocks) {
			if(block.getName().equals(blockName)) {
				return block;
			}
		}
		
		return new InputBlock(null);
	}
	
	public List<InputBlock> getInputBlocks() {
		return variableBlocks;
	}
	
	public int size() {
		return variableBlocks.size();
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
