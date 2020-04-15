package apple.npc.reading.command;

import apple.npc.reading.Reading;

/**
 * abstract to match ReadingText
 */
public abstract class ReadingCommand implements Reading {
    protected String command = "";

    public void setCommand(String string) {
        this.command = string;
    }

}
