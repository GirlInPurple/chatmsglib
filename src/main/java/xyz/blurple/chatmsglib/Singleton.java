package xyz.blurple.chatmsglib;

import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

/**
 * All the appended text is displayed on the same line.
 * Uses {@link MutableText} in the backend, use that if you need a more complex syntax.
 * */
public class Singleton {
    MutableText t;

    public Singleton() {
        this.t = Text.empty();
    }

    public Singleton add(Text inputText) {
        this.t.append(inputText);
        this.t.append(ScreenTexts.SPACE);
        return this;
    }

    public Text create() {
        return this.t;
    }
}
