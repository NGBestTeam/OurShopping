package com.zxing.encoding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * @author Ryan Tang
 *
 */
public final class EncodingHandler {
	private static final int BLACK = 0xff000000;
	public static  Bitmap enCodeStringWithLogo(String content,Context context,Bitmap logo,int hightAndWidth){
		Bitmap bitmap = null;

		// 生成二维码的配置信息
		Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        // 设置二维码 编码
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

		// 容错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

		try {

			// 使用 QRCodeWriter 来生成 黑白 位矩阵
			QRCodeWriter writer = new QRCodeWriter();

			// 编码矩阵
			BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, hightAndWidth, hightAndWidth, hints);

			// 获取矩阵的宽高
			int width = matrix.getWidth();
			int height = matrix.getHeight();
			// 每一个像素，转换为一维数组
			int[] pixels = new int[width * height];

			for (int y = 0; y < height; y++) {  // 每一行
				int offset = y * width;
				for (int x = 0; x < width; x++) {
					pixels[offset + x] = matrix.get(x, y) ? Color.BLACK : Color.WHITE;
				}
			}

			bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			// 设置中心logo
			if (logo!=null){
				bitmap = addLogo(bitmap,logo);
			}
			pixels = null;

			matrix = null;

			writer = null;
         return bitmap;

		} catch (WriterException e) {
			e.printStackTrace();
		}
        return  bitmap;
	}
	
	/**
	 * 在二维码中间添加Logo图案
	 */
	private static Bitmap addLogo(Bitmap src, Bitmap logo) {
		if (src == null) {
			return null;
		}

		if (logo == null) {
			return src;
		}

		//获取图片的宽高
		int srcWidth = src.getWidth();
		int srcHeight = src.getHeight();
		int logoWidth = logo.getWidth();
		int logoHeight = logo.getHeight();

		if (srcWidth == 0 || srcHeight == 0) {
			return null;
		}

		if (logoWidth == 0 || logoHeight == 0) {
			return src;
		}

		//logo大小为二维码整体大小的1/5
		float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
		Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
		try {
			Canvas canvas = new Canvas(bitmap);
			canvas.drawBitmap(src, 0, 0, null);
			canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
			canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

			canvas.save(Canvas.ALL_SAVE_FLAG);
			canvas.restore();
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}

		return bitmap;
	}
}
