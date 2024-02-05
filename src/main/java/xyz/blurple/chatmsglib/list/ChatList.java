package xyz.blurple.chatmsglib.list;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static xyz.blurple.chatmsglib.CMLInit.*;

public abstract class ChatList {

    public List<Text> components = new ArrayList<>();
    public String pre;
    public String suf;
    public Formatting form;

    public ChatList prefix(String prefix) {
        if (pre == null) {
            pre = prefix;
            return this;
        } else {
            throw new IllegalArgumentException("Cannot set \"prefix\" value more than once.");
        }
    }

    public ChatList suffix(String suffix) {
        if (suf == null) {
            suf = suffix;
            return this;
        } else {
            throw new IllegalArgumentException("Cannot set \"suffix\" value more than once.");
        }
    }

    public ChatList formatted(Formatting format) {
        if (form == null) {
            form = format;
            return this;
        } else {
            throw new IllegalArgumentException("Cannot set \"format\" value more than once.");
        }
    }

    public ChatList formatted(Function<Integer, Formatting> formatter) {
        MutableText lastComponent = (MutableText) components.get(components.size() - 1);
        if (lastComponent != null) {
            components.set(components.size() - 1, Text.literal(((Text) lastComponent).getContent().toString())
                    .styled(style -> style.withFormatting(formatter.apply(Integer.parseInt(((Text) lastComponent).getContent().toString())))));
        }
        return this;
    }

    public List<Text> create() throws IllegalAccessException {
        LOGGER.error("Cannot use create inside abstract ChatList. Extend the class and overwrite this method.");
        return null;
    }
}
