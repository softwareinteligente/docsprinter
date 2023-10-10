package main;

import com.google.gson.JsonObject;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class DocPanelCartaporte extends DocPanel {

	public DocPanelCartaporte () {
		initComponents ();
		docType = "cartaporte";
		imageIcon = (ImageIcon) imageLabel.getIcon ();
	}

	public void setConstraintsToTextAreas () {
		this.txt00.setFontSize ("large");
		this.txt00.setText (docNumber);
		
		addKeyListenerForEmpresa (txt02);
		addKeyListenerForEmpresa (txt03);
		addKeyListenerForEmpresa (txt04);
		addKeyListenerForEmpresa (txt05);
	}

	public void addKeyListenerForEmpresa (DocText txtEmpresa) {
		txtEmpresa.addKeyListener (new KeyAdapter () {
			public void keyPressed (KeyEvent e) {
				if (e.getKeyCode () != KeyEvent.VK_ENTER) {
					super.keyPressed (e);
					return;
				}
				if (e.isControlDown () && e.getKeyCode () == KeyEvent.VK_ENTER) {
					System.out.println (">>> Actualizando una nueva emprea desde el texto:" + txtEmpresa.getText ());
					String[] textLines = txtEmpresa.getText ().split (System.lineSeparator ());
					String nombre = textLines[0];
					String direccion = textLines[1];
					String[] ciudadPais = textLines[2].split ("\\s*-\\s*");
					String ciudad = ciudadPais.length > 0 ? ciudadPais[0] : null;
					String pais = ciudadPais.length > 1 ? ciudadPais[1] : null;
					String[] typeIdId = textLines[3].split ("\\s*:\\s*");
					String tipoId = typeIdId.length > 0 ? typeIdId[0] : null;
					String id = typeIdId.length > 1 ? typeIdId[1] : null;
					DocDB.addEmpresa (nombre, direccion, ciudad, pais, tipoId, id);
				} else if (e.getKeyCode () == KeyEvent.VK_ENTER) {
					System.out.println (">>> Consultando empresa desde el texto: " + txtEmpresa.getText ());
					String nombre = txtEmpresa.getText ();
					JsonObject result = DocDB.searchEmpresa (DocGlobals.database, nombre);
					if (result != null) {
						txtEmpresa.setText (
							DocDB.getValue (result, "nombre") + System.lineSeparator ()
							+ DocDB.getValue (result, "direccion") + System.lineSeparator ()
							+ DocDB.getValue (result, "ciudad") + " - " + DocDB.getValue (result, "pais") + System.lineSeparator ()
							+ DocDB.getValue (result, "tipo_id") + ":" + DocDB.getValue (result, "id"));
						e.consume();
					}
				}
			}
		});
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
		this.txt22.setParameters (6, 70, "normal");
		this.txt23.setParameters (1, 70, "normal");
	}

	public DocText getTxt00 () {
		return txt00;
	}

	public DocText getTxt01 () {
		return txt01;
	}

	public DocText getTxt02 () {
		return txt02;
	}

	public DocText getTxt03 () {
		return txt03;
	}

	public DocText getTxt04 () {
		return txt04;
	}

	public DocText getTxt05 () {
		return txt05;
	}

	public DocText getTxt06 () {
		return txt06;
	}

	public DocText getTxt07 () {
		return txt07;
	}

	public DocText getTxt08 () {
		return txt08;
	}

	public DocText getTxt09 () {
		return txt09;
	}

	public DocText getTxt10 () {
		return txt10;
	}

	public DocText getTxt11 () {
		return txt11;
	}

	public DocText getTxt12 () {
		return txt12;
	}

	public DocText getTxt14 () {
		return txt13_1;
	}

	public DocText getTxt15 () {
		return txt13_2;
	}

	public DocText getTxt16 () {
		return txt14;
	}

	public DocText getTxt17 () {
		return txt15;
	}

	public DocText getTxt18 () {
		return txt16;
	}

	public DocText getTxt17_11 () {
		return txt17_11;
	}

	public DocText getTxt17_12 () {
		return txt17_12;
	}

	public DocText getTxt17_13 () {
		return txt17_13;
	}

	public DocText getTxt17_14 () {
		return txt17_14;
	}

	public DocText getTxt17_21 () {
		return txt17_21;
	}

	public DocText getTxt17_22 () {
		return txt17_22;
	}

	public DocText getTxt17_23 () {
		return txt17_23;
	}

	public DocText getTxt17_24 () {
		return txt17_24;
	}

	public DocText getTxt17_31 () {
		return txt17_31;
	}

	public DocText getTxt17_32 () {
		return txt17_32;
	}

	public DocText getTxt17_33 () {
		return txt17_33;
	}

	public DocText getTxt17_34 () {
		return txt17_34;
	}

	public DocText getTxt22_1 () {
		return txt17_41;
	}

	public DocText getTxt22_2 () {
		return txt17_42;
	}

	public DocText getTxt22_3 () {
		return txt17_43;
	}

	public DocText getTxt22_4 () {
		return txt17_44;
	}

	public DocText getTxt23 () {
		return txt18;
	}

	public DocText getTxt24 () {
		return txt19;
	}

	public DocText getTxt25 () {
		return txt20;
	}

	public DocText getTxt26 () {
		return txt21;
	}

	public DocText getTxt27 () {
		return txt22;
	}

	public DocText getTxt28 () {
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

    txt00 = new main.DocText();
    txt01 = new main.DocText();
    txt02 = new main.DocText();
    txt03 = new main.DocText();
    txt04 = new main.DocText();
    txt05 = new main.DocText();
    txt06 = new main.DocText();
    txt07 = new main.DocText();
    txt08 = new main.DocText();
    txt09 = new main.DocText();
    txt10 = new main.DocText();
    txt11 = new main.DocText();
    txt12 = new main.DocText();
    txt13_1 = new main.DocText();
    txt13_2 = new main.DocText();
    txt14 = new main.DocText();
    txt15 = new main.DocText();
    txt16 = new main.DocText();
    txt17_11 = new main.DocText();
    txt17_12 = new main.DocText();
    txt17_13 = new main.DocText();
    txt17_14 = new main.DocText();
    txt17_21 = new main.DocText();
    txt17_22 = new main.DocText();
    txt17_23 = new main.DocText();
    txt17_24 = new main.DocText();
    txt17_31 = new main.DocText();
    txt17_32 = new main.DocText();
    txt17_33 = new main.DocText();
    txt17_34 = new main.DocText();
    txt17_41 = new main.DocText();
    txt17_42 = new main.DocText();
    txt17_43 = new main.DocText();
    txt17_44 = new main.DocText();
    txt18 = new main.DocText();
    txt19 = new main.DocText();
    txt20 = new main.DocText();
    txt21 = new main.DocText();
    txt22 = new main.DocText();
    txt23 = new main.DocText();
    imageLabel = new javax.swing.JLabel();

    setBackground(new java.awt.Color(255, 255, 255));
    setLayout(null);
    add(txt00);
    txt00.setBounds(790, 75, 240, 35);

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
  private main.DocText txt00;
  private main.DocText txt01;
  private main.DocText txt02;
  private main.DocText txt03;
  private main.DocText txt04;
  private main.DocText txt05;
  private main.DocText txt06;
  private main.DocText txt07;
  private main.DocText txt08;
  private main.DocText txt09;
  private main.DocText txt10;
  private main.DocText txt11;
  private main.DocText txt12;
  private main.DocText txt13_1;
  private main.DocText txt13_2;
  private main.DocText txt14;
  private main.DocText txt15;
  private main.DocText txt16;
  private main.DocText txt17_11;
  private main.DocText txt17_12;
  private main.DocText txt17_13;
  private main.DocText txt17_14;
  private main.DocText txt17_21;
  private main.DocText txt17_22;
  private main.DocText txt17_23;
  private main.DocText txt17_24;
  private main.DocText txt17_31;
  private main.DocText txt17_32;
  private main.DocText txt17_33;
  private main.DocText txt17_34;
  private main.DocText txt17_41;
  private main.DocText txt17_42;
  private main.DocText txt17_43;
  private main.DocText txt17_44;
  private main.DocText txt18;
  private main.DocText txt19;
  private main.DocText txt20;
  private main.DocText txt21;
  private main.DocText txt22;
  private main.DocText txt23;
  // End of variables declaration//GEN-END:variables

}
