package org.kpmp.slides.overlay;

import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.springframework.stereotype.Component;


@Component
public class OverlayBuilder {
    public String buildSVGOverlay(int pixelsX, int pixelsY, int micronsX, int micronsY) {
        SVGGraphics2D graphic = new SVGGraphics2D(pixelsX, pixelsY);
        
        graphic.drawLine(0 , 0, pixelsX, 0);
        graphic.drawLine(0 , 10, pixelsX, 10);
        graphic.drawLine(0 , 20, pixelsX, 20);
        graphic.drawLine(0 , 30, pixelsX, 30);
        graphic.drawLine(0 , 40, pixelsX, 40);
        return graphic.getSVGDocument();
    }
}
