package com.me.image;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Random;

public class CreateImage {
//    private  static Random random = new Random(System.currentTimeMillis());
//    private int colorNum = 256;
//
//    public void create() {
//        int width = 100;
//        int height = 100;
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics graphics = image.getGraphics();
//
//        draw2(width, height, graphics);
//
//        createImage("/Users/buyulian/E/utilcode/commonUtils/a.jpg", image);
//    }
//
//    private void draw(int width, int height, Graphics graphics) {
//        int ld = 1;
//
//        int[][][] bitMap = new int[width + 1][height + 1][3];
//        for (int i = 0; i < width + 1; i+=ld) {
//            for (int j = 0; j < height + 1; j+=ld) {
//                if (i == 0 || j == 0) {
//                    bitMap[i][j] = new int[]{colorNum/2 , colorNum/2 , colorNum/2};
//                    continue;
//                }
//                nextData(bitMap, random, i, j, 0);
//                nextData(bitMap, random, i, j, 1);
//                nextData(bitMap, random, i, j, 2);
//                System.out.println(String.format("i %d j %d %s", i, j, Arrays.toString(bitMap[i][j])));
//                graphics.setColor(new Color(bitMap[i][j][0], bitMap[i][j][1], bitMap[i][j][2]));
//                graphics.fillRect(i - 1, j - 1, ld, ld);
//            }
//        }
//    }
//
//    private void draw2(int width, int height, Graphics graphics) {
//        int ld = 1;
//        for (int i = 0; i < width; i+=ld) {
//            for (int j = 0; j < height; j+=ld) {
//                double ir = i / (double)width;
//                double jr = j / (double)height;
//
//                int r = i + j;
//                int g = i - j;
//                int b = - i + j;
//                r = getMod(r);
//                g = getMod(g);
//                b = getMod(b);
//                System.out.println(r+" "+g+" "+b);
//                graphics.setColor(new Color(r, g, b));
//                graphics.fillRect(i, j, ld, ld);
//            }
//        }
//    }
//
//    private void nextData(int[][][] bitMap, Random random, int i, int j, int i2) {
//        int b = (bitMap[i - 1][j][i2] + bitMap[i][j - 1][i2]) / 2 + getRandomInt(random);
//        int c = getMod(b);
//        bitMap[i][j][i2] = c;
//    }
//
//    private int getRandomInt(Random random) {
//        int range = 10;
//        return range/2 - random.nextInt(range);
//    }
//
//    private int getMod(int r) {
//        if (r < 0) {
//            r = 0;
//        }
//        if (r > colorNum - 1) {
//            r = colorNum - 1;
//        }
//        int i = (r + colorNum * 10) % colorNum;
//        return i;
//    }
//
//    void createImage(String fileLocation, BufferedImage image) {
//        try {
//            FileOutputStream fos = new FileOutputStream(fileLocation);
//            BufferedOutputStream bos = new BufferedOutputStream(fos);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
//            encoder.encode(image);
//            bos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
