package me.refraction.oof;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tv.twitch.chat.Chat;

import java.io.*;
import java.util.ArrayList;

@Mod(modid = oof.MODID, version = oof.VERSION, name = oof.NAME)
public class oof
{
    public static final String MODID = "refractionoof";
    public static final String VERSION = "1.0";
    public static final String NAME = "oof";

    private String nickData;
    private String msg;
    private ArrayList<String> deathMessages = new ArrayList<String>();
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand((ICommand)new Commands());
        populateDeathMessages();
    }

    @SubscribeEvent
    public void onChatReceived(ClientChatReceivedEvent e) {
        msg = e.message.getUnformattedText();

        // checks if msg is nothing, if yes display it (for some reason it wasn't getting displayed without this??)
        if(msg.trim().length() == 0 || msg.equals("") || msg == null) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(" "));
        }

        // checks if msg is a divider (e.g. -----------), if yes display it (also wasn't getting displayed without this???)
        // also this returns the divider in green (&a) always...might wanna change that if I figure out how
        boolean div = true;
        char c1 = msg.charAt(0);
        if(c1 != '-') {
            div = false;
        }
        for(int i = 1; i < msg.length(); i++) {
            char temp = msg.charAt(i);
            if(c1 != temp) {
                div = false;
            }
        }
        if(div) {
            Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GREEN + msg));
        }

        ArrayList<String> formattedDeathMessages = new ArrayList<String>();
        ArrayList<String> nickedFormattedDeathMessages = new ArrayList<String>();
         for(String m : deathMessages) {
             formattedDeathMessages.add(m.replace("PLAYER", Minecraft.getMinecraft().thePlayer.getName()));
         }
         if(getNick() != "null") {
             for(String m : deathMessages) {
                 nickedFormattedDeathMessages.add(m.replace("PLAYER", getNick()));
             }
         }

         String[] compare = msg.split(" ", 2);
         // check below if death message is like "was shot by PLAYER (XX)"
