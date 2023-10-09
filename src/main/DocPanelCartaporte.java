package main;

import java.awt.Dimension;
import javax.swing.ImageIcon;

public class DocPanelCartaporte extends DocPanel {
	public DocPanelCartaporte () {
		initComponents ();
		imgTemplateFilepath = DocGlobals.imgCartaporteFilepath;
		pdfTemplateFilepath = DocGlobals.pdfCartaporteFilepath;
		docType = "cartaporte";
		imageIcon = (ImageIcon) imageLabel.getIcon ();
	}
	
	public void setConstraintsToTextAreas () {
			this.txt00.setFontSize ("large");
	}
	

	// Text limits to maxLines and maxChars
	public void setLimitsToTextAreas () {
		// Headers
		this.txt00.setParameters (1, 10, "large");
		this.txt01.setParameters (4, 70, "large");

		// 02, 03, 04, 05: Nombre, dir, pais, rut
		this.txt02.setParameters (4, 70, "normal");
		this.txt03.setParameters (4, 70, "normal");
		this.txt04.setParameters (4, 70, "normal");
		this.txt05.setParameters (4, 70, "normal");

		// Ciudad, Pais, Fecha
		this.txt06.setParameters (4, 70, "normal");
		this.txt07.setParameters (4, 70, "normal");
		this.txt08.setParameters (4, 70, "normal");

		// 09: Condiciones
		this.txt09.setParameters (4, 70, "normal");

		// 10, 11, 12: Bultos
		this.txt10.setParameters (18, 14, "normal");
		this.txt11.setParameters (18, 14, "normal");
		this.txt12.setParameters (25, 70, "normal");

		// 13_1, 13_2, 14, 15: Peso, volumen, medidas
		this.txt13_1.setParameters (1, 14, "normal", "alignRight");
		this.txt13_2.setParameters (1, 14, "normal", "alignRight");
		this.txt14.setParameters (1, 14, "normal", "alignRight");
		this.txt15.setParameters (1, 14, "normal", "alignRight");

		// 16: Incoterms
		this.txt16.setParameters (1, 25, "normal");

		// 17: Gastos
		this.txt17_11.setParameters (1, 14, "normal", "alignRight");
		this.txt17_12.setParameters (1, 14, "normal", "alignRight");
		this.txt17_13.setParameters (1, 14, "normal", "alignRight");
		this.txt17_14.setParameters (1, 14, "normal", "alignRight");

		this.txt17_21.setParameters (1, 14, "normal", "alignRight");
		this.txt17_22.setParameters (1, 14, "normal", "alignRight");
		this.txt17_23.setParameters (1, 14, "normal", "alignRight");
		this.txt17_24.setParameters (1, 14, "normal", "alignRight");

		this.txt17_31.setParameters (1, 14, "normal", "alignRight"); 	
		this.txt17_32.setParameters (1, 14, "normal", "alignRight"); 	
		this.txt17_33.setParameters (1, 14, "normal", "alignRight"); 	
		this.txt17_34.setParameters (1, 14, "normal", "alignRight"); 	

		this.txt17_41.setParameters (1, 14, "normal", "alignRight"); 	
		this.txt17_42.setParameters (1, 14, "normal", "alignRight"); 	
		this.txt17_43.setParameters (1, 14, "normal", "alignRight"); 	
		this.txt17_44.setParameters (1, 14, "normal", "alignRight"); 	
		
		// 18, 19, 20, 21, 22, 23:
		this.txt18.setParameters (2, 70, "normal");
		this.txt19.setParameters (2, 70, "normal");
		this.txt20.setParameters (6, 70, "normal");
		this.txt21.setParameters (4, 70, "normal");
		this.txt22.setParameters (6	, 70, "normal");
		this.txt23.setParameters (1, 70, "normal");
	}

	//@Override
	public Dimension getPreferredSize () {
		Dimension imageSize = new Dimension (imageIcon.getIconWidth (), imageIcon.getIconHeight ());
		return imageSize;
	}


	public DocTextArea getTxt00 () {
		return txt00;
	}

	public DocTextArea getTxt01 () {
		return txt01;
	}

	public DocTextArea getTxt02 () {
		return txt02;
	}

	public DocTextArea getTxt03 () {
		return txt03;
	}

	public DocTextArea getTxt04 () {
		return txt04;
	}

	public DocTextArea getTxt05 () {
		return txt05;
	}

	public DocTextArea getTxt06 () {
		return txt06;
	}

	public DocTextArea getTxt07 () {
		return txt07;
	}

	public DocTextArea getTxt08 () {
		return txt08;
	}

	public DocTextArea getTxt09 () {
		return txt09;
	}

	public DocTextArea getTxt10 () {
		return txt10;
	}

