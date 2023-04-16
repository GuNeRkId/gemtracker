package com.gemtracker;

import com.google.common.collect.ImmutableSet;
import net.runelite.api.AnimationID;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import net.runelite.api.ItemID;
import net.runelite.client.plugins.xptracker.XpTrackerService;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.Set;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY;

class GemTrackerOverlay extends OverlayPanel {
    private static final String GEMTRACKER_RESET = "Reset";

    @Inject
    ItemManager itemManager;


    private static final Set<Integer> MINING_ANIMATION = ImmutableSet.of(
            AnimationID.MINING_BRONZE_PICKAXE,
            AnimationID.MINING_IRON_PICKAXE,
            AnimationID.MINING_STEEL_PICKAXE,
            AnimationID.MINING_BLACK_PICKAXE,
            AnimationID.MINING_MITHRIL_PICKAXE,
            AnimationID.MINING_ADAMANT_PICKAXE,
            AnimationID.MINING_RUNE_PICKAXE,
            AnimationID.MINING_GILDED_PICKAXE,
            AnimationID.MINING_DRAGON_PICKAXE,
            AnimationID.MINING_DRAGON_PICKAXE_OR,
            AnimationID.MINING_DRAGON_PICKAXE_UPGRADED,
            AnimationID.MINING_3A_PICKAXE,
            AnimationID.MINING_INFERNAL_PICKAXE,
            AnimationID.MINING_CRYSTAL_PICKAXE);

    private final Client client;
    private final GemTrackerPlugin plugin;
    private final GemTrackerConfig config;

    private final GemTrackerSession gemTrackerSession;
    private XpTrackerService xpTrackerService;

    @Inject
    public GemTrackerOverlay(Client client, GemTrackerPlugin plugin, GemTrackerConfig config, GemTrackerSession gemTrackerSession, XpTrackerService xpTrackerService, ItemManager itemManager) {
        super(plugin);
        setPosition(OverlayPosition.TOP_LEFT);
        this.client = client;
        this.plugin = plugin;
        this.gemTrackerSession = gemTrackerSession;
        this.config = config;
        this.xpTrackerService = xpTrackerService;
        this.itemManager = itemManager;
        addMenuEntry(RUNELITE_OVERLAY, GEMTRACKER_RESET, "Gem Tracker overlay", e -> plugin.reset());
    }

