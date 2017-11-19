package me.refraction.oof;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

/**
 * Created by Refraction on 18/11/2017.
 */
public class Commands extends CommandBase {

    @Override
    public String getCommandName() {
        return "oofnick";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/oofnick <nick | reset>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        oof oof = new oof();
        if(args.length == 0) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Usage: /oofnick <nick | reset>"));
        }
        else if(args.length > 1) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.RED + "Usage: /oofnick <nick | reset>"));
        }
        else {
            if(args[0].equals("reset")) {
                oof.resetNick();
                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "[oof] " + EnumChatFormatting.GREEN + "Your nick has been reset."));
            }
            else {
                oof.storeNick(args[0]);
                Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "[oof] " + EnumChatFormatting.GREEN + "Will now listen for chat messages with the nickname '" + args[0] + "'."));
            }
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
}
