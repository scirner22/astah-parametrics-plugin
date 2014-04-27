package com.astah.diagram;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.change_vision.jude.api.inf.editor.ModelEditorFactory;
import com.change_vision.jude.api.inf.editor.SysmlModelEditor;
import com.change_vision.jude.api.inf.editor.TransactionManager;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.presentation.IPresentation;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

public class VariableParametricOutputTest {
	private ProjectAccessor project;
	
	@Before
	public void createAstahModel() throws Exception {
		project = AstahTestProject.getInstance().getProjectAccessor();
	}
	
	@After
	public void tearDown() throws Exception {
		AstahTestProject.getInstance().close();
	}
	
	@Test
	public void testCreateDiagram() throws Exception {
		TransactionManager.beginTransaction();
		
		SysmlModelEditor sme = ModelEditorFactory.getSysmlModelEditor();
        IPackage pakkage = sme.createPackage(project.getProject(), "PackageCreateDiagram");
        
        VariableParametricOutput output = new VariableParametricOutput("TestAnalysisOutput", pakkage);
        
        output.addVariableBlock(new OutputBlock("Concrete1"));
        output.addVariableBlock(new OutputBlock("Concrete2"));
        output.addVariableBlock(new OutputBlock("Concrete3"));
        
        IDiagram diagram = output.createDiagram(project);
		
		TransactionManager.endTransaction();
		
		int blockCount = 0;
		for(IPresentation presentation : diagram.getPresentations()) {
			if("Block".equals(presentation.getType())) {
				blockCount ++;
			}
		}
		
		assertEquals(4, blockCount);
	}
}
