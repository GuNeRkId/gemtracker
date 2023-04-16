package com.gemtracker;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.Notifier;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.xptracker.XpTrackerPlugin;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Set;

@PluginDescriptor(
        name = "Gem Rock Tracker",
        description = "A plugin that tracks gems from gem rocks that you mine in the Shilo Village underground mine.",
        tags = {"gem", "mining"}
)

@PluginDependency(XpTrackerPlugin.class)
public class GemTrackerPlugin extends Plugin
{
    @Getter(AccessLevel.PACKAGE)
    @Inject
    private GemTrackerSession session;

    private static final Set<Integer> MINING_PICKS = ImmutableSet.of(7283, 628, 3873, 625,8347, 7139, 8346, 8887, 642, 8313, 4482, 626, 629, 624, 627);

    private static final Set<Integer> SHILO_VILLAGE_MINE_REGION = ImmutableSet.of(11153, 11154, 11155, 11409, 11410, 11411, 11665, 11666, 11667);
    @Getter
    private static final Set<Integer> UNCUT_GEM_IDS = ImmutableSet.of(ItemID.UNCUT_JADE, ItemID.UNCUT_DIAMOND, ItemID.UNCUT_SAPPHIRE, ItemID.UNCUT_OPAL, ItemID.UNCUT_EMERALD,
                                                                        ItemID.UNCUT_RUBY, ItemID.UNCUT_RED_TOPAZ);
    @Inject
    private Client client;

    @Inject
    private Notifier notifier;
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ClientThread clientThread;

    @Inject
    private GemTrackerConfig config;
    @Inject
    private GemTrackerOverlay overlay;
    @Getter(AccessLevel.PACKAGE)
    public boolean inSvm;
    @Inject
    @Getter
    public String lastGemFoundS = "";


    @Provides
    GemTrackerConfig getConfig(ConfigManager configManager) {return configManager.getConfig(GemTrackerConfig.class);}

    @Override
    protected void startUp() throws Exception{
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        session.setGemFound(null);
    }

    @Subscribe
    @Inject
    public void onChatMessage(ChatMessage event)
    {
        if (!checkInSvm() || event.getType() != ChatMessageType.SPAM)
        {
            return;
        }

        String chatMessage = event.getMessage();

        switch (chatMessage)
        {
            case "You just mined a Diamond!":
                session.incrementGemFound(ItemID.UNCUT_DIAMOND);
                lastGemFoundS = "Diamond";
                break;

            case "You just mined a Ruby!":
                session.incrementGemFound(ItemID.UNCUT_RUBY);
                lastGemFoundS = "Ruby";
                break;

            case "You just mined an Emerald!":
                session.incrementGemFound(ItemID.UNCUT_EMERALD);
                lastGemFoundS = "Emerald";
                break;

            case "You just mined a Sapphire!":
                session.incrementGemFound(ItemID.UNCUT_SAPPHIRE);
                lastGemFoundS = "Sapphire";
                break;

            case "You just mined an Opal!":
                session.incrementGemFound(ItemID.UNCUT_OPAL);
                lastGemFoundS = "Opal";
                break;

            case "You just mined a piece of Jade!":
                session.incrementGemFound(ItemID.UNCUT_JADE);
                lastGemFoundS = "Jade";
                break;

            case "You just mined a Red Topaz!":
                session.incrementGemFound(ItemID.UNCUT_RED_TOPAZ);
                lastGemFoundS = "Red Topaz";
                break;
        }
    }

    public boolean checkInSvm()
    {
        int[] currentMapRegions = client.getMapRegions();

        GameState gameState = client.getGameState();
        if (gameState == GameState.LOGGED_IN)
        {
            for (int region : currentMapRegions) {
                if (SHILO_VILLAGE_MINE_REGION.contains(region)) {
                    inSvm = true;
                    return true;
                }
            }
        }

        inSvm = false;
        return false;
    }

    void reset() {session.setGemFound(null); }

    public boolean areMining()
    {
        if (Arrays.asList(MINING_PICKS).contains(client.getLocalPlayer().getAnimation()))
        {
            return true;
        }
        return false;
    }

}