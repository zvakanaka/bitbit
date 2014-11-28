/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitbit;

import static bitbit.ColorConsole.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author adam
 */
public class PicTerm {
     //TODO: switch to be in object
    static int width = 0;
    static int height = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            int[][] bitmap;
            if (args.length == 0) {
                String temp;
               temp = "/home/adam/Desktop/fun.bmp";
                bitmap = new PicTerm().seeBMPImage(temp);
            } else {
                bitmap = new PicTerm().seeBMPImage(args[0]);
            }
            PicTerm.displayArray2D(bitmap, width, height);
        } catch (IOException ex) {
            Logger.getLogger(PicTerm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        BufferedReader dataIn = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Press ENTER to end...");
            String temp = dataIn.readLine();
        } catch (IOException ex) {
            Logger.getLogger(PicTerm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int[][] seeBMPImage(String BMPFileName) throws IOException {

        BufferedImage image = null;
        image = ImageIO.read(new File(BMPFileName));
        int[][] array2D = new int[image.getWidth()][image.getHeight()];
        //TODO: put width and height in an object
        width = image.getWidth();
        height = image.getHeight();
        for (int yPixel = 0; yPixel < image.getHeight(); yPixel++) {
            for (int xPixel = 0; xPixel < image.getWidth(); xPixel++) {
                int color = image.getRGB(xPixel, yPixel);
                //displayColorInfo(xPixel, yPixel, color);
                array2D[xPixel][yPixel] = color;
            }
        }
        return array2D;
    }

    public static void displayArray2D(int[][] color, int xMax, int yMax) {
        System.out.println("Width:" + width + ", Height:"
                + height);
        int stride = 1;
        int termWidth = 208 / 2;
        stride = computeStride(termWidth);
        System.out.print("   ");
        System.out.println(" X");
        for (int yPixel = 0; yPixel < yMax; yPixel += stride) {
            //allignment of line number spacing
            System.out.print(yPixel);//line numbers
            if (yPixel < 10) {
                System.out.print(" ");
            }
            if (yPixel < 100) {
                System.out.print(" ");
            }
            if (height > 99 && yPixel < 1000) {
                System.out.print(" ");
            }
            for (int xPixel = 0; xPixel < xMax; xPixel += stride) {
                String r = intToHex(color[xPixel][yPixel]).substring(2, 4);
                String g = intToHex(color[xPixel][yPixel]).substring(4, 6);
                String b = intToHex(color[xPixel][yPixel]).substring(6, 8);
                String s = intToHex(color[xPixel][yPixel]).substring(0, 2);
                //print each pixel in color here
                displayPixel(hexToInt(r), hexToInt(g), hexToInt(b), hexToInt(s));
            }
            System.out.println();
        }
        System.out.println(" Y");
    }
    
    //Math functions:
           //TODO: complete toHex method here:
    public static String intToHex(int val) {
        String hex = Integer.toHexString(val);
        return hex;
    }
    
    //chops off excess at len
    public static String intToHex(int i, int len) {
        String hex = intToHex(i);  
        if (hex.length() > len) {
            hex = hex.substring(0, len);
        }
        return hex;
    }

    public static int hexToInt(String hexString) {
        int val = Integer.valueOf(hexString, 16).intValue();
        return val;
    }

    //for big images, we want to skip pixels
    public static int computeStride(int termWidth) {
        int stride = 1;
        if (width > termWidth) {
            stride *= 2;
            if (width > termWidth * 2) {
                stride *= 2;
                if (width > termWidth * 4) {
                    stride *= 2;
                    if (width > termWidth * 8) {
                        stride *= 2;
                        if (width > termWidth * 16) {
                            stride *= 2;
                            if (width > termWidth * 32) {
                                stride *= 2;
                                if (width > termWidth * 64) {
                                    stride *= 2;
                                }
                            }
                        }
                    }
                }
            }
        }
        return stride;
    }
    
    public static String displayPixel(int r, int g, int b) {
        return displayPixel(r, g, b, -1);
    }
     
    public static String displayPixel(String rS, String gS, String bS) {
        int r, g, b;
        r = hexToInt(rS);
        g = hexToInt(gS);
        b = hexToInt(bS);
        return displayPixel(r, g, b, -1);
    }
    
    //TODO: have it return, not display
    //contains logic for deciding colors
    public static String displayPixel(int r, int g, int b, int s) {
        String colorString = "Not implemented yet";
        if (b < 50 && r < 50 && g < 50) {
            System.out.print(ANSI_BLACK + "[" + "b" + "");
        } else if (b < 50 && r > 50 && g < 50) {
            System.out.print(ANSI_RED + "[" + "R" + "");
        } else if (b < 150 && r < 100 && g > 50) {
            System.out.print(ANSI_GREEN + "[" + "G" + "");
        } else if (b > 50 && r < 50 && g < 50) {
            System.out.print(ANSI_BLUE + "[" + "B" + "");
        } else if (b > 50 && r > 50 && g < 70) {
            System.out.print(ANSI_PURPLE + "[" + "P" + "");
        } else if (b > 150 && r > 150 && g > 150) {
            System.out.print(ANSI_WHITE + "[" + "W" + "");
        } else if (b < 50 && r > 50 && g > 50) {
            System.out.print(ANSI_YELLOW + "[" + "Y" + "");
        } else {
            System.out.print(ANSI_CYAN + "[" + "?" + "");
        }
        System.out.print(ANSI_RESET);
        return "Not Yet Implemented";//colorString;
    }

    public void displayColorInfo(int x, int y, int color) {

        System.out.print("Adding color: " + intToHex(color));
        System.out.println("  R=" + intToHex(color).substring(2, 4)
                + " G=" + intToHex(color).substring(4, 6)
                + " B=" + intToHex(color).substring(6, 8)
                + " Special=" + intToHex(color).substring(0, 2)
                + " to 2d array, X=" + x + " Y=" + y);
    }
}