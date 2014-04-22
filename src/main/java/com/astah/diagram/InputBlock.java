package com.astah.diagram;

import java.util.HashMap;

import com.change_vision.jude.api.inf.model.IBlock;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class InputBlock {
	private IBlock block;
	private Multimap<String, String> values;
	
	public InputBlock(IBlock block) {
		this.block = block;
		values = ArrayListMultimap.create();
	}
	
	public void put(String key, String value) {
		values.put(key, value);
	}
	
	public String getName() {
		return block.getName();
	}
	
	public int size() {
		return values.size();
	}
	
	public HashMap<String, String> asFlattenedMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		
		for (String key : values.keySet()) {
			String value = values.get(key).toString();
			map.put(key, value.substring(1, value.length() - 1));
		}
		
		return map;
	}
}
