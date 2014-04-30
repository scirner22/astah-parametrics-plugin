package com.astah.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.astah.diagram.InputBlock;
import com.astah.diagram.OutputBlock;
import com.astah.diagram.VariableParametricInput;
import com.astah.diagram.VariableParametricOutput;
import com.astah.wolfram.WolframAlphaProxy;
import com.change_vision.jude.api.inf.editor.TransactionManager;
import com.change_vision.jude.api.inf.exception.InvalidEditingException;
import com.change_vision.jude.api.inf.exception.InvalidUsingException;
import com.change_vision.jude.api.inf.model.IBindingConnector;
import com.change_vision.jude.api.inf.model.IBlockDefinitionDiagram;
import com.change_vision.jude.api.inf.model.IConstraint;
import com.change_vision.jude.api.inf.model.IConstraintParameter;
import com.change_vision.jude.api.inf.model.IConstraintProperty;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IParametricDiagram;
import com.change_vision.jude.api.inf.model.IValueType;
import com.change_vision.jude.api.inf.presentation.IPresentation;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

public class ParametricsSolver {
	private VariableParametricInput inputDiagram;
	private IParametricDiagram par;
	private String outputBddName;
	private WolframAlphaProxy waProxy;
	
	public ParametricsSolver(IDiagram bdd, IParametricDiagram par) throws InvalidUsingException {
		inputDiagram = new VariableParametricInput((IBlockDefinitionDiagram) bdd);
		this.par = par;
		waProxy = new WolframAlphaProxy();
		
		if(bdd.getName().contains("Input")) {
			outputBddName = bdd.getName().replace("Input", "Output");
		}
		else {
			outputBddName = bdd.getName() + "Output";
		}
	}
	
	private void insertValue(List<String> equations, String lookupSubstring, String value) {
		for(int i = 0; i < equations.size(); i ++) {
			if(equations.get(i).contains(lookupSubstring)) {
				String temp = equations.get(i).replace(lookupSubstring, value);
				equations.set(i, temp);
			}
		}
	}
	
	private HashMap<String, String> formatEquations(List<String> equations) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(String equation : equations) {
			String key = equation.replaceFirst("=.+", "").trim();
			String value = equation.replaceFirst(".+=", "").trim();
			
			map.put(key, value);
		}
		
		return map;
	}
	
	public String solve(ProjectAccessor projectAccessor) throws InvalidUsingException, ClassNotFoundException, InvalidEditingException {
		VariableParametricOutput outputDiagram = new VariableParametricOutput(outputBddName, inputDiagram.getPackage());
		
		for(InputBlock inputBlock : inputDiagram.getInputBlocks()) {
			List<String> equations = getEquations();
			HashMap<String, String> input = inputBlock.asFlattenedMap();
			OutputBlock outputBlock = new OutputBlock(inputBlock.getName() + "_Results");
			
			HashMap<String, IConstraintParameter> portMap = getPorts();
			
			for(Map.Entry<String, IConstraintParameter> entry : portMap.entrySet()) {
				String key = entry.getKey();
				
				if(input.containsKey(key)) {
					insertValue(equations, entry.getValue().getName(), input.get(key));
				}
			}
			
			for(Map.Entry<String, String> entry : formatEquations(equations).entrySet()) {
				String key = entry.getKey();
				
				if(portMap.containsKey(key)) {
					String value = waProxy.query(entry.getValue());
					outputBlock.addValueAttribute(key, (IValueType) portMap.get(key).getType(), value);
				}
			}
			
			outputDiagram.addVariableBlock(outputBlock);
		}
		
		TransactionManager.beginTransaction();
		outputDiagram.createDiagram(projectAccessor);
		TransactionManager.endTransaction();
		
		return outputBddName;
	}
	
	public List<String> getEquations() throws InvalidUsingException {
		List<String> equations = new ArrayList<String>();
		
		for(IPresentation presentation : par.getPresentations()) {
			if("ConstraintProperty".equals(presentation.getType())) {
				IConstraintProperty property = (IConstraintProperty) presentation.getModel();
				
				for(IConstraint constraint : property.getType().getConstraints()) {
					equations.add(constraint.getName());
				}
			}
		}
		
		return equations;
	}
	
	public HashMap<String, IConstraintParameter> getPorts() throws InvalidUsingException {
		HashMap<String, IConstraintParameter> inputOutputs = new HashMap<String, IConstraintParameter>();
		
		for(IPresentation presentation : par.getPresentations()) {
			if("BindingConnector".equals(presentation.getType())) {
				String key = ((IBindingConnector) presentation.getModel()).getConstraintParameters()[0].getName();
				IConstraintParameter value = ((IBindingConnector) presentation.getModel()).getConstraintParameters()[1];

				inputOutputs.put(key, value);
			}
		}
		
		return inputOutputs;
	}
}
