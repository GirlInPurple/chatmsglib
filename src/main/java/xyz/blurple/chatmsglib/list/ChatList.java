package xyz.blurple.chatmsglib.list;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

import static xyz.blurple.chatmsglib.CMLInit.LOGGER;

public abstract class ChatList {

    public String pre = "";
    public String suf = "";
    public Formatting form = Formatting.WHITE;

    public ChatList prefix(String prefix) {
        if (pre == null) {
            pre = prefix;
            return this;
        } else {
            LOGGER.error("Cannot set \"prefix\" value more than once.");
            return this;
        }
    }

    public ChatList suffix(String suffix) {
        if (suf == null) {
            suf = suffix;
            return this;
        } else {
            LOGGER.error("Cannot set \"suffix\" value more than once.");
            return this;
        }
    }

    public ChatList indent(int indent) {
        LOGGER.error("Cannot use indent inside abstract ChatList. Extend the class and overwrite this method.");
        return null;
    }

    public ChatList formatted(Formatting format) {
        if (form == null) {
            form = format;
            return this;
        } else {
            LOGGER.error("Cannot set \"format\" value more than once.");
            return this;
        }
    }

    public List<Text> create(){
        LOGGER.error("Cannot use create inside abstract ChatList. Extend the class and overwrite this method.");
        return null;
    }
}
