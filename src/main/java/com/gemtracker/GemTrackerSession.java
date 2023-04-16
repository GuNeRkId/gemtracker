package com.gemtracker;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.client.game.ItemManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;

@Slf4j
@Singleton
public class GemTrackerSession
{
    @Getter
    @Setter
    public Instant gemFound;
    @Inject
    ItemManager itemManager;

    private final GemTrackerPlugin plugin;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int jadesFound;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int diamondsFound;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int rubiesFound;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int opalsFound;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int sapphiresFound;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int emeraldsFound;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private int redtopazFound;

    @Getter
    @Setter
    public int loadsDone;

    int profitDiamonds;
    int profitRubies;
    int profitEmeralds;
    int profitSapphires;
    int profitJades;
    int profitOpals;
    int profitRedTopaz;
    int tProfit = -1;

    @Getter
    public int currTotalGems;
    @Getter
    private Instant lastMined;



    @Inject
    public GemTrackerSession(ItemManager itemManager, GemTrackerPlugin plugin) {
        this.itemManager = itemManager;
        this.plugin = plugin;
    }

    void incrementGemFound(int gemID)
    {
        gemFound = Instant.now();

        switch (gemID)
        {
                case ItemID.UNCUT_DIAMOND:
                    diamondsFound++;
                    break;

                case ItemID.UNCUT_RUBY:
                    rubiesFound++;
                    break;

                case ItemID.UNCUT_EMERALD:
                    emeraldsFound++;
                    break;

                case ItemID.UNCUT_SAPPHIRE:
                    sapphiresFound++;
                    break;

                case ItemID.UNCUT_JADE:
                    jadesFound++;
                    break;

                case ItemID.UNCUT_OPAL:
                    opalsFound++;
                    break;

                case ItemID.UNCUT_RED_TOPAZ:
                    redtopazFound++;
                    break;

                default:
                    //log.debug("Invalid gem type specified. The gem count will not be incremented.");
                    System.out.println("Invalid gem type.");

        }
    }

    void setLastMined() {
        lastMined = Instant.now();
}

    public int calcLoadsDone () {

        currTotalGems = 0;

        int tGems = (diamondsFound + rubiesFound + emeraldsFound + sapphiresFound + jadesFound + opalsFound + redtopazFound);

        if (tGems > 0) {
            currTotalGems = tGems / 28;
            if (currTotalGems >= 1) {
                return Math.round(currTotalGems);
            }
        }
        return 0;
    }

    public int calcTotalValue() {

        int totalGems = (diamondsFound + rubiesFound + emeraldsFound + sapphiresFound + jadesFound + opalsFound + redtopazFound);

        if (totalGems > 0) {

            if (diamondsFound > 0) {
                profitDiamonds = (diamondsFound * itemManager.getItemPrice(ItemID.UNCUT_DIAMOND));
            }
            if (rubiesFound > 0) {
                profitRubies = (rubiesFound * itemManager.getItemPrice(ItemID.UNCUT_RUBY));
            }
            if (emeraldsFound > 0) {
                profitEmeralds = (emeraldsFound * itemManager.getItemPrice(ItemID.UNCUT_EMERALD));
            }
            if (sapphiresFound > 0) {
                profitSapphires = (sapphiresFound * itemManager.getItemPrice(ItemID.UNCUT_SAPPHIRE));
            }
            if (jadesFound > 0) {
                profitJades = (jadesFound * itemManager.getItemPrice(ItemID.UNCUT_JADE));
            }
            if (opalsFound > 0) {
                profitOpals = (opalsFound * itemManager.getItemPrice(ItemID.UNCUT_OPAL));
            }
            if (redtopazFound > 0) {
                profitRedTopaz = (redtopazFound * itemManager.getItemPrice(ItemID.UNCUT_RED_TOPAZ));
            }

            tProfit = (profitDiamonds + profitRubies + profitEmeralds + profitSapphires + profitJades + profitOpals + profitRedTopaz);

            return tProfit;
        }

        return 0;
    }

}
