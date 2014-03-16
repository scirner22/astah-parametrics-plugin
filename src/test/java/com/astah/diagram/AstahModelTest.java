package com.astah.diagram;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.editor.ActivityDiagramEditor;
import com.change_vision.jude.api.inf.editor.BlockDefinitionDiagramEditor;
import com.change_vision.jude.api.inf.editor.DiagramEditor;
import com.change_vision.jude.api.inf.editor.ModelEditorFactory;
import com.change_vision.jude.api.inf.editor.ParametricDiagramEditor;
import com.change_vision.jude.api.inf.editor.SequenceDiagramEditor;
import com.change_vision.jude.api.inf.editor.SysmlModelEditor;
import com.change_vision.jude.api.inf.editor.TransactionManager;
import com.change_vision.jude.api.inf.editor.UseCaseDiagramEditor;
import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IParametricDiagram;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

public class AstahModelTest {
	private IModel project;
	private ProjectAccessor prjAccessor;
	
	@Before
	public void createAstahModel() throws Exception {
		prjAccessor = AstahAPI.getAstahAPI().getProjectAccessor();
		prjAccessor.create("test.asml");
	    project = prjAccessor.getProject();
	    
        TransactionManager.beginTransaction();
        SysmlModelEditor sme = ModelEditorFactory.getSysmlModelEditor();
        BlockDefinitionDiagramEditor bdde = prjAccessor.getDiagramEditorFactory().getBlockDefinitionDiagramEditor();
        ActivityDiagramEditor ade = prjAccessor.getDiagramEditorFactory().getActivityDiagramEditor();
        ParametricDiagramEditor pde = prjAccessor.getDiagramEditorFactory().getParametricDiagramEditor();
        
        IPackage packageA = sme.createPackage(project, "PackageA");
        IPackage packageB = sme.createPackage(project, "PackageB");
        IBlock block1 = sme.createBlock(packageA, "Block1");
        IBlock block2 = sme.createBlock(packageB, "Block2");
        IDiagram bdd1 = bdde.createBlockDefinitionDiagram(packageA, "BDD1");
        IDiagram bdd2 = bdde.createBlockDefinitionDiagram(packageB, "BDD2");
        IDiagram bdd3 = bdde.createBlockDefinitionDiagram(packageB, "BDD3");
        IDiagram ad1 = ade.createActivityDiagram(packageA, "ACT1");
        IDiagram ad2 = ade.createActivityDiagram(packageB, "ACT2");
        IParametricDiagram par1 = pde.createParametricDiagram(block1, "Par1");
        IParametricDiagram par2 = pde.createParametricDiagram(block2, "Par2");
        TransactionManager.endTransaction();
	}
	
	@After
	public void tearDown() {
		prjAccessor.close();
	}
	
	public List<String> convertToNames(List<IDiagram> elements) {
		List<String> elementsString = new ArrayList<String>();
		
		for(INamedElement element : elements) {
			elementsString.add(element.getName());
		}
		
		return elementsString;
	}
	
	@Test
	public void fetchBlockDefinitionDiagrams() {
		List<IDiagram> elements = AstahModel.getBlockDefinitionDiagrams(project);
		
		assertEquals(Arrays.asList("BDD1", "BDD2", "BDD3"), convertToNames(elements));
	}
	
	@Test
	public void fetchParametricDiagrams() {
		List<IDiagram> elements = AstahModel.getParametricDiagrams(project);
		
		assertEquals(Arrays.asList("Par1", "Par2"), convertToNames(elements));
	}
}