	public DocTextArea getTxt11 () {
		return txt11;
	}

	public DocTextArea getTxt12 () {
		return txt12;
	}

	public DocTextArea getTxt14 () {
		return txt13_1;
	}

	public DocTextArea getTxt15 () {
		return txt13_2;
	}

	public DocTextArea getTxt16 () {
		return txt14;
	}

	public DocTextArea getTxt17 () {
		return txt15;
	}

	public DocTextArea getTxt18 () {
		return txt16;
	}

	public DocTextArea getTxt17_11 () {
		return txt17_11;
	}

	public DocTextArea getTxt17_12 () {
		return txt17_12;
	}

	public DocTextArea getTxt17_13 () {
		return txt17_13;
	}

	public DocTextArea getTxt17_14 () {
		return txt17_14;
	}

	public DocTextArea getTxt17_21 () {
		return txt17_21;
	}

	public DocTextArea getTxt17_22 () {
		return txt17_22;
	}

	public DocTextArea getTxt17_23 () {
		return txt17_23;
	}

	public DocTextArea getTxt17_24 () {
		return txt17_24;
	}

	public DocTextArea getTxt17_31 () {
		return txt17_31;
	}

	public DocTextArea getTxt17_32 () {
		return txt17_32;
	}

	public DocTextArea getTxt17_33 () {
		return txt17_33;
	}

	public DocTextArea getTxt17_34 () {
		return txt17_34;
	}

	public DocTextArea getTxt22_1 () {
		return txt17_41;
	}

	public DocTextArea getTxt22_2 () {
		return txt17_42;
	}

	public DocTextArea getTxt22_3 () {
		return txt17_43;
	}

	public DocTextArea getTxt22_4 () {
		return txt17_44;
	}

	public DocTextArea getTxt23 () {
		return txt18;
	}

	public DocTextArea getTxt24 () {
		return txt19;
	}

	public DocTextArea getTxt25 () {
		return txt20;
	}

	public DocTextArea getTxt26 () {
		return txt21;
	}

	public DocTextArea getTxt27 () {
		return txt22;
	}

