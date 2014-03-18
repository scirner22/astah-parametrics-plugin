package com.astah.diagram;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IModel;

public class AstahModelTest {
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
	public void fetchBlockDefinitionDiagrams() {
		List<IDiagram> elements = AstahModel.getBlockDefinitionDiagrams(project);
		
		assertEquals(Arrays.asList("BDD1", "BDD2", "BDD3"), AstahTestProject.convertToNames(elements));
	}
	
	@Test
	public void fetchParametricDiagrams() {
		List<IDiagram> elements = AstahModel.getParametricDiagrams(project);
		
		assertEquals(Arrays.asList("Par1", "Par2"), AstahTestProject.convertToNames(elements));
	}
}
