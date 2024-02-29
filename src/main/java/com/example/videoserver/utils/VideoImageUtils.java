package com.example.videoserver.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class VideoImageUtils {

    /*默认图片格式 jpg*/
    public static String DEFAULT_IMG_FORMAT = "jpg";

    //参数：视频路径和缩略图保存路径
    public static void fetchFrame(String videofile, String framefile) {
        try {
            File targetFile = new File(framefile);
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
            ff.start();
            int length = ff.getLengthInFrames();
            int i = 0;
            Frame f = null;
            while (i < length) {
                // 去掉前5帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabImage();
                if ((i > 5) && (f.image != null)) {
                    break;
                }
                i++;
            }
            ImageIO.write(FrameToBufferedImage(f), DEFAULT_IMG_FORMAT, targetFile);
            //ff.flush();
            ff.stop();
        } catch (Exception e) {
            System.out.println("获取视频有误：" + videofile);
            System.out.println(e.toString());
        }
    }

    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }
}