	public DocTextArea getTxt28 () {
		return txt23;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    txt00 = new main.DocTextArea();
    txt01 = new main.DocTextArea();
    txt02 = new main.DocTextArea();
    txt03 = new main.DocTextArea();
    txt04 = new main.DocTextArea();
    txt05 = new main.DocTextArea();
    txt06 = new main.DocTextArea();
    txt07 = new main.DocTextArea();
    txt08 = new main.DocTextArea();
    txt09 = new main.DocTextArea();
    txt10 = new main.DocTextArea();
    txt11 = new main.DocTextArea();
    txt12 = new main.DocTextArea();
    txt13_1 = new main.DocTextArea();
    txt13_2 = new main.DocTextArea();
    txt14 = new main.DocTextArea();
    txt15 = new main.DocTextArea();
    txt16 = new main.DocTextArea();
    txt17_11 = new main.DocTextArea();
    txt17_12 = new main.DocTextArea();
    txt17_13 = new main.DocTextArea();
    txt17_14 = new main.DocTextArea();
    txt17_21 = new main.DocTextArea();
    txt17_22 = new main.DocTextArea();
    txt17_23 = new main.DocTextArea();
    txt17_24 = new main.DocTextArea();
    txt17_31 = new main.DocTextArea();
    txt17_32 = new main.DocTextArea();
    txt17_33 = new main.DocTextArea();
    txt17_34 = new main.DocTextArea();
    txt17_41 = new main.DocTextArea();
    txt17_42 = new main.DocTextArea();
    txt17_43 = new main.DocTextArea();
    txt17_44 = new main.DocTextArea();
    txt18 = new main.DocTextArea();
    txt19 = new main.DocTextArea();
    txt20 = new main.DocTextArea();
    txt21 = new main.DocTextArea();
    txt22 = new main.DocTextArea();
    txt23 = new main.DocTextArea();
    imageLabel = new javax.swing.JLabel();

    setBackground(new java.awt.Color(255, 255, 255));
    setPreferredSize(new java.awt.Dimension(1100, 1424));
    setLayout(null);
    add(txt00);
    txt00.setBounds(790, 90, 240, 15);

    txt01.setFont(new java.awt.Font("Arial Narrow", 1, 14)); // NOI18N
    add(txt01);
    txt01.setBounds(33, 155, 508, 110);
    add(txt02);
    txt02.setBounds(33, 295, 508, 70);
    add(txt03);
    txt03.setBounds(33, 390, 508, 65);
    add(txt04);
    txt04.setBounds(33, 485, 508, 85);
    add(txt05);
    txt05.setBounds(548, 135, 508, 65);
    add(txt06);
    txt06.setBounds(548, 225, 508, 40);
    add(txt07);
    txt07.setBounds(548, 295, 508, 70);
    add(txt08);
    txt08.setBounds(548, 390, 508, 65);
    add(txt09);
    txt09.setBounds(548, 485, 508, 85);
    add(txt10);
    txt10.setBounds(33, 640, 100, 296);
    add(txt11);
    txt11.setBounds(140, 640, 110, 296);
    add(txt12);
    txt12.setBounds(258, 640, 520, 296);
    add(txt13_1);
    txt13_1.setBounds(790, 670, 128, 20);
    add(txt13_2);
    txt13_2.setBounds(928, 670, 128, 20);
    add(txt14);
    txt14.setBounds(790, 770, 128, 20);
    add(txt15);
    txt15.setBounds(928, 770, 128, 20);
    add(txt16);
    txt16.setBounds(788, 862, 270, 74);
    add(txt17_11);
    txt17_11.setBounds(105, 990, 121, 18);
    add(txt17_12);
    txt17_12.setBounds(105, 1011, 121, 18);
    add(txt17_13);
    txt17_13.setBounds(105, 1031, 121, 18);
    add(txt17_14);
    txt17_14.setBounds(105, 1052, 121, 18);
    add(txt17_21);
    txt17_21.setBounds(233, 990, 87, 18);
    add(txt17_22);
    txt17_22.setBounds(233, 1011, 87, 18);
    add(txt17_23);
    txt17_23.setBounds(233, 1031, 87, 18);
    add(txt17_24);
    txt17_24.setBounds(233, 1052, 87, 18);
    add(txt17_31);
    txt17_31.setBounds(327, 990, 107, 18);
    add(txt17_32);
    txt17_32.setBounds(327, 1011, 107, 18);
    add(txt17_33);
    txt17_33.setBounds(327, 1031, 107, 18);
    add(txt17_34);
    txt17_34.setBounds(327, 1052, 107, 18);
    add(txt17_41);
    txt17_41.setBounds(440, 990, 101, 18);
    add(txt17_42);
    txt17_42.setBounds(440, 1011, 101, 18);
    add(txt17_43);
    txt17_43.setBounds(440, 1031, 101, 18);
    add(txt17_44);
    txt17_44.setBounds(440, 1052, 101, 18);
    add(txt18);
    txt18.setBounds(33, 1096, 510, 39);
    add(txt19);
    txt19.setBounds(33, 1164, 510, 47);
    add(txt20);
    txt20.setBounds(33, 1240, 510, 103);
    add(txt21);
    txt21.setBounds(548, 968, 510, 102);
    txt21.getAccessibleContext().setAccessibleName("");

    add(txt22);
    txt22.setBounds(548, 1096, 510, 115);
    add(txt23);
    txt23.setBounds(548, 1326, 510, 17);

    imageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/image-cartaporte-vacia-SILOG.png"))); // NOI18N
    imageLabel.setAlignmentY(0.0F);
    add(imageLabel);
    imageLabel.setBounds(0, 0, 1100, 1424);
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel imageLabel;
  private main.DocTextArea txt00;
  private main.DocTextArea txt01;
  private main.DocTextArea txt02;
  private main.DocTextArea txt03;
  private main.DocTextArea txt04;
  private main.DocTextArea txt05;
  private main.DocTextArea txt06;
  private main.DocTextArea txt07;
  private main.DocTextArea txt08;
  private main.DocTextArea txt09;
  private main.DocTextArea txt10;
  private main.DocTextArea txt11;
  private main.DocTextArea txt12;
  private main.DocTextArea txt13_1;
  private main.DocTextArea txt13_2;
  private main.DocTextArea txt14;
  private main.DocTextArea txt15;
  private main.DocTextArea txt16;
  private main.DocTextArea txt17_11;
  private main.DocTextArea txt17_12;
  private main.DocTextArea txt17_13;
  private main.DocTextArea txt17_14;
  private main.DocTextArea txt17_21;
  private main.DocTextArea txt17_22;
  private main.DocTextArea txt17_23;
  private main.DocTextArea txt17_24;
  private main.DocTextArea txt17_31;
  private main.DocTextArea txt17_32;
  private main.DocTextArea txt17_33;
  private main.DocTextArea txt17_34;
  private main.DocTextArea txt17_41;
  private main.DocTextArea txt17_42;
  private main.DocTextArea txt17_43;
  private main.DocTextArea txt17_44;
  private main.DocTextArea txt18;
  private main.DocTextArea txt19;
  private main.DocTextArea txt20;
  private main.DocTextArea txt21;
  private main.DocTextArea txt22;
  private main.DocTextArea txt23;
  // End of variables declaration//GEN-END:variables

}
