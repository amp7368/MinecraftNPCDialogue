package apple.npc.reading.command;

import apple.npc.reading.Reading;

/**
 * abstract to match ReadingText
 */
public abstract class ReadingCommand extends Reading {

    public abstract void setCommand(String message);
}
