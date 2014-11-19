package com.tcr.game;

import com.tcr.game.gfx.Screen;
import com.tcr.game.input.InputHandler;
import com.tcr.game.references.Finals;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable
{
	private Thread thread;
	private JFrame frame;
	private boolean running = false;

	private Screen screen;
	private InputHandler input;
	private int x, y;

	private BufferedImage image = new BufferedImage(Finals.WIDTH, Finals.HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game()
	{
		setPreferredSize(new Dimension(Finals.WIDTH * Finals.SCALE, Finals.HEIGHT * Finals.SCALE));
		setMinimumSize(new Dimension(Finals.WIDTH * Finals.SCALE, Finals.HEIGHT * Finals.SCALE));
		setMaximumSize(new Dimension(Finals.WIDTH * Finals.SCALE, Finals.HEIGHT * Finals.SCALE));

		frame = new JFrame(Finals.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public void init()
	{
		screen = new Screen(Finals.WIDTH, Finals.HEIGHT);
		input = new InputHandler(this);
	}

	public synchronized void start()
	{
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public synchronized void stop()
	{
		running = false;
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		long lastTime = System.nanoTime();
		final double nsPerTick = 1000000000D / 60D;
		double delta = 0;

		int ticks = 0, frames = 0;

		long lastTimer = System.currentTimeMillis();

		init();
		while (running)
		{
			long now = System.nanoTime();

			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			while (delta >= 1)
			{
				ticks++;
				tick();
				delta--;
			}
			frames++;
			render();

			if (System.currentTimeMillis() - lastTimer > 1000)
			{
				lastTimer += 1000;
				frame.setTitle(Finals.NAME + " | " + frames + "fps, " + ticks + "ticks");
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void tick()
	{
		if (input.left.isPressed()) x--;
		if (input.right.isPressed()) x++;
		if (input.up.isPressed()) y--;
		if (input.down.isPressed()) y++;
	}

	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}

		screen.clear();
		screen.render(x, y);

		for (int i = 0; i < pixels.length; i++) pixels[i] = screen.pixels[i];

		Graphics g = bs.getDrawGraphics();
		{
			g.setColor(Color.black);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String args[])
	{
		new Game().start();
	}
}
