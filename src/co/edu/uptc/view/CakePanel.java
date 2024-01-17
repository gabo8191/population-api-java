package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CakePanel extends JPanel {
    private List<String> quantities;
    private List<String> percentages;
    private List<String> years;

    public CakePanel(List<String> quantities, List<String> percentages, List<String> years) {
        this.quantities = quantities;
        this.percentages = percentages;
        this.years = years;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        int cx = width / 2;
        int cy = height / 2;
        int radius = Math.min(width, height) / 2 - 10;

        double startAngle = 0;
        Color[] sliceColors = getSliceColors(years.size());

        for (int i = 0; i < quantities.size(); i++) {
            double sweepAngle = Double.parseDouble(percentages.get(i)) * 360 / 100;
            g2d.setColor(sliceColors[i]);
            g2d.fillArc(cx - radius, cy - radius, 2 * radius, 2 * radius, (int) startAngle, (int) sweepAngle);
            startAngle += sweepAngle;

            drawYearLabel(g2d, cx, cy, radius, startAngle - sweepAngle / 2, years.get(i));
        }
    }

    private void drawYearLabel(Graphics2D g2d, int cx, int cy, int radius, double angle, String year) {
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 13));

        FontMetrics metrics = g2d.getFontMetrics();
        int labelWidth = metrics.stringWidth(year);
        int labelHeight = metrics.getHeight();

        double x = cx + radius * Math.cos(Math.toRadians(angle)) - labelWidth / 2;
        double y = cy - radius * Math.sin(Math.toRadians(angle)) + labelHeight / 2;

        g2d.drawString(year, (int) x, (int) y);
    }

    private Color[] getSliceColors(int numberOfSlices) {
        Color[] sliceColors = new Color[numberOfSlices];
        sliceColors[0] = new Color(255, 0, 0);
        sliceColors[1] = new Color(0, 255, 0);
        sliceColors[2] = new Color(0, 0, 255);
        sliceColors[3] = new Color(255, 255, 0);
        sliceColors[4] = new Color(255, 0, 255);
        sliceColors[5] = new Color(0, 255, 255);
        sliceColors[6] = new Color(255, 128, 0);
        sliceColors[7] = new Color(128, 0, 255);
        sliceColors[8] = new Color(0, 255, 128);
        sliceColors[9] = new Color(128, 128, 128);
        sliceColors[10] = new Color(128, 0, 0);
        sliceColors[11] = new Color(0, 128, 0);

        return sliceColors;
    }
}