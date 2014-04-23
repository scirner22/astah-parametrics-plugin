package com.astah.diagram;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.change_vision.jude.api.inf.editor.ModelEditorFactory;
import com.change_vision.jude.api.inf.editor.SysmlModelEditor;
import com.change_vision.jude.api.inf.editor.TransactionManager;
import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IValueType;

public class OutputBlockTest {
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
	public void testGetName() throws Exception {
		OutputBlock block = new OutputBlock("Concrete1");
		
		assertEquals("Concrete1", block.getName());
	}
	
	@Test
	public void testAddValueAttribute() throws Exception {
		TransactionManager.beginTransaction();
		
		SysmlModelEditor sme = ModelEditorFactory.getSysmlModelEditor();
        IPackage pakkage = sme.createPackage(project, "PackageAddValue");
        
        OutputBlock block = new OutputBlock("Concrete1");
        IValueType integerValueType = sme.createValueType(pakkage, "Integer1");
        block.addValueAttribute("value1", integerValueType, "10");
        
		IBlock iBlock = block.createBlock(sme, pakkage);
		
		TransactionManager.endTransaction();
		
		assertEquals("value1", iBlock.getValueAttributes()[0].getName());
	}
	
	@Test
	public void testCreateBlock() throws Exception {
		TransactionManager.beginTransaction();
        
        SysmlModelEditor sme = ModelEditorFactory.getSysmlModelEditor();
        IPackage packageOutput = sme.createPackage(project, "PackageOutput");
		
		OutputBlock block = new OutputBlock("Concrete1");
		block.createBlock(sme, packageOutput);
		
		TransactionManager.endTransaction();
		
		assertEquals(1, packageOutput.getOwnedElements().length);
	}
}
