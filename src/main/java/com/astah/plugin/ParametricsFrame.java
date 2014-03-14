package com.astah.plugin;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.astah.diagram.AstahModel;
import com.change_vision.jude.api.inf.model.IModel;

public class ParametricsFrame {
	private JFrame frame;
	
	public ParametricsFrame(IModel model) {
		this.initGui(model);
	}
	
	private void initGui(IModel model) {
		frame = new JFrame("Variable Parametric Analysis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel descriptionPanel = new JPanel(new GridLayout(0, 1, 5, 5));
		descriptionPanel.add(new JLabel("Input Diagram:", JLabel.RIGHT));
		descriptionPanel.add(new JLabel("Parametric Diagram:", JLabel.RIGHT));
		
		JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
		inputPanel.add(new JComboBox(new DefaultComboBoxModel(AstahModel.getBlockDefinitionDiagrams(model).toArray())));
		inputPanel.add(new JComboBox(new DefaultComboBoxModel(AstahModel.getParametricDiagrams(model).toArray())));
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JButton analysisButton = new JButton("Run Analysis");
		analysisButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button Pressed");
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		buttonPanel.add(cancelButton, BorderLayout.WEST);
		buttonPanel.add(analysisButton, BorderLayout.EAST);
		
		frame.add(descriptionPanel, BorderLayout.WEST);
		frame.add(inputPanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.getRootPane().setBorder(new EmptyBorder(30, 20, 15, 20));
		frame.pack();
		frame.setVisible(true);
	}
}