    @Override
    public Dimension render(Graphics2D graphics) {

        if (gemTrackerSession.getGemFound() == null || !plugin.isInSvm()) {
            return null;
        }

        if (plugin.isInSvm()) {
            if (config.standAloneMode()) {
                if (MINING_ANIMATION.contains(client.getLocalPlayer().getAnimation())) {
                    panelComponent.getChildren().add(TitleComponent.builder()
                            .text("Gem Tracker")
                            .color(Color.GREEN)
                            .build());
                } else {
                    panelComponent.getChildren().add(TitleComponent.builder()
                            .text("Gem Tracker")
                            .color(Color.RED)
                            .build());
                }
            } else {
                if (gemTrackerSession.getGemFound() != null) {
                    panelComponent.getChildren().add(TitleComponent.builder()
                            .text("Gem Tracker")
                            .color(config.cTextColor())
                            .build());
                }
            }

            int actions = xpTrackerService.getActions(Skill.MINING);
            if (actions > 0) {

                if (config.trackGemTypes() && config.gemPrices()) {
                    if (plugin.getSession().getOpalsFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Opal: ")
                                .rightColor(config.cTextColor())
                                .right("("+ itemManager.getItemPrice(ItemID.UNCUT_OPAL) + ")" + " " + plugin.getSession().getOpalsFound())
                                .build());
                    }
                    if (plugin.getSession().getJadesFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Jade: ")
                                .rightColor(config.cTextColor())
                                .right("("+ itemManager.getItemPrice(ItemID.UNCUT_JADE) + ")" + " " + plugin.getSession().getJadesFound())
                                .build());
                    }
                    if (plugin.getSession().getRedtopazFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Red Topaz: ")
                                .rightColor(config.cTextColor())
                                .right("("+ itemManager.getItemPrice(ItemID.UNCUT_RED_TOPAZ) + ")" + " " + plugin.getSession().getRedtopazFound())
                                .build());
                    }
                    if (plugin.getSession().getSapphiresFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Sapphire: ")
                                .rightColor(config.cTextColor())
                                .right("("+ itemManager.getItemPrice(ItemID.UNCUT_SAPPHIRE) + ")" + " " + plugin.getSession().getSapphiresFound())
                                .build());
                    }
                    if (plugin.getSession().getEmeraldsFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Emerald: ")
                                .rightColor(config.cTextColor())
                                .right("("+ itemManager.getItemPrice(ItemID.UNCUT_EMERALD) + ")" + " " + plugin.getSession().getEmeraldsFound())
                                .build());
                    }
                    if (plugin.getSession().getRubiesFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Ruby: ")
                                .rightColor(config.cTextColor())
                                .right("("+ itemManager.getItemPrice(ItemID.UNCUT_RUBY) + ")" + " " + plugin.getSession().getRubiesFound())
                                .build());
                    }
                    if (plugin.getSession().getDiamondsFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Diamond: ")
                                .rightColor(config.cTextColor())
                                .right("("+ itemManager.getItemPrice(ItemID.UNCUT_DIAMOND) + ")" + " " + plugin.getSession().getDiamondsFound())
                                .build());
                    }
                }

                if (config.trackGemTypes() &! config.gemPrices()) {
                    if (plugin.getSession().getOpalsFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Opal: ")
                                .rightColor(config.cTextColor())
                                .right(Integer.toString(plugin.getSession().getOpalsFound()))
                                .build());
                    }
                    if (plugin.getSession().getJadesFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Jade: ")
                                .rightColor(config.cTextColor())
                                .right(Integer.toString(plugin.getSession().getJadesFound()))
                                .build());
                    }
                    if (plugin.getSession().getRedtopazFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Red Topaz: ")
                                .rightColor(config.cTextColor())
                                .right(Integer.toString(plugin.getSession().getRedtopazFound()))
                                .build());
                    }
                    if (plugin.getSession().getSapphiresFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Sapphire: ")
                                .rightColor(config.cTextColor())
                                .right(Integer.toString(plugin.getSession().getSapphiresFound()))
                                .build());
                    }
                    if (plugin.getSession().getEmeraldsFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Emerald: ")
                                .rightColor(config.cTextColor())
                                .right(Integer.toString(plugin.getSession().getEmeraldsFound()))
                                .build());
                    }
                    if (plugin.getSession().getRubiesFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Ruby: ")
                                .rightColor(config.cTextColor())
                                .right(Integer.toString(plugin.getSession().getRubiesFound()))
                                .build());
                    }
                    if (plugin.getSession().getDiamondsFound() > 0) {
                        panelComponent.getChildren().add(LineComponent.builder()
                                .leftColor(config.cTextColor())
                                .left("Diamond: ")
                                .rightColor(config.cTextColor())
                                .right(Integer.toString(plugin.getSession().getDiamondsFound()))
                                .build());
                    }
                }

                if (config.standAloneMode() || config.trackProfit() || config.trackXpHr()) {
                    panelComponent.getChildren().add(LineComponent.builder()
                            .build());
                }

                if (config.trackLoads()) {
                    panelComponent.getChildren().add(LineComponent.builder()
                            .leftColor(config.cTextColor())
                            .left("Total loads:")
                            .rightColor(config.cTextColor())
                            .right(String.format("%,d", gemTrackerSession.calcLoadsDone()))
                            .build());
                }

                if (config.standAloneMode()) {

                    panelComponent.getChildren().add(LineComponent.builder()
                            .leftColor(config.cTextColor())
                            .left("Gems mined:")
                            .rightColor(config.cTextColor())
                            .right(String.format("%,d", actions))
                            .build());

                    panelComponent.getChildren().add(LineComponent.builder()
                            .leftColor(config.cTextColor())
                            .left("Gems/hr:")
                            .rightColor(config.cTextColor())
                            .right(String.format("%,d", xpTrackerService.getActionsHr(Skill.MINING)))
                            .build());
                }

                if (config.trackProfit()) {
                    panelComponent.getChildren().add(LineComponent.builder()
                            .leftColor(config.cTextColor())
                            .left("Profit:")
                            .rightColor(config.cTextColor())
                            .right(String.format("%,d", plugin.getSession().calcTotalValue()))
                            .build());
                }

                if (config.trackXpHr()) {
                    panelComponent.getChildren().add(LineComponent.builder()
                            .leftColor(config.cTextColor())
                            .left("XP/hr:")
                            .rightColor(config.cTextColor())
                            .right(String.format("%,d", xpTrackerService.getXpHr(Skill.MINING)))
                            .build());
                }

            }
        }

        return super.render(graphics);
    }
}