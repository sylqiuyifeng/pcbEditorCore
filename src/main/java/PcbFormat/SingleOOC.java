package PcbFormat;

import static PcbFormat.CommandUtil.colorBlackTech;
import static PcbFormat.CommandUtil.escape;

/**
 * Created by pca006132 on 2016/5/5.
 */
public class SingleOOC {
    //constants
    final String prefix = "/summon FallingSand ~ ~1 ~2 " +
            "{Time:1,Block:\"minecraft:redstone_block\",Passengers:" +
            "[{id:\"FallingSand\",Time:1,Block:\"minecraft:activator_rail\"" +
            ",Passengers:[";
    final String suffix = "{id:\"MinecartCommandBlock\",Command:\"" +
            "/setblock ~ ~-1 ~ minecraft:lava 15\"},{id:\"MinecartCommandBlock\",Command:\"setblock ~ ~ ~ air 0\"}]}]}";
    final String cmdPrefix = "{id:\"MinecartCommandBlock\",Command:\"";
    final int prefixLength = prefix.length();
    final int colorPrefixLength = escape(escape(prefix)).length();
    final int cmdPrefixLength = cmdPrefix.length();
    final int colorCmdPrefixLength = escape(escape(cmdPrefix)).length();
    //end constants

    private boolean useColorBlackTech;
    private StringBuilder cmd = new StringBuilder();
    private int normalLength = prefixLength;
    private int colorModeLength = colorPrefixLength;
    private boolean oocGenerated = false;

    public SingleOOC() {
        cmd.append(prefix);
    }

    public void addCommand(String command) throws IllegalAccessError {
        if (oocGenerated)
            throw new IllegalAccessError();
        if (command.contains("§"))
            useColorBlackTech = true;
        cmd.append(cmdPrefix);
        cmd.append(escape(command));
        cmd.append("\"},");
        colorModeLength += escape(escape(command)).length() + colorCmdPrefixLength + 4;
        normalLength += escape(command).length() + cmdPrefixLength + 3;
    }
    public String getOOC() {
        if (useColorBlackTech)
            return getColorOOC();
        else
            return getNormalOOC();
    }
    public boolean canAddCommand(String command) throws PcbParseException {
        if (escape(command).length() > 30000)
            throw new PcbParseException("单一命令过长");
        if (useColorBlackTech || command.contains("§")) {
            if (colorModeLength + escape(escape(command)).length() + colorCmdPrefixLength + 4 > 27000)
                return false;
            else
                return true;
        } else {
            if (normalLength + escape(command).length() + cmdPrefixLength + 3 > 27000)
                return false;
            else
                return true;
        }
    }

    String getNormalOOC() {
        if (oocGenerated)
            return cmd.toString();
        else {
            cmd.append(suffix);
            oocGenerated = true;
            return cmd.toString();
        }
    }
    String getColorOOC() {
        if (oocGenerated)
            return colorBlackTech(cmd.toString());
        else {
            cmd.append(suffix);
            oocGenerated = true;
            return colorBlackTech(cmd.toString());
        }
    }
}