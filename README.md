# Chat Message Library

I was annoyed that there was no [`java.lang.StringBuilder`](https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html) equivalent for `net.minecraft.Text`, so I decided to built my own.

## For Users;

This is a library mod, and only adds a single command, `/cml`, used as an example command.\
Download the mod from [Modrinth]() (or compile it on your own) and drop it in your `.minecraft/mods/` folder.

## For Developers;

To use the library, add this to your `build.gradle`, `gradle.properties`, and your mod's `ModInitializer` class:

```groovy
repositories {
    maven { url = "https://api.modrinth.com/maven" }
}

dependencies {
    modApi include("maven.modrinth:chatmsglib:${project.cml_version}")
}
```
```properties
# Dependencies

# This may be out of date!!
cml_version=1.0
```
```java
public class YourMod implements ModInitializer {
    
    public static final ChatMessage CML = new ChatMessage();
    
    /*
    * This text is generated at startup and is held until server stop
    * To send it, use msg::send or msg.send(PlayerEntity)
    * You can also modify it on the fly by adding more text
    */
    private static final ChatMessage msg = CML
            .header("This is always be displayed at the top of the message", Formatting.AQUA)
            .footer("This is always be displayed at the bottom of the message", Formatting.BOLD, Formatting.BLUE)
            .literal("Just a literal string, nothing special", Formatting.BLUE)
            .literal("Option for an \"on\" statement as well",
                    new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/help"))
            .object(
                    // A List<Integer> turned into Text form
                    new IntList(yourInts)
                            // No spaces are added for Prefixes and Suffixes and must be added manually!
                            .prefix("What comes before the number is printed ")
                            .suffix(" What comes after the number is printed")
                            .create())
            .object(
                    // A JsonObject turned into Text form
                    new JsonList(yourJson)
                            .prefix("- ")
                            .indent(1)
                            .create()
            )
            .object(
                    /*
                    * You can create your own lists by extending xyz.blurple.list.ChatList
                    * It has to output List<Text>, everything else is your choice!
                    */
                    new YourList(YourInput)
                            .prefix("= ")
                            .create()
            );

    @Override
    public void onInitialize() {
        
        // This command simply prints out msg with the formatting you chose
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(
                CommandManager.literal("your_command")
                    .executes(msg::send)
            )
        );
    }
}
```

If you have a request or issue with the library, make an issue, and I'll take a shot at adding it.\
Feel free to do anything you like with this code; fork, learn, whatever you like.
