package xyz.blurple.chatmsglib;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.ClickEvent;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.blurple.chatmsglib.list.IntList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMLInit implements ModInitializer {

    public static String MODID = "chatmsglib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ChatMessage CML = new ChatMessage();
    private static ChatMessage msg;
    private static final List<Integer> outputList = new ArrayList<>(
        Arrays.asList(
            1, 578, 436857, 2, 467, 9
        )
    );

    static {
        try {
            msg = CML
                    .header("This is always be displayed at the top of the message", Formatting.AQUA)
                    .footer("This is always be displayed at the bottom of the message", Formatting.BOLD, Formatting.BLUE)
                    .literal("Just a literal string, nothing special", Formatting.BLUE)
                    .literal("Option for an \"on\" statement as well",
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help"))
                    .object(
                            new IntList(outputList)
                                    .prefix("What comes before the number is printed")
                                    .suffix("What comes after the number is printed")
                                    //.formatted(i -> i <= 0 ? Formatting.GREEN : Formatting.RED)
                                    .create());
        } catch (IllegalAccessException ignored) {}
    }

    @Override
    public void onInitialize() {
        LOGGER.info("started");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(
                CommandManager.literal("cml")
                .executes(c -> {
                    if (c.getSource().getEntity() != null) {
                        msg.send(c.getSource().getEntityOrThrow());
                        return 0;
                    } else {
                        LOGGER.error("Source is not an entity.");
                        return 1;
                    }
                })
            )
        );

        LOGGER.info("registered");
    }
}
