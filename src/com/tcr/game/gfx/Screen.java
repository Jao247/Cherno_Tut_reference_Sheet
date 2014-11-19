package com.tcr.game.gfx;

import com.tcr.game.references.Finals;

import java.util.Random;

public class Screen
{
	private int width, height;
	public int[] pixels;
	public int[] tiles = new int[Finals.MAP_SIZE * Finals.MAP_SIZE];

	private Random rand = new Random();

	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];

		for (int i = 0; i < Finals.MAP_SIZE * Finals.MAP_SIZE; i++)
			tiles[i] = rand.nextInt(0xffffff);
	}

	public void clear()
	{
		for (int i = 0; i < pixels.length; i++) pixels[i] = 0;
	}

	public void render(int xOffset, int yOffset)
	{
		for (int y = 0; y < height; y++)
		{
			int yy = y + yOffset;
//			if (yy < 0 || yy >= height) break;
			for (int x = 0; x < width; x++)
			{
				int xx = x + xOffset;
//				if (xx < 0 || xx >= width) break;
				int tileIndex = ((xx >> Finals.TILE_SIZE) & Finals.MAP_SIZE_MASK) + ((yy >> Finals.TILE_SIZE) & Finals.MAP_SIZE_MASK) * Finals.MAP_SIZE;
				pixels[x + y * width] = tiles[tileIndex];
			}
		}
	}
}
