package com.tcr.game.references;

public class Finals
{
	public static final int WIDTH = 300;
	public static final int HEIGHT = WIDTH / 16 * 9;
	public static final int SCALE = 3;
	public static final String NAME = "Rain";
	public static final int MAP_SIZE = 32;
	public static final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public static final int TILE_SIZE = 4; //Bitwise based 2^(value) -> 2^4 = 16, 2^5 = 32
}
