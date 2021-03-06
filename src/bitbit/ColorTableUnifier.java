/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitbit;

import java.awt.AWTException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class ColorTableUnifier {
    
    public ColorTable unify(List<ColorTable> tables) throws AWTException {
        ColorTable result = new ColorTable();
        for (ColorTable table : tables) {
            for (BmpColor color : table) {
                result.addColor(color);
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
 
        try {
            Bitmap bmp1 = new Bitmap("C:/Users/David/Desktop/test.bmp");
            Bitmap bmp2 = new Bitmap("C:/Users/David/Desktop/test2.bmp");
            
            System.out.println("Two Bitmaps:");
            System.out.println(bmp1.debug());
            System.out.println(bmp2.debug());
            
            List<ColorTable> tables = new ArrayList<>();
            tables.add(bmp1.getColorTable());
            tables.add(bmp2.getColorTable());
            
            ColorTable unified = new ColorTableUnifier().unify(tables);
            
            System.out.println("Unified color table");
            System.out.println(unified);
            
            bmp1.replaceColorTable(unified);
            bmp2.replaceColorTable(unified);
            
            System.out.println("Two bitmaps modified");
            System.out.println(bmp1.debug());
            System.out.println(bmp2.debug());
            
            bmp1.exportBitmap("C:/Users/David/Desktop/test3.bmp");
            bmp2.exportBitmap("C:/Users/David/Desktop/test4.bmp");
            
        } catch (Exception ex) {
            Logger.getLogger(ColorTableUnifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
