package com.InfernoTracker;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import org.apache.commons.lang3.ArrayUtils;

@Slf4j
@PluginDescriptor(
	name = "Example"
)
public class InfernoTrackerPlugin extends Plugin
{
	//TODO: if no file, numWaves, numKills, numDeaths = 0
	//else, read from file :3

	int numWaves = 0;
	int numKills = 0;
	int numDeaths = 0;


	@Inject
	private Client client;

	@Inject
	private InfernoTrackerConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("InfernoTracker started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("InfernoTracker stopped!");
	}

	//shows custom greeting if enabled (remnant of plugin template but its fun)
	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN && config.showGreeting())
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", config.greeting(), null);
		}
	}

	//searches for chat message corresponding to each of the 3 tracked stats
	@Subscribe
	public void onChatMessage(ChatMessage event) {
		if(isInInferno() && event.getType() == ChatMessageType.GAMEMESSAGE){
			String msg = event.getMessage();

			if (msg.contains("Wave complete")){
				numWaves++;
			}

			if (msg.contains("Oh dear, you are dead")){
				numDeaths++;
			}

			if (msg.contains("Your TzKal-Zuk kill count")){
				numKills++;
			}
		}
	}

	@Provides
	InfernoTrackerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(InfernoTrackerConfig.class);
	}

	private static final int INFERNO_REGION = 9043;
	private boolean isInInferno()
	{
		return ArrayUtils.contains(client.getMapRegions(), INFERNO_REGION);
	}
}
