package xyz.blurple.chatmsglib;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.blurple.chatmsglib.list.IntList;
import xyz.blurple.chatmsglib.list.JsonList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMLInit implements ModInitializer {

    public static String MODID = "chatmsglib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ChatMessage CML = new ChatMessage();
    private static final List<Integer> testInts = new ArrayList<>(
        Arrays.asList(
            1, 578, 436857, 2, 467, 9
        )
    );
    private static final String json = """
            {
                "name":"John",
                "age":30,
                "department": "IT",
                "skills": ["Java", "Python", "C++"],
                "address":{
                    "city":"New York",
                    "zip":10001
                },
                "phone_numbers":[
                    {
                        "type":"home",
                        "number":"123-456-7890"
                    },
                    {
                        "type":"work",
                        "number":"987-654-3210"
                    }
                ]
            }
            """;
    private static final JsonObject testJson = JsonParser.parseString(json).getAsJsonObject();
    private static final ChatMessage msg = CML
            .header("This is always be displayed at the top of the message", Formatting.AQUA)
            .footer("This is always be displayed at the bottom of the message", Formatting.BOLD, Formatting.BLUE)
            .literal("Just a literal string, nothing special", Formatting.BLUE)
            .literal("Option for an \"on\" statement as well",
                    new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help"))
            .object(
                    new IntList(testInts)
                            .prefix("What comes before the number is printed ")
                            .suffix(" What comes after the number is printed")
                            //.formatted(i -> i <= 0 ? Formatting.GREEN : Formatting.RED)
                            .create())
            .object(
                    new JsonList(testJson)
                            .prefix("- ")
                            .indent(1)
                            .create()
            )
            .object(
                    new Singleton()
                            .add(Text.literal("Singletons are used to display text in one line only"))
                            .add(
                                    Text.literal("Also, commands in-line as well")
                                        .styled(style -> style.withClickEvent(
                                                new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help")
                                        ))
                            )
                            .create()
            );

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {

            CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                dispatcher.register(
                    CommandManager.literal("cml")
                    .executes(msg::send)
                )
            );
            LOGGER.info("command registered");
        }
    }
}
