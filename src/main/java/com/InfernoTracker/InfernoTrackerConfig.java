package com.InfernoTracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface InfernoTrackerConfig extends Config
{
	@ConfigItem(
		keyName = "greeting",
		name = "Welcome Greeting",
		description = "The message to show to the user when they login"
	)
	default String greeting()
	{
		return "WELCOME TO EVIL RUNESCAPE. THE EVILEST GAME EVER MADE";
	}

	@ConfigItem(
			keyName = "showGreeting",
			name = "Show Greeting",
			description = "Enable this checkmark to show a fun and nice greeting message"
	)
	default boolean showGreeting() {
		return false;
	}

}
