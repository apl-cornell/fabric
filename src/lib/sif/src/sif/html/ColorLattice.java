/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package sif.html;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class ColorLattice extends Applet implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int numPrins = 0;

	int xref = 700;
	int yref = 100;
	TextField numPrin;
	Button submitButton;
	Vector<String> allCombs;
	
	final static int HSV = 0;
	final static int QUAD_CONVEX = 1;
	char letters[] = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	public void init() {
		setLayout(new FlowLayout());
		numPrin = new TextField(30);
		submitButton = new Button("Submit");
		submitButton.addActionListener(this);
		submitButton.addKeyListener(this);
		add(numPrin);
		add(submitButton);
		this.setBackground(Color.WHITE);
	}
	
	public void paint(Graphics g) {
		this.resize(1200,800);
		g.setColor(Color.BLACK);
		for(int i = 0; i < numPrins; i++) {
			Vector<String> prins = C(numPrins,i);
			for(int j = 0; j < prins.size(); j++) {
				String set = prins.get(j);
				int x = xref+(j-prins.size()/2)*150;
				int y = i*73 + yref;
				Color col = getCol(set, i, HSV);
				g.setColor(col);
				g.drawString(set, x, y);
				g.drawString(col.toString().substring(14), x-57, y+15);
				g.fillOval(x, y+26, 20, 20);
			}
		}
	}
	
	private Color getHSVCol(String set, int pos) {
		float x=0;
		for(int i = 0; i < set.length(); i++) {
			int index = set.charAt(i) - 'A';
			x += (float)Math.pow(2,index)/Math.pow(2,numPrins);
//			x += (float)1.0*index/(2*numPrins);
		}

		// Adding all the colors and scaling them
		int scaleFactor = set.length();
		x = x/scaleFactor;
		float y = (float)1.0*pos/numPrins;

		
		float hue = x*360;
		float saturation = (float)0.6;
		float value = (float)(0.75 - y/2);
		
		return getHSVCol(hue, saturation, value);
	}
	
	private Color getHSVCol(float hue, float saturation, float value) {
		float blue=0,green=0,red=0;
		int Hi = (int)Math.floor(hue/60)%6;
		float f = hue/60 - Hi;
		float p = value*(1-saturation);
		float q = value*(1-f*saturation);
		float t = value*(1-(1-f)*saturation);
		switch(Hi) {
		case 0: red = value; green = t; blue = p;break;
		case 1: red = q; green = value; blue = p;break;
		case 2: red = p; green = value; blue = t;break;
		case 3: red = p; green = q; blue = value;break;
		case 4: red = t; green = p; blue = value;break;
		case 5: red = value; green = p; blue = q;break;
		}
		
		return new Color(red, green, blue);		
	}
	
	private Color getQuadConvexCol(String set, int pos) {
		float blue=0,green=0,red=0;
		float y = (float)1.0*pos/numPrins;
		for(int i = 0; i < set.length(); i++) {
			int index = set.charAt(i) - 'A';
			float x = (float)1.0*index/numPrins;
			red += x*(2-x);
			green += 1-x*x;
		}
		int scaleFactor = set.length(); 
		blue = y;
		green = green/scaleFactor;
		red = red/scaleFactor;
		return new Color(red, green, blue);
	}

	private Color getCol(String set, int pos, int option) {
		switch(option) {
		case HSV: return getHSVCol(set,pos);
		case QUAD_CONVEX: return getQuadConvexCol(set,pos);
		}
		return null;
	}
	
	private Vector<String> genCombs(int n) {
		Vector<String> toReturn = new Vector<String>();
		genCombs(n, toReturn, 0, "");
		return toReturn;
	}
	private void genCombs(int n, Vector<String> combs, int level, String insert) {
		if(level == n) {
			combs.add(insert);
		} else {
			genCombs(n, combs, level+1, insert+letters[level]);
			genCombs(n, combs, level+1, insert);
		}
	}
	
	private Vector<String> C(int n, int r) {
		Vector<String> toReturn = new Vector<String>();
		for(int i = 0; i < allCombs.size(); i++) {
			String str = allCombs.get(i);
			if(str.length()==r) {
				toReturn.add(str);
			}
		}
		return toReturn;
	}
	
	public void actionPerformed(ActionEvent act) {
		numPrins = Integer.parseInt(numPrin.getText());
		allCombs = genCombs(numPrins);
		repaint();
	}
	
	public void keyTyped(KeyEvent key) {
		int code = key.getKeyCode();
		if(code == KeyEvent.VK_DOWN) {
			yref -= 10;
		} else if(code == KeyEvent.VK_UP) {
			yref += 10;
		} else if(code == KeyEvent.VK_LEFT) {
			xref += 10;
		} else if(code == KeyEvent.VK_RIGHT) {
			xref -= 10;
		} else if(code == KeyEvent.VK_HOME) {
			xref = 700; yref=100;
		}
		this.repaint();		
	}
	
	public void keyPressed(KeyEvent key) {
		keyTyped(key);
	}
	
	public void keyReleased(KeyEvent key) {
	}
}
