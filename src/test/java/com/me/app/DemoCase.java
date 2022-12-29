package com.me.app;

import com.me.file.FileIo;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 将一张图片生成为字符画
 *
 * @author 11475
 *
 */
public class DemoCase {

    class ParamDto {
        String readFileName;
        double gapX;
        double ratio;

        public ParamDto(String readFileName, double gapX, double ratio) {
            this.readFileName = readFileName;
            this.gapX = gapX;
            this.ratio = ratio;
        }
    }

    @Test
    public void test() {
        String path = "/Users/buyulian/E/utilcode/charPicture/";
//                String path = "/Users/buyulian/Pictures/yy.jpg";
        String readFileName = "1.png";
//        readFileName = "yy.jpg";
//        readFileName = "a.jpg";
//        readFileName = "b.jpeg";
//        readFileName = "c.png";
//        readFileName = "d.png";
//        readFileName = "e.jpeg";
//        readFileName = "f.jpg";
        readFileName = "g.jpg";
//        readFileName = "h.png";

        ParamDto paramDto = new ParamDto("i.jpg", 8, 1.5);
        DemoCase.createAsciiPic(path, paramDto);
    }

    /**
     * @param path 图片路径
     */
    public static void createAsciiPic(final String path, ParamDto paramDto) {
        String[] baseStr = new String[] {
                "囍"
                ,"春"
                ,"新"
                ,"快"
                ,"年"
                ,"乐"
                ,"五"
                ,"土"
                ,"三"
                ,"十"
                ,"二"
                ,"一"
                ,"丨"
                ,"  "
        };
//        baseStr = new String[] {
//                "新"
//                ,"  "
//        };
        boolean isReverse = false;
        int length = baseStr.length;
        StringBuilder sb = new StringBuilder();
        String readFileName = paramDto.readFileName;
        try {
            final BufferedImage image = ImageIO.read(new File(path + readFileName));
            double gapX = paramDto.gapX;
            double gapY = paramDto.ratio * gapX;
            for (double y = 0; y < image.getHeight(); y += gapY) {
                for (double x = 0; x < image.getWidth(); x += gapX) {
                    float graySum = 0;
                    double num = 0;
                    for (double i = 0; i < gapX && y + i < image.getHeight(); i++) {
                        for (double j = 0; j < gapY && x + j < image.getWidth(); j++) {
                            final int pixel = image.getRGB((int) (x + j), (int)(y + i));
                            final float gray = getGray(pixel);
                            graySum += gray;
                            num += 1;
                        }
                    }
                    double avgGray = graySum/num;
                    int index = (int) Math.floor(avgGray * length / 256);
                    if (isReverse) {
                        index = length - 1 - index;
                    }
                    String curStr = baseStr[index];
                    sb.append(curStr);
                }
                sb.append("\n");
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        String content = sb.toString();
        System.out.println(content);
        FileIo.writeFile(new File(path+readFileName+"w.txt"), content);
    }

    private static float getGray(int pixel) {
        if (pixel == 0) {
            return 255;
        }
        final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
        final float gray = 0.301f * r + 0.584f * g + 0.115f * b;
        return gray;
    }
}