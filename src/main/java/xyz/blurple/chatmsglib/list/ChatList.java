package xyz.blurple.chatmsglib.list;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

import static xyz.blurple.chatmsglib.CMLInit.LOGGER;

public abstract class ChatList {

    String pre;
    String suf;
    Formatting form = Formatting.WHITE;

    public ChatList prefix(String prefix) {
        this.pre = prefix;
        return this;
    }

    public ChatList suffix(String suffix) {
        this.suf = suffix;
        return this;
    }

    public ChatList indent(int indent) {
        LOGGER.error("Cannot use indent inside abstract ChatList. Extend the class and overwrite this method.");
        return null;
    }

    public ChatList formatted(Formatting format) {
        this.form = format;
        return this;
    }

    public List<Text> create(){
        LOGGER.error("Cannot use create inside abstract ChatList. Extend the class and overwrite this method.");
        return null;
    }
}
