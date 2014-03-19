package com.astah.diagram;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IBlockDefinitionDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.IValueAttribute;

public class VariableParametricInputTest {
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
	public void testVariableParametricBlocks() throws Exception {
		VariableParametricInput input = new VariableParametricInput((IBlockDefinitionDiagram) AstahModel.getBlockDefinitionDiagrams(project).get(0));
		List<IBlock> blocks = input.getVariableInputBlocks();
		
		assertEquals(Arrays.asList("Concrete1", "Concrete2", "Concrete3"), AstahTestProject.convertToNames(blocks));
	}
	
	@Test
	public void testInputBlockContents() throws Exception {
		VariableParametricInput input = new VariableParametricInput((IBlockDefinitionDiagram) AstahModel.getBlockDefinitionDiagrams(project).get(0));
		List<IValueAttribute> values = input.getValuesForBlock("Concrete1");
		List<String> blockValues = new ArrayList<String>();
		
		for(IValueAttribute value : values) {
			blockValues.add(value.getName() + ":" + value.getTypeExpression() + "=" + value.getInitialValue());
		}
		
		assertEquals(Arrays.asList("con1:Integer=10", "con2:Real=15", "con3:Boolean=true"), blockValues);
	}
}
