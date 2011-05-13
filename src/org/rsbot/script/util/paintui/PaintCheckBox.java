package org.rsbot.script.util.paintui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PaintCheckBox extends PaintComponent {
	private boolean checked = false;
	private String label = "";

	public PaintCheckBox(final String label) {
		this.label = label;
		setSize(15, 15);
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(final MouseEvent e) {
				toggle();
			}

			@Override
			public void mouseEntered(final MouseEvent e) {
			}

			@Override
			public void mouseExited(final MouseEvent e) {
			}

			@Override
			public void mousePressed(final MouseEvent e) {
			}

			@Override
			public void mouseReleased(final MouseEvent e) {
			}
		});
		setStyle(getClass());
	}

	public void toggle() {
		checked = !checked;
		queuePaint();
	}

	public void setLabel(final String s) {
		label = s;
		queuePaint();
	}

	public String getLabel() {
		return label;
	}

	public boolean isChecked() {
		return checked;
	}

	@Override
	public void paint(final Graphics render) {
		final Graphics2D g = (Graphics2D) render.create();
		final Rectangle boxRect = getAbsoluteBounds();
		g.setColor(checked ? Color.GREEN : Color.RED);
		g.fill3DRect(boxRect.x, boxRect.y, boxRect.width, boxRect.height,
				!checked);
		if (getCurrentStyle().fgColor != null && getCurrentStyle().font != null) {
			g.setColor(getCurrentStyle().fgColor);
			g.setFont(getCurrentStyle().font);
			if (isItalic() && isBold()) {
				g.setFont(g.getFont().deriveFont(Font.BOLD | Font.ITALIC));
			} else if (isItalic()) {
				g.setFont(g.getFont().deriveFont(Font.ITALIC));
			} else if (isBold()) {
				g.setFont(g.getFont().deriveFont(Font.BOLD));
			}
			g.drawString(getDisplay(), boxRect.x + boxRect.width + 3, boxRect.y
					+ boxRect.height - 2);
		}
		if (getCurrentStyle().border != null) {
			g.setColor(getCurrentStyle().border);
			g.draw3DRect(boxRect.x, boxRect.y, boxRect.width, boxRect.height,
					!checked);
		}
		g.dispose();
	}

	private String getDisplay() {
		return label.replace("[B]", "").replace("[I]", "").replace("[i]", "")
		.replace("[b]", "");
	}

	private boolean isBold() {
		return label.toLowerCase().contains("[b]");
	}

	private boolean isItalic() {
		return label.toLowerCase().contains("[i]");
	}
}