//         boolean isSnipe = false;
//         if(compare[1].matches("was shot by " + Minecraft.getMinecraft().thePlayer.getName() +" (.*)") || compare[1].matches(".*(.*).")) {
//             isSnipe = true;
//         }
         for(String m : formattedDeathMessages) {
             if(compare[1].equals(m)) {
                 Minecraft.getMinecraft().thePlayer.playSound("refractionoof:oof", 5.0f, 1);
             }
         }

        if(getNick() != "null") {
            for(String m : nickedFormattedDeathMessages) {
                if(compare[1].equals(m)) {
                    Minecraft.getMinecraft().thePlayer.playSound("refractionoof:oof", 5.0f, 1);
                }
            }
        }

         if(msg.equals("You have finished setting up your nickname!")) {
             Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "[oof] " + EnumChatFormatting.GREEN + "Don't forget to /oofnick with your current nickname in order for the oof mod to work properly."));
         }

         if(msg.equals("Your nick has been reset!")) {
             Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(EnumChatFormatting.GOLD + "[oof] " + EnumChatFormatting.GREEN + "Automatically detected /nick reset and reset your oof nick data."));
             resetNick();
         }

    }

    public void populateDeathMessages() {
        deathMessages.add("was slain by PLAYER");
        deathMessages.add("was shot by PLAYER");
        deathMessages.add("was thrown into the void by PLAYER");
        deathMessages.add("was doomed to fall by PLAYER");
        deathMessages.add("was toasted by PLAYER");
        deathMessages.add("was killed by PLAYER");
        deathMessages.add("was bomberman'd by PLAYER");
        deathMessages.add("was Bomberman'd by PLAYER");
        deathMessages.add("was thrown off a cliff by PLAYER");
        deathMessages.add("was shot and killed by PLAYER");
        deathMessages.add("was snowballed to death by PLAYER");
        deathMessages.add("was killed with a potion by PLAYER");
        deathMessages.add("was killed with an explosion by PLAYER");
        deathMessages.add("was killed with magic by PLAYER");
        deathMessages.add("was filled full of lead by PLAYER");
        deathMessages.add("was iced by PLAYER");
        deathMessages.add("met their end by PLAYER");
        deathMessages.add("lost a drinking contest with PLAYER");
        deathMessages.add("was killed with dynamite by PLAYER");
        deathMessages.add("lost the draw to PLAYER");
        deathMessages.add("was struck down by PLAYER");
        deathMessages.add("was turned to dust by PLAYER");
        deathMessages.add("was turned to ash by PLAYER");
        deathMessages.add("was melted by PLAYER");
        deathMessages.add("was incinerated by PLAYER");
        deathMessages.add("was vaporized by PLAYER");
        deathMessages.add("was struck with Cupid's arrow by PLAYER");
        deathMessages.add("was given the cold shoulder by PLAYER");
        deathMessages.add("was hugged too hard by PLAYER");
        deathMessages.add("drank a love potion from PLAYER");
        deathMessages.add("was hit by a love bomb from PLAYER");
        deathMessages.add("was no match for PLAYER");
        deathMessages.add("was smote from afar by PLAYER");
        deathMessages.add("was justly ended by PLAYER");
        deathMessages.add("was purified by PLAYER");
        deathMessages.add("was killed with holy water by PLAYER");
        deathMessages.add("was dealt vengeful justice by PLAYER");
        deathMessages.add("was returned to dust by PLAYER");
        deathMessages.add("be shot and killed by PLAYER");
        deathMessages.add("be snowballed to death by PLAYER");
        deathMessages.add("be sent to Davy Jones' locker by PLAYER");
        deathMessages.add("be killed with rum by PLAYER");
        deathMessages.add("be shot with cannon by PLAYER");
        deathMessages.add("be killed with magic by PLAYER");
        deathMessages.add("was glazed in BBQ sauce by PLAYER");
        deathMessages.add("was sprinkled with chilli powder by PLAYER");
        deathMessages.add("was sprinkled with chili powder by PLAYER");
        deathMessages.add("was sliced up by PLAYER");
        deathMessages.add("was overcooked by PLAYER");
        deathMessages.add("was deep fried by PLAYER");
        deathMessages.add("was boiled by PLAYER");
        deathMessages.add("was spooked by PLAYER");
        deathMessages.add("was spooked off the map by PLAYER");
        deathMessages.add("was totally spooked by PLAYER");
        deathMessages.add("was remotely spooked by PLAYER");
        deathMessages.add("was slain by PLAYER.");
        deathMessages.add("was shot by PLAYER.");
        deathMessages.add("was thrown into the void by PLAYER.");
        deathMessages.add("was doomed to fall by PLAYER.");
        deathMessages.add("was toasted by PLAYER.");
        deathMessages.add("was killed by PLAYER.");
        deathMessages.add("was bomberman'd by PLAYER.");
        deathMessages.add("was Bomberman'd by PLAYER.");
        deathMessages.add("was thrown off a cliff by PLAYER.");
        deathMessages.add("was shot and killed by PLAYER.");
        deathMessages.add("was snowballed to death by PLAYER.");
        deathMessages.add("was killed with a potion by PLAYER.");
        deathMessages.add("was killed with an explosion by PLAYER.");
        deathMessages.add("was killed with magic by PLAYER.");
        deathMessages.add("was filled full of lead by PLAYER.");
        deathMessages.add("was iced by PLAYER.");
        deathMessages.add("met their end by PLAYER.");
        deathMessages.add("lost a drinking contest with PLAYER.");
        deathMessages.add("was killed with dynamite by PLAYER.");
        deathMessages.add("lost the draw to PLAYER.");
        deathMessages.add("was struck down by PLAYER.");
        deathMessages.add("was turned to dust by PLAYER.");
        deathMessages.add("was turned to ash by PLAYER.");
        deathMessages.add("was melted by PLAYER.");
        deathMessages.add("was incinerated by PLAYER.");
        deathMessages.add("was vaporized by PLAYER.");
        deathMessages.add("was struck with Cupid's arrow by PLAYER.");
        deathMessages.add("was given the cold shoulder by PLAYER.");
        deathMessages.add("was hugged too hard by PLAYER.");
        deathMessages.add("drank a love potion from PLAYER.");
        deathMessages.add("was hit by a love bomb from PLAYER.");
        deathMessages.add("was no match for PLAYER.");
        deathMessages.add("was smote from afar by PLAYER.");
        deathMessages.add("was justly ended by PLAYER.");
        deathMessages.add("was purified by PLAYER.");
        deathMessages.add("was killed with holy water by PLAYER.");
        deathMessages.add("was dealt vengeful justice by PLAYER.");
        deathMessages.add("was returned to dust by PLAYER.");
        deathMessages.add("be shot and killed by PLAYER.");
        deathMessages.add("be snowballed to death by PLAYER.");
        deathMessages.add("be sent to Davy Jones' locker by PLAYER.");
        deathMessages.add("be killed with rum by PLAYER.");
        deathMessages.add("be shot with cannon by PLAYER.");
        deathMessages.add("be killed with magic by PLAYER.");
        deathMessages.add("was glazed in BBQ sauce by PLAYER.");
        deathMessages.add("was sprinkled with chilli powder by PLAYER.");
        deathMessages.add("was sprinkled with chili powder by PLAYER.");
        deathMessages.add("was sliced up by PLAYER.");
        deathMessages.add("was overcooked by PLAYER.");
        deathMessages.add("was deep fried by PLAYER.");
        deathMessages.add("was boiled by PLAYER.");
        deathMessages.add("was spooked by PLAYER.");
        deathMessages.add("was spooked off the map by PLAYER.");
        deathMessages.add("was totally spooked by PLAYER.");
        deathMessages.add("was remotely spooked by PLAYER.");
    }

    public void storeNick(String nick) {
        final File nickData = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "oof", "nickData.txt");
        try {
            if (!nickData.exists()) {
                nickData.getParentFile().mkdirs();
                nickData.createNewFile();
            }
            final BufferedWriter writer = new BufferedWriter(new FileWriter(nickData));
            writer.write(nick);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void resetNick() {
        final File nickData = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "oof", "nickData.txt");
        try {
            if (!nickData.exists()) {
                nickData.getParentFile().mkdirs();
                nickData.createNewFile();
            }
            final BufferedWriter writer = new BufferedWriter(new FileWriter(nickData));
            writer.write("null");
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        try {
            final File file = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "oof", "nickData.txt");
            if (!file.exists()) {
                return "null";
            }
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                switch (++i) {
                    case 1: {
                        nickData = line;
                    }
                }
            }
            reader.close();
            return nickData;
        }
        catch (Throwable e) {
            e.printStackTrace();
            return "null";
        }
    }

}
