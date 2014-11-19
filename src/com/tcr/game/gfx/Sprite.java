package com.tcr.game.gfx;

public class Sprite
{
	private final int SIZE;
	private int x, y;
	private SpriteSheet sheet;

	public int[] pixels;

	public Sprite(SpriteSheet sheet, int x, int y, int size)
	{
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		SIZE = size;

		pixels = new int[SIZE * SIZE];
	}
}
