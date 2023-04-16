package com.gemtracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("gemtracker")
public interface GemTrackerConfig extends Config
        {
            @ConfigItem(
                position = 1,
                keyName = "standAloneMode",
                name = "Stand alone mode?",
                description = "Keeps track of overall mining stats while your mining. Use if not using the mining plugin."
            )
            default boolean standAloneMode() { return false; }

            @ConfigItem(
                    position = 2,
                    keyName = "trackGemTypes",
                    name = "Track gems types?",
                    description = "Keeps track of individual gem types while your mining."
            )
            default boolean trackGemTypes() { return true; }
            @ConfigItem(
                    position = 3,
                    keyName = "trackXpHr",
                    name = "Display xp per hour?",
                    description = "Keeps track of xp per hour while mining."
            )
            default boolean trackXpHr() { return true; }

            @ConfigItem(
                    position = 4,
                    keyName = "trackProfit",
                    name = "Display total profit?",
                    description = "Tracks profit made from mining gem rocks."
            )
            default boolean trackProfit() { return true; }

            @ConfigItem(
                    position = 5,
                    keyName = "trackLoads",
                    name = "Display total loads?",
                    description = "Tracks how many load of 28 gems have been mined."
            )
            default boolean trackLoads() { return false; }

            @ConfigItem(
                    position = 6,
                    keyName = "gemPrices",
                    name = "Display gem prices?",
                    description = "Displays current gem prices beside their count."
            )
            default boolean gemPrices() { return false; }

            @ConfigItem(
                    position = 7,
                    keyName = "textColor",
                    name = "Change text color",
                    description = "Change the color of the text within the overlay."
            )
            default Color cTextColor() {return Color.WHITE;}
}