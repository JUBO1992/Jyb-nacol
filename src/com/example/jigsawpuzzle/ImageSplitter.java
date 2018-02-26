package com.example.jigsawpuzzle;

import java.util.ArrayList;
import java.util.List;

import com.example.jyb.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Description: Í¼Æ¬ÇÐÆ¬Àà
 */
public class ImageSplitter {
	/**
	 * ½«Í¼Æ¬ÇÐ³É , piece *piece
	 * 
	 * @param bitmap
	 * @param piece
	 * @return
	 */
	public static List<ImagePiece> split(Bitmap bitmap, int piece) {

		List<ImagePiece> pieces = new ArrayList<ImagePiece>(piece * piece);

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		// Log.e("TAG", "bitmap Width = " + width + " , height = " + height);
		int pieceWidth = Math.min(width, height) / piece;
		int startX = width >= height ? (width - height) / 2 : 0;
		int startY = height >= width ? (height - width) / 2 : 0;

		for (int i = 0; i < piece; i++) {
			for (int j = 0; j < piece; j++) {
				ImagePiece imagePiece = new ImagePiece();
				imagePiece.index = j + i * piece;
				int xValue = startX + j * pieceWidth;
				int yValue = startY + i * pieceWidth;

				imagePiece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue, pieceWidth, pieceWidth);
				pieces.add(imagePiece);
			}
		}
		return pieces;
	}

	public static Bitmap thumbnail(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap bmap = null;
		if (width >= height) {
			bmap = Bitmap.createBitmap(bitmap, (width - height) / 2, 0, height, height);
		} else {
			bmap = Bitmap.createBitmap(bitmap, (height - width) / 2, 0, width, width);
		}

		return bmap;
	}
}
