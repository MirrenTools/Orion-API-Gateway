package org.mirrentools.gateway.console.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
/**
 * 验证码生成工具
 * 
 * @author <a href="https://mirrentools.org/">Mirren</a>
 *
 */
public class VerifyCodeUtils {
	/** 使用字体 */
	public final static String DEFAULT_FONT = "Aladin";
	// 验证码值字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
	public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	/** 图片的默认宽度 */
	public final static int DEFAULT_WIDTH = 200;
	/** 图片的默认宽度 */
	public final static int DEFAULT_HEIGHT = 40;
	private static Random random = new Random();

	/**
	 * 使用系统默认字符源生成验证码
	 * 
	 * @param verifySize
	 *          验证码长度
	 * @return
	 */
	public static String generateVerifyCode(int verifySize) {
		return generateVerifyCode(verifySize, VERIFY_CODES);
	}
	/**
	 * 使用指定源生成验证码
	 * 
	 * @param verifySize
	 *          验证码长度
	 * @param sources
	 *          验证码字符源
	 * @return
	 */
	public static String generateVerifyCode(int verifySize, String sources) {
		if (sources == null || sources.length() == 0) {
			sources = VERIFY_CODES;
		}
		int codesLen = sources.length();
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(verifySize);
		for (int i = 0; i < verifySize; i++) {
			char c;
			do {
				c = sources.charAt(rand.nextInt(codesLen - 1));
			} while (verifyCode.indexOf(Character.toString(c)) != -1);
			synchronized (verifyCode) {
				verifyCode.append(c);
			}
		}
		return verifyCode.toString();
	}

	/**
	 * 生成验证码<br>
	 * 
	 * @return index = 索引<br>
	 *         value = 索引校验值<br>
	 *         data = 校验码<br>
	 *         code = 验证码<br>
	 */
	public static Map<String, String> generateVerityData() {
		String verifyCode = generateVerifyCode(5);
		List<Integer> list = new ArrayList<>();
		while (list.size() < 4) {
			Random rd = new Random();
			int int1 = rd.nextInt(5);
			while (!list.contains(int1)) {
				list.add(int1);
				int1 = rd.nextInt(5);
				break;
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append(verifyCode.charAt(list.get(0)));
		sb.append(verifyCode.charAt(list.get(1)));
		sb.append(verifyCode.charAt(list.get(2)));
		sb.append(verifyCode.charAt(list.get(3)));
		String index = Long.toString(System.nanoTime()) + random.nextInt(3);
		String value = "0" + list.get(0) + "0" + list.get(1) + "0" + list.get(2) + "0" + list.get(3);
		Map<String, String> result = new HashMap<>(3);
		result.put("index", index);
		result.put("value", value);
		result.put("data", sb.toString());
		result.put("code", verifyCode);
		return result;
	}
	/**
	 * 生成二进制的图片
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public static byte[] getImageBytes(String code) throws IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		outputImage(DEFAULT_WIDTH, DEFAULT_HEIGHT, stream, code);
		byte[] bs = stream.toByteArray();
		return bs;
	}
	/**
	 * 生成指定验证码图像文件
	 * 
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param code
	 * @throws IOException
	 */
	public static void outputImage(int w, int h, File outputFile, String code) throws IOException {
		if (outputFile == null) {
			return;
		}
		File dir = outputFile.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		outputFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(outputFile);
		outputImage(w, h, fos, code);
		fos.close();
	}

	/**
	 * 输出指定验证码图片流
	 * 
	 * @param w
	 * @param h
	 * @param os
	 * @param code
	 * @throws IOException
	 */
	public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
		int verifySize = code.length();
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Random rand = new Random();
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color[] colors = new Color[5];
		Color[] colorSpaces = new Color[]{Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK,
				Color.YELLOW};
		float[] fractions = new float[colors.length];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
			fractions[i] = rand.nextFloat();
		}
		Arrays.sort(fractions);

		g2.setColor(Color.GRAY);// 设置边框色
		g2.fillRect(0, 0, w, h);

		Color c = getRandColor(245, 250);
		g2.setColor(Color.white);// 设置背景色
		g2.fillRect(0, 2, w, h - 4);

		// 添加噪点
		float yawpRate = 0.05f;// 噪声率
		int area = (int) (yawpRate * w * h);
		for (int i = 0; i < area; i++) {
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}

		shear(g2, w, h, c);// 使图片扭曲

		g2.setColor(getRandColor(100, 160));
		int fontSize = h - 4;
		Font font = new Font(DEFAULT_FONT, Font.ITALIC, fontSize);
		g2.setFont(font);
		char[] chars = code.toCharArray();
		Color lineColor = getRandColor(160, 200);
		for (int i = 0; i < verifySize; i++) {
			AffineTransform affine = new AffineTransform();
			g2.setTransform(affine);
			int y = (40 * i) + 7;
			g2.setColor(lineColor);
			//画分割线
			if (i!=0) {
				g2.drawLine(i * 40, 1, i * 40, 40);
			}
			g2.setColor(getRandColor(160, 200));
			g2.drawChars(chars, i, 1, y, 33);
		}

		g2.dispose();
		ImageIO.write(image, "jpg", os);
	}

	private static Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private static int getRandomIntColor() {
		int[] rgb = getRandomRgb();
		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}

	private static int[] getRandomRgb() {
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}

	private static void shear(Graphics g, int w1, int h1, Color color) {
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	private static void shearX(Graphics g, int w1, int h1, Color color) {
		int period = random.nextInt(2);
		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);
		for (int i = 0; i < h1; i++) {
			double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap) {
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}

	}

	private static void shearY(Graphics g, int w1, int h1, Color color) {
		int period = random.nextInt(40) + 10; // 50;
		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++) {
			double d = (double) (period >> 1) * Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase) / (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap) {
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}
		}
	}
}
