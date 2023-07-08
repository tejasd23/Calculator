
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame {
	JButton btnAdd, btnSubtract, btnDivide, btnMultiply, btnClear, btnDelete,btnPercent, btnplusminus, btnEquals,btnDot;
	JButton numBtn[];
	JTextField output;
	String previous, current, operator;
 
	public Calculator() {
		setSize(300,400);
		setTitle("Calculator");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
	        mainPanel.setLayout(new GridLayout(5, 4));

		current = "";
		previous = "";

		output = new JTextField(16);
		output.setMaximumSize(new Dimension(185, 40));
        	output.setFont(new Font("Calibri", Font.BOLD, 27));
        	output.setDisabledTextColor(new Color(0, 0, 0));
        	output.setMargin(new Insets(0, 5, 0, 0));
                output.setEditable(false);

        	JPanel outputPanel = new JPanel(new BorderLayout());
        	outputPanel.add(output, BorderLayout.NORTH);
		add(outputPanel,BorderLayout.NORTH);


		btnSubtract = createButton("-", this::opBtnHandler);
		btnAdd = createButton("+", this::opBtnHandler);
		btnDivide = createButton("÷", this::opBtnHandler);
		btnMultiply = createButton("x", this::opBtnHandler);
		btnPercent = createButton("%", this::opBtnHandler);


        	btnplusminus = createButton("+/-", e -> toggleSign());

        	btnDot = createButton(".", e -> numBtnHandler(e));

        	btnEquals = createButton("=", e -> otherBtnHandler(e));
        	btnClear = createButton("C", e -> otherBtnHandler(e));
        	btnDelete = createButton("D", e -> otherBtnHandler(e));

		numBtn = new JButton[11];
	        numBtn[10] = btnDot;
        	for (int count = 0; count < numBtn.length - 1; count++) {
    	            numBtn[count] = new JButton(String.valueOf(count));
        	    numBtn[count].setFont(new Font("Calibri", Font.BOLD, 22));
		    numBtn[count].addActionListener(this::numBtnHandler);
        	}

	        mainPanel.add(btnClear);
        	mainPanel.add(btnDelete);
	        mainPanel.add(btnPercent);
        	mainPanel.add(btnDivide);

	        mainPanel.add(numBtn[7]);
        	mainPanel.add(numBtn[8]);
	        mainPanel.add(numBtn[9]);
        	mainPanel.add(btnMultiply);

	        mainPanel.add(numBtn[4]);
        	mainPanel.add(numBtn[5]);
	        mainPanel.add(numBtn[6]);
        	mainPanel.add(btnSubtract);

	        mainPanel.add(numBtn[1]);
        	mainPanel.add(numBtn[2]);
	        mainPanel.add(numBtn[3]);
        	mainPanel.add(btnAdd);

        	mainPanel.add(btnplusminus);
        	mainPanel.add(numBtn[0]);
	        mainPanel.add(btnDot);
	        mainPanel.add(btnEquals);

		add(mainPanel, BorderLayout.CENTER);	

		setVisible(true);

	}

	public JButton createButton(String text, ActionListener listener) {
        	JButton button = new JButton(text);
       		button.setFont(new Font("Calibri", Font.BOLD, 22));
        	button.addActionListener(listener);
       		return button;
    	}


	public void delete() {
		if (current.length() > 0) {
                	current = current.substring(0, current.length() - 1);
        	}
	}

	public void clear() {
		current = "";
        	previous = "";
        	operator = null;
	}

	public void updateOutput(){
		output.setText(current);
	}

	public void appendToOutput(String num) {
		if (num.equals(".") && current.contains(".")) {
            		return;
        	}
		current += num;
	}

	public void toggleSign() {
        	double number = Double.parseDouble(current);
        	number *= -1;
		current = String.valueOf(number);
		updateOutput();
    	}

	public void selectOperator(String newOperator) {
		if (current.isEmpty()) {
            		operator = newOperator;
            		return;
        	}
        
        	if (!previous.isEmpty()) {
            		calculate();
        	}
        	operator = newOperator;
        	previous = current;
        	current = "";
	}

	public void calculate() {
		if (previous.length() < 1 || current.length() < 1) {
		JOptionPane.showMessageDialog(this,"Invalid input: Missing operand");			
        		return;
        	}
        	double result = 0.0;
        	double num1 = Double.parseDouble(previous);
        	double num2 = Double.parseDouble(current);
        	switch (operator) {
            	    case "x" -> result = num1 * num2;
            	    case "+" -> result = num1 + num2;
            	    case "-" -> result = num1 - num2;
                    case "÷" -> {
			if(num2 == 0){
				 JOptionPane.showMessageDialog(this,"Invalid input: can't divide by zero");
			} result = num1/num2;
		     } 
		    case "%" -> result = (num1/100) * num2;
		    default -> JOptionPane.showMessageDialog(this,"Invalid input");
               	}
        	current = String.valueOf(result);
       		operator = null;
        	previous = "";
    	}

        public void numBtnHandler(ActionEvent e) { 
		JButton selectedBtn = (JButton) e.getSource();
        	for (JButton btn : numBtn) {
            		if (selectedBtn == btn) {
               			appendToOutput(btn.getText());
               			updateOutput();
          		}
        	}
       	}
    	

	public void opBtnHandler(ActionEvent e) {
        	JButton selectedBtn = (JButton) e.getSource();
            		if (selectedBtn == btnMultiply) {
                		selectOperator("x");
            		} else if (selectedBtn == btnAdd) {
                		selectOperator("+");
            		} else if (selectedBtn == btnSubtract) {
                		selectOperator("-");
            		} else if (selectedBtn == btnDivide) {
                		selectOperator("÷");
            		} else if (selectedBtn == btnPercent) {
                		selectOperator("%");
            		} 
            	updateOutput();
    	}


        	public void otherBtnHandler(ActionEvent e) {
            		JButton selectedBtn = (JButton) e.getSource();
            		if (selectedBtn == btnDelete) {
                		delete();
            		}else if (selectedBtn == btnClear) {
                		clear();
            		}else if (selectedBtn == btnEquals) {
                		calculate();
            		}
            		updateOutput();
        	}
    	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
    			new Calculator();
		});
	}
}

























