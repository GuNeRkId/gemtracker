package com.gemtracker;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class GemTrackerPluginText
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(GemTrackerPlugin.class);
		RuneLite.main(args);
	}
}