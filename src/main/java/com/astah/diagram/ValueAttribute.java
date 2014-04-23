package com.astah.diagram;

import com.change_vision.jude.api.inf.model.IValueType;

public class ValueAttribute {
	private String name;
	private IValueType valueType;
	private String value;
	
	public ValueAttribute(String name, IValueType valueType, String value) {
		this.name = name;
		this.valueType = valueType;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public IValueType getValueType() {
		return valueType;
	}
	
	public String getValue() {
		return value;
	}
}
