package co.edu.uptc.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BarPanel extends JPanel {

    private List<String> quantities;
    private List<String> percentages;
    private List<String> years;

    public BarPanel(List<String> quantities, List<String> percentages, List<String> years) {
        this.quantities = quantities;
        this.percentages = percentages;
        this.years = years;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintAxes(g);
        paintBars(g);
    }

    private void paintAxes(Graphics g) {
        int xOffset = 50;
        int yOffset = 20;
        int maxHeight = getHeight() - yOffset;
        int maxWidth = getWidth() - xOffset;

        g.drawLine(xOffset, yOffset, xOffset, maxHeight);
        g.drawLine(xOffset, maxHeight, maxWidth, maxHeight);

        int barWidth = 40;
        int spaceBetweenBars = 10;
        int x = xOffset;

        for (int i = 0; i < quantities.size(); i++) {
            g.drawLine(x, maxHeight, x, maxHeight + 5);
            g.drawString(years.get(i), x - 10, maxHeight + 20);
            x += barWidth + spaceBetweenBars;
        }

        long maxMark = getMaxValue();
        int numMarks = 5;
        int yInterval = maxHeight / numMarks;

        for (int i = 0; i <= numMarks; i++) {
            int y = maxHeight - i * yInterval;
            g.drawLine(xOffset - 5, y, xOffset, y);
            String value = String.valueOf(i * maxMark / numMarks);
            g.drawString(value, xOffset - 30, y + 5);
        }
    }

    private void paintBars(Graphics g) {
        int barWidth = 40;
        int spaceBetweenBars = 10;
        int x = 50;
        int maxHeight = getHeight() - 50;
        long maxValue = getMaxValue();

        for (int i = 0; i < quantities.size(); i++) {
            int barHeight = (int) (Integer.parseInt(quantities.get(i)) * 1.0 / maxValue * maxHeight);
            int y = maxHeight - barHeight + 20;

            g.setColor(barColor(i));
            g.fillRect(x, y, barWidth, barHeight);

            printThePercentage(g, i, x, y - 10);

            x += barWidth + spaceBetweenBars;
        }
    }

    private long getMaxValue() {
        long max = 0;
        for (String quantity : quantities) {
            long value = Long.parseLong(quantity);
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private void printThePercentage(Graphics g, int index, int xCoordinate, int yCoordinate) {
        double percentage = Double.parseDouble(percentages.get(index));
        String formattedPercentage = String.format("%.2f%%", percentage);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 13));
        g.drawString(formattedPercentage, xCoordinate + 10, yCoordinate);
    }

    private Color barColor(int index) {
        Color[] colors = {
                new Color(255, 0, 0),
                new Color(0, 255, 0),
                new Color(0, 0, 255),
                new Color(255, 255, 0),
                new Color(255, 0, 255),
                new Color(0, 255, 255),
                new Color(255, 128, 0),
                new Color(128, 0, 255),
                new Color(0, 255, 128),
                new Color(128, 128, 128),
                new Color(128, 0, 0),
                new Color(0, 128, 0)
        };
        return colors[index % colors.length];
    }

}