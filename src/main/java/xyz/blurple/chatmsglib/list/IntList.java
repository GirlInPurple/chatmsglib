package xyz.blurple.chatmsglib.list;

import net.minecraft.text.Text;

import java.util.List;
import java.util.stream.Collectors;

public class IntList extends ChatList{
    List<Integer> outputList;

    public IntList(List<Integer> outputList) {
        this.outputList = outputList;
    }

    @Override
    public List<Text> create() {
        return outputList.stream()
                .map(i -> Text.literal(pre + i + suf).formatted(form))
                .collect(Collectors.toList());
    }
}
