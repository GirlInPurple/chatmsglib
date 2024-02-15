package xyz.blurple.chatmsglib;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A custom {@link StringBuilder} fork for {@link Text}.
 * The Wiki/Javadoc is located <a href="">here</a>
 * */
public class ChatMessage {

    private List<Text> components = new ArrayList<>();
    private Text head;
    private Text foot;

    /**
     * A {@code literal} that is always printed at the top of the message.
     * */
    public ChatMessage header(String text) {
        head = Text.literal(text);
        return this;
    }

    public ChatMessage header(String text, Formatting... formattings) {
        head = Text.literal(text).formatted(formattings);
        return this;
    }

    public ChatMessage header(String text, ClickEvent clickEvent, Formatting... formattings) {
        head = Text.literal(text).formatted(formattings).styled(style -> style.withClickEvent(clickEvent));
        return this;
    }

    /**
     * A {@code literal} that is always printed at the bottom of the message.
     * */
    public ChatMessage footer(String text) {
        foot = Text.literal(text);
        return this;
    }

    public ChatMessage footer(String text, Formatting... formattings) {
        foot = Text.literal(text).formatted(formattings);
        return this;
    }

    public ChatMessage footer(String text, ClickEvent clickEvent, Formatting... formattings) {
        foot = Text.literal(text).formatted(formattings).styled(style -> style.withClickEvent(clickEvent));
        return this;
    }

    public ChatMessage literal(String text) {
        components.add(Text.literal(text));
        return this;
    }

    public ChatMessage literal(String text, Formatting... formattings) {
        components.add(Text.literal(text).formatted(formattings));
        return this;
    }

    public ChatMessage literal(String text, ClickEvent clickEvent, Formatting... formattings) {
        components.add(Text.literal(text).formatted(formattings).styled(style -> style.withClickEvent(clickEvent)));
        return this;
    }

    public ChatMessage translatable(String translation, Object... args) {
        components.add(Text.translatable(translation, args));
        return this;
    }

    public ChatMessage translatable(String translation, ClickEvent clickEvent, Object... args) {
        components.add(Text.translatable(translation, args).styled(style -> style.withClickEvent(clickEvent)));
        return this;
    }

    public ChatMessage formatted(Formatting formatting) {
        if (!components.isEmpty()) {
            MutableText lastComponent = (MutableText) components.get(components.size() - 1);
            lastComponent.styled(style -> style.withFormatting(formatting));
        }
        return this;
    }

    public ChatMessage formatted(Function<Integer, Formatting> formatter) {
        MutableText lastComponent = (MutableText) components.get(components.size() - 1);
        if (lastComponent != null) {
            components.set(components.size() - 1, Text.literal(((Text) lastComponent).getContent().toString())
                    .styled(style -> style.withFormatting(formatter.apply(Integer.parseInt(((Text) lastComponent).getContent().toString())))));
        }
        return this;
    }


    public ChatMessage object(List<Text> text) {
        components.addAll(text);
        return this;
    }

    public ChatMessage object(Text text) {
        components.add(text);
        return this;
    }

    public void send(Entity e) {
        if (head != null) {e.sendMessage(head);}
        for (Text t : components) {
            e.sendMessage(t);
        }
        if (foot != null) {e.sendMessage(foot);}
    }

    public int send(CommandContext<ServerCommandSource> c) throws CommandSyntaxException {
        send(c.getSource().getEntityOrThrow());
        return 0;
    }
}

