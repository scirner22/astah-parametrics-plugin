package com.astah.diagram;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.editor.ActivityDiagramEditor;
import com.change_vision.jude.api.inf.editor.BlockDefinitionDiagramEditor;
import com.change_vision.jude.api.inf.editor.ModelEditorFactory;
import com.change_vision.jude.api.inf.editor.ParametricDiagramEditor;
import com.change_vision.jude.api.inf.editor.SysmlModelEditor;
import com.change_vision.jude.api.inf.editor.TransactionManager;
import com.change_vision.jude.api.inf.model.IBlock;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IModel;
import com.change_vision.jude.api.inf.model.INamedElement;
import com.change_vision.jude.api.inf.model.IPackage;
import com.change_vision.jude.api.inf.model.IValueType;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

public class AstahTestProject {
	private IModel project;
	private ProjectAccessor prjAccessor;
	private static AstahTestProject instance;
	
	private AstahTestProject() throws Exception {
		this.createProject();
	}
	
	public static AstahTestProject getInstance() throws Exception {
		if(instance == null) {
			instance = new AstahTestProject();
		}
		
		return instance;
	}
	
	public void close() {
		prjAccessor.close();
		instance = null;
	}
	
	public IModel getProject() {
		return project;
	}
	
	private void createProject() throws Exception {
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
        IDiagram bdd1 = bdde.createBlockDefinitionDiagram(packageA, "BDD1");
        bdde.createBlockDefinitionDiagram(packageB, "BDD2");
        bdde.createBlockDefinitionDiagram(packageB, "BDD3");
        ade.createActivityDiagram(packageA, "ACT1");
        ade.createActivityDiagram(packageB, "ACT2");
        IBlock block1 = sme.createBlock(packageA, "AbstractBlock");
        block1.setAbstract(true);
        IBlock block2 = sme.createBlock(packageA, "Concrete1");
        IBlock block3 = sme.createBlock(packageB, "Concrete2");
        IBlock block4 = sme.createBlock(packageA, "Concrete3");
        pde.createParametricDiagram(block2, "Par1");
        pde.createParametricDiagram(block3, "Par2");
        bdde.setDiagram(bdd1);
        bdde.createNodePresentation(block1, new Point2D.Double(10.0, 10.0));
        bdde.createNodePresentation(block2, new Point2D.Double(10.0, 10.0));
        bdde.createNodePresentation(block3, new Point2D.Double(10.0, 10.0));
        bdde.createNodePresentation(block4, new Point2D.Double(10.0, 10.0));
        IValueType integerValueType = sme.createValueType(packageA, "Integer");
        IValueType realValueType = sme.createValueType(packageA, "Real");
        sme.createValueAttribute(block2, "con1{ex1}", integerValueType).setInitialValue("10");
        sme.createValueAttribute(block2, "con1{ex2}", integerValueType).setInitialValue("10.9");
        sme.createValueAttribute(block2, "con2", realValueType).setInitialValue("15.5");
        sme.createValueAttribute(block2, "con3", realValueType).setInitialValue("11.2");
        sme.createValueAttribute(block3, "val1", integerValueType).setInitialValue("5");
        
        TransactionManager.endTransaction();
	}
	
	public static <T extends INamedElement> List<String> convertToNames(List<T> elements) {
		List<String> elementsString = new ArrayList<String>();
		
		for(INamedElement element : elements) {
			elementsString.add(element.getName());
		}
		
		return elementsString;
	}
}
