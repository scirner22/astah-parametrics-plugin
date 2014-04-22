package com.astah.diagram;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.change_vision.jude.api.inf.model.IBlockDefinitionDiagram;
import com.change_vision.jude.api.inf.model.IModel;

public class InputBlockTest {
	private IModel project;
	
	@Before
	public void createAstahModel() throws Exception {
		project = AstahTestProject.getInstance().getProject();
	}
	
	@After
	public void tearDown() throws Exception {
		AstahTestProject.getInstance().close();
	}
	
	@Test
	public void testBlockName() throws Exception {
		VariableParametricInput input = new VariableParametricInput((IBlockDefinitionDiagram) AstahModel.getBlockDefinitionDiagrams(project).get(0));
		InputBlock block = input.getInputBlock("Concrete1");
		
		assertEquals("Concrete1", block.getName());
	}
	
	@Test
	public void testBlockSize() throws Exception {
		VariableParametricInput input = new VariableParametricInput((IBlockDefinitionDiagram) AstahModel.getBlockDefinitionDiagrams(project).get(0));
		InputBlock block = input.getInputBlock("Concrete1");
		
		assertEquals(4, block.size());
	}
	
	@Test
	public void testFlattenedMap() throws Exception {
		VariableParametricInput input = new VariableParametricInput((IBlockDefinitionDiagram) AstahModel.getBlockDefinitionDiagrams(project).get(0));
		InputBlock block = input.getInputBlock("Concrete1");
		HashMap<String, String> values = block.asFlattenedMap();
		
		HashMap<String, String> testMap = new HashMap<String, String>();
		testMap.put("con1", "10, 10.9");
		testMap.put("con2", "15.5");
		testMap.put("con3", "11.2");
		
		assertEquals(testMap, values);
	}
}
