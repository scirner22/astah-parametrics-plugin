package com.astah.diagram;

import java.util.ArrayList;
import java.util.List;

import com.change_vision.jude.api.inf.editor.SysmlModelEditor;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IValueAttribute;
import com.change_vision.jude.api.inf.model.IValueType;

public class OutputBlock {
	private String name;
	private List<ValueAttribute> attributes;
	
	public OutputBlock(String name) {
		this.name = name;
		attributes = new ArrayList<ValueAttribute>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addValueAttribute(String name, IValueType valueType, String initialValue) {
		ValueAttribute attribute = new ValueAttribute(name, valueType, initialValue);
		attributes.add(attribute);
	}
	
	public IBlock createBlock(SysmlModelEditor sme, IPackage pakkage) throws InvalidEditingException {
		IBlock block = sme.createBlock(pakkage, name);
		
		for(ValueAttribute attribute : attributes) {
			IValueAttribute attr = sme.createValueAttribute(block, attribute.getName(), attribute.getValueType());
			attr.setInitialValue(attribute.getValue());
		}
		
		return block;
	}
}
