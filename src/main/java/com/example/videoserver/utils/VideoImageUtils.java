package com.example.videoserver.utils;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

public class VideoImageUtils {

    /*默认图片格式 jpg*/
    public static String DEFAULT_IMG_FORMAT = "jpg";

    public static BufferedImage getVideoImage(String videoFilePAth, int frameNum) {
        if(!FileUtils.isFileExist(videoFilePAth)) {
            return null;
        }
        try {
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFilePAth);
            ff.start();
            int length = ff.getLengthInFrames();
            /*第几帧判断设置*/
            if (frameNum < 0) {
                frameNum = 0;
            }
            if (frameNum > length) {
                frameNum = length - 5;
            }
            //指定第几帧
            ff.setFrameNumber(frameNum);
            int i = 0;
            Frame f = null;
            while (i < length) {
                // 过滤前5帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabFrame();
                if ((i >= 5) && (f.image != null)) {
                    break;
                }
                i++;
            }
            opencv_core.IplImage img = f.image;
            int width = img.width();
            int height = img.height();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                    0, 0, null);
            ff.flush();
            ff.stop();

            return bi;
        }catch (Exception e) {
            System.out.println("获取视频封面有误：" + videoFilePAth);
        }

        return null;
    }

    public static String getVideoImageToBase64 (String videoFilePAth, int frameNum) {
        if(!FileUtils.isFileExist(videoFilePAth)) {
            return null;
        }
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();) {
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFilePAth);
            ff.start();
            int length = ff.getLengthInFrames();
            /*第几帧判断设置*/
            if (frameNum < 0) {
                frameNum = 0;
            }
            if (frameNum > length) {
                frameNum = length - 5;
            }
            //指定第几帧
            ff.setFrameNumber(frameNum);
            int i = 0;
            Frame f = null;
            while (i < length) {
                // 过滤前5帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabFrame();
//                if ((i >= 5) && (f.image != null)) {
//                    break;
//                }
                if (f.image != null) {
                    break;
                }
                i++;
            }
            opencv_core.IplImage img = f.image;
            int width = img.width();
            int height = img.height();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                    0, 0, null);
            ImageIO.write(bi, DEFAULT_IMG_FORMAT, output);
            ff.flush();
            ff.stop();

            return Base64.getEncoder().encodeToString(output.toByteArray());
        }catch (Exception e) {
            System.out.println("获取视频封面有误：" + videoFilePAth);
        }

        return null;
    }

    public static void getVideoImageToJpg(String videoFilePAth, String targetFilePath, int frameNum) {
        if(!FileUtils.isFileExist(videoFilePAth)) {
            return;
        }
        try {
            File frameFile = new File(targetFilePath);
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFilePAth);
            ff.start();
            int length = ff.getLengthInFrames();
            /*第几帧判断设置*/
            if (frameNum < 0) {
                frameNum = 0;
            }
            if (frameNum > length) {
                frameNum = length - 5;
            }
            //指定第几帧
            ff.setFrameNumber(frameNum);
            int i = 0;
            Frame f = null;
            while (i < length) {
                // 过滤前5帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabFrame();
                if (f.image != null) {
                    break;
                }
                i++;
            }
            opencv_core.IplImage img = f.image;
            int width = img.width();
            int height = img.height();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                    0, 0, null);
            ff.flush();
            ff.stop();
            ImageIO.write(bi, DEFAULT_IMG_FORMAT, frameFile);
        } catch (Exception e) {
            System.out.println("获取视频封面有误：" + videoFilePAth);
        }
    }
}
